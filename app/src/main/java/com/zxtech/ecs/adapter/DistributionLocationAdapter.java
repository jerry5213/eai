package com.zxtech.ecs.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;

import java.util.List;

/**
 * Created by syp523 on 2018/3/31.
 */

public class DistributionLocationAdapter extends CommonAdapter<String> {
    private int selectedPostion = -1;

    public DistributionLocationAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        TextView content_tv = holder.getView(R.id.content_tv);
        if (selectedPostion == position) {
            content_tv.setBackground(getSelectBorder());
            content_tv.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            content_tv.setBackground(getBorder());
            content_tv.setTextColor(mContext.getResources().getColor(R.color.default_text_grey_color));
        }
        content_tv.setText(s);
    }

    public int getSelectedPostion() {
        return selectedPostion;
    }

    public void setSelectedPostion(int selectedPostion) {
        this.selectedPostion = selectedPostion;
    }

    private GradientDrawable getBorder() {
        int strokeWidth = 1; // 3px not dp
        int roundRadius = 8; // 8px not dp
        int strokeColor = mContext.getResources().getColor(R.color.main_grey);
        int fill = mContext.getResources().getColor(R.color.main_grey);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        gd.setColor(fill);
        return gd;
    }

    private GradientDrawable getSelectBorder() {
        int strokeWidth = 1; // 3px not dp
        int roundRadius = 8; // 8px not dp
        int strokeColor = mContext.getResources().getColor(R.color.main);
        int fill = mContext.getResources().getColor(R.color.main);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        gd.setColor(fill);
        return gd;
    }
}
