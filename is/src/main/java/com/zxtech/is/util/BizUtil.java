package com.zxtech.is.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

//import com.zxtech.common.util.T;

public class BizUtil {

//    public static void setIconFont(TextView tv, CharSequence text) {
//        if (tv != null) {
//            tv.setTypeface(Typeface.createFromAsset(tv.getContext().getAssets(), "font/iconfont.ttf"));
//            if (text != null)
//                tv.setText(text);
//        }
//    }
//
//    public static DisplayImageOptions.Builder getDefaultImageOPTSBuilder(int cornerRadiusPixels) {
//        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
//                .showImageOnFail(R.drawable.img_null)
//                .cacheInMemory(true) // default
//                .cacheOnDisk(true) // default
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .displayer(new RoundedBitmapDisplayer(cornerRadiusPixels));
//        return builder;
//    }

//    /**
//     * 查找插件
//     *
//     * @return
//     */
//    public static Map findPlugins(Context ctx) {
//
//        Map<String, String> plugins = new HashMap<>();
//
//        //遍历包名，来获取插件
//        PackageManager pm = ctx.getPackageManager();
//
//        List<PackageInfo> pkgs = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
//        for (PackageInfo pkg : pkgs) {
//            //包名
//            String packageName = pkg.packageName;
//            String sharedUserId = pkg.sharedUserId;
//
//            if ("com.zxtech.lxwalk".equals(sharedUserId)){
//                T.showToast(ctx,"com.zxtech.lxwalk");
//            }
//
//            //sharedUserId是开发时约定好的，这样判断是否为自己人
//            if (!"com.zxtech.lxwalk".equals(sharedUserId) || "com.zxtech.ecs.test".equals(packageName))
//                continue;
//            //进程名
//            String prcessName = pkg.applicationInfo.processName;
//
//            //label，也就是appName了
//            String label = pm.getApplicationLabel(pkg.applicationInfo).toString();
//
//            plugins.put("appName", label);
//            plugins.put("packageName", packageName);
//        }
//        return plugins;
//    }

    public static boolean isPkgInstalled(Context ctx, String pkgName) {

        PackageInfo packageInfo;
        try {
            packageInfo = ctx.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
}
