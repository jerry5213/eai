package com.zxtech.is;

import android.os.Environment;

import com.zxtech.is.util.AppUtil;

import java.io.File;

/**
 * Created by syp523 on 2018/1/25.
 */

public class APPConfig {
    /**
     * 文件下载地址
     */
    public final static String DOWN_LOAD_PATH = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";

    public static final String ECS_MODEL_RESOURCES = Environment.getExternalStorageDirectory().getPath() + "/ecs_model_res/";


    public static final String BASE_URL = BuildConfig.BASE_URL;

//	public static final String BASE_URL_FOR_LOGIN = BuildConfig.BASE_URL_FOR_LOGIN;

//    public static final String BASE_URL = "";

    public static final String BASE_URL_FOR_LOGIN = BuildConfig.BASE_URL_FOR_LOGIN;


    public static final String VIDEO_URL = "http://118.190.202.234:2603/zxtechBot_ecs/pv/";

    /**
     * 当前页码
     */
    public static int PAGE_INDEX = 1;
    /**
     * 每页记录数
     */
    public static int PAGE_SIZE = 20;
    public static String SDCARD_ROOT = AppUtil.getContext().getExternalFilesDir(null) + File.separator;


}
