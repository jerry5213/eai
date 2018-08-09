package com.zxtech.is.common;

import android.os.Environment;

import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.util.AppUtil;

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

    public static final String ESC_ALL_PARAMETERS = "EscTag1,EscTag2,EscTag5";

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


    public static final String CODE_ELE_M = "Ele_M";
    public static final String CODE_BUIDINGNUMBER = "BUIDINGNUMBER";
    public static final String CODE_APFL_UNITS_ID = "Ele_No";
    public static final String CODE_CODE = "Code";
    public static final String CODE_ELEVATORTYPE = "Ele_Type";
    public static final String CODE_MACHINEROOM = "MR";
    public static final String CODE_DIM_SHAFT_WIDTH_WW = "HW";
    public static final String CODE_DIM_SHAFT_DEPTH_WD = "HD";
    public static final String CODE_QTY_NUMBER_OF_FLOORS = "Floors";
    public static final String CODE_QTY_FLOORS_SERVED_TOTAL = "Stops";
    public static final String CODE_VAL_RATED_SPEED = "V";
    public static final String CODE_WGT_RATED_LOAD_Q = "Load";
    public static final String CODE_QTY_NUMBER_OF_PASSENGERS = "Passengers";
    public static final String CODE_DIM_TRAVEL_HEIGHT_H = "TH";
    public static final String CODE_DIM_HEADROOM_SH = "OH";
    public static final String CODE_DIM_PIT_HEIGHT_PH = "PD";
    public static final String CODE_DIM_SHAFT_HEIGHT = "HH";
    public static final String CODE_DIM_CAR_SHELL_WIDTH_BB = "CW";
    public static final String CODE_DIM_CAR_SHELL_DEPTH_DD = "CD";
    public static final String CODE_DIM_CAR_HEIGHT_CH = "CH";
    public static final String CODE_TYP_ELEC_FEAT_TTC = "TYP_ELEC_FEAT_TTC";
    public static final String CODE_DIM_DOOR_LL_A = "OPW";
    public static final String CODE_DIM_CDO_HH_A = "OPH";
    public static final String CODE_TYP_DOOR_A = "TYP_DOOR_A";
    public static final String CODE_TYP_DOOR_C = "TYP_DOOR_C";
    public static final String CODE_QTY_LDO_A = "QTY_LDO_A";
    public static final String CODE_QTY_LDO_C = "QTY_LDO_C";
    public static final String CODE_POS_OF_CWT = "CWT_Loc";
    public static final String CODE_QTY_EMERGENCY_DOOR = "Safe_Door";
    public static final String CODE_SUPPLYPOWER = "Supp_Voltage";
    public static final String CODE_FREQUENCY = "Supp_Freq";
    public static final String CODE_LIGHTING = "LT_Voltage";
    public static final String CODE_TYP_CONTROL_SYSTEM = "TYP_CONTROL_SYSTEM";
    public static final String CODE_GG019 = "Ele_Pos";
    public static final String CODE_QTY_GROUP_SIZE = "NEG";
    public static final String CODE_LANGUAGE = "ANN_Lang";
    public static final String CODE_TYP_DRIVE_SYSTEM_ORIGIN = "Dri_Type";
    public static final String CODE_TYP_CONTROL_SYSTEM_ORIGIN = "Ctrl_Sys";
    public static final String CODE_TYP_ELEC_FEAT_FRD = "FFS";
    public static final String CODE_TYP_ELEC_FEAT_ISE = "TYP_ELEC_FEAT_ISE";
    public static final String CODE_TYP_CDO_SAFETY_DEVICE = "Imp_Safety";
    public static final String CODE_TYP_ELEC_FEAT_EAQ = "Imp_Govenor";
    public static final String CODE_TYP_CAR_RIDE_COMFORT = "Noise_INS";
    public static final String CODE_TYP_CAR_AIR_CONDITION_PORT_REQ = "Audio";
    public static final String CODE_TYP_ELEC_FEAT_CTV = "Survieliance_Interface";
    public static final String CODE_TYP_ELEC_FEAT_AUD = "TYP_ELEC_FEAT_AUD";
    public static final String CODE_TYP_ELEC_FEAT_ACU = "Handicapped_Kit";
    public static final String CODE_TYP_ELEC_FEAT_ABE = "TYP_ELEC_FEAT_ABE";
    public static final String CODE_TYP_ELEC_FEAT_LIL = "TYP_ELEC_FEAT_LIL";
    public static final String CODE_TYP_ELEC_FEAT_FRE = "TYP_ELEC_FEAT_FRE";
    public static final String CODE_TYP_ELEC_FEAT_PRL = "TYP_ELEC_FEAT_PRL";
    public static final String CODE_TYP_ELEC_FEAT_ATS = "Att_Service";
    public static final String CODE_TYP_ELEC_FEAT_GOC = "TYP_ELEC_FEAT_GOC";
    public static final String CODE_TYP_ELEC_FEAT_CRM = "Imp_Buff";
    public static final String CODE_TYP_CAR_TRAP_DOOR = "TYP_CAR_TRAP_DOOR";
    public static final String CODE_TYP_ELEC_FEAT_EPS = "TYP_ELEC_FEAT_EPS";
    public static final String CODE_TYP_ELEC_FEAT_EBD = "Door_Prot";
    public static final String CODE_TYP_ELEC_FEAT_BMV = "ADO";
    public static final String CODE_TYP_DRIVE_ORIGIN = "Strc_Sevice";
    public static final String CODE_TYP_SIGNALISATION_SERIES = "TYP_SIGNALISATION_SERIES";
    public static final String CODE_TYP_ELEC_FEAT_NUD = "TYP_ELEC_FEAT_NUD";
    public static final String CODE_TYP_ELEC_FEAT_LOC = "TYP_ELEC_FEAT_LOC";
    public static final String CODE_TYP_ELEC_FEAT_LOL = "TYP_ELEC_FEAT_LOL";
    public static final String CODE_TYP_ELEC_FEAT_OCL = "TYP_ELEC_FEAT_OCL";
    public static final String CODE_TYP_ELEC_FEAT_OCV = "TYP_ELEC_FEAT_OCV";
    public static final String CODE_TYP_ELEC_FEAT_DOE = "TYP_ELEC_FEAT_DOE";
    public static final String CODE_TYP_ELEC_FEAT_OSS = "TYP_ELEC_FEAT_OSS";
    public static final String CODE_TYP_CAR_GUIDE_SHOE = "Car_DOper_Model";
    public static final String CODE_TYP_ELEC_FEAT_CSM = "TYP_ELEC_FEAT_CSM";
    public static final String CODE_VAL_LIGHT_VOLTAGE_SH = "VAL_LIGHT_VOLTAGE_SH";
    public static final String CODE_TYP_CWT_GUIDE_SHOE = "TYP_CWT_GUIDE_SHOE";
    public static final String CODE_TYP_IMPORT_COMPONENT = "TYP_IMPORT_COMPONENT";
    public static final String CODE_TYP_CWT_SAFETY_GEAR_SG = "TYP_CWT_SAFETY_GEAR_SG";
    public static final String CODE_MAT_CWT_FILLER = "MAT_CWT_FILLER";
    public static final String CODE_TYP_WELL_STRUCTURE = "TYP_WELL_STRUCTURE";
    public static final String CODE_FLOORHEIGHT_YTH = "FLOORHEIGHT_YTH";
    public static final String CODE_B_JDBH_C = "B_JDBH_C";
    public static final String CODE_JDBH_Q = "JDBH_Q";
    public static final String CODE_JDBH_Z = "JDBH_Z";
    public static final String CODE_JDBH_Y = "JDBH_Y";
    public static final String CODE_JDBH_H = "JDBH_H";
    public static final String CODE_STARTFLOOR = "STARTFLOOR";
    public static final String CODE_ENDFLOOR = "ENDFLOOR";
    public static final String CODE_DIM_MACHINE_ROOM_HEIGHT_MH = "DIM_MACHINE_ROOM_HEIGHT_MH";
    public static final String CODE_DIM_MR_RAISED_HEIGHT = "DIM_MR_RAISED_HEIGHT";
    public static final String CODE_DIM_FLOORING_THICKNESS = "DIM_FLOORING_THICKNESS";
    public static final String CODE_TYP_LDO_SILL_CONCRETE_BEAM = "TYP_LDO_SILL_CONCRETE_BEAM";
    public static final String CODE_POS_MAIN_FLOOR_POSITION = "POS_MAIN_FLOOR_POSITION";
    public static final String CODE_DIM_BRACKETS_DISTANCE = "DIM_BRACKETS_DISTANCE";
    public static final String CODE_MAT_CAR_FRONT_WALL_A = "C_Mat";
    public static final String CODE_MAT_CAR_SIDE_WALL = "Deco_C";
    public static final String CODE_MAT_CAR_REAR_WALL = "Deco_C_Model";
    public static final String CODE_RAL_CAR_WALL = "RAL_CAR_WALL";
    public static final String CODE_DWG_CAR_CEILING = "DWG_CAR_CEILING";
    public static final String CODE_POS_CAR_MIRROR_WALL_C = "POS_CAR_MIRROR_WALL_C";
    public static final String CODE_TYP_COP_IC_CARD_READER_RES_REQ = "TYP_COP_IC_CARD_READER_RES_REQ";
    public static final String CODE_TYP_COP_IC_CARD_ACT_REQ = "TYP_COP_IC_CARD_ACT_REQ";
    public static final String CODE_TYP_ELEV_IC_CARD_MAKER_REQ = "TYP_ELEV_IC_CARD_MAKER_REQ";
    public static final String CODE_QTY_ELEV_IC_CARD = "QTY_ELEV_IC_CARD";
    public static final String CODE_TYP_CAR_STAINLESS_KICKPLATE = "TYP_CAR_STAINLESS_KICKPLATE";
    public static final String CODE_DWG_CAR_HANDRAIL = "Ele_Group";
    public static final String CODE_POS_CAR_HANDRAIL_WALL_B = "POS_CAR_HANDRAIL_WALL_B";
    public static final String CODE_POS_CAR_HANDRAIL_WALL_C = "POS_CAR_HANDRAIL_WALL_C";
    public static final String CODE_POS_CAR_HANDRAIL_WALL_D = "POS_CAR_HANDRAIL_WALL_D";
    public static final String CODE_MAT_CAR_FLOORING = "MAT_CAR_FLOORING";
    public static final String CODE_DWG_CAR_FLOORING = "DWG_CAR_FLOORING";
    public static final String CODE_DIM_CAR_FLOORING_RESERVE_SS = "DIM_CAR_FLOORING_RESERVE_SS";
    public static final String CODE_WGT_CAR_EXTRA_DECO = "WGT_CAR_EXTRA_DECO";
    public static final String CODE_WGT_CAR_DECORATION_EXTRA_TOTAL = "WGT_CAR_DECORATION_EXTRA_TOTAL";
    public static final String CODE_TYP_DOOR_OPERATOR = "RMI";
    public static final String CODE_MAT_CDO_PANEL_A = "FL_Rece";
    public static final String CODE_MAT_CDO_PANEL_C = "MAT_CDO_PANEL_C";
    public static final String CODE_RAL_CDO_PANEL_A = "RAL_CDO_PANEL_A";
    public static final String CODE_MAT_LDO_PANEL_1A = "MAT_LDO_PANEL_1A";
    public static final String CODE_MAT_LDO_PANEL_2A = "MAT_LDO_PANEL_2A";
    public static final String CODE_MAT_LDO_PANEL_1C = "MAT_LDO_PANEL_1C";
    public static final String CODE_MAT_LDO_PANEL_2C = "MAT_LDO_PANEL_2C";
    public static final String CODE_RAL_LDO_PANEL = "RAL_LDO_PANEL";
    public static final String CODE_QTY_LDO_1A = "QTY_LDO_1A";
    public static final String CODE_QTY_LDO_2A = "QTY_LDO_2A";
    public static final String CODE_QTY_LDO_1C = "QTY_LDO_1C";
    public static final String CODE_QTY_LDO_2C = "QTY_LDO_2C";
    public static final String CODE_MAT_LDO_FRAME_1A = "MAT_LDO_FRAME_1A";
    public static final String CODE_MAT_LDO_FRAME_2A = "MAT_LDO_FRAME_2A";
    public static final String CODE_MAT_LDO_FRAME_1C = "MAT_LDO_FRAME_1C";
    public static final String CODE_MAT_LDO_FRAME_2C = "MAT_LDO_FRAME_2C";
    public static final String CODE_RAL_LDO_FRAME = "RAL_LDO_FRAME";
    public static final String CODE_QTY_LDO_FRAME_1A = "QTY_LDO_FRAME_1A";
    public static final String CODE_QTY_LDO_FRAME_2A = "QTY_LDO_FRAME_2A";
    public static final String CODE_QTY_LDO_FRAME_1C = "QTY_LDO_FRAME_1C";
    public static final String CODE_QTY_LDO_FRAME_2C = "QTY_LDO_FRAME_2C";
    public static final String CODE_TYP_COP_PRODUCT = "TYP_COP_PRODUCT";
    public static final String CODE_TYP_COP_PRODUCT_1 = "TYP_COP_PRODUCT_1";
    public static final String CODE_MAT_COP_FACE_PLATE_1 = "MAT_COP_FACE_PLATE_1";
    public static final String CODE_TYP_COP_DISPLAY_1 = "TYP_COP_DISPLAY_1";
    public static final String CODE_TYP_COP_CALL_BUTTON_SHAPE_1 = "TYP_COP_CALL_BUTTON_SHAPE_1";
    public static final String CODE_TYP_COP_BTN_BRAILLE_1 = "ANN";
    public static final String CODE_TYP_COP_DEPUTY_PRODUCT = "TYP_COP_DEPUTY_PRODUCT";
    public static final String CODE_TYP_REQUIRE_HANDICAP_COP = "Voice_ANN";
    public static final String CODE_TYP_COP_HAN_KEYPAD = "TYP_COP_HAN_KEYPAD";
    public static final String CODE_MAT_COP_HAN_KEYPAD_FACE_PLATE = "MAT_COP_HAN_KEYPAD_FACE_PLATE";
    public static final String CODE_TYP_LCS_PRODUCT = "TYP_LCS_PRODUCT";
    public static final String CODE_TYP_LCI_DISPLAY = "TYP_LCI_DISPLAY";
    public static final String CODE_MAT_LCI_FACE_PLATE = "MAT_LCI_FACE_PLATE";
    public static final String CODE_MAT_LCS_FACE_PLATE = "MAT_LCS_FACE_PLATE";
    public static final String CODE_TYP_LCI_BUTTON_SHAPE = "TYP_LCI_BUTTON_SHAPE";
    public static final String CODE_S_HALLPUSHBUTTON = "S_HALLPUSHBUTTON";
    public static final String CODE_TYP_LCS_BTN_BRAILLE = "COP_IC";
    public static final String CODE_TYP_HI_DISPLAY = "TYP_HI_DISPLAY";
    public static final String CODE_QTY_HI = "QTY_HI";
    public static final String CODE_MAT_HI_FACEPLATE = "MAT_HI_FACEPLATE";
    public static final String CODE_TYP_REQUIRE_BELL_HI = "TYP_REQUIRE_BELL_HI";
    public static final String CODE_TYP_HL_FACE_SHAPE = "TYP_HL_FACE_SHAPE";
    public static final String CODE_QTY_HL = "QTY_HL";
    public static final String CODE_MAT_HL_FACEPLATE = "MAT_HL_FACEPLATE";
    public static final String CODE_TYP_REQUIRE_BELL_HL = "TYP_REQUIRE_BELL_HL";
    public static final String CODE_JFACDC = "JFACDC";
    public static final String CODE_JFBCDC = "JFBCDC";
    public static final String CODE_JFCCDC = "JFCCDC";
    public static final String CODE_JFDCDC = "JFDCDC";
    public static final String CODE_JFBHDA = "JFBHDA";
    public static final String CODE_JFBHDB = "JFBHDB";
    public static final String CODE_JFBHDC = "JFBHDC";
    public static final String CODE_JFBHDD = "JFBHDD";
    public static final String CODE_JFBYCWZ = "JFBYCWZ";
    public static final String CODE_JFFSWZ = "JFFSWZ";
    public static final String CODE_JFMWZ = "JFMWZ";
    public static final String CODE_LIOP_R_L = "LIOP_R_L";
    public static final String CODE_LIOP_DOOR = "LIOP_DOOR";
    public static final String CODE_HALLPUSHBUTTONHEIGHT = "HALLPUSHBUTTONHEIGHT";
    public static final String CODE_STB_ABCD = "STB_ABCD";
    public static final String CODE_TMDK_JDQB = "TMDK_JDQB";
    public static final String CODE_JXZX_JDZB = "JXZX_JDZB";
    public static final String CODE_JXZX_DZZX_X = "JXZX_DZZX_X";
    public static final String CODE_JXZX_DZZX_Y = "JXZX_DZZX_Y";
    public static final String CODE_JXZX_JDQB = "JXZX_JDQB";
    public static final String CODE_TYP_CWT_GR = "TYP_CWT_GR";
    public static final String CODE_TYP_CAR_GR = "TYP_CAR_GR";
    public static final String CODE_POS_OF_CWT_ENGR = "POS_OF_CWT_ENGR";
    public static final String CODE_DIM_DOOR_KMPX = "DIM_DOOR_KMPX";
    public static final String CODE_JFM_JFDCQBX = "JFM_JFDCQBX";
    public static final String CODE_JFM_JFACQBY = "JFM_JFACQBY";
    public static final String CODE_HR_MODEL = "HR_Model";
    public static final String CODE_JAMB_TYPE = "Jamb_Type";
    public static final String CODE_M_COP_M = "M_COP_M";
    public static final String CODE_RG = "RG";
    public static final String CODE_COM_MON = "Com_Mon";

    public static final String CODE_H_COP_M = "H_COP_M";
    public static final String CODE_CLO_DELAY = "Clo_Delay";

    public static final String CODE_M_COP_FP_MAT = "M_COP_FP_Mat";
    public static final String CODE_CAB_DECO_TYPE = "Cab_Deco_Type";
    public static final String CODE_FL_MODEL = "FL_Model";
    public static final String CODE_CHIME = "Chime";
    public static final String CODE_THR_ENTR = "Thr_Entr";
    public static final String CODE_SPECIALNONSTANDARD = "SpecialNonStandard";
    public static final String CODE_OPH = "OPH";
    public static final String CODE_OPW = "OPW";
    public static final String CODE_ES_BBS = "Es_BBS";
    public static final String CODE_ES_TBS = "Es_TBS";
    public static final String CODE_ES_TH = "Es_TH";
    public static final String CODE_ES_HD = "Es_HD";


    public static final int REQUEST_VIDEO_CODE = 0x1118;
    public static final int PHOTO_REQUEST_CUT = 0x111;
    public static final int PHOTO_REQUEST_GALLERY = 0x112;
    public static final int PHOTO_REQUEST_GALLERY_CODE3 = 0x1122;
    public static final int CAMERA_REQUEST_CODE3 = 0x1123;
    public static final int CAMERA_REQUEST = 0x113;
    public static final int IMAGE_CAPTURE = 0x114;
    public static final int QR_CODE = 0x1115;
    public static final int CODE2 = 0x1116;
    public static final int CODE3 = 0x1117;
    public static final int RESULT_CODE_QR_SCAN = 0xA1;
    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";


    public static final String SUB_SYSTEM = "ZXTCode";
    public static final String FWZT_CODE = "FWZTCode";
    public static final String ZCLX_CODE = "ZCLXCode";
    public static final String ZCLXCode = "ZCLXCode";
    public static final String JJQK_CODE = "JJQKCode";
    public static final String YZD_CODE = "YZDCode";
    public static final String FSPL_CODE = "FSPLCode";
    public static final String FKYY_CODE = "FKYYCode";
    public static final String WTDM_CODE = "WTDMCode";
    public static final String DW_CODE = "DWCode";
    public static final String MYDDF_CODE = "MYDDFCode";
    public static final String ZJ_CODE = "ZJCode";
    public static final String BJ_CODE = "BJCode";
    public static final String LJ_CODE = "LJCode";
    public static final String SXMS_CODE = "SXMSCode";

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

    public static final String EVENT_REFRESH_LANGUAGE = "refresh_language";

    public static final String SHARED_LANGUAGE = "LANGUAGE";

    public static final String ELEVATORTYPE = "STKJS1000";

}
