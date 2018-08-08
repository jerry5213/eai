package com.zxtech.ecs.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ReduceConfig;

import java.util.List;

/**
 * Created by syp523 on 2018/3/6.
 */

public class ReduceConfigDetailAdapter extends CommonAdapter<ReduceConfig.ReduceParam>{
    public ReduceConfigDetailAdapter(Context context, int layoutId, List<ReduceConfig.ReduceParam> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ReduceConfig.ReduceParam rp, int position) {
        holder.setText(R.id.param_name_tv,rp.getParamName());
        holder.setText(R.id.change_before_tv,rp.getChangeBefore());
        holder.setText(R.id.change_after_tv,rp.getChangeAfater());


    }
}
