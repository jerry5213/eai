package com.zxtech.esp;

import android.content.Context;
import android.graphics.Color;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zxtech.esp.util.UILImageLoader;
import com.zxtech.module.common.base.BaseApplication;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import g.api.http.GHttp;
import g.api.http.GRequestCallBack;
import g.api.http.GRequestData;
import g.api.http.GRequestParams;

//import com.zxtech.module.common.base.BaseApplication;

/**
 * Created by SYP521 on 2017/6/29.
 */

public class App extends BaseApplication {
    private static App instance;
    private GHttp http;
    private static String userId;
    private static String token;
    private static String photo_url;
    private static String nick_name;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init(instance);
    }

    public static App getInstance() {
        return instance;
    }

    private void init(Context ctx) {
        AppConfig.init(ctx);
        initImageLoader(ctx);
        initGalleryFinal(ctx);
    }

    public GHttp getHttp() {
        if (http == null)
            http = new GHttp(instance) {

                private void handlerRequestData(GRequestData requestData) {
                    if (requestData != null) {
                        if (requestData.getParams() == null) {
                            requestData.setParams(new GRequestParams());
                        }
                        requestData.getParams().addHeader("Authorization", "Basic " + token);
                    }
                }

                @Override
                public void send(GRequestData requestData, GRequestCallBack callBack) {
                    handlerRequestData(requestData);
                    super.send(requestData, callBack);
                }

                @Override
                public void sendFile(GRequestData requestData, GRequestCallBack callBack, boolean chunkMode) {
                    handlerRequestData(requestData);
                    super.sendFile(requestData, callBack, chunkMode);
                }
            };
        return http;
    }

    protected void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(3);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator() {
            public String generate(String imageUri) {
                return super.generate(imageUri) + "_cache";
            }
        });
        config.diskCacheSize(104857600);
        config.tasksProcessingOrder(QueueProcessingType.FIFO);
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config.build());
    }

    protected void initGalleryFinal(Context context) {

        //设置主题
        //ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.parseColor("#00000000"))
                .setTitleBarIconColor(Color.parseColor("#1a7efa"))
                .setTitleBarTextColor(Color.parseColor("#1a7efa"))
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .setMutiSelectMaxSize(5)
                .build();

        //配置imageloader
        cn.finalteam.galleryfinal.ImageLoader imageloader = new UILImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(context, imageloader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        App.userId = userId;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        App.token = token;
    }

    public static String getPhoto_url() {
        return photo_url;
    }

    public static void setPhoto_url(String photo_url) {
        App.photo_url = photo_url;
    }

    public static String getNick_name() {
        return nick_name;
    }

    public static void setNick_name(String nick_name) {
        App.nick_name = nick_name;
    }
}
