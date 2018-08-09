package com.zxtech.common.download;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.zxtech.esp.R;

/**
 * Created by Administrator on 2016/8/4.
 */
public class SimpleDialog {
    private SimpleDialog() {
    }


    /**
     * 创建简单对话框
     *
     * @param context
     * @param title
     * @param msg
     * @param posStr
     * @param negStr
     * @param neuStr
     * @param posListener
     * @param negListener
     * @param neuListener
     * @param canceledOnTouchOutside
     * @return
     */
    public static AlertDialog createDialog(Context context, CharSequence title, CharSequence msg, CharSequence posStr, CharSequence negStr, CharSequence neuStr, DialogInterface.OnClickListener posListener, DialogInterface.OnClickListener negListener, DialogInterface.OnClickListener neuListener, boolean canceledOnTouchOutside) {
        if (context == null)
            return null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context,  R.style.DialogTheme);
        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);
        if (!TextUtils.isEmpty(msg))
            builder.setMessage(msg);
        if (!TextUtils.isEmpty(posStr)) {
            if (posListener != null)
                builder.setPositiveButton(posStr, posListener);
            else
                builder.setPositiveButton(posStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        }
        if (!TextUtils.isEmpty(negStr)) {
            if (negListener != null)
                builder.setNegativeButton(negStr, negListener);
            else
                builder.setNegativeButton(negStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        }
        if (!TextUtils.isEmpty(neuStr)) {
            if (neuListener != null)
                builder.setNeutralButton(neuStr, neuListener);
            else
                builder.setNeutralButton(neuStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        }

        AlertDialog d = builder.create();
        d.setCanceledOnTouchOutside(canceledOnTouchOutside);
        d.setCancelable(true);
        return d;
    }

    /**
     * @see #createDialog
     */
    public static AlertDialog createDialog(Context context, CharSequence title, CharSequence msg, CharSequence posStr, CharSequence negStr, DialogInterface.OnClickListener posListener, DialogInterface.OnClickListener negListener, boolean canceledOnTouchOutside) {
        return createDialog(context, title, msg, posStr, negStr, null, posListener, negListener, null, canceledOnTouchOutside);
    }

    /**
     * @see #createDialog
     */
    public static AlertDialog createDialog(Context context, CharSequence title, CharSequence msg) {
        return createDialog(context, title, msg, "确定", null, null, null, true);
    }

    /**
     * @see #createDialog
     */
    public static AlertDialog createDialog(Context context, CharSequence title, CharSequence msg, DialogInterface.OnClickListener posListener, boolean canceledOnTouchOutside) {
        return createDialog(context, title, msg, "确定", "取消", posListener, null, canceledOnTouchOutside);
    }
}
