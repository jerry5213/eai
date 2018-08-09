package com.zxtech.mt.utils;

import com.zxtech.mt.common.Constants;
import com.zxtech.mt.common.UIController;

/**
 * Created by syp523 on 2017/6/23.
 */

public class UrlManage {

    /**维保 今日列表url */
    public static String getMtTodayList(int page){
        return "";
      //  return appendPaging(UIController.IP+"/mtmo/mtworkplangetlist.mo?sortname=mtp.plan_date&sortorder=asc&re_status=4&line_id="+ UIController.LINE_ID+"&plan_date="+ DateUtil.getCurrentDate()+"&emp_id="+UIController.EMP_INFO.getId(),page);
    }


    /**维保 未完成列表url  状态<4 & 计划日期<=今天*/
    public static String getMtUnFinishList(int page){
        return "";
      //  return appendPaging(UIController.IP+"/mtmo/mtworkplangetlist.mo?sortname=mtp.plan_date&sortorder=asc&re_status=4&now=1&line_id="+ UIController.LINE_ID+"&emp_id="+UIController.EMP_INFO.getId(),page);
    }

    /**维保 完成列表url  状态=5 */
    public static String getMtFinishList(int page){
        return "";
      //  return appendPaging(UIController.IP+"/mtmo/mtworkplangetlist.mo?sortname=mtp.last_update_timestamp&sortorder=desc&status=5&line_id="+ UIController.LINE_ID,page);
    }

    /**维保 本周列表url  状态=5 */
    public static String getMtWeekList(int page){
        String[] weekFirstAndLastDays = DateUtil.getWeekFirstAndLastDays();
        return "";
       // return appendPaging(UIController.IP+"/mtmo/mtworkplangetlist.mo?sortname=mtp.plan_date&sortorder=asc&re_status=4&line_id="+ UIController.LINE_ID+"&week_begin_date="+ weekFirstAndLastDays[0]+"&week_end_date="+weekFirstAndLastDays[1]+"&emp_id="+UIController.EMP_INFO.getId(),page);
    }

    /**
     * 维保 签字列表url  状态=4
     */
    public static String getMtSignList(int page) {
        return "";
       // return appendPaging(UIController.IP + "/mtmo/mtworkplangetlist.mo?sortname=mtp.last_update_timestamp&sortorder=desc&status=4&line_id=" + UIController.LINE_ID, page);
    }

    /** 上传维保单列表url */
    public static String getMtFormList(int page) {
        return "";
       // return appendPaging(UIController.IP+"/mtmo/mtworkplangetlist.mo?sortname=mtp.last_update_timestamp&sortorder=desc&order_pincipal="+UIController.EMP_INFO.getId(), page);
    }



    public static String appendPaging(String url, int page) {
        return url+"&pagesize="+ Constants.SHOW_PAGE_SIZE+"&page="+page;
    }
}
