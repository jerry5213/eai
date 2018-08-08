package com.zxtech.ecs;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mobstat.StatService;
import com.liulishuo.filedownloader.FileDownloader;
import com.zxtech.ecs.service.LocationService;
import com.zxtech.ecs.util.AppUtil;
import com.zxtech.module.common.base.BaseApplication;

/**
 * Created by syp523 on 2018/1/29.
 */

public class App extends BaseApplication {


    public LocationService locationService;

    @Override
    public void onCreate() {
        AppUtil.init(this);
        super.onCreate();

        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();
        }
        ARouter.init(this);


        StatService.setDebugOn(BuildConfig.DEBUG);
        FileDownloader.setup(this);

        locationService = new LocationService(getApplicationContext());

    }


}
