package com.zxtech.is.ui.civilreview.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.R2;

import java.util.List;
import java.util.Map;

/**
 * Created by syp660 on 2018/4/23.
 */

public class CivilReviewAdapter extends BaseQuickAdapter<Map<String, Object>, BaseViewHolder> {

    public CivilReviewAdapter(int layoutResId, @Nullable List<Map<String, Object>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, Object> item) {
        helper.setText(R.id.item_civil_review_a, String.valueOf(item.get("prjName"))) //项目名称
                .setText(R.id.item_civil_review_b, String.valueOf(item.get("eleName")))// 电梯名称
                .setText(R.id.item_civil_review_c, String.valueOf(item.get("confirmTime")));
        if ("".equals(String.valueOf(item.get("confirmUsr")))) {
            helper.setGone(R.id.item_civil_review_d, true).setText(R.id.item_civil_review_d, "已完成");
        } else {
            helper.setText(R.id.item_civil_review_d, String.valueOf(item.get("userName"))).setText(R.id.item_civil_review_d, "审核中");
        }
    }
}
