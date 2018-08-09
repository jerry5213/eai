package com.zxtech.is.ui.check.adaper;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.check.SecurityCheck;

import java.util.List;

/**
 * Created by hsz on 2018/4/26.
 */

public class SecurityCheckAdaper extends BaseQuickAdapter<SecurityCheck, BaseViewHolder> {
    public SecurityCheckAdaper(int layoutResId, @Nullable List<SecurityCheck> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SecurityCheck securityCheck) {

        helper.setText(R.id.item_security_check_text_0, securityCheck.getIsMstChkSRowNumber() + "、" + securityCheck.getIsMstChkSItemContent());
        //如果有课件则显示播放按钮
        if(securityCheck.getIsMstChkSCourseGuid()!=null&&securityCheck.getIsMstChkSCourseGuid()!=""){
            helper.setVisible(R.id.item_class_play,true);
        }
        if (securityCheck.getIsMstChkSRequired() != null
                && securityCheck.getIsMstChkSRequired().equals("1")) {
            helper.setTextColor(R.id.item_security_check_text_0, Color.parseColor("#FEB300"));
        }


        helper.addOnClickListener(R.id.item_security_check_right_but);
        helper.addOnClickListener(R.id.item_security_check_wrong_but);
        helper.addOnClickListener(R.id.item_class_play);

        if (securityCheck.getIsCkSecurityItemBeHistoryFlag() != null
                && securityCheck.getIsCkSecurityItemBeHistoryFlag().equals("1")) {
            if (securityCheck.getIsCkSecurityItemResult() != null) {
                if (securityCheck.getIsCkSecurityItemResult().equals("1")) {
                    ((ImageView) helper.getView(R.id.item_security_check_default_but)).setImageResource(R.drawable.is_right_blue);
                } else if (securityCheck.getIsCkSecurityItemResult().equals("0")) {
                    ((ImageView) helper.getView(R.id.item_security_check_default_but)).setImageResource(R.drawable.is_wrong_red);
                }
            }
        } else {
            helper.getView(R.id.item_security_check_default_but).setVisibility(View.GONE);
            helper.getView(R.id.item_security_check_imageview_linearlayout)
                    .setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 4.0f));
            helper.getView(R.id.item_security_check_textview_linearlayout)
                    .setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        }
    }
}
