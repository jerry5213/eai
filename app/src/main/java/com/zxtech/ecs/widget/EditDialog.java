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

public class EditDialog implements DialogInterface.OnClickListener {
    private DialogConfirmCallBack callBack;

    private static final EditDialog EDIT_DIALOG = new EditDialog();
    private static AlertDialog.Builder builder;

    private EditText content_et;

    private Context mContext;

    public static EditDialog newInstance() {
        return EDIT_DIALOG;
    }

    public EditDialog setBuider(Context context, String title, String textHint, DialogConfirmCallBack callBack) {
        return this.setBuider(context,title,textHint, InputType.TYPE_CLASS_TEXT,callBack);
    }


    public EditDialog setBuider(Context context, String title, String textHint, int inputType, DialogConfirmCallBack callBack) {
        this.mContext = context;
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialog_edit = inflater.inflate(R.layout.dialog_edit, null);
        content_et = dialog_edit.findViewById(R.id.content_et);
        content_et.setHint(textHint);
        content_et.setInputType(inputType);
        builder.setView(dialog_edit);

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
                KeyboardUtils.hideSoftInput(content_et);
                dialog.dismiss();
                break;
            case -1:
                KeyboardUtils.hideSoftInput(content_et);
                dialog.dismiss();
                if (callBack != null) {
                    callBack.confirm(content_et.getText().toString());
                }
                break;
        }
    }



    public interface DialogConfirmCallBack {
        // 确认操作
        void confirm(String value);
    }

}

