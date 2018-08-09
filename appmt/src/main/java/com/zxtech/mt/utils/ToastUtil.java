package com.zxtech.mt.utils;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zxtech.mtos.R;

public class ToastUtil {

    private static Toast toast;

    /**
     * 弹出较长时间提示信息
     * @param context 上下文对象
     * @param msg 要显示的信息
     */
	public static void showLong(Context context, String msg){
        if (context != null && msg != null && !msg.equals("")) {
            if (toast == null) {
                LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflate.inflate(R.layout.transient_notification, null);

                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(0xA6000000);
                gradientDrawable.setCornerRadius(DensityUtil.dip2px(context, 5f));
                if (Build.VERSION.SDK_INT >= 16) {
                    view.setBackground(gradientDrawable);
                } else {
                    view.setBackgroundDrawable(gradientDrawable);
                }
                toast = new Toast(context);
                toast.setView(view);
                toast.setGravity(Gravity.CENTER, 0, 0);
            }

                toast.setDuration(Toast.LENGTH_LONG);

            TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
            if (tv != null) {
                tv.setText(msg);
                toast.show();
            }
        }

    }


    /**
     * 弹出较短时间提示信息
     * @param context 上下文对象
     * @param msg 要显示的信息
     */
	public static void showShort(Context context, String msg){
        if (context != null && msg != null && !msg.equals("")) {
            if (toast == null) {
                LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflate.inflate(R.layout.transient_notification, null);

                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(0xA6000000);
                gradientDrawable.setCornerRadius(DensityUtil.dip2px(context, 5f));
                if (Build.VERSION.SDK_INT >= 16) {
                    view.setBackground(gradientDrawable);
                } else {
                    view.setBackgroundDrawable(gradientDrawable);
                }
                toast = new Toast(context);
                toast.setView(view);
                toast.setGravity(Gravity.CENTER, 0, 0);
            }

            toast.setDuration(Toast.LENGTH_SHORT);

            TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
            if (tv != null) {
                tv.setText(msg);
                toast.show();
            }
        }
	}
	
	
	
}
