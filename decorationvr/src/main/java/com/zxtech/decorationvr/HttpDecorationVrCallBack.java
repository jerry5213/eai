package com.zxtech.decorationvr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by syp691 on 2018/1/23.
 */

public class HttpDecorationVrCallBack implements IDecorationVrCallBack {

    public IVrChangeCompleted ui_call_back_ = null;
    public void SetUICallBack(IVrChangeCompleted cb)
    {
        ui_call_back_ = cb;
    }

    public boolean first_run = true;

    @Override
    public void CompleteCallBack(int wpara, int lpara, String content)
    {
        if(first_run)
        {
            DecorationVrWrapper.SendVrMessage("msg_enter360", 1);
            first_run = false;
        }

        switch (wpara)
        {
            case 20://ok
                if(ui_call_back_ != null)
                {
                    ui_call_back_.VrChangeCompletedCallBack(wpara, lpara, content);
                }
                break;
            case -1://error
                if(ui_call_back_ != null)
                {
                    ui_call_back_.VrChangeCompletedCallBack(wpara, lpara, content);
                }
                break;
            default:
                break;
        }
    }

    //************************************
    // Method:    DownloadData
    // FullName:  decoration_vr_interface::IDecorationVrCallBack::DownloadData
    // Access:    virtual public
    // Returns:   char*
    // Qualifier: char* 数据缓冲区的指针，缓冲区内保存的是一个json字符串，使用后需要调用FreeBuf释放，如果出错，返回nullptr
    // Parameter: const char * download_url
    // Parameter: const char * post_data
    //************************************
    @Override
    public String DownloadData(String download_url, String post_data)
    {
        if(download_url.isEmpty() || post_data.isEmpty())
        {
            return "";
        }
        final MediaType JSON = MediaType.parse("application/json");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, post_data);
        final Request request = new Request.Builder()
                .url(download_url)
                .post(body)
                .addHeader("Connection", "close")
                .addHeader("Content-Length", String.valueOf(post_data.length()))
                .build();
        Call call = client.newCall(request);
        try {
            String s = call.execute().body().string();
            return s;//4.获得返回结果com/zxtech/decorationvr/HttpDecorationVrCallBack.java:72
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public void CreatePathIfNotExist(String file_abs_path)
    {
        String str = file_abs_path.substring(0, file_abs_path.lastIndexOf("/"));
        File absPath = new File(str);
        if(!absPath.exists()){
            absPath.mkdirs();
        }        
    }


    //************************************
    // Method:    DownloadFile
    // FullName:  decoration_vr_interface::IDecorationVrCallBack::DownloadFile
    // Access:    virtual public
    // Returns:   bool 下载成功返回true，失败返回false
    // Qualifier: 下载文件
    // Parameter: const char * download_url 下载文件的URL
    // Parameter: const char * file_abs_path 文件在本地保存的绝对路径
    //************************************
    @Override
    public boolean DownloadFile(String download_url, String file_abs_path)
    {
        boolean result = false;
        OkHttpClient client = new OkHttpClient();

        CreatePathIfNotExist(file_abs_path);

        final File file = new File(file_abs_path);

        final Request request = new Request.Builder().url(download_url).build();
        final Call call = client.newCall(request);
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            ResponseBody body = call.execute().body();
            is = body.byteStream();
            fos = new FileOutputStream(file);
            while((len = is.read(buf)) != -1)
            {
                fos.write(buf, 0, len);
            }
            fos.flush();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}
