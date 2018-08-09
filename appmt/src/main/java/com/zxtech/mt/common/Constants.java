package com.zxtech.mt.common;

/**
 * Created by Chw on 2016/6/22.
 */
public class Constants {
    /**召修状态 已通知*/
    public static String CAL_TASK_STATUS_NOTICE = "2";
    /**召修状态 已接单*/
    public static String CAL_TASK_STATUS_ORDER = "3";
    /**召修状态 已到达*/
    public static String CAL_TASK_STATUS_ARRIVE = "4";
    /**召修状态 已完成*/
    public static String CAL_TASK_STATUS_SUBMIT = "5";
    /**召修状态 停梯*/
    public static String CAL_TASK_STATUS_STOP = "6";
    /**召修状态 暂停*/
    public static String CAL_TASK_STATUS_COMPLETE = "8";
    /**网络请求 成功*/
    public static String SUCCESS = "success";
    /**网络请求 失败*/
    public static String ERROR = "error";
    /**维保状态 新建*/
    public static String MT_WORK_STATUS_NEW = "0";
    /**维保状态 待保养*/
    public static String MT_WORK_STATUS_WAIT= "1";
    /**维保状态 保养中*/
    public static String MT_WORK_STATUS_IN= "2";
    /**维保状态 暂停中*/
    public static String MT_WORK_STATUS_PAUSE= "3";
    /**维保状态 已恢复*/
    public static String MT_WORK_STATUS_RENEW= "4";
    /**维保状态 提交*/
    public static String MT_WORK_STATUS_SUBMIT= "5";
    /**维保状态 完成*/
    public static String MT_WORK_STATUS_FINISH = "6";


    public static int SEC_SUB_CHECK_DEFAULT = -1;

    public static int SEC_SUB_CHECK_OK = 1;

    public static int SEC_SUB_CHECK_NO = 0;

    public static int SEC_SUP_CHECK_OK = 1;

    public static int SEC_SUP_CHECK_NO = 0;

    public static int SEC_INCLUDE = 1;

    public static int SEC_UNINCLUDE = 0;

    /**直梯*/
    public static String ELEVATOR_CATEGORY_LAD = "1";
    /**扶梯*/
    public static String ELEVATOR_CATEGORY_ESC = "2";
    /**人行道*/
    public static String ELEVATOR_CATEGORY_SIDEWALK = "3";
    /**货梯*/
    public static String ELEVATOR_CATEGORY_CARGOLIFT = "4";
    /** 分页每页显示条数*/
    public static String SHOW_PAGE_SIZE = "10";

    public static final String SHARED_LANGUAGE = "LANGUAGE";


}
