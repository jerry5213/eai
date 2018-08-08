package com.zxtech.ecs.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.LayoutSelect;

import java.util.List;

/**
 * Created by syp523 on 2018/4/4.
 */

public class LayoutSchemeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public LayoutSchemeAdapter(int layoutId, List<String> datas) {
        super(layoutId, datas);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        TextView scheme_no_tv = holder.getView(R.id.scheme_no_tv);
        scheme_no_tv.setBackground(getBorder(mContext, holder.getAdapterPosition()));
        if (item.equals("1")) {
            holder.setImageResource(R.id.status_iv, R.drawable.download_finish);
        } else {
            holder.setImageResource(R.id.status_iv, R.drawable.download);
        }
        holder.addOnClickListener(R.id.status_iv);
    }

    public static GradientDrawable getBorder(Context mContext, int position) {
        int[] colors = new int[]{mContext.getResources().getColor(R.color.light_blue), mContext.getResources().getColor(R.color.green),
                mContext.getResources().getColor(R.color.grass_green), mContext.getResources().getColor(R.color.yellow), mContext.getResources().getColor(R.color.dark_red)
                , mContext.getResources().getColor(R.color.purple)};
        int roundRadius = 8; // 8px not dp
        int fillColor = colors[position % 6];

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        float[] radius = new float[]{10, 10, 10, 10, 0, 0, 0, 0};
        gd.setCornerRadii(radius);
        gd.setColor(fillColor);
        return gd;
    }
}
