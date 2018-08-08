package com.zxtech.ecs.util;

import android.graphics.drawable.GradientDrawable;

/**
 * Created by syp523 on 2018/7/26.
 */

public class ShapeUtil {

    /**
     *
     * @param strokeWidth 边线大小
     * @param roundRadius 圆角大小
     * @param colorValue 边框颜色
     * @return
     */
    public static GradientDrawable getRoundBG(int strokeWidth,int roundRadius,int colorValue) {
        int strokeColor = colorValue;

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }

    /**
     *
     * @param strokeWidth 边线大小
     * @param roundRadius 圆角大小
     * @param colorValue 填充颜色
     * @return
     */
    public static GradientDrawable getFillRoundBG(int strokeWidth,int roundRadius,int colorValue) {
        int strokeColor = colorValue;

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        gd.setColor(colorValue);
        return gd;
    }
}
