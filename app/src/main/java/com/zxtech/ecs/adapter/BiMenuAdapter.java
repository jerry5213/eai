package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;

import java.util.List;

/**
 * Created by syp523 on 2018/3/23.
 */

public class BiMenuAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private int[] images = new int[]{R.drawable.icon_statistics, R.drawable.icon_capacity, R.drawable.icon_activities,R.drawable.icon_trend};


    public BiMenuAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.title_tv, item);
        helper.setImageResource(R.id.icon, images[helper.getAdapterPosition()]);
    }
}
