package com.zxtech.ecs.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Parameters;
import com.zxtech.ecs.util.AppUtil;
import com.zxtech.ecs.util.ColorTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by syp523 on 2018/1/22.
 */

public class SmartSearchAdapter extends CommonAdapter<Map<String, String>> {
    private int selectedPosition = -1;

    public SmartSearchAdapter(Context context, int layoutId, List<Map<String, String>> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Map<String, String> s, int position) {
        TextView part_tv = holder.getView(R.id.part_tv);
        if (Constants.LANGUAGE_ZH.equals(AppUtil.getAppLanguage())) {
            part_tv.setText(s.get("value"));
        } else {
            part_tv.setText(s.get("value_en"));
        }

        //   View convertView = holder.getConvertView();

        if (selectedPosition == position) {
            if (TextUtils.isEmpty(s.get("color_value"))) {
                part_tv.setBackground(ColorTemplate.getBorder(mContext, true, position));
                part_tv.setTextColor(mContext.getResources().getColor(R.color.white));
            } else {
                part_tv.setBackground(getSelectBorder(Color.parseColor(s.get("color_value"))));
            }
            part_tv.setTextColor(mContext.getResources().getColor(R.color.white));
        } else if (TextUtils.isEmpty(s.get("color_value"))) {
            part_tv.setBackground(ColorTemplate.getBorder(mContext, false, position));
            part_tv.setTextColor(mContext.getResources().getColor(R.color.default_text_black_color));
        } else {
            part_tv.setBackground(getBorder(Color.parseColor(s.get("color_value"))));
            part_tv.setTextColor(mContext.getResources().getColor(R.color.default_text_black_color));
        }

    }


    private GradientDrawable getBorder(int colorValue) {
        int strokeWidth = 1; // 3px not dp
        int roundRadius = 8; // 8px not dp
        int strokeColor = colorValue;

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }

    private GradientDrawable getSelectBorder(int colorValue) {
        int strokeWidth = 1; // 3px not dp
        int roundRadius = 8; // 8px not dp
        int strokeColor = colorValue;

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        gd.setColor(colorValue);
        return gd;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
}
