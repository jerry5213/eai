package com.zxtech.module.common.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.zxtech.module.common.utils.ClassUtils;
import com.zxtech.module.common.utils.Utils;

import java.util.List;

/**
 * Created by syp523 on 2018/6/15.
 */

public class BaseApplication extends MultiDexApplication {

    public static final String ROOT_PACKAGE = "delegate";

    private List<IApplicationDelegate> mAppDelegateList;


    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        mAppDelegateList = ClassUtils.getObjectsWithInterface(this, IApplicationDelegate.class, ROOT_PACKAGE);
        for (IApplicationDelegate delegate : mAppDelegateList) {
            delegate.onCreate(this);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        for (IApplicationDelegate delegate : mAppDelegateList) {
            delegate.onLowMemory();
        }
    }
}
