package com.zxtech.is.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {


    private static Toast toast;

    /**
     * 弹出较长时间提示信息
     *
     * @param msg 要显示的信息
     */
    public static void showLong(String msg) {
        if (toast == null) {
            toast = Toast.makeText(AppUtil.getContext(),
                    msg,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }


    /**
     * 弹出较短时间提示信息
     *
     * @param context 上下文对象
     * @param msg     要显示的信息
     */
    public static void showShort(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(AppUtil.getContext(),
                    msg,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }


}
