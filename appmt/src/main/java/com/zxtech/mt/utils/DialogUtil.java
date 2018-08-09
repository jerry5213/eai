package com.zxtech.mt.utils;

import android.app.Activity;
import android.content.Context;

import com.zxtech.mt.widget.SelectDialog;
import com.zxtech.mtos.R;

import java.util.List;

/**
 * Created by syp523 on 2017/8/5.
 */

public class DialogUtil {

    public static SelectDialog showDialog(Activity context, SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(context, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!context.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }
}
