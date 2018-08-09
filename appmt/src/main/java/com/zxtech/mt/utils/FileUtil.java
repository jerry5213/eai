package com.zxtech.mt.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Environment;

import com.zxtech.mt.common.UIController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/7/27.
 */
public class FileUtil {


    /**
     * assets资源下指定文件夹 复制文件到sdcard
     *
     * @param context
     */
    public static void copyResDBFile(Context context,String fileName) {

        // 判断目录存在不存在
        File mDirectory = new File(UIController.SD_DIR_PATH);
        if (!mDirectory.exists())
            mDirectory.mkdir();

        // 复制数据库文件
        try {
            AssetManager am = context.getResources().getAssets();
            InputStream is = am.open(fileName);
            // 得到数据库的输入流
            FileOutputStream fos = new FileOutputStream(UIController.SD_DIR_PATH+"/"+fileName);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }

                fos.flush();
                fos.close();
                is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static String readSdcardURL(Context mContext,String fileName) {
        String res = "";

        try {
            File file = new File(fileName);

            if(!file.exists()){
                FileUtil.copyResDBFile(mContext,"ipconfig.txt");
            }

            FileInputStream fin = new FileInputStream(file);

            int length = fin.available();

            byte[] buffer = new byte[length];

            fin.read(buffer);

            res = new String(buffer,"UTF-8");

            fin.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public static boolean isExists(String path){
        File jhPath = new File(path);
        return jhPath.exists();
    }

    public static void write(String filePath, boolean append, String text)  {
        if (text == null)
            return;
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(filePath,
                    append));
            out.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String saveSdcard(Context context, Bitmap bmp) {

        File appDir = new File(Environment.getExternalStorageDirectory(), "mt");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getPath();
    }
}
