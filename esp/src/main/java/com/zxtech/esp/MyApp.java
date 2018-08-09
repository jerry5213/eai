package com.zxtech.esp;

import android.content.Context;
import android.graphics.Color;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zxtech.common.util.SPCache;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.util.UILImageLoader;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import g.api.http.GHttp;
import g.api.http.GRequestCallBack;
import g.api.http.GRequestData;
import g.api.http.GRequestParams;

/**
 * Created by SYP521 on 2017/6/29.
 */

public class MyApp {

    public static SPCache spCache;
    private static MyApp myApp;
    public static Context context;
    private GHttp http;
    private static String userId;
    private static String token;
    private static String photo_url;
    private static String nick_name;

    public static MyApp getInstance() {

        if (myApp == null) {
            myApp = new MyApp();
        }
        return myApp;
    }

    public void init(Context ctx) {

        AppConfig.init(ctx);
        initImageLoader(ctx);
        initGalleryFinal(ctx);
        spCache = SPCache.getInstance(ctx);
    }

    public GHttp getHttp() {
        if (http == null)

            token = spCache.getString(C.TOKEN,"");
            http = new GHttp(context) {

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
        MyApp.userId = userId;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        MyApp.token = token;
    }

    public static String getPhoto_url() {
        return photo_url;
    }

    public static void setPhoto_url(String photo_url) {
        MyApp.photo_url = photo_url;
    }

    public static String getNick_name() {
        return nick_name;
    }

    public static void setNick_name(String nick_name) {
        MyApp.nick_name = nick_name;
    }
}
