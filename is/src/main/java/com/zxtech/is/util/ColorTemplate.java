
package com.zxtech.is.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import com.zxtech.is.R;
import com.zxtech.is.R2;

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
        int[] colors = new int[]{mContext.getResources().getColor(R.color.light_blue), mContext.getResources().getColor(R.color.green),
                mContext.getResources().getColor(R.color.grass_green), mContext.getResources().getColor(R.color.yellow), mContext.getResources().getColor(R.color.dark_red)
                , mContext.getResources().getColor(R.color.purple)};
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
