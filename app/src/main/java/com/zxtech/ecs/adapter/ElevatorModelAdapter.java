package com.zxtech.ecs.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;

import java.util.List;

/**
 * Created by syp523 on 2018/4/5.
 */

public class ElevatorModelAdapter extends CommonAdapter<String> {
    private int selectedPosition = -1;
    public ElevatorModelAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        TextView part_tv = holder.getView(R.id.model_tv);
        part_tv.setText(s);
        LinearLayout layout = holder.getView(R.id.layout);
        if (selectedPosition == position) {
            layout.setBackground(getSelectBorder(position));
            part_tv.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            layout.setBackground(getBorder(position));
            part_tv.setTextColor(mContext.getResources().getColor(R.color.default_text_black_color));
        }
    }

    private GradientDrawable getSelectBorder(int position) {
        int[] colors = new int[]{mContext.getResources().getColor(R.color.light_blue), mContext.getResources().getColor(R.color.green),
                mContext.getResources().getColor(R.color.grass_green), mContext.getResources().getColor(R.color.yellow), mContext.getResources().getColor(R.color.dark_red)
                , mContext.getResources().getColor(R.color.purple)};
        int strokeWidth = 1; // 3px not dp
        int roundRadius = 8; // 8px not dp
        int fillColor = colors[position % 6];

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        gd.setColor(fillColor);
        gd.setStroke(strokeWidth, fillColor);
        return gd;
    }

    private GradientDrawable getBorder(int position) {
        int[] colors = new int[]{mContext.getResources().getColor(R.color.light_blue), mContext.getResources().getColor(R.color.green),
                mContext.getResources().getColor(R.color.grass_green), mContext.getResources().getColor(R.color.yellow), mContext.getResources().getColor(R.color.dark_red)
                , mContext.getResources().getColor(R.color.purple)};
        int strokeWidth = 1; // 3px not dp
        int roundRadius = 8; // 8px not dp
        int strokeColor = colors[position % 6];

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

}
