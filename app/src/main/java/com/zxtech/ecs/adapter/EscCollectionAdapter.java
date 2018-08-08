package com.zxtech.ecs.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class EscCollectionAdapter extends CommonAdapter<Collection> {
    private List<Integer> selectList = new ArrayList<>();
    private OnclickCallBack callBack;

    public EscCollectionAdapter(Context context, int layoutId, List<Collection> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Collection collection, final int position) {
        LinearLayout group_layout = holder.getView(R.id.group_layout);
        if (position % 2 == 0) {
            group_layout.setBackgroundColor(mContext.getResources().getColor(R.color.main_grey));
        } else {
            group_layout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        holder.setText(R.id.collection_tv, collection.getCollectionName());
        holder.setText(R.id.params1_tv, "自动扶梯".equals(collection.getElevatorProduct())?mContext.getResources().getString(R.string.escalator):collection.getElevatorProduct());
        holder.setText(R.id.params3_tv, collection.getEs_V());
        holder.setText(R.id.params4_tv, collection.getEs_Angle());
        holder.setText(R.id.params5_tv, collection.getEs_SW());
        holder.setText(R.id.params6_tv, collection.getEs_LS());
        holder.setText(R.id.params7_tv, collection.getEs_TH());
        holder.setText(R.id.params8_tv, collection.getEs_HD());

        holder.setText(R.id.price_tv, Util.numberFormat(collection.getElevatorPrice()));
        holder.setText(R.id.collect_time_tv, collection.getCreateDate() != null ? collection.getCreateDate().replace("T", " ").substring(0, 19) : "");


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
