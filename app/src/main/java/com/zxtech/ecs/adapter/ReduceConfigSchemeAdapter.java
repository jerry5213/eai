package com.zxtech.ecs.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.Programme;
import com.zxtech.ecs.model.ReduceConfig;
import com.zxtech.ecs.ui.home.scheme.ReduceConfigDetailDialogFragment;
import com.zxtech.ecs.util.Util;

import java.util.List;

/**
 * Created by syp523 on 2018/2/2.
 */

public class ReduceConfigSchemeAdapter extends CommonAdapter<ReduceConfig> {
    public ReduceConfigSchemeAdapterCallBack callBack;
    private int currentPage = 0;
    private int pageSize = 3;

    public ReduceConfigSchemeAdapter(Context context, int layoutId, List<ReduceConfig> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final ReduceConfig reduceConfig, final int position) {
        holder.setText(R.id.number_tv, mContext.getResources().getString(R.string.proposal) + String.valueOf(position + 1 + currentPage * pageSize));
        holder.setText(R.id.changeinfo_tv, reduceConfig.getFirstReduce());

        holder.setText(R.id.reduce_price_tv, reduceConfig.getReducePrice() + mContext.getString(R.string.rmb));
        TextView detail_tv = holder.getView(R.id.detail_tv);

        detail_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.reduceDetail(position);
            }
        });
        TextView application_tv = holder.getView(R.id.application_tv);
        application_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.application(position);
            }
        });
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public interface ReduceConfigSchemeAdapterCallBack {
        void application(int position);

        void reduceDetail(int position);
    }
}
