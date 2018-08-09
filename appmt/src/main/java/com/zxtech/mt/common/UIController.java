package com.zxtech.mt.common;

import android.os.Environment;

/**
 * Created by Chw on 2016/7/18.
 */
public class UIController {

    public static boolean OUT = false;

    //183.129.144.67:9096
    //10.19.0.113:8080
    //public  final static  String IP = "http://183.129.144.67:9096/essos";

    public final static  String IP = "https://emp.5000m.com/essos";
//    public final static  String IP = "http://192.168.0.214:8080/essos";
//    public final static  String IP = "https://emp.5000m.com/essos";





    public final static  String SHOP_IP = "http://demo.qiban365.net";



    public static  int SCREEN_WIDTH = 0;

    public static  int SCREEN_HEIGHT = 0;




    public static String SD_DIR_PATH = Environment.getExternalStorageDirectory()+"/mt";


    public static String IMEI = "";

    public static String SIGN_FILE_PATH = "";

}
