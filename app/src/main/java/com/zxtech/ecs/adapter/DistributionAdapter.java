package com.zxtech.ecs.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.DistributionElevator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syp523 on 2018/3/31.
 */

public class DistributionAdapter extends CommonAdapter<DistributionElevator> {

    public DistributionAdapter(Context context, int layoutId, List<DistributionElevator> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, DistributionElevator de, int position) {
        TextView content_tv = holder.getView(R.id.content_tv);
        content_tv.setText(de.getElevator());
        if (de.isDistribution()) {
            content_tv.setTextColor(mContext.getResources().getColor(R.color.main));
            holder.itemView.setBackground(getSelectBorder());
        } else {
            content_tv.setTextColor(mContext.getResources().getColor(R.color.default_text_grey_color));
            holder.itemView.setBackground(getBorder());
        }

    }



    private GradientDrawable getBorder() {
        int strokeWidth = 1; // 3px not dp
        int roundRadius = 8; // 8px not dp
        int strokeColor = mContext.getResources().getColor(R.color.white);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        gd.setColor(strokeColor);
        return gd;
    }

    private GradientDrawable getSelectBorder() {
        int strokeWidth = 1; // 3px not dp
        int roundRadius = 8; // 8px not dp
        int strokeColor = mContext.getResources().getColor(R.color.main);
        int fill = mContext.getResources().getColor(R.color.white);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        gd.setColor(fill);
        return gd;
    }
}
