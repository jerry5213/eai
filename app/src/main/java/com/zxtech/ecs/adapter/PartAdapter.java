package com.zxtech.ecs.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.Parameters;

import java.util.List;

/**
 * Created by syp523 on 2018/1/22.
 */

public class PartAdapter extends CommonAdapter<Parameters> {
    private int selectedPosition = -1;
    private String language = "zh";

    public PartAdapter(Context context, int layoutId, List<Parameters> datas,String language) {
        super(context, layoutId, datas);
        this.language = language;
    }

    @Override
    protected void convert(ViewHolder holder, Parameters s, int position) {
        TextView part_tv = holder.getView(R.id.part_tv);
        part_tv.setText(s.getName());
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
        int[] colors = mContext.getResources().getIntArray(R.array.btn_cycle_color);
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
        int[] colors = mContext.getResources().getIntArray(R.array.btn_cycle_color);
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
