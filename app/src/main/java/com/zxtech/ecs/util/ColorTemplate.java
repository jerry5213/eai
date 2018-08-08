
package com.zxtech.ecs.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import com.zxtech.ecs.R;

import java.util.ArrayList;
import java.util.List;

import static com.zxtech.ecs.common.Constants.DIMEN_AQDJ;
import static com.zxtech.ecs.common.Constants.DIMEN_CZFW;
import static com.zxtech.ecs.common.Constants.DIMEN_JNDJ;
import static com.zxtech.ecs.common.Constants.DIMEN_MGD;
import static com.zxtech.ecs.common.Constants.DIMEN_SSD;

/**
 * @author chw
 */
public class ColorTemplate {

    public static final int[] DIMENSION_COLORS = {
            rgb("#96c814"), rgb("#f91f1d"), rgb("#feb300")
    };


    /**
     * Converts the given hex-color-string to rgb.
     *
     * @param hex
     * @return
     */
    public static int rgb(String hex) {
        int color = (int) Long.parseLong(hex.replace("#", ""), 16);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        return Color.rgb(r, g, b);
    }


    public static GradientDrawable getDimensionBg(int position, boolean fill) {
        int strokeWidth = 1; // 3px not dp
        int roundRadius = 8; // 8px not dp
        int strokeColor = DIMENSION_COLORS[position];

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        if (fill) {
            gd.setColor(strokeColor);
        }
        return gd;
    }


    public static GradientDrawable getBorder(Context mContext, boolean fill, int position) {
        int[] colors = mContext.getResources().getIntArray(R.array.btn_cycle_color);
        int strokeWidth = 1; // 3px not dp
        int roundRadius = 8; // 8px not dp
        int fillColor = colors[position % 6];

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        if (fill)
            gd.setColor(fillColor);
        gd.setStroke(strokeWidth, fillColor);
        return gd;
    }


}
