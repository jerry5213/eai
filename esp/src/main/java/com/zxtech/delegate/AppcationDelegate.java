package com.zxtech.delegate;

import android.content.Context;

import com.zxtech.esp.MyApp;
import com.zxtech.module.common.base.IApplicationDelegate;

/**
 * Created by syp523 on 2018/6/15.
 */

public class AppcationDelegate implements IApplicationDelegate {
    @Override
    public void onCreate(Context applicationContext) {
        MyApp.getInstance().init(applicationContext);
        MyApp.context = applicationContext;
    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int level) {

    }
}
