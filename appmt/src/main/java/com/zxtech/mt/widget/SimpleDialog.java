//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zxtech.mt.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.text.TextUtils;

import com.zxtech.mtos.R;


public class SimpleDialog {
    private SimpleDialog() {
    }

    public static AlertDialog createDialog(Context context, CharSequence title, CharSequence msg, CharSequence posStr, CharSequence negStr, CharSequence neuStr, OnClickListener posListener, OnClickListener negListener, OnClickListener neuListener, boolean canceledOnTouchOutside) {
        if(context == null) {
            return null;
        } else {
            Builder builder = new Builder(context, R.style.DialogTheme);
            if(!TextUtils.isEmpty(title)) {
                builder.setTitle(title);
            }

            if(!TextUtils.isEmpty(msg)) {
                builder.setMessage(msg);
            }

            if(!TextUtils.isEmpty(posStr)) {
                if(posListener != null) {
                    builder.setPositiveButton(posStr, posListener);
                } else {
                    builder.setPositiveButton(posStr, new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }
            }

            if(!TextUtils.isEmpty(negStr)) {
                if(negListener != null) {
                    builder.setNegativeButton(negStr, negListener);
                } else {
                    builder.setNegativeButton(negStr, new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }
            }

            if(!TextUtils.isEmpty(neuStr)) {
                if(neuListener != null) {
                    builder.setNeutralButton(neuStr, neuListener);
                } else {
                    builder.setNeutralButton(neuStr, new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }
            }

            AlertDialog d = builder.create();
            d.setCanceledOnTouchOutside(canceledOnTouchOutside);
            d.setCancelable(true);
            return d;
        }
    }

    public static AlertDialog createDialog(Context context, CharSequence title, CharSequence msg, CharSequence posStr, CharSequence negStr, OnClickListener posListener, OnClickListener negListener, boolean canceledOnTouchOutside) {
        return createDialog(context, title, msg, posStr, negStr, (CharSequence)null, posListener, negListener, (OnClickListener)null, canceledOnTouchOutside);
    }

    public static AlertDialog createDialog(Context context, CharSequence title, CharSequence msg) {
        return createDialog(context, title, msg, context.getString(R.string.confirm), (CharSequence)null, (OnClickListener)null, (OnClickListener)null, true);
    }

    public static AlertDialog createDialog(Context context, CharSequence title, CharSequence msg, OnClickListener posListener, boolean canceledOnTouchOutside) {
        return createDialog(context, title, msg, context.getString(R.string.confirm), context.getString(R.string.cancel), posListener, (OnClickListener)null, canceledOnTouchOutside);
    }
}
