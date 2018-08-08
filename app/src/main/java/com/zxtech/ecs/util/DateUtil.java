package com.zxtech.ecs.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/29.
 */
public class DateUtil {

    public static String yyyy_MM_dd = "yyyy-MM-dd";
    public static String yyyy_MM_dd_HH_mm_ss = "yyyy/MM/dd HH:mm:ss";
    private static final int FIRST_DAY = Calendar.MONDAY;

    public static String formatChange(int year, int month, int day, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        return sdf.format(c.getTime());
    }


    /**
     * 得到系统字符串系统日期 yyy-MM-dd
     *
     * @return
     */
    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        String str = null;
        Date date = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);
        str = sdf.format(date);
        return str;
    }


    public static Date getDate() {
        Calendar c = Calendar.getInstance();
        String str = null;
        Date date = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);
        str = sdf.format(date);
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getCurrentDate1() {
        Calendar c = Calendar.getInstance();
        String str = null;
        Date date = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        str = sdf.format(date);
        return str;
    }

    public static String getCurrentWeek() {
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        return dateFm.format(date);
    }

    public static String formatDateStr(String date) {
        if (TextUtils.isEmpty(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = null;
        try {
            parse = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(parse);
    }

    /**
     * 获取本周的第一天和最后一天
     *
     * @return
     */
    public static String[] getWeekFirstAndLastDays() {
        String[] s = new String[2];
        Calendar calendar = Calendar.getInstance();
        setToFirstDay(calendar);
        String dateStr = null;
        dateStr = printDay(calendar);
        s[0] = dateStr;
        calendar.add(Calendar.DATE, 6);
        dateStr = printDay(calendar);
        s[1] = dateStr;
        return s;
    }


    public static void setToFirstDay(Calendar calendar) {
        while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
            calendar.add(Calendar.DATE, -1);
        }
    }

    public static String printDay(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    public static String dateFormat(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = sdf.parse(s);
            return sdf1.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return s;
    }
}
