package com.zxtech.is.ui.check.adaper;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.check.QualityCheck;

import java.util.List;

/**
 * Created by hsz on 2018/4/21.
 */

public class QualityCheckAdaper extends BaseQuickAdapter<QualityCheck, BaseViewHolder> {
    public QualityCheckAdaper(int layoutResId, @Nullable List<QualityCheck> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QualityCheck qualityCheck) {

        helper.setText(R.id.item_quality_check_text_0, qualityCheck.getIsMstChkQRowNumber() + "、" + qualityCheck.getIsMstChkQItemContent());
        //如果有课件则显示播放按钮
        if(qualityCheck.getIsMstChkQCourseGuid()!=null&&qualityCheck.getIsMstChkQCourseGuid()!=""){
            helper.setVisible(R.id.item_class_play,true);
        }
        if (qualityCheck.getIsMstChkQRequired() != null
                && qualityCheck.getIsMstChkQRequired().equals("1")) {
            helper.setTextColor(R.id.item_quality_check_text_0, Color.parseColor("#FEB300"));
        }

        helper.addOnClickListener(R.id.item_quality_check_right_but);
        helper.addOnClickListener(R.id.item_quality_check_wrong_but);
        helper.addOnClickListener(R.id.item_class_play);

        if (qualityCheck.getIsCkQualityItemResult() != null) {
            if (qualityCheck.getIsCkQualityItemResult().equals("1")) {
                helper.getView(R.id.item_quality_check_right_but).setSelected(true);
            } else if (qualityCheck.getIsCkQualityItemResult().equals("0")) {
                helper.getView(R.id.item_quality_check_wrong_but).setSelected(true);
            }
        }
    }
}

