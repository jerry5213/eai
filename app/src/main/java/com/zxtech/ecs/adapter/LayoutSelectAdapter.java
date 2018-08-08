package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.LayoutSelect;

import java.util.List;

/**
 * Created by syp523 on 2018/4/5.
 */

public class LayoutSelectAdapter extends BaseQuickAdapter<LayoutSelect, BaseViewHolder> {
    public LayoutSelectAdapter(int layoutResId, @Nullable List<LayoutSelect> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LayoutSelect item) {

        helper.setText(R.id.title_tv, item.getTitle());
        helper.setText(R.id.value_tv, item.getValue()).addOnClickListener(R.id.value_tv);
    }
}
