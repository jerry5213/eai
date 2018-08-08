package com.zxtech.ecs.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.Require;

import java.util.List;

/**
 * Created by syp523 on 2018/1/22.
 */

public class RequireAdapter extends CommonAdapter<Require> {
    public RequireAdapter(Context context, int layoutId, List<Require> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Require require, final int position) {
        holder.setText(R.id.number_tv,position+1+"");
        holder.setText(R.id.content_tv,require.getContent().split("#")[1]);
        Button delete_btn = holder.getView(R.id.delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.remove(position);
                notifyDataSetChanged();
            }
        });
    }
}
