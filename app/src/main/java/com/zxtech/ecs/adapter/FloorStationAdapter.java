package com.zxtech.ecs.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.FloorStationParam;

import java.util.List;

/**
 * Created by syp523 on 2018/4/3.
 */

public class FloorStationAdapter extends BaseQuickAdapter<FloorStationParam, BaseViewHolder> {

    public FloorStationAdapter(int layoutResId, @Nullable List<FloorStationParam> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, FloorStationParam floorStationParam) {
        holder.setText(R.id.floor_height_tv, floorStationParam.getHeight() + "");
        holder.setText(R.id.engineering_floor_tv, floorStationParam.getEngineeringFloor() + "");
        holder.setText(R.id.identifying_floor_tv, floorStationParam.getIdentifyingFloor() + "");
        holder.setText(R.id.service_a_tv, floorStationParam.getServiceA());
        holder.setText(R.id.service_c_tv, floorStationParam.getServiceC());
        holder.addOnClickListener(R.id.service_a_tv).addOnClickListener(R.id.service_c_tv).addOnClickListener(R.id.floor_height_tv).addOnClickListener(R.id.identifying_floor_tv);
    }
}
