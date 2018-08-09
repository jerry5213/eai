package com.zxtech.module.common.base;

import android.content.Context;
import android.support.annotation.Keep;

/**
 * base Applicationdelegate 解决模块初始化
 */
@Keep
public interface IApplicationDelegate {

    void onCreate(Context applicationContext);

    void onTerminate();

    void onLowMemory();

    void onTrimMemory(int level);

}
