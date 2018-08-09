package com.zxtech.is.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zxtech.is.common.Constants;
import com.zxtech.is.model.Programme;
import com.zxtech.is.model.SearchBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by syp523 on 2018/1/5.
 */

public class ConvertUtil {

    private static HashMap<String, String> convertMap = null;
    private static HashMap<String, String> codeMap = null;

    public static String paramConvert(String code, String value) {
        if (convertMap == null) {
            convertMap = new HashMap<>();
            inputText();
        }
        return convertMap.get(code + "#" + value);
    }

    public static String codeConvert(String code) {
        if (codeMap == null) {
            codeMap = new HashMap<>();
            inputCode();
        }
        return codeMap.get(code) == null ? "" : codeMap.get(code);
    }

    private static void inputCode() {
        codeMap.put(Constants.CODE_ELE_M, "电梯型号");

        codeMap.put(Constants.CODE_BUIDINGNUMBER, "楼号");
        codeMap.put(Constants.CODE_APFL_UNITS_ID, "梯号");
        codeMap.put(Constants.CODE_CODE, "电梯标准");
        codeMap.put(Constants.CODE_ELEVATORTYPE, "电梯类型");
        codeMap.put(Constants.CODE_MACHINEROOM, "有无机房");
        codeMap.put(Constants.CODE_DIM_SHAFT_WIDTH_WW, "井道宽度");
        codeMap.put(Constants.CODE_DIM_SHAFT_DEPTH_WD, "井道深度");
        codeMap.put(Constants.CODE_QTY_NUMBER_OF_FLOORS, "层数");
        codeMap.put(Constants.CODE_QTY_FLOORS_SERVED_TOTAL, "站数");
        codeMap.put(Constants.CODE_VAL_RATED_SPEED, "额定速度 m/s");
        codeMap.put(Constants.CODE_WGT_RATED_LOAD_Q, "额定载重量 kg");
        codeMap.put(Constants.CODE_QTY_NUMBER_OF_PASSENGERS, "乘客人数");
        codeMap.put(Constants.CODE_DIM_TRAVEL_HEIGHT_H, "提升高度 mm");
        codeMap.put(Constants.CODE_DIM_HEADROOM_SH, "顶层高 mm");
        codeMap.put(Constants.CODE_DIM_PIT_HEIGHT_PH, "底坑深 mm");
        codeMap.put(Constants.CODE_DIM_SHAFT_HEIGHT, "井道总高");
        codeMap.put(Constants.CODE_DIM_CAR_SHELL_WIDTH_BB, "轿厢宽 mm");
        codeMap.put(Constants.CODE_DIM_CAR_SHELL_DEPTH_DD, "轿厢深 mm");
        codeMap.put(Constants.CODE_DIM_CAR_HEIGHT_CH, "轿厢高 mm");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_TTC, "是否贯通");
        codeMap.put(Constants.CODE_DIM_DOOR_LL_A, "开门距(A侧) mm");
        codeMap.put(Constants.CODE_DIM_CDO_HH_A, "净门高");
        codeMap.put(Constants.CODE_TYP_DOOR_A, "开门方式(A侧)");
        codeMap.put(Constants.CODE_TYP_DOOR_C, "C侧开门方式");
        codeMap.put(Constants.CODE_QTY_LDO_A, "厅门数量（A侧）");
        codeMap.put(Constants.CODE_QTY_LDO_C, "厅门数量（C侧）");
        codeMap.put(Constants.CODE_POS_OF_CWT, "对重位置");
        codeMap.put(Constants.CODE_QTY_EMERGENCY_DOOR, "井道安全门");
        codeMap.put(Constants.CODE_SUPPLYPOWER, "供电电压");
        codeMap.put(Constants.CODE_FREQUENCY, "电源频率");
        codeMap.put(Constants.CODE_LIGHTING, "照明电压");
        codeMap.put(Constants.CODE_TYP_CONTROL_SYSTEM, "控制方式");
        codeMap.put(Constants.CODE_GG019, "布置方式");
        codeMap.put(Constants.CODE_QTY_GROUP_SIZE, "群控数量");
        codeMap.put(Constants.CODE_LANGUAGE, "语音类型");
        codeMap.put(Constants.CODE_TYP_CONTROL_SYSTEM_ORIGIN, "控制系统");
        codeMap.put(Constants.CODE_TYP_DRIVE_SYSTEM_ORIGIN, "驱动方式");
        codeMap.put(Constants.CODE_TYP_CAR_RIDE_COMFORT, "轿内隔音处理");
        codeMap.put(Constants.CODE_TYP_CAR_AIR_CONDITION_PORT_REQ, "音频功能");
        codeMap.put(Constants.CODE_TYP_CAR_GUIDE_SHOE, "轿厢导靴");
        codeMap.put(Constants.CODE_TYP_DRIVE_ORIGIN, "担架功能");
        codeMap.put(Constants.CODE_TYP_DOOR_OPERATOR, "远程监控");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_BMV, "预开门");
        codeMap.put(Constants.CODE_MAT_CAR_FRONT_WALL_A, "前轿壁材料");
        codeMap.put(Constants.CODE_MAT_CAR_SIDE_WALL, "吊顶预留");
        codeMap.put(Constants.CODE_MAT_CAR_REAR_WALL, "吊顶型号");
        codeMap.put(Constants.CODE_MAT_CDO_PANEL_A, "轿底预留");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_FRD, "消防功能");
        codeMap.put(Constants.CODE_TYP_CDO_SAFETY_DEVICE, "安全钳进口");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_EAQ, "限速器进口");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_CRM, "缓冲器进口");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_EBD, "门保护");
        codeMap.put(Constants.CODE_DWG_CAR_HANDRAIL, "控制类型");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_ACU, "残障功能");
        codeMap.put(Constants.CODE_TYP_COP_BTN_BRAILLE_1, "强制关门");
        codeMap.put(Constants.CODE_TYP_REQUIRE_HANDICAP_COP, "语音报站");
        codeMap.put(Constants.CODE_TYP_LCS_BTN_BRAILLE, "操纵盘带IC卡");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_ISE, "五方通话");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_CTV, "预留视频接口");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_AUD, "音频接口");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_ABE, "警铃");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_LIL, "BA接口");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_FRE, "快速召回");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_PRL, "外呼优先服务");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_ATS, "司机服务");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_GOC, "轿厢到站钟");
        codeMap.put(Constants.CODE_TYP_CAR_TRAP_DOOR, "轿顶安全窗");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_EPS, "备用电源转换装置");
        codeMap.put(Constants.CODE_TYP_SIGNALISATION_SERIES, "信号系统类型");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_NUD, "强制关门");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_LOC, "内呼锁定");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_LOL, "外呼锁定");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_OCL, "轿厢照明功能");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_OCV, "轿厢通风功能");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_DOE, "开门延时功能");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_OSS, "轿内退出服务");
        codeMap.put(Constants.CODE_TYP_CAR_GUIDE_SHOE, "门机型号");
        codeMap.put(Constants.CODE_TYP_ELEC_FEAT_CSM, "上行强制停靠基站");
        codeMap.put(Constants.CODE_VAL_LIGHT_VOLTAGE_SH, "底坑照明电压（V）");
        codeMap.put(Constants.CODE_TYP_CWT_GUIDE_SHOE, "对重导靴");
        codeMap.put(Constants.CODE_TYP_IMPORT_COMPONENT, "进口部件");
        codeMap.put(Constants.CODE_TYP_CWT_SAFETY_GEAR_SG, "对重安全钳");
        codeMap.put(Constants.CODE_MAT_CWT_FILLER, "对重块是否采用铸铁");
        codeMap.put(Constants.CODE_TYP_WELL_STRUCTURE, "井道结构 Well structure");
        codeMap.put(Constants.CODE_FLOORHEIGHT_YTH, "楼层高");
        codeMap.put(Constants.CODE_B_JDBH_C, "井道中间墙厚");
        codeMap.put(Constants.CODE_JDBH_Q, "井道前壁厚");
        codeMap.put(Constants.CODE_JDBH_Z, "井道左壁厚");
        codeMap.put(Constants.CODE_JDBH_Y, "井道右壁厚");
        codeMap.put(Constants.CODE_JDBH_H, "井道后壁厚");
        codeMap.put(Constants.CODE_STARTFLOOR, "起始楼层");
        codeMap.put(Constants.CODE_ENDFLOOR, "结束楼层");
        codeMap.put(Constants.CODE_DIM_MACHINE_ROOM_HEIGHT_MH, "机房高度");
        codeMap.put(Constants.CODE_DIM_MR_RAISED_HEIGHT, "机房局部抬高");
        codeMap.put(Constants.CODE_DIM_FLOORING_THICKNESS, "楼面装饰层厚度");
        codeMap.put(Constants.CODE_TYP_LDO_SILL_CONCRETE_BEAM, "有无牛腿");
        codeMap.put(Constants.CODE_POS_MAIN_FLOOR_POSITION, "基站位置");
        codeMap.put(Constants.CODE_DIM_BRACKETS_DISTANCE, "导轨支架间距");
        codeMap.put(Constants.CODE_MAT_CAR_FRONT_WALL_A, "轿顶材质");
        codeMap.put(Constants.CODE_RAL_CAR_WALL, "轿厢壁颜色(涂装钢板时)");
        codeMap.put(Constants.CODE_DWG_CAR_CEILING, "吊顶型号");
        codeMap.put(Constants.CODE_POS_CAR_MIRROR_WALL_C, "预留后壁半身镜");
        codeMap.put(Constants.CODE_TYP_COP_IC_CARD_READER_RES_REQ, "预留轿内IC卡");
        codeMap.put(Constants.CODE_TYP_COP_IC_CARD_ACT_REQ, "轿内IC卡");
        codeMap.put(Constants.CODE_TYP_ELEV_IC_CARD_MAKER_REQ, "注册机");
        codeMap.put(Constants.CODE_QTY_ELEV_IC_CARD, "IC卡片数量");
        codeMap.put(Constants.CODE_TYP_CAR_STAINLESS_KICKPLATE, "不锈钢踢脚线");
        codeMap.put(Constants.CODE_POS_CAR_HANDRAIL_WALL_B, "B侧扶手");
        codeMap.put(Constants.CODE_POS_CAR_HANDRAIL_WALL_C, "C侧扶手");
        codeMap.put(Constants.CODE_POS_CAR_HANDRAIL_WALL_D, "D侧扶手");
        codeMap.put(Constants.CODE_MAT_CAR_FLOORING, "地板装潢材料");
        codeMap.put(Constants.CODE_DWG_CAR_FLOORING, "轿厢地板材料");
        codeMap.put(Constants.CODE_DIM_CAR_FLOORING_RESERVE_SS, "轿厢地板厚度");
        codeMap.put(Constants.CODE_WGT_CAR_EXTRA_DECO, "轿厢额外装潢重量");
        codeMap.put(Constants.CODE_WGT_CAR_DECORATION_EXTRA_TOTAL, "轿厢额外装潢总重量");
        codeMap.put(Constants.CODE_MAT_CDO_PANEL_C, "C侧轿厢门材料");
        codeMap.put(Constants.CODE_RAL_CDO_PANEL_A, "轿厢门颜色（涂装钢板时）");
        codeMap.put(Constants.CODE_MAT_LDO_PANEL_1A, "A侧第一种厅门材料");
        codeMap.put(Constants.CODE_MAT_LDO_PANEL_2A, "A侧第二种厅门材料");
        codeMap.put(Constants.CODE_MAT_LDO_PANEL_1C, "C侧第一种厅门材料");
        codeMap.put(Constants.CODE_MAT_LDO_PANEL_2C, "C侧第二种厅门材料");
        codeMap.put(Constants.CODE_RAL_LDO_PANEL, "厅门颜色");
        codeMap.put(Constants.CODE_QTY_LDO_1A, "A侧第一种厅门材料数量");
        codeMap.put(Constants.CODE_QTY_LDO_2A, "A侧第二种厅门材料数量");
        codeMap.put(Constants.CODE_QTY_LDO_1C, "C侧第一种厅门材料数量");
        codeMap.put(Constants.CODE_QTY_LDO_2C, "C侧第二种厅门材料数量");
        codeMap.put(Constants.CODE_MAT_LDO_FRAME_1A, "A侧第一种小门套材料");
        codeMap.put(Constants.CODE_MAT_LDO_FRAME_2A, "A侧第二种小门套材料");
        codeMap.put(Constants.CODE_MAT_LDO_FRAME_1C, "C侧第一种小门套材料");
        codeMap.put(Constants.CODE_MAT_LDO_FRAME_2C, "C侧第二种小门套材料");
        codeMap.put(Constants.CODE_RAL_LDO_FRAME, "小门套颜色");
        codeMap.put(Constants.CODE_QTY_LDO_FRAME_1A, "A侧第一种小门套材料数量");
        codeMap.put(Constants.CODE_QTY_LDO_FRAME_2A, "A侧第二种小门套材料数量");
        codeMap.put(Constants.CODE_QTY_LDO_FRAME_1C, "C侧第一种小门套材料数量");
        codeMap.put(Constants.CODE_QTY_LDO_FRAME_2C, "C侧第二种小门套材料数量");
        codeMap.put(Constants.CODE_TYP_COP_PRODUCT, "操纵器类型");
        codeMap.put(Constants.CODE_TYP_COP_PRODUCT_1, "主操纵器类型");
        codeMap.put(Constants.CODE_MAT_COP_FACE_PLATE_1, "主操纵器面板材料");
        codeMap.put(Constants.CODE_TYP_COP_DISPLAY_1, "主操纵器显示类型");
        codeMap.put(Constants.CODE_TYP_COP_CALL_BUTTON_SHAPE_1, "主操纵器按钮类型");
        codeMap.put(Constants.CODE_TYP_COP_DEPUTY_PRODUCT, "副操纵器型号");
        codeMap.put(Constants.CODE_TYP_COP_HAN_KEYPAD, "键盘式残疾人操纵器类型");
        codeMap.put(Constants.CODE_MAT_COP_HAN_KEYPAD_FACE_PLATE, "键盘式残疾人操纵器面板材料");
        codeMap.put(Constants.CODE_TYP_LCS_PRODUCT, "呼梯盒类型");
        codeMap.put(Constants.CODE_TYP_LCI_DISPLAY, "呼梯盒显示类型");
        codeMap.put(Constants.CODE_MAT_LCI_FACE_PLATE, "带显示呼梯盒面板材料");
        codeMap.put(Constants.CODE_MAT_LCS_FACE_PLATE, "不带显示呼梯盒面板材料");
        codeMap.put(Constants.CODE_TYP_LCI_BUTTON_SHAPE, "带显示呼梯盒按钮类型");
        codeMap.put(Constants.CODE_S_HALLPUSHBUTTON, "外呼是否共用");
        codeMap.put(Constants.CODE_TYP_HI_DISPLAY, "楼层显示显示类型");
        codeMap.put(Constants.CODE_QTY_HI, "楼层显示数量");
        codeMap.put(Constants.CODE_MAT_HI_FACEPLATE, "楼层显示面板材料");
        codeMap.put(Constants.CODE_TYP_REQUIRE_BELL_HI, "楼层显示是否带厅站到站钟");
        codeMap.put(Constants.CODE_TYP_HL_FACE_SHAPE, "方向显示类型");
        codeMap.put(Constants.CODE_QTY_HL, "方向显示数量");
        codeMap.put(Constants.CODE_MAT_HL_FACEPLATE, "方向显示面板材料");
        codeMap.put(Constants.CODE_TYP_REQUIRE_BELL_HL, "方向显示是否带厅站到站钟");
        codeMap.put(Constants.CODE_JFACDC, "机房A侧大出");
        codeMap.put(Constants.CODE_JFBCDC, "机房B侧大出");
        codeMap.put(Constants.CODE_JFCCDC, "机房C侧大出");
        codeMap.put(Constants.CODE_JFDCDC, "机房D侧大出");
        codeMap.put(Constants.CODE_JFBHDA, "机房壁厚度A");
        codeMap.put(Constants.CODE_JFBHDB, "机房壁厚度B");
        codeMap.put(Constants.CODE_JFBHDC, "机房壁厚度C");
        codeMap.put(Constants.CODE_JFBHDD, "机房壁厚度D");
        codeMap.put(Constants.CODE_JFBYCWZ, "机房百叶窗位置");
        codeMap.put(Constants.CODE_JFFSWZ, "机房风扇位置");
        codeMap.put(Constants.CODE_JFMWZ, "机房门位置");
        codeMap.put(Constants.CODE_LIOP_R_L, "外呼在左手边or右手边");
        codeMap.put(Constants.CODE_LIOP_DOOR, "外呼距离开门边");
        codeMap.put(Constants.CODE_HALLPUSHBUTTONHEIGHT, "外呼距地面高度");
        codeMap.put(Constants.CODE_STB_ABCD, "绳头板槽钢搭设墙");
        codeMap.put(Constants.CODE_TMDK_JDQB, "厅门地坎至井道前壁");
        codeMap.put(Constants.CODE_JXZX_JDZB, "轿厢中心至井道左壁");
        codeMap.put(Constants.CODE_JXZX_DZZX_X, "轿厢中心至对重中心X");
        codeMap.put(Constants.CODE_JXZX_DZZX_Y, "轿厢中心至对重中心Y");
        codeMap.put(Constants.CODE_JXZX_JDQB, "轿厢中心至井道前壁");
        codeMap.put(Constants.CODE_TYP_CWT_GR, "对重导轨类型");
        codeMap.put(Constants.CODE_TYP_CAR_GR, "轿厢导轨类型");
        codeMap.put(Constants.CODE_POS_OF_CWT_ENGR, "对重位置");
        codeMap.put(Constants.CODE_DIM_DOOR_KMPX, "开门偏心");
        codeMap.put(Constants.CODE_JFM_JFDCQBX, "机房门X方向距机房D侧");
        codeMap.put(Constants.CODE_JFM_JFACQBY, "机房门Y方向距机房A侧");
        codeMap.put(Constants.CODE_COM_MON, "小区监控");
        codeMap.put(Constants.CODE_H_COP_M, "残操纵盘型号");
        codeMap.put(Constants.CODE_CLO_DELAY, " 关门延时");
        codeMap.put(Constants.CODE_CHIME, " 轿内到站钟");
        codeMap.put(Constants.CODE_RG, " 滚轮导靴");
        codeMap.put(Constants.CODE_THR_ENTR, " 是否贯通");

    }

    private static void inputText() {
        convertMap.put(Constants.CODE_BUIDINGNUMBER + "#B1", "B1");
        convertMap.put(Constants.CODE_APFL_UNITS_ID + "#L1", "L1");
        convertMap.put(Constants.CODE_APFL_UNITS_ID + "#L2", "L2");
        convertMap.put(Constants.CODE_APFL_UNITS_ID + "#L3", "L3");
        convertMap.put(Constants.CODE_APFL_UNITS_ID + "#L4", "L4");
        convertMap.put(Constants.CODE_APFL_UNITS_ID + "#L5", "L5");
        convertMap.put(Constants.CODE_APFL_UNITS_ID + "#L6", "L6");
        convertMap.put(Constants.CODE_APFL_UNITS_ID + "#L7", "L7");
        convertMap.put(Constants.CODE_APFL_UNITS_ID + "#L8", "L8");
        convertMap.put(Constants.CODE_CODE + "#GB 7588-2003", "GB 7588-2003");
        convertMap.put(Constants.CODE_CODE + "#NA", "NA");
        convertMap.put(Constants.CODE_ELEVATORTYPE + "#客梯", "客梯");
        convertMap.put(Constants.CODE_ELEVATORTYPE + "#货梯", "货梯");
        convertMap.put(Constants.CODE_MACHINEROOM + "#YES", "YES");
        convertMap.put(Constants.CODE_MACHINEROOM + "#NO", "NO");
        convertMap.put(Constants.CODE_VAL_RATED_SPEED + "#1.0", "1.0");
        convertMap.put(Constants.CODE_VAL_RATED_SPEED + "#1.6", "1.6");
        convertMap.put(Constants.CODE_VAL_RATED_SPEED + "#1.75", "1.75");
        convertMap.put(Constants.CODE_VAL_RATED_SPEED + "#2.0", "2.0");
        convertMap.put(Constants.CODE_VAL_RATED_SPEED + "#2.5", "2.5");
        convertMap.put(Constants.CODE_VAL_RATED_SPEED + "#3.0", "3.0");
        convertMap.put(Constants.CODE_VAL_RATED_SPEED + "#3.5", "3.5");
        convertMap.put(Constants.CODE_VAL_RATED_SPEED + "#4.0", "4.0");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_TTC + "#0", "否");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_TTC + "#1", "是");
        convertMap.put(Constants.CODE_TYP_DOOR_A + "#1C", "1C");
        convertMap.put(Constants.CODE_TYP_DOOR_A + "#2L", "2L");
        convertMap.put(Constants.CODE_TYP_DOOR_A + "#2R", "2R");
        convertMap.put(Constants.CODE_TYP_DOOR_A + "#2C", "2C");
        convertMap.put(Constants.CODE_TYP_DOOR_A + "#3C", "3C");
        convertMap.put(Constants.CODE_POS_OF_CWT + "#B", "侧左");
        convertMap.put(Constants.CODE_POS_OF_CWT + "#C", "后侧");
        convertMap.put(Constants.CODE_POS_OF_CWT + "#D", "侧右");
        convertMap.put(Constants.CODE_TYP_CONTROL_SYSTEM + "#COL", "集选");
        convertMap.put(Constants.CODE_TYP_CONTROL_SYSTEM + "#DD", "并联");
        convertMap.put(Constants.CODE_TYP_CONTROL_SYSTEM + "#GROUP", "群控");
        convertMap.put(Constants.CODE_GG019 + "#单梯", "单梯");
        convertMap.put(Constants.CODE_GG019 + "#双梯", "双梯并联");
        convertMap.put(Constants.CODE_GG019 + "#三梯", "三梯群控");
        convertMap.put(Constants.CODE_GG019 + "#四梯", "四梯群控");
        convertMap.put(Constants.CODE_LANGUAGE + "#中文", "中文");
        convertMap.put(Constants.CODE_LANGUAGE + "#英文", "英文");
        convertMap.put(Constants.CODE_TYP_DRIVE_SYSTEM_ORIGIN + "#VVVF", "VVVF");
        convertMap.put(Constants.CODE_TYP_DRIVE_SYSTEM_ORIGIN + "#NA", "NA");
        convertMap.put(Constants.CODE_TYP_CAR_RIDE_COMFORT + "#无隔音处理", "无隔音处理");
        convertMap.put(Constants.CODE_TYP_CAR_RIDE_COMFORT + "#轿壁板厚度增至1.5mm", "轿壁板厚度增至1.5mm");
        convertMap.put(Constants.CODE_TYP_CAR_RIDE_COMFORT + "#增加隔音减振板", "增加隔音减振板");
        convertMap.put(Constants.CODE_TYP_CAR_AIR_CONDITION_PORT_REQ + "#YES", "YES");
        convertMap.put(Constants.CODE_TYP_CAR_AIR_CONDITION_PORT_REQ + "#NO", "NO");
        convertMap.put(Constants.CODE_TYP_CONTROL_SYSTEM_ORIGIN + "#iSTAR-AS380", "iSTAR-AS380");
        convertMap.put(Constants.CODE_TYP_CONTROL_SYSTEM_ORIGIN + "#NICE3000", "NICE3000");
        convertMap.put(Constants.CODE_TYP_CONTROL_SYSTEM_ORIGIN + "#BL6-U", "BL6-U");
        convertMap.put(Constants.CODE_TYP_CAR_GUIDE_SHOE + "#FECO.02A", "FECO.02A");
        convertMap.put(Constants.CODE_TYP_CAR_GUIDE_SHOE + "#FECO.01A", "FECO.01A");
        convertMap.put(Constants.CODE_TYP_CAR_GUIDE_SHOE + "#FECO.02C/FS", "FECO.02C/FS");
        convertMap.put(Constants.CODE_TYP_CAR_GUIDE_SHOE + "#FESO.02A", "FESO.02A");
        convertMap.put(Constants.CODE_TYP_CAR_GUIDE_SHOE + "#FESD.01", "FESD.01");
        convertMap.put(Constants.CODE_TYP_CAR_GUIDE_SHOE + "#FESO.02C/FS", "FESO.02C/FS");
        convertMap.put(Constants.CODE_TYP_DRIVE_ORIGIN + "#LOCAL", "LOCAL");
        convertMap.put(Constants.CODE_TYP_DRIVE_ORIGIN + "#IMPORT", "IMPORT");
        convertMap.put(Constants.CODE_TYP_DOOR_OPERATOR + "#YES", "YES");
        convertMap.put(Constants.CODE_TYP_DOOR_OPERATOR + "#NO", "NO");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_BMV + "#YES", "YES");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_BMV + "#NO", "NO");
        convertMap.put(Constants.CODE_MAT_CAR_FRONT_WALL_A + "#钢板喷涂", "钢板喷涂");
        convertMap.put(Constants.CODE_MAT_CAR_FRONT_WALL_A + "#443发纹不锈钢", "443发纹不锈钢");
        convertMap.put(Constants.CODE_MAT_CAR_FRONT_WALL_A + "#304发纹不锈钢", "304发纹不锈钢");
        convertMap.put(Constants.CODE_MAT_CAR_FRONT_WALL_A + "#镜面不锈钢", "镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_CAR_FRONT_WALL_A + "#NA", "NA");
        convertMap.put(Constants.CODE_MAT_CAR_SIDE_WALL + "#YES", "YES");
        convertMap.put(Constants.CODE_MAT_CAR_SIDE_WALL + "#NO", "NO");

        convertMap.put(Constants.CODE_MAT_CAR_REAR_WALL + "#BF-806D", "BF-806D");
        convertMap.put(Constants.CODE_MAT_CAR_REAR_WALL + "#BF-821D", "BF-821D");
        convertMap.put(Constants.CODE_MAT_CAR_REAR_WALL + "#BF-846D", "BF-846D");
        convertMap.put(Constants.CODE_MAT_CAR_REAR_WALL + "#BF-835D", "BF-835D");
        convertMap.put(Constants.CODE_MAT_CAR_REAR_WALL + "#BF-811D", "BF-811D");
        convertMap.put(Constants.CODE_MAT_CAR_REAR_WALL + "#BF-827D", "BF-827D");
        convertMap.put(Constants.CODE_MAT_CAR_REAR_WALL + "#无", "无");
        convertMap.put(Constants.CODE_MAT_CDO_PANEL_A + "#YES", "YES");
        convertMap.put(Constants.CODE_MAT_CDO_PANEL_A + "#NO", "NO");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_FRD + "#YES", "YES");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_FRD + "#NO", "NO");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_EAQ + "#YES", "YES");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_EAQ + "#NO", "NO");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_CRM + "#NO", "NO");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_CRM + "#YES", "YES");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_EBD + "#单一光幕", "单一光幕");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_EBD + "#二合一光幕", "二合一光幕");
        convertMap.put(Constants.CODE_DWG_CAR_HANDRAIL + "#单梯", "单梯");
        convertMap.put(Constants.CODE_DWG_CAR_HANDRAIL + "#并联", "并联");
        convertMap.put(Constants.CODE_DWG_CAR_HANDRAIL + "#群控", "群控");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_ACU + "#YES", "YES");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_ACU + "#NO", "NO");
        convertMap.put(Constants.CODE_TYP_COP_BTN_BRAILLE_1 + "#YES", "YES");
        convertMap.put(Constants.CODE_TYP_COP_BTN_BRAILLE_1 + "#NO", "NO");
        convertMap.put(Constants.CODE_TYP_REQUIRE_HANDICAP_COP + "#YES", "YES");
        convertMap.put(Constants.CODE_TYP_REQUIRE_HANDICAP_COP + "#NO", "NO");
        convertMap.put(Constants.CODE_TYP_LCS_BTN_BRAILLE + "#YES", "YES");
        convertMap.put(Constants.CODE_TYP_LCS_BTN_BRAILLE + "#NO", "NO");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_ISE + "#0", "无");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_ISE + "#1", "有");
        convertMap.put(Constants.CODE_TYP_CDO_SAFETY_DEVICE + "#YES", "YES");
        convertMap.put(Constants.CODE_TYP_CDO_SAFETY_DEVICE + "#NO", "NO");
        convertMap.put(Constants.CODE_TYP_CAR_RIDE_COMFORT + "#1", "1");
        convertMap.put(Constants.CODE_TYP_CAR_RIDE_COMFORT + "#2", "2");
        convertMap.put(Constants.CODE_TYP_CAR_RIDE_COMFORT + "#3", "3");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_CTV + "#YES", "YES");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_CTV + "#NO", "NO");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_AUD + "#0", "无");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_AUD + "#AUD", "有");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_ABE + "#0", "无");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_ABE + "#1", "有");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_LIL + "#0", "无");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_LIL + "#1", "有");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_FRE + "#0", "否");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_FRE + "#1", "是");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_PRL + "#0", "无");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_PRL + "#1", "有");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_ATS + "#NO", "NO");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_ATS + "#YES", "YES");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_GOC + "#0", "无");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_GOC + "#1", "有");
        convertMap.put(Constants.CODE_TYP_CAR_TRAP_DOOR + "#0", "无");
        convertMap.put(Constants.CODE_TYP_CAR_TRAP_DOOR + "#1", "有");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_EPS + "#0", "无");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_EPS + "#EPS", "有");
        convertMap.put(Constants.CODE_TYP_DRIVE_ORIGIN + "#NO", "NO");
        convertMap.put(Constants.CODE_TYP_DRIVE_ORIGIN + "#YES", "YES");
        convertMap.put(Constants.CODE_TYP_SIGNALISATION_SERIES + "#108", "108");
        convertMap.put(Constants.CODE_TYP_SIGNALISATION_SERIES + "#118", "118");
        convertMap.put(Constants.CODE_TYP_SIGNALISATION_SERIES + "#128", "128");
        convertMap.put(Constants.CODE_TYP_SIGNALISATION_SERIES + "#158", "158");
        convertMap.put(Constants.CODE_TYP_SIGNALISATION_SERIES + "#168", "168");
        convertMap.put(Constants.CODE_TYP_SIGNALISATION_SERIES + "#188", "188");
        convertMap.put(Constants.CODE_TYP_SIGNALISATION_SERIES + "#198", "198");
        convertMap.put(Constants.CODE_TYP_SIGNALISATION_SERIES + "#228", "228");
        convertMap.put(Constants.CODE_TYP_SIGNALISATION_SERIES + "#258", "258");
        convertMap.put(Constants.CODE_TYP_SIGNALISATION_SERIES + "#268", "268");
        convertMap.put(Constants.CODE_TYP_SIGNALISATION_SERIES + "#278", "278");
        convertMap.put(Constants.CODE_TYP_SIGNALISATION_SERIES + "#318", "318");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_NUD + "#0", "无");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_NUD + "#1", "有");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_LOC + "#0", "无");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_LOC + "#1", "有");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_LOL + "#0", "无");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_LOL + "#1", "有");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_OCL + "#0", "无");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_OCL + "#1", "有");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_OCV + "#0", "无");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_OCV + "#1", "有");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_DOE + "#0", "无");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_DOE + "#1", "有");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_OSS + "#0", "无");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_OSS + "#1", "有");
        convertMap.put(Constants.CODE_TYP_CAR_GUIDE_SHOE + "#S", "滑动导靴");
        convertMap.put(Constants.CODE_TYP_CAR_GUIDE_SHOE + "#R", "滚动导靴");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_CSM + "#0", "无");
        convertMap.put(Constants.CODE_TYP_ELEC_FEAT_CSM + "#1", "有");
        convertMap.put(Constants.CODE_VAL_LIGHT_VOLTAGE_SH + "#36", "36");
        convertMap.put(Constants.CODE_VAL_LIGHT_VOLTAGE_SH + "#220", "220");
        convertMap.put(Constants.CODE_TYP_CWT_GUIDE_SHOE + "#SLG", "滑动导靴");
        convertMap.put(Constants.CODE_TYP_CWT_GUIDE_SHOE + "#RG", "滚动导靴");
        convertMap.put(Constants.CODE_TYP_IMPORT_COMPONENT + "#BUFFER", "进口缓冲器");
        convertMap.put(Constants.CODE_TYP_IMPORT_COMPONENT + "#CAR_OSG", "进口轿厢限速器");
        convertMap.put(Constants.CODE_TYP_IMPORT_COMPONENT + "#CAR_SG", "进口轿厢安全钳");
        convertMap.put(Constants.CODE_TYP_IMPORT_COMPONENT + "#CWT_OSG", "进口对重限速器");
        convertMap.put(Constants.CODE_TYP_IMPORT_COMPONENT + "#HOIST_ROPE", "进口曳引钢丝绳");
        convertMap.put(Constants.CODE_TYP_IMPORT_COMPONENT + "#MACHINE", "进口曳引机");
        convertMap.put(Constants.CODE_TYP_IMPORT_COMPONENT + "#OSG_ROPE", "进口限速器钢丝绳");
        convertMap.put(Constants.CODE_TYP_IMPORT_COMPONENT + "#CAR_RGS", "进口轿厢滚动导靴");
        convertMap.put(Constants.CODE_TYP_IMPORT_COMPONENT + "#CWT_RGS", "进口对重滚动导靴");
        convertMap.put(Constants.CODE_TYP_CWT_SAFETY_GEAR_SG + "#0", "无");
        convertMap.put(Constants.CODE_TYP_CWT_SAFETY_GEAR_SG + "#1", "有");
        convertMap.put(Constants.CODE_MAT_CWT_FILLER + "#0", "无");
        convertMap.put(Constants.CODE_MAT_CWT_FILLER + "#1", "有");
        convertMap.put(Constants.CODE_TYP_WELL_STRUCTURE + "#1", "混凝土");
        convertMap.put(Constants.CODE_TYP_WELL_STRUCTURE + "#2", "圈梁");
        convertMap.put(Constants.CODE_TYP_WELL_STRUCTURE + "#3", "钢结构");
        convertMap.put(Constants.CODE_TYP_LDO_SILL_CONCRETE_BEAM + "#0", "无");
        convertMap.put(Constants.CODE_TYP_LDO_SILL_CONCRETE_BEAM + "#1", "有");
        convertMap.put(Constants.CODE_POS_MAIN_FLOOR_POSITION + "#最底层", "最底层");
        convertMap.put(Constants.CODE_POS_MAIN_FLOOR_POSITION + "#中间层", "中间层");
        convertMap.put(Constants.CODE_POS_MAIN_FLOOR_POSITION + "#最顶层", "最顶层");
        convertMap.put(Constants.CODE_RAL_CAR_WALL + "#GS001", "GS001");
        convertMap.put(Constants.CODE_RAL_CAR_WALL + "#GS002", "GS002");
        convertMap.put(Constants.CODE_RAL_CAR_WALL + "#GS004", "GS004");
        convertMap.put(Constants.CODE_RAL_CAR_WALL + "#GS007", "GS007");
        convertMap.put(Constants.CODE_RAL_CAR_WALL + "#GS008", "GS008");
        convertMap.put(Constants.CODE_RAL_CAR_WALL + "#GS009", "GS009");
        convertMap.put(Constants.CODE_RAL_CAR_WALL + "#GS0010", "GS0010");
        convertMap.put(Constants.CODE_RAL_CAR_WALL + "#GS0012", "GS0012");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G1025003A", "G1025003A");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G1025007", "G1025007");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G1025011", "G1025011");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G1025018", "G1025018");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G1025022", "G1025022");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G1025023", "G1025023");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G1025024", "G1025024");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G1025025", "G1025025");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G1025027", "G1025027");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G1025030", "G1025030");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G1025031", "G1025031");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G1025041", "G1025041");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G1025037", "G1025037");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G1025038", "G1025038");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G1025039", "G1025039");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G1025040", "G1025040");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G32027", "G32027");
        convertMap.put(Constants.CODE_DWG_CAR_CEILING + "#G32026", "G32026");
        convertMap.put(Constants.CODE_POS_CAR_MIRROR_WALL_C + "#0", "无");
        convertMap.put(Constants.CODE_POS_CAR_MIRROR_WALL_C + "#1", "有");
        convertMap.put(Constants.CODE_TYP_COP_IC_CARD_READER_RES_REQ + "#0", "无");
        convertMap.put(Constants.CODE_TYP_COP_IC_CARD_READER_RES_REQ + "#1", "有");
        convertMap.put(Constants.CODE_TYP_COP_IC_CARD_ACT_REQ + "#0", "无");
        convertMap.put(Constants.CODE_TYP_COP_IC_CARD_ACT_REQ + "#1", "有");
        convertMap.put(Constants.CODE_TYP_ELEV_IC_CARD_MAKER_REQ + "#0", "无");
        convertMap.put(Constants.CODE_TYP_ELEV_IC_CARD_MAKER_REQ + "#1", "有");
        convertMap.put(Constants.CODE_QTY_ELEV_IC_CARD + "#0", "无");
        convertMap.put(Constants.CODE_QTY_ELEV_IC_CARD + "#1", "有");
        convertMap.put(Constants.CODE_TYP_CAR_STAINLESS_KICKPLATE + "#0", "无");
        convertMap.put(Constants.CODE_TYP_CAR_STAINLESS_KICKPLATE + "#1", "有");
        convertMap.put(Constants.CODE_POS_CAR_HANDRAIL_WALL_B + "#0", "无");
        convertMap.put(Constants.CODE_POS_CAR_HANDRAIL_WALL_B + "#1", "有");
        convertMap.put(Constants.CODE_POS_CAR_HANDRAIL_WALL_C + "#0", "无");
        convertMap.put(Constants.CODE_POS_CAR_HANDRAIL_WALL_C + "#1", "有");
        convertMap.put(Constants.CODE_POS_CAR_HANDRAIL_WALL_D + "#0", "无");
        convertMap.put(Constants.CODE_POS_CAR_HANDRAIL_WALL_D + "#1", "有");
        convertMap.put(Constants.CODE_MAT_CAR_FLOORING + "#PVC", "PVC");
        convertMap.put(Constants.CODE_MAT_CAR_FLOORING + "#MA", "大理石");
        convertMap.put(Constants.CODE_MAT_CAR_FLOORING + "#0", "客户自理");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#BT0010", "BT0010");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003009", "G4003009");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#51052853", "51052853");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#51052854", "51052854");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#51052855", "51052855");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#51052856", "51052856");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#51052857", "51052857");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#51052859", "51052859");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#51056032", "51056032");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#51056033", "51056033");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#51056034", "51056034");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#51056035", "51056035");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#51056036", "51056036");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#51056037", "51056037");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003008", "G4003008");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003011", "G4003011");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003012", "G4003012");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003018", "G4003018");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003023", "G4003023");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4083001", "G4083001");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4083002", "G4083002");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4083003", "G4083003");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003013", "G4003013");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003014", "G4003014");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003015", "G4003015");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003016", "G4003016");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003017", "G4003017");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003019", "G4003019");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003020", "G4003020");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003021", "G4003021");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003022", "G4003022");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003003", "G4003003");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003005", "G4003005");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003006", "G4003006");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#G4003010", "G4003010");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#DIAMOND PLATE", "DIAMOND PLATE");
        convertMap.put(Constants.CODE_DWG_CAR_FLOORING + "#NA", "NA");
        convertMap.put(Constants.CODE_DIM_CAR_FLOORING_RESERVE_SS + "#3", "3");
        convertMap.put(Constants.CODE_DIM_CAR_FLOORING_RESERVE_SS + "#20", "20");
        convertMap.put(Constants.CODE_DIM_CAR_FLOORING_RESERVE_SS + "#25", "25");
        convertMap.put(Constants.CODE_WGT_CAR_EXTRA_DECO + "#5", "5");
        convertMap.put(Constants.CODE_WGT_CAR_EXTRA_DECO + "#200", "200");
        convertMap.put(Constants.CODE_WGT_CAR_EXTRA_DECO + "#0", "0");
        convertMap.put(Constants.CODE_MAT_CDO_PANEL_A + "#MP1", "镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_CDO_PANEL_A + "#GL", "观光玻璃");
        convertMap.put(Constants.CODE_MAT_CDO_PANEL_C + "#P", "涂装钢板");
        convertMap.put(Constants.CODE_MAT_CDO_PANEL_C + "#ST43", "发纹不锈钢");
        convertMap.put(Constants.CODE_MAT_CDO_PANEL_C + "#MP1", "镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_CDO_PANEL_C + "#MP2ET", "G4002001");
        convertMap.put(Constants.CODE_MAT_CDO_PANEL_C + "#MP1ET", "G4002002");
        convertMap.put(Constants.CODE_MAT_CDO_PANEL_C + "#HLET3", "G4002003");
        convertMap.put(Constants.CODE_MAT_CDO_PANEL_C + "#HLET5", "G4002005");
        convertMap.put(Constants.CODE_MAT_CDO_PANEL_C + "#TGCET", "G4002007");
        convertMap.put(Constants.CODE_MAT_CDO_PANEL_C + "#MPHL", "G4002008");
        convertMap.put(Constants.CODE_MAT_CDO_PANEL_C + "#MP2", "钛金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_CDO_PANEL_C + "#MP3", "玫瑰金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_CDO_PANEL_C + "#GL", "观光玻璃");
        convertMap.put(Constants.CODE_RAL_CDO_PANEL_A + "#GS001", "GS001");
        convertMap.put(Constants.CODE_RAL_CDO_PANEL_A + "#GS002", "GS002");
        convertMap.put(Constants.CODE_RAL_CDO_PANEL_A + "#GS004", "GS004");
        convertMap.put(Constants.CODE_RAL_CDO_PANEL_A + "#GS007", "GS007");
        convertMap.put(Constants.CODE_RAL_CDO_PANEL_A + "#GS008", "GS008");
        convertMap.put(Constants.CODE_RAL_CDO_PANEL_A + "#GS009", "GS009");
        convertMap.put(Constants.CODE_RAL_CDO_PANEL_A + "#GS0010", "GS0010");
        convertMap.put(Constants.CODE_RAL_CDO_PANEL_A + "#GS0012", "GS0012");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1A + "#P", "涂装钢板");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1A + "#ST43", "发纹不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1A + "#MP1", "镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1A + "#MP2ET", "G4002001");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1A + "#MP1ET", "G4002002");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1A + "#HLET3", "G4002003");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1A + "#HLET5", "G4002005");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1A + "#TGCET", "G4002007");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1A + "#MPHL", "G4002008");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1A + "#MP2", "钛金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1A + "#MP3", "玫瑰金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1A + "#GL", "观光玻璃");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2A + "#P", "涂装钢板");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2A + "#ST43", "发纹不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2A + "#MP1", "镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2A + "#MP2ET", "G4002001");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2A + "#MP1ET", "G4002002");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2A + "#HLET3", "G4002003");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2A + "#HLET5", "G4002005");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2A + "#TGCET", "G4002007");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2A + "#MPHL", "G4002008");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2A + "#MP2", "钛金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2A + "#MP3", "玫瑰金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2A + "#GL", "观光玻璃");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1C + "#P", "涂装钢板");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1C + "#ST43", "发纹不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1C + "#MP1", "镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1C + "#MP2ET", "G4002001");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1C + "#MP1ET", "G4002002");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1C + "#HLET3", "G4002003");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1C + "#HLET5", "G4002005");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1C + "#TGCET", "G4002007");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1C + "#MPHL", "G4002008");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1C + "#MP2", "钛金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1C + "#MP3", "玫瑰金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_1C + "#GL", "观光玻璃");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2C + "#P", "涂装钢板");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2C + "#ST43", "发纹不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2C + "#MP1", "镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2C + "#MP2ET", "G4002001");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2C + "#MP1ET", "G4002002");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2C + "#HLET3", "G4002003");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2C + "#HLET5", "G4002005");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2C + "#TGCET", "G4002007");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2C + "#MPHL", "G4002008");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2C + "#MP2", "钛金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2C + "#MP3", "玫瑰金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_PANEL_2C + "#GL", "观光玻璃");
        convertMap.put(Constants.CODE_RAL_LDO_PANEL + "#GS001", "GS001");
        convertMap.put(Constants.CODE_RAL_LDO_PANEL + "#GS002", "GS002");
        convertMap.put(Constants.CODE_RAL_LDO_PANEL + "#GS004", "GS004");
        convertMap.put(Constants.CODE_RAL_LDO_PANEL + "#GS007", "GS007");
        convertMap.put(Constants.CODE_RAL_LDO_PANEL + "#GS008", "GS008");
        convertMap.put(Constants.CODE_RAL_LDO_PANEL + "#GS009", "GS009");
        convertMap.put(Constants.CODE_RAL_LDO_PANEL + "#GS0010", "GS0010");
        convertMap.put(Constants.CODE_RAL_LDO_PANEL + "#GS0012", "GS0012");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_1A + "#P", "涂装钢板");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_1A + "#ST43", "发纹不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_1A + "#MP1", "镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_1A + "#MP2", "钛金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_1A + "#MP3", "玫瑰金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_2A + "#P", "涂装钢板");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_2A + "#ST43", "发纹不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_2A + "#MP1", "镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_2A + "#MP2", "钛金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_2A + "#MP3", "玫瑰金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_1C + "#P", "涂装钢板");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_1C + "#ST43", "发纹不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_1C + "#MP1", "镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_1C + "#MP2", "钛金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_1C + "#MP3", "玫瑰金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_2C + "#P", "涂装钢板");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_2C + "#ST43", "发纹不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_2C + "#MP1", "镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_2C + "#MP2", "钛金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LDO_FRAME_2C + "#MP3", "玫瑰金镜面不锈钢");
        convertMap.put(Constants.CODE_RAL_LDO_FRAME + "#GS001", "GS001");
        convertMap.put(Constants.CODE_RAL_LDO_FRAME + "#GS002", "GS002");
        convertMap.put(Constants.CODE_RAL_LDO_FRAME + "#GS004", "GS004");
        convertMap.put(Constants.CODE_RAL_LDO_FRAME + "#GS007", "GS007");
        convertMap.put(Constants.CODE_RAL_LDO_FRAME + "#GS008", "GS008");
        convertMap.put(Constants.CODE_RAL_LDO_FRAME + "#GS009", "GS009");
        convertMap.put(Constants.CODE_RAL_LDO_FRAME + "#GS0010", "GS0010");
        convertMap.put(Constants.CODE_RAL_LDO_FRAME + "#GS0012", "GS0012");
        convertMap.put(Constants.CODE_TYP_COP_PRODUCT + "#bangao", "半高");
        convertMap.put(Constants.CODE_TYP_COP_PRODUCT + "#quangao", "全高");
        convertMap.put(Constants.CODE_TYP_COP_PRODUCT + "#zhengti", "整体");
        convertMap.put(Constants.CODE_TYP_COP_PRODUCT_1 + "#198_PH", "198_PH");
        convertMap.put(Constants.CODE_TYP_COP_PRODUCT_1 + "#258_PH", "258_PH");
        convertMap.put(Constants.CODE_TYP_COP_PRODUCT_1 + "#258_FH", "258_FH");
        convertMap.put(Constants.CODE_TYP_COP_PRODUCT_1 + "#278_FH", "278_FH");
        convertMap.put(Constants.CODE_TYP_COP_PRODUCT_1 + "#318", "318");
        convertMap.put(Constants.CODE_MAT_COP_FACE_PLATE_1 + "#ST4", "发纹不锈钢");
        convertMap.put(Constants.CODE_MAT_COP_FACE_PLATE_1 + "#MP1", "镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_COP_FACE_PLATE_1 + "#MP3", "钛金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_COP_FACE_PLATE_1 + "#BOM", "黑色有机玻璃");
        convertMap.put(Constants.CODE_MAT_COP_FACE_PLATE_1 + "#MP4", "黑钛镜面不锈钢");
        convertMap.put(Constants.CODE_TYP_COP_DISPLAY_1 + "#12", "红色七段码");
        convertMap.put(Constants.CODE_TYP_COP_DISPLAY_1 + "#11", "红色点阵");
        convertMap.put(Constants.CODE_TYP_COP_DISPLAY_1 + "#8", "蓝底白字单色液晶");
        convertMap.put(Constants.CODE_TYP_COP_DISPLAY_1 + "#15", "白色点阵");
        convertMap.put(Constants.CODE_TYP_COP_DISPLAY_1 + "#4", "白色段码");
        convertMap.put(Constants.CODE_TYP_COP_DISPLAY_1 + "#24", "9英寸单色液晶");
        convertMap.put(Constants.CODE_TYP_COP_DISPLAY_1 + "#16", "7英寸图片式液晶");
        convertMap.put(Constants.CODE_TYP_COP_DISPLAY_1 + "#18", "10.4英寸图片式液晶");
        convertMap.put(Constants.CODE_TYP_COP_DISPLAY_1 + "#19", "10.4英寸多媒体液晶");
        convertMap.put(Constants.CODE_TYP_COP_DISPLAY_1 + "#9", "黑底白字单色液晶");
        convertMap.put(Constants.CODE_TYP_COP_CALL_BUTTON_SHAPE_1 + "#R", "圆形");
        convertMap.put(Constants.CODE_TYP_COP_CALL_BUTTON_SHAPE_1 + "#S", "方形");
        convertMap.put(Constants.CODE_TYP_COP_HAN_KEYPAD + "#S", "壁挂式");
        convertMap.put(Constants.CODE_TYP_COP_HAN_KEYPAD + "#F", "嵌入式");
        convertMap.put(Constants.CODE_MAT_COP_HAN_KEYPAD_FACE_PLATE + "#ST4", "发纹不锈钢");
        convertMap.put(Constants.CODE_MAT_COP_HAN_KEYPAD_FACE_PLATE + "#MP1", "镜面不锈钢");
        convertMap.put(Constants.CODE_TYP_LCS_PRODUCT + "#LCI", "带显示呼梯盒(单个)");
        convertMap.put(Constants.CODE_TYP_LCS_PRODUCT + "#LCI_D", "带显示呼梯盒(二合一)");
        convertMap.put(Constants.CODE_TYP_LCS_PRODUCT + "#LCS", "不带显示呼梯盒(单个)");
        convertMap.put(Constants.CODE_TYP_LCS_PRODUCT + "#LCS_D", "不带显示呼梯盒(二合一)");
        convertMap.put(Constants.CODE_TYP_LCI_DISPLAY + "#12", "红色七段码");
        convertMap.put(Constants.CODE_TYP_LCI_DISPLAY + "#11", "红色点阵");
        convertMap.put(Constants.CODE_TYP_LCI_DISPLAY + "#8", "蓝底白字单色液晶");
        convertMap.put(Constants.CODE_TYP_LCI_DISPLAY + "#9", "黑底白字单色液晶");
        convertMap.put(Constants.CODE_TYP_LCI_DISPLAY + "#16", "7英寸图片式液晶");
        convertMap.put(Constants.CODE_TYP_LCI_DISPLAY + "#4", "白色段码");
        convertMap.put(Constants.CODE_MAT_LCI_FACE_PLATE + "#ST4", "发纹不锈钢");
        convertMap.put(Constants.CODE_MAT_LCI_FACE_PLATE + "#MP1", "镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LCI_FACE_PLATE + "#MP3", "钛金镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LCS_FACE_PLATE + "#ST4", "发纹不锈钢");
        convertMap.put(Constants.CODE_MAT_LCS_FACE_PLATE + "#MP1", "镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_LCS_FACE_PLATE + "#MP3", "钛金镜面不锈钢");
        convertMap.put(Constants.CODE_TYP_LCI_BUTTON_SHAPE + "#R", "圆形");
        convertMap.put(Constants.CODE_TYP_LCI_BUTTON_SHAPE + "#S", "方形");
        convertMap.put(Constants.CODE_S_HALLPUSHBUTTON + "#NO", "否");
        convertMap.put(Constants.CODE_S_HALLPUSHBUTTON + "#YES", "是");
        convertMap.put(Constants.CODE_TYP_HI_DISPLAY + "#1", "白色段码");
        convertMap.put(Constants.CODE_TYP_HI_DISPLAY + "#6", "白色点阵");
        convertMap.put(Constants.CODE_TYP_HI_DISPLAY + "#NA", "NA");
        convertMap.put(Constants.CODE_MAT_HI_FACEPLATE + "#ST4", "发纹不锈钢");
        convertMap.put(Constants.CODE_MAT_HI_FACEPLATE + "#MP1", "镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_HI_FACEPLATE + "#MP3", "钛金镜面不锈钢");
        convertMap.put(Constants.CODE_TYP_REQUIRE_BELL_HI + "#0", "无");
        convertMap.put(Constants.CODE_TYP_REQUIRE_BELL_HI + "#1", "有");
        convertMap.put(Constants.CODE_TYP_HL_FACE_SHAPE + "#0", "矩形");
        convertMap.put(Constants.CODE_TYP_HL_FACE_SHAPE + "#1", "白色宝石型");
        convertMap.put(Constants.CODE_MAT_HL_FACEPLATE + "#ST4", "发纹不锈钢");
        convertMap.put(Constants.CODE_MAT_HL_FACEPLATE + "#MP1", "镜面不锈钢");
        convertMap.put(Constants.CODE_MAT_HL_FACEPLATE + "#MP3", "钛金镜面不锈钢");
        convertMap.put(Constants.CODE_TYP_REQUIRE_BELL_HL + "#0", "无");
        convertMap.put(Constants.CODE_TYP_REQUIRE_BELL_HL + "#1", "有");
        convertMap.put(Constants.CODE_LIOP_R_L + "#R", "右手边");
        convertMap.put(Constants.CODE_LIOP_R_L + "#L", "左手边");
        convertMap.put(Constants.CODE_STB_ABCD + "#A", "A");
        convertMap.put(Constants.CODE_STB_ABCD + "#B", "B");
        convertMap.put(Constants.CODE_STB_ABCD + "#C", "C");
        convertMap.put(Constants.CODE_STB_ABCD + "#D", "D");
        convertMap.put(Constants.CODE_TYP_CWT_GR + "#TK3A", "TK3A");
        convertMap.put(Constants.CODE_TYP_CWT_GR + "#TK5A", "TK5A");
        convertMap.put(Constants.CODE_TYP_CWT_GR + "#HT60", "HT60");
        convertMap.put(Constants.CODE_TYP_CWT_GR + "#T75", "T75");
        convertMap.put(Constants.CODE_TYP_CWT_GR + "#T89", "T89");
        convertMap.put(Constants.CODE_TYP_CWT_GR + "#T90", "T90");
        convertMap.put(Constants.CODE_TYP_CWT_GR + "#TD65", "TD65");
        convertMap.put(Constants.CODE_TYP_CAR_GR + "#T75", "T75");
        convertMap.put(Constants.CODE_TYP_CAR_GR + "#T89", "T89");
        convertMap.put(Constants.CODE_TYP_CAR_GR + "#T90", "T90");
        convertMap.put(Constants.CODE_TYP_CAR_GR + "#T114", "T114");
        convertMap.put(Constants.CODE_TYP_CAR_GR + "#T127", "T127");
        convertMap.put(Constants.CODE_TYP_CAR_GR + "#T70", "T70");
        convertMap.put(Constants.CODE_TYP_CAR_GR + "#T82", "T82");
        convertMap.put(Constants.CODE_POS_OF_CWT_ENGR + "#B", "B");
        convertMap.put(Constants.CODE_POS_OF_CWT_ENGR + "#C", "C");
        convertMap.put(Constants.CODE_POS_OF_CWT_ENGR + "#D", "D");

        convertMap.put(Constants.CODE_ELE_M + "#TKJ", "TKJ");
        convertMap.put(Constants.CODE_ELE_M + "#THJ", "THJ");
        convertMap.put(Constants.CODE_ELE_M + "#TWJ", "TWJ");
        convertMap.put(Constants.CODE_ELE_M + "#TGJ", "TGJ");
        convertMap.put(Constants.CODE_ELE_M + "#TBJ", "TBJ");

        convertMap.put(Constants.CODE_RG + "#标准", "标准");
        convertMap.put(Constants.CODE_RG + "#导靴加长", "导靴加长");
        convertMap.put(Constants.CODE_RG + "#国内滚轮", "国内滚轮");
        convertMap.put(Constants.CODE_RG + "#进口滚轮", "进口滚轮");
        convertMap.put(Constants.CODE_COM_MON + "#YES", "YES");
        convertMap.put(Constants.CODE_COM_MON + "#NO", "NO");

        convertMap.put(Constants.CODE_H_COP_M + "#BF-136P", "BF-136P");
        convertMap.put(Constants.CODE_H_COP_M + "#NA", "NA");
        convertMap.put(Constants.CODE_CLO_DELAY + "#YES", "YES");
        convertMap.put(Constants.CODE_CLO_DELAY + "#NO", "NO");

        convertMap.put(Constants.CODE_CHIME + "#YES", "YES");
        convertMap.put(Constants.CODE_CHIME + "#NO", "NO");


    }


    public static String dimensConvert(int value) {
        String text = "";
        switch (value) {
            case 2:
                text = "低";
                break;
            case 4:
                text = "中低";
                break;
            case 6:
                text = "中";
                break;
            case 8:
                text = "中高";
                break;
            case 10:
                text = "高";
                break;
        }
        return text;
    }


    public static HashMap<String, String> convertMap(Map<String, String> rawMap) {
        HashMap<String, String> param = new HashMap<>();
        Gson gson = new Gson();
        param.put("pageParameters", gson.toJson(rawMap));
        param.put("price", gson.toJson(new SearchBean.Price()));
        JsonObject salesParamJson = new JsonObject();
        salesParamJson.addProperty("DrawingNumber", "YITIHUA--001");
        JsonObject inputParamJson = new JsonObject();
        inputParamJson.addProperty("DIM_SHAFT_DEPTH_WD", rawMap.get("DIM_SHAFT_DEPTH_WD"));
        inputParamJson.addProperty("DIM_SHAFT_WIDTH_WW", rawMap.get("DIM_SHAFT_WIDTH_WW"));
        inputParamJson.addProperty("ELEVATORTYPE", rawMap.get("ELEVATORTYPE"));
        inputParamJson.addProperty("MACHINEROOM", rawMap.get("MACHINEROOM"));
        inputParamJson.addProperty("QTY_NUMBER_OF_FLOORS", rawMap.get("QTY_NUMBER_OF_FLOORS"));
        salesParamJson.add("InputParameter", inputParamJson);
        param.put("salesParameter", gson.toJson(salesParamJson));
        final List<SearchBean.Dimen> dimenList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SearchBean.Dimen dimen = new SearchBean.Dimen();
            String dpName = "";
            String dName = "";
            switch (i) {
                case 0:
                    dName = "AQPZ";
                    dpName = "AQPZ";
                    dimen.setValue(rawMap.get("YITIHUA-AQPZ"));
                    break;
                case 1:
                    dName = "CZFW";
                    dpName = "CZFW";
                    dimen.setValue(rawMap.get("YITIHUA-CZFW"));
                    break;
                case 2:
                    dName = "JNDJ";
                    dpName = "JNDJ";
                    dimen.setValue(rawMap.get("YITIHUA-JNDJ"));
                    break;
                case 3:
                    dName = "MGD";
                    dpName = "MGD";
                    dimen.setValue(rawMap.get("YITIHUA-MGD"));
                    break;
                case 4:
                    dName = "WD";
                    dpName = "SSD";
                    dimen.setValue(rawMap.get("YITIHUA-WD"));
                    break;
            }
            String caption = null;
            dimen.setDimensionName("YITIHUA-" + dName);
            dimen.setDimensionParamName(dpName);

            dimenList.add(dimen);
        }
        param.put("dimensions", gson.toJson(dimenList));
        return param;
    }

    public static JsonObject convertParamJSON(Programme programme) {
        JsonObject jsonObject = new JsonObject();
        for (int i = 0; i < programme.getDimensions().size(); i++) {
            Programme.DimensionsBean dimensionsBean = programme.getDimensions().get(i);
            for (int j = 0; j < dimensionsBean.getParams().size(); j++) {
                Programme.DimensionsBean.ParamsBean paramsBean = dimensionsBean.getParams().get(j);
                jsonObject.addProperty(paramsBean.getProECode(), paramsBean.getValue());
            }
        }
        return jsonObject;
    }

    public static JsonObject convertALLParamJSON(Programme programme) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Price", programme.getPrice());
        for (int i = 0; i < programme.getDimensions().size(); i++) {
            Programme.DimensionsBean dimensionsBean = programme.getDimensions().get(i);
            jsonObject.addProperty(dimensionsBean.getProECode(), dimensionsBean.getValue());
            for (int j = 0; j < dimensionsBean.getParams().size(); j++) {
                Programme.DimensionsBean.ParamsBean paramsBean = dimensionsBean.getParams().get(j);
                jsonObject.addProperty(paramsBean.getProECode(), paramsBean.getValue());
            }
        }
        return jsonObject;
    }

    public static HashMap<String, String> convertParamMap(Programme programme) {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < programme.getDimensions().size(); i++) {
            Programme.DimensionsBean dimensionsBean = programme.getDimensions().get(i);
            for (int j = 0; j < dimensionsBean.getParams().size(); j++) {
                Programme.DimensionsBean.ParamsBean paramsBean = dimensionsBean.getParams().get(j);
                map.put(paramsBean.getProECode(), paramsBean.getValue());
            }
        }
        return map;
    }


    public static HashMap<String, Programme.DimensionsBean.ParamsBean> convertParamBean(Programme programme) {
        HashMap<String, Programme.DimensionsBean.ParamsBean> map = new HashMap<>();
        for (int i = 0; i < programme.getDimensions().size(); i++) {
            Programme.DimensionsBean dimensionsBean = programme.getDimensions().get(i);
            for (int j = 0; j < dimensionsBean.getParams().size(); j++) {
                Programme.DimensionsBean.ParamsBean paramsBean = dimensionsBean.getParams().get(j);
                map.put(paramsBean.getProECode(), paramsBean);
            }
        }
        return map;
    }

    public static List<Programme.DimensionsBean.ParamsBean> convertProgramme(Programme programme) {
        List<Programme.DimensionsBean.ParamsBean> paramsBeanList = new ArrayList<>();
        for (int i = 0; i < programme.getDimensions().size(); i++) {
            Programme.DimensionsBean dimensionsBean = programme.getDimensions().get(i);
            paramsBeanList.addAll(dimensionsBean.getParams());
        }
        return paramsBeanList;
    }


    public static JsonArray joinDimens(HashMap<String, String> rawMap, Map<String, Boolean> fixDimensMap) {

        JsonArray dimenArray = new JsonArray();
        JsonObject dimenJson = null;
        String[] dpNames = new String[]{Constants.DIMEN_SSD, Constants.DIMEN_JNDJ, Constants.DIMEN_MGD, Constants.DIMEN_CZFW, Constants.DIMEN_AQDJ};
        String[] dpParamNames = new String[]{Constants.DIMEN_FlAG_SSD, Constants.DIMEN_FLAG_JNDJ, Constants.DIMEN_FLAG_MGD, Constants.DIMEN_FLAG_CZFW, Constants.DIMEN_FLAG_AQDJ};
        JsonArray rangeArr = new JsonArray();
        rangeArr.add("2");
        rangeArr.add("4");
        rangeArr.add("6");
        rangeArr.add("8");
        rangeArr.add("10");
        for (int i = 0; i < 5; i++) {
            dimenJson = new JsonObject();
            dimenJson.addProperty("DimensionName", dpNames[i]);
            dimenJson.addProperty("RunTime", DateUtil.getCurrentDate());
            dimenJson.addProperty("Value", rawMap.get(dpNames[i]));
            dimenJson.add("ValueRange", rangeArr);
            dimenJson.addProperty("OrderId", 0);
            dimenJson.addProperty("DimensionParamName", dpParamNames[i]);
            if (fixDimensMap != null && (fixDimensMap.get(dpNames[i]) == null
                    || !fixDimensMap.get(dpNames[i]))) {
                dimenJson.addProperty("IsFix", "true");
            } else {
                dimenJson.addProperty("IsFix", "false");
            }
            dimenArray.add(dimenJson);
        }
        return dimenArray;
    }


    // 接口要求必须这个结构
    public static JsonObject joinPrice(String drawingNumber) {
        JsonObject priceParamJson = new JsonObject();
        priceParamJson.addProperty("DrawingNumber", drawingNumber);
        priceParamJson.addProperty("Price", 0);
        priceParamJson.addProperty("RunTime", DateUtil.getCurrentDate());
        return priceParamJson;
    }

}
