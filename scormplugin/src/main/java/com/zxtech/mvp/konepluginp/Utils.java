package com.zxtech.mvp.konepluginp;

import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * Created by syp523 on 2018/6/14.
 */

public final class Utils {


    public static boolean isExistsPlugin(Context context){
        Log.d("chw", context.getDir("extracted_xwalkcore",Context.MODE_PRIVATE).getAbsolutePath()+"/libxwalkcore.so");
        return new File(context.getDir("extracted_xwalkcore",Context.MODE_PRIVATE).getAbsolutePath()+"/libxwalkcore.so").exists();
    }
}
