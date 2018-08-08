package com.zxtech.ecs.common;

import android.os.Environment;

import com.zxtech.ecs.BuildConfig;
import com.zxtech.ecs.R;
import com.zxtech.ecs.util.AppUtil;

/**
 * Created by syp523 on 2018/1/22.
 */

public class Constants {
    public static final String TAG = "chw";

    public static final String BOT_APP_ID = "zxtechBot";
    public static final String SD_DIR_PATH = Environment.getExternalStorageDirectory() + "/ecs";

    public static final String BOT_STATE_PROCESS = "process";


    //基础参数
    public static final String BASIC_PARAMETERS = "Tag5";
    //土建参数
    public static final String CONSTRUCTION_PARAMETERS = "Tag1";
    //基础+土建参数
    public static final String BASIC_CONSTRUCTION_PARAMETERS = "Tag5,Tag1";
    //功能参数
    public static final String FUNCTION_PARAMETERS = "Tag7";
    //装潢参数
    public static final String DECORATION_PARAMETERS = "Tag6";

    public static final String ALL_PARAMETERS = "Tag5,Tag6,Tag1,Tag7";

    public static final String FUNCTION_PARAMETERS_CAR = "Tag6B1";

    public static final String FUNCTION_PARAMETERS_HALL = "Tag6C1";

    public static final String FUNCTION_PARAMETERS_STANDARD = "Tag7C1";

    public static final String FUNCTION_PARAMETERS_MATCH_A = "Tag7A1";

    public static final String FUNCTION_PARAMETERS_MATCH_B = "Tag7B1";

    //扶梯 土建参数
    public static final String ESC_BASIC_CONSTRUCTION_PARAMETERS = "EscTag1";

    //扶梯 基本信息
    public static final String ESC_BASIC_INFO = "EscTag1C1";

    //扶梯 基本参数
    public static final String ESC_BASIC_PARAMETERS = "EscTag1A1";

    //扶梯 装潢参数
    public static final String ESC_DECORATION_PARAMETERS = "EscTag5";

    //扶梯 功能参数
    public static final String ESC_FUNCTION_PARAMETERS = "EscTag2";

    //扶梯 功能参数标配
    public static final String ESC_FUNCTION_PARAMETERSD1 = "EscTag2D1";
    //扶梯 功能参数非标
    public static final String ESC_FUNCTION_PARAMETERSA1 = "EscTag2A1";
    //扶梯 功能参数非标
    public static final String ESC_FUNCTION_PARAMETERSB1 = "EscTag2B1";
    //扶梯 功能参数非标
    public static final String ESC_FUNCTION_PARAMETERSC1 = "EscTag2C1";

    //直梯所有参数
    public static final String ELE_ALL_PARAMETERS = "Tag5,Tag1,Tag6,Tag7";

    //基础信息
    public static final String BASIC_SUB_PARAMETERS = "Tag5A1";
    //井道与轿厢信息
    public static final String BASIC_WELL_PARAMETERS = "Tag5B1";
    //井道与轿厢信息
    public static final String BASIC_CAR_PARAMETERS = "Tag1B1";
    //机房信息
    public static final String BASIC_ROOM_PARAMETERS = "Tag1C1";
    //定位尺寸信息
    public static final String BASIC_LOCATION_PARAMETERS = "Tag1E1";

    public static final String ESC_ALL_PARAMETERS = "EscTag1,EscTag2,EscTag5";


    public static final String DIMEN_SSD = "Comfortable";
    public static final String DIMEN_JNDJ = "OPTIONALF";
    public static final String DIMEN_MGD = "BF-MGD";
    public static final String DIMEN_CZFW = "SEVICE";
    public static final String DIMEN_AQDJ = "SafetyF";

    public static final String DIMEN_FlAG_SSD = "COMFORTABLE";
    public static final String DIMEN_FLAG_JNDJ = "OPTIONALF";
    public static final String DIMEN_FLAG_MGD = "MGD";
    public static final String DIMEN_FLAG_CZFW = "SEVICE";
    public static final String DIMEN_FLAG_AQDJ = "SAFETYF";



    public static final String CODE_ELE_TYPE = "Ele_Type";
    public static final String CODE_MACHINEROOM = "MR";
    public static final String CODE_DIM_SHAFT_WIDTH_WW = "HW";
    public static final String CODE_DIM_SHAFT_DEPTH_WD = "HD";
    public static final String CODE_QTY_NUMBER_OF_FLOORS = "Floors";//楼层数
    public static final String CODE_DIM_HEADROOM_SH = "OH";//顶层高度
    public static final String CODE_DIM_PIT_HEIGHT_PH = "PD"; //底坑深度
    public static final String CODE_DIM_SHAFT_HEIGHT = "HH";
    public static final String CODE_DIM_CAR_SHELL_WIDTH_BB = "CW";
    public static final String CODE_DIM_CAR_SHELL_DEPTH_DD = "CD";
    public static final String CODE_DIM_CAR_HEIGHT_CH = "CH";
    public static final String CODE_DIM_DOOR_LL_A = "OPW";
    public static final String CODE_DIM_CDO_HH_A = "OPH";
    public static final String CODE_TH = "TH";
    public static final String CODE_SYSTEM_LANG = "System_lang";
    public static final String CODE_ELEVATORPRODUCT = "ElevatorProduct";
    public static final String CODE_ELEVATORTYPE = "ElevatorType";
    public static final String CODE_V = "V";
    public static final String CODE_LOAD = "LOAD";
    public static final String CODE_ES_SW = "Es_SW";//扶梯踏板宽度
    public static final String CODE_ES_ANGLE = "Es_Angle"; //扶梯倾斜角度
    public static final String CODE_THR_ENTR = "Thr_Entr";//贯通门


    public static final String CODE_POS_OF_CWT = "Cwt_Loc"; //对重位置




    public static final String CODE_SPECIALNONSTANDARD = "SpecialNonStandard"; //非标CODE
    public static final String CODE_OPH = "OPH";
    public static final String CODE_OPW = "OPW";
    public static final String CODE_ES_BBS = "Es_BBS";
    public static final String CODE_ES_TBS = "Es_TBS";
    public static final String CODE_ES_TH = "Es_TH";
    public static final String CODE_ES_HD = "Es_HD";
    public static final String CODE_LAYOUT_LANG = "Layout_lang";
    public static final String CODE_PRICE = "Price";


    public static final int REQUEST_VIDEO_CODE = 0x1118;
    public static final int PHOTO_REQUEST_CUT = 0x111;
    public static final int PHOTO_REQUEST_GALLERY = 0x112;
    public static final int PHOTO_REQUEST_GALLERY_CODE3 = 0x1122;
    public static final int CAMERA_REQUEST_CODE3 = 0x1123;
    public static final int CAMERA_REQUEST = 0x113;
    public static final int IMAGE_CAPTURE = 0x114;
    public static final int QR_CODE = 0x1115;
    public static final int RESULT_CODE_QR_SCAN = 0xA1;
    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";



    public static final String LANGUAGE_ZH = "zh";
    public static final String LANGUAGE_EN = "en";
    public static final String ELEVATOR = "Elevator";
    public static final String ESCALATOR = "Escalator";

    public static final String BOT_APP_ID_QMS = "qms";
    //requestCode
    public static final int REQ_EVALUATE = 0x0011;

    public static final int REQ_TAKE_PHOTO = 100;
    public static final int REQ_ALBUM = 101;
    public static final int REQ_ZOOM = 102;

    public static final String VOICE_RESULT_ACTION_MESSAGE = "MESSAGE";
    public static final String VOICE_RESULT_ACTION_ACTION = "ACTION";
    public static final String VOICE_RESULT_ACTION_AM = "AM";


    public static final String WIDGET_TYPE_INPUT = "input";
    public static final String WIDGET_TYPE_SELECT = "select";


    public static final String SHARED_LANGUAGE = "LANGUAGE";


    public static final String AGENT_MNK = "mnk";

    public static final String AGENT_BF = "bf";

    public static final String NET_GUID_NULL = "00000000-0000-0000-0000-000000000000";

    public static final String WECHAT_SDK_STATE = "wechat_sdk_微信登录";

    public static final String WECHAT_APP_ID = BuildConfig.WECHAT_APP_ID;

    public static final String WECHAT_APP_SECRET = BuildConfig.WECHAT_APP_SECRET;
}
