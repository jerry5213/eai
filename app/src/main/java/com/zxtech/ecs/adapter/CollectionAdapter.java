package com.zxtech.ecs.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.Collection;
import com.zxtech.ecs.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by syp523 on 2018/1/24.
 */

public class CollectionAdapter extends CommonAdapter<Collection> {
    private HashMap<Integer, String> map = new HashMap<>();
    private List<Integer> selectList = new ArrayList<>();
    private OnclickCallBack callBack;

    public CollectionAdapter(Context context, int layoutId, List<Collection> datas) {
        super(context, layoutId, datas);
        map.put(2, context.getResources().getString(R.string.low));
        map.put(4, context.getResources().getString(R.string.low_medium));
        map.put(6, context.getResources().getString(R.string.medium));
        map.put(8, context.getResources().getString(R.string.medium_high));
        map.put(10, context.getResources().getString(R.string.high));
        map.put(1, context.getResources().getString(R.string.mr));
        map.put(0, context.getResources().getString(R.string.mrl));
        map.put(100, context.getResources().getString(R.string.passenger_elevator));
        map.put(101, context.getResources().getString(R.string.house_elevator));
    }

    @Override
    protected void convert(ViewHolder holder, Collection collection, final int position) {
        holder.setText(R.id.collection_tv, collection.getCollectionName());
        if ("家用电梯".equals(collection.getElevatorProduct())) {
            holder.setText(R.id.elevator_type_tv, map.get(101));
        }else{
            holder.setText(R.id.elevator_type_tv, map.get(100));
        }
        TextView room_tv = holder.getView(R.id.room_tv);
        if (collection.isHaveHome()) {
            room_tv.setText(map.get(1));
        } else {
            room_tv.setText(map.get(0));
        }
        holder.setText(R.id.width_tv, String.valueOf(collection.getHW()));
        holder.setText(R.id.depth_tv, String.valueOf(collection.getHD()));
        holder.setText(R.id.floor_tv, String.valueOf(collection.getFloorCountNum()));
        holder.setText(R.id.ssd_tv, map.get(collection.getComfortLevel()));
        holder.setText(R.id.aqdj_tv, map.get(collection.getEnergyLevel()));
        holder.setText(R.id.czfwld_tv, map.get(collection.getAesthetic()));
        holder.setText(R.id.mgd_tv, map.get(collection.getTrafficEfficiency()));
        holder.setText(R.id.jndj_tv, map.get(collection.getSafetyLevel()));
        holder.setText(R.id.price_tv, Util.numberFormat(collection.getElevatorPrice())+"");
        holder.setText(R.id.collect_time_tv, collection.getCreateDate() != null ? collection.getCreateDate().replace("T", " ").substring(0, 19) : "");
        LinearLayout group_layout = holder.getView(R.id.group_layout);
        if (position % 2 == 0) {
            group_layout.setBackgroundColor(mContext.getResources().getColor(R.color.main_grey));
        } else {
            group_layout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        final ImageView select_iv = holder.getView(R.id.select_iv);

        if (selectList.contains(position)) {
            select_iv.setImageResource(R.drawable.match_check);
        } else {
            select_iv.setImageResource(R.drawable.match);
        }
        LinearLayout select_layout = holder.getView(R.id.select_layout);
        select_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectList.contains(position)) {
                    selectList.remove(Integer.valueOf(position));
                    select_iv.setImageResource(R.drawable.match);
                } else {
                    selectList.add(position);
                    select_iv.setImageResource(R.drawable.match_check);
                }
                callBack.getSelectSize(selectList.size());
            }
        });

    }

    public List<Integer> getSelectList() {
        return selectList;
    }

    public void clearSelect() {
        selectList.clear();
        callBack.getSelectSize(selectList.size());
    }

    public void setListener(OnclickCallBack callBack) {
        this.callBack = callBack;
    }

    public interface OnclickCallBack {
        void getSelectSize(int size);
    }
}
