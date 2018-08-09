package com.zxtech.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * Created by SYP521 on 2017/6/29.
 */

public class T {

    private static Toast toast;

    public static StateListDrawable getDrawableSelector(Drawable selected, Drawable unSelect) {
        return getStateListDrawable(selected, selected, selected, unSelect);
    }

    public static StateListDrawable getStateListDrawable(Drawable perssed, Drawable focused, Drawable selected, Drawable unabled) {
        StateListDrawable bg = new StateListDrawable();
        bg.addState(new int[]{android.R.attr.state_pressed}, perssed);
        bg.addState(new int[]{android.R.attr.state_focused}, focused);
        bg.addState(new int[]{android.R.attr.state_selected}, selected);
        bg.addState(new int[0], unabled);
        return bg;
    }

    public static View getNoParentView(View rootView) {
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        return rootView;
    }

    public static void showToast(Context ctx, String msg) {

        if(toast == null){
            toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static PackageInfo getPackageInfo(Context context, String packageName) {
        try {
            PackageManager e = context.getPackageManager();
            return e.getPackageInfo(packageName, 0);
        } catch (Exception var3) {
            return null;
        }
    }

    public static String getVersionName(Context context, String packageName, String defaultVersionName) {
        PackageInfo packageInfo = getPackageInfo(context, packageName);
        return packageInfo != null ? packageInfo.versionName : defaultVersionName;
    }

    public static String getVersionName(Context context, String defaultVersionName) {
        return getVersionName(context, context.getPackageName(), defaultVersionName);
    }

    public static int getVersionCode(Context context, String packageName, int defaultVersionCode) {
        PackageInfo packageInfo = getPackageInfo(context, packageName);
        return packageInfo != null ? packageInfo.versionCode : defaultVersionCode;
    }

    public static int getVersionCode(Context context, int defaultVersionCode) {
        return getVersionCode(context, context.getPackageName(), defaultVersionCode);
    }


    public static int getStatusBarHeight(Context context) {
        Class c = null;
        Object obj = null;
        Field field = null;
        boolean x = false;
        byte statusBarHeight = 0;

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x1 = Integer.parseInt(field.get(obj).toString());
            int statusBarHeight1 = context.getResources().getDimensionPixelSize(x1);
            return statusBarHeight1;
        } catch (Exception var7) {
            var7.printStackTrace();
            return statusBarHeight;
        }
    }

    public static int getFitStatusBarHeight19(Context context) {
        return Build.VERSION.SDK_INT >= 19?T.getStatusBarHeight(context):0;
    }

    public static void fitSystemWindow19(View view) {
        if(view != null) {
            view.getLayoutParams().height = getFitStatusBarHeight19(view.getContext());
        }
    }

    /**
     * 获取dimens定义的大小
     *
     * @param dimensionId
     * @return
     */
    public static int getPixelById(Context context,int dimensionId) {
        return context.getResources().getDimensionPixelSize(dimensionId);
    }

    public static boolean isEquals(String a, String b) {
        return a == null && b == null?true:(a != null && b != null?a.equals(b):false);
    }
}
