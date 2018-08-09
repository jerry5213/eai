package com.zxtech.mt.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zxtech.mt.entity.CalCallFix;
import com.zxtech.mt.entity.MtWorkPlan;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/7/13.
 */
public class Util {


    public static String getSHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取GUID
     */
    public static String getGUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }


    /**
     * <br>对服务器发来的json格式的数据进行格式修正
     * <br>清除掉看不见的隐藏数据
     *
     * @param json
     * @return
     */
    public static boolean cleanErrorCode(String json) {
        if (!json.startsWith("{")) {
            return false;
        }
        if (!json.startsWith("[")) {
            return false;
        }
        return true;
    }

    public static void closeKeybord(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public static String getIMEI(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        return szImei;
    }

    public static void closeSoftInputFromWindow(EditText view, InputMethodManager imm) {
        if (imm.hideSoftInputFromWindow(view.getWindowToken(), 0)) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            //关闭
        }
    }


    public static void showSoftInputFromWindow(EditText view, InputMethodManager imm) {
        if (imm.hideSoftInputFromWindow(view.getWindowToken(), 0)) {
            imm.showSoftInputFromInputMethod(view.getWindowToken(), 0);
            //关闭
        }
    }


    public static String getVersion(Context context)//获取版本号
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "版本不存在";
        }
    }

    public static int getVersionCode(Context context)//获取版本号(内部识别号)
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static boolean isServiceRunning(Context mContext, String className) {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(30);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    public static List<MtWorkPlan> removeDistinct(List<MtWorkPlan> rawList, List<MtWorkPlan> list) {
        List<MtWorkPlan> resultList = new ArrayList<>();
        if (rawList != null && list != null) {

            for (MtWorkPlan plan1 : list) {
                boolean isContains = false;
                for (MtWorkPlan plan : rawList) {
                    if (plan1.getId().equals(plan.getId())  ) {
                        isContains = true;
                    }
                }
                if (!isContains) {
                    resultList.add(plan1);
                }
            }
        }
        return resultList;
    }

    public static List<CalCallFix> removeDistinctCall(List<CalCallFix> rawList, List<CalCallFix> list) {
        List<CalCallFix> resultList = new ArrayList<>();
        if (rawList != null && list != null) {

            for (CalCallFix plan1 : list) {
                boolean isContains = false;
                for (CalCallFix plan : rawList) {
                    if (plan1.getId().equals(plan.getId())  ) {
                        isContains = true;
                    }
                }
                if (!isContains) {
                    resultList.add(plan1);
                }
            }
        }
        return resultList;
    }

    public static String getMD5(String message) {
        String md5str = "";
        try {
            // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 2 将消息变成byte数组
            byte[] input = message.getBytes();

            // 3 计算后获得字节数组,这就是那128位了
            byte[] buff = md.digest(input);

            // 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
            md5str = bytesToHex(buff);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5str;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        // 把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString();
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }




    public static String getNumOfStr(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String chineseNumberInt(String str) {
        HashMap<String,String> map = new HashMap<>();
        map.put("一", "1");
        map.put("二", "2");
        map.put("三", "3");
        map.put("四", "4");
        map.put("五", "5");
        map.put("六", "6");
        map.put("七", "7");
        map.put("八", "8");
        map.put("九", "9");
        map.put("十", "0");
        map.put("零", "0");
        int n = str.length();
        String result = "";
        for (int i = 0; i < n; i++) {
            result+=map.get(str.charAt(i));
        }
        return result;
    }

}
