package com.zxtech.is.util;

import android.content.Context;
import android.widget.Toast;

public class AlertUtil {

    private static Toast sToast;

    public static void t(Context context, String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(msg);
        }
        sToast.show();
    }

    public static void t(Context context, int msgRes) {
        if (sToast == null) {
            sToast = Toast.makeText(context, msgRes, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(msgRes);
        }
        sToast.show();
    }

    public static void t(Context context, int msgRes, int duration) {
        Toast.makeText(context, msgRes, duration).show();
    }

    public static void t(Context context, String msg, int duration) {
        Toast.makeText(context, msg, duration).show();
    }
}
