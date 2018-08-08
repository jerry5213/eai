package com.zxtech.ecs.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.Goods;

import java.util.List;
import java.util.Map;

/**
 * Created by syp523 on 2018/3/2.
 */

public class SmartSearchContentAdapter extends CommonAdapter<Map<String,String>> {

    public SmartSearchContentAdapter(Context context, int layoutId, List<Map<String,String>> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Map<String,String> goods, int position) {
        Glide.with(mContext).load(goods.get("url")).fitCenter().into((ImageView) holder.getView(R.id.image));
        holder.setText(R.id.title,goods.get("commodityName"));
    }
}
