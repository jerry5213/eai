package com.zxtech.ecs.ui.home.scheme;

import android.content.Context;
import android.os.Environment;

import com.zxtech.ecs.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by syp691 on 2018/1/24.
 */

//该类中静态方法用于将assets中的压缩文件释放到本地data目录
public class PackageManager {

    //测试方法
    public static void test(Context context)
    {
        String baseFilePath = Environment.getExternalStorageDirectory().getPath() + "/ecs_model_res/";
        FileUtil.createDirs(baseFilePath);
      //  String baseFilePath = context.getFilesDir().getPath() + "/";
        if(!PackageManager.CheckResourceDataFileExist(baseFilePath))
        {
            PackageManager.UpzipResourceDataFile(context,baseFilePath+"res.zip", baseFilePath);
        }
    }

    //解压完毕会生成Check.sign文件，用于下次启动时检查文件是否全部解压完毕
    final public static String checkFile = "check.sign";
    final private static String checkContext = "OK";
    /**
     * 检查必要文件是否存在
     * @param path 本地存放文件的基本路径
     * @return
     */
    public static boolean CheckResourceDataFileExist(String path)
    {
       String fileName = path + checkFile;
        File file = new File(fileName);
        if (!file.exists())
            return false;

        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(fileName);
            byte temp[] = new byte[1024];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = inputStream.read(temp)) > 0){
                sb.append(new String(temp, 0, len));
            }
            inputStream.close();

            if(sb.toString().compareToIgnoreCase(checkContext) == 0)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 解压资源文件到本地data目录
     * @param assetsZipFile assets中的压缩文件名(如有相对路径，则包含相对路径)
     * @param dataLocalPath 需要拷贝到本地的路径名 一般取getFilesDir末尾带斜杠
     * @return 成功返回true 失败返回 false
     */
    public static boolean UpzipResourceDataFile(Context context, String assetsZipFile, String dataLocalPath)
    {
        // /data/data/<application package>/files
        // 创建解压目标目录
        File file = new File(assetsZipFile);
        // 如果目标目录不存在，则创建
        File dir = new File(dataLocalPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            // 打开压缩文件
            InputStream inputStream = null;
            inputStream = new FileInputStream(file);
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            // 读取一个进入点
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            // 使用1Mbuffer
            byte[] buffer = new byte[1024 * 1024];
            // 解压时字节计数
            int count = 0;
            // 如果进入点为空说明已经遍历完所有压缩包中文件和目录
            while (zipEntry != null) {
                // 如果是一个目录
                if (zipEntry.isDirectory()) {
                    file = new File(dataLocalPath + File.separator + zipEntry.getName());
                    // 文件需要覆盖或者是文件不存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                } else {
                    // 如果是文件
                    file = new File(dataLocalPath + File.separator + zipEntry.getName());
                    // 文件需要覆盖或者文件不存在，则解压文件
                    if (!file.exists()) {
                        file.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        while ((count = zipInputStream.read(buffer)) > 0) {
                            fileOutputStream.write(buffer, 0, count);
                        }
                        fileOutputStream.close();
                    }
                }
                // 定位到下一个文件入口
                zipEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.close();

            //写入检查文件，确定文件拷贝完毕
            FileOutputStream outputStream = new FileOutputStream(dataLocalPath + File.separator + checkFile);
            outputStream.write(checkContext.getBytes());
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
