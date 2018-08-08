package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;

import java.util.List;

/**
 * Created by syp523 on 2018/3/23.
 */

public class ToolMenuAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private int[] images = new int[]{R.drawable.menu_layout_scheme, R.drawable.menu_decorating_matching, R.drawable.menu_flow_analysis, R.drawable.menu_ladder_configuration};


    public ToolMenuAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.title_tv, item);
        helper.setImageResource(R.id.icon, images[helper.getAdapterPosition()]);
    }
}
