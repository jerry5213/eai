package com.zxtech.ecs.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.zxtech.ecs.R;
import com.zxtech.module.common.utils.KeyboardUtils;

/**
 * Created by syp523 on 2018/7/7.
 */

public class ConfirmDialog implements DialogInterface.OnClickListener {
    private DialogConfirmCallBack callBack;

    private static final ConfirmDialog EDIT_DIALOG = new ConfirmDialog();
    private static AlertDialog.Builder builder;

    private Context mContext;

    public static ConfirmDialog newInstance() {
        return EDIT_DIALOG;
    }



    public ConfirmDialog setBuider(Context context, String title, String message, DialogConfirmCallBack callBack) {
        this.mContext = context;
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);


        this.callBack = callBack;
        builder.setPositiveButton(context.getString(R.string.confirm), this);
        builder.setNegativeButton(context.getString(R.string.cancel), this);

        return this;
    }

    private static AlertDialog.Builder getBuider() {
        return builder;
    }

    public void show(){
        getBuider().show();
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            // -2表示取消，-1表示确认
            case -2:
                dialog.dismiss();
                break;
            case -1:
                dialog.dismiss();
                if (callBack != null) {
                    callBack.confirm();
                }
                break;
        }
    }



    public interface DialogConfirmCallBack {
        // 确认操作
        void confirm();
    }

}

