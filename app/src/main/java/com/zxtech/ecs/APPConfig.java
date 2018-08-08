package com.zxtech.ecs;

import android.os.Environment;



/**
 * Created by syp523 on 2018/1/25.
 */

public class APPConfig {
	/**
	 * 文件下载地址
	 */
	public final static String DOWN_LOAD_PATH = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";

	public static final String ECS_MODEL_RESOURCES = Environment.getExternalStorageDirectory().getPath() + "/vr_model_res/";


    public static final String BASE_URL = BuildConfig.BASE_URL;

	/**
	 * 每页记录数
	 */
	public static int PAGE_SIZE = 15;


}
