package com.zxtech.esp;

import android.content.Context;

import com.zxtech.common.util.T;

import java.util.Locale;

/**
 * Created by SYP521 on 2017/6/29.
 */

public class AppConfig {

    //测试服务器
    private static String HOST;
    private static String FILE_HOST;
    private static String SCORM_PLUGIN_URL;
    private static String PRODUCT;

    public static String LAN = "zh";
    public static String LAN_OLD = "zh";

    protected static void init(Context ctx){

        //PRODUCT = ctx.getResources().getString(R.string.product);
        if (T.isEquals("official", PRODUCT)) {
            HOST = "http://etp.5000m.com/lmsclient/";
            FILE_HOST = "http://etp.5000m.com/lmsFiles/";
            SCORM_PLUGIN_URL = "http://etp.5000m.com/scorm-plugin/";
        } else if (T.isEquals("beta", PRODUCT)) {
            /*HOST = "http://etp.5000m.com:8090/lmsclient/";
            FILE_HOST = "http://etp.5000m.com:8090/lmsFiles/";*/
            HOST = "http://192.168.0.180:8090/lmsclient/";
            FILE_HOST = "http://192.168.0.180:8090/lmsFiles/";
            SCORM_PLUGIN_URL = "http://etp.5000m.com:8090/scorm-plugin/";
        }
        LAN = Locale.getDefault().getLanguage();
        LAN_OLD = LAN;
    }


    public static String getPRODUCT() {
        return PRODUCT;
    }

    public static String getHOST() {
        return "http://etp.5000m.com/lmsclient/";
    }

    public static String getFileHost(){
        return "http://etp.5000m.com/lmsFiles/";
    }

    public static String getScormPluginUrl() {
        return "http://etp.5000m.com/scorm-plugin/";
    }
}
