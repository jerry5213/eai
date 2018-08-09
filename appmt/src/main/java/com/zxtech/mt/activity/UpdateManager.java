package com.zxtech.mt.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.zxtech.mt.common.UIController;
import com.zxtech.mtos.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * 应用更新管理
 * Created by Chw on 2016/6/28.
 */
public class UpdateManager {
    private Context mContext;
    private final String updateMsg = "亲，有新版本，快下载吧！";            //下载消息提示
    private Dialog noticeDialog;                                        //下载提示对话框
    private Dialog downloadDialog;                                      //下载进度对话框
    private ProgressBar mProgressBar;                                   //进度条
    private Boolean interceptFlag = false;                              //标记用户是否在下载过程中取消下载
    private Thread downloadApkThread = null;                            //下载线程
    private String apkUrl = "";       //apk的URL地址
    private final String saveFileName = UIController.SD_DIR_PATH+"/xio_worker.apk";   //下载的apk文件
    private int progress = 0;                                           //下载进度
    private final int DOWNLOAD_ING = 1;                                 //标记正在下载
    private final int DOWNLOAD_OVER = 2;                                //标记下载完成
    private final String TAG="版本更新";

    //日志打印标签
    private Handler mhandler = new Handler() {                          //更新UI的handler

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DOWNLOAD_ING:
                    // 更新进度条
                    mProgressBar.setProgress(progress);
                    break;
                case DOWNLOAD_OVER:
                    downloadDialog.dismiss();
                    installApk();
                    //安装
                    break;
                default:
                    break;
            }
        }

    };

    /*
     * 构造方法
     */
    public UpdateManager(Context context) {
        this.mContext = context;
    }

    /*
     * 检查是否有需要更新，具体比较版本xml
     */
    public void checkUpdate() {
        // 到服务器检查软件是否有新版本
        //如果有则
        showNoticeDialog();
    }

    /*
     * 显示版本更新对话框
     */
    public void showNoticeDialog() {

        noticeDialog = new Dialog(mContext, R.style.dialog);
        noticeDialog.setContentView(R.layout.com_alert_dialog);
        noticeDialog.setCanceledOnTouchOutside(false);

        TextView title = (TextView)noticeDialog.findViewById(R.id.title);
        TextView message = (TextView)noticeDialog.findViewById(R.id.message);
        Button positiveButton = (Button)noticeDialog.findViewById(R.id.positiveButton);
//        Button negativeButton = (Button)noticeDialog.findViewById(R.id.negativeButton);

        title.setText("版本更新");
        message.setText(updateMsg);
        positiveButton.setText("更新");
        positiveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                noticeDialog.dismiss();
                showDownloadDialog();
            }
        });

//        negativeButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                noticeDialog.dismiss();
//            }
//        });

//        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//        builder.setTitle("版本更新");
//        builder.setMessage(updateMsg);
//        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
//
//            public void onClick(DialogInterface dialog, int which) {
//                noticeDialog.dismiss();
//                showDownloadDialog();
//            }
//        });
//        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
//
//            public void onClick(DialogInterface dialog, int which) {
//                noticeDialog.dismiss();
//            }
//        });
//        noticeDialog = builder.create();

        showDialog(mContext, noticeDialog, 0.8);
        noticeDialog.show();
        noticeDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                {
                    return true;
                }
                else
                {
                    return true; //默认返回 false
                }

            }
        });
    }

    /*
     * 弹出下载进度对话框
     */
    private void showDownloadDialog() {

        downloadDialog = new Dialog(mContext, R.style.dialog);
        downloadDialog.setContentView(R.layout.progress);
        downloadDialog.setCanceledOnTouchOutside(false);
        TextView title = (TextView)downloadDialog.findViewById(R.id.process_title);
        mProgressBar = (ProgressBar) downloadDialog.findViewById(R.id.updateProgress);
        Button negativeButton = (Button)downloadDialog.findViewById(R.id.process_negativeButton);

        title.setText("软件更新");
        negativeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                downloadDialog.dismiss();
                interceptFlag = true;
            }
        });

//        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//        builder.setTitle("软件更新");
//        final LayoutInflater inflater = LayoutInflater.from(mContext);
//        View v = inflater.inflate(R.layout.progress, null);
//        mProgressBar = (ProgressBar) v.findViewById(R.id.updateProgress);
//        builder.setView(v);
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//            public void onClick(DialogInterface dialog, int which) {
//                downloadDialog.dismiss();
//                interceptFlag = true;
//            }
//        });
//        downloadDialog = builder.create();
        showDialog(mContext, downloadDialog,0.8);
        downloadDialog.show();

        downloadLatestVersionApk();

    }

    /*
     * 下载最新的apk文件
     */
    private void downloadLatestVersionApk() {
        downloadApkThread = new Thread(downloadApkRunnable);
        downloadApkThread.start();
    }

    //匿名内部类，apk文件下载线程
    private Runnable downloadApkRunnable = new Runnable() {

        public void run() {
            try {
                URL url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.connect();
                int length = conn.getContentLength();
                Log.e(TAG, "总字节数:" + length);
                InputStream is = conn.getInputStream();
                File file = new File(UIController.SD_DIR_PATH);
                if (!file.exists()) {
                    file.mkdir();
                }
                File apkFile = new File(saveFileName);
                FileOutputStream out = new FileOutputStream(apkFile);
                int count = 0;
                int readnum = 0;
                byte[] buffer = new byte[1024];
                do {
                    readnum = is.read(buffer);
                    count += readnum;
                    progress = (int) (((float) count / length) * 100);
                    Log.e(TAG, "下载进度"+progress);
                    mhandler.sendEmptyMessage(DOWNLOAD_ING);
                    if (readnum <= 0) {
                        // 下载结束
                        mhandler.sendEmptyMessage(DOWNLOAD_OVER);
                        break;
                    }
                    out.write(buffer,0,readnum);
                } while (!interceptFlag);
                is.close();
                out.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };
    /*
     * 安装下载的apk文件
     */
    private void installApk() {
        File file= new File(saveFileName);
        if(!file.exists()){
            return;
        }
        Intent intent= new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }


    public  void showDialog(Context context, final Dialog dialog, double scale) {
        WindowManager wm = (WindowManager) context .getSystemService(Context.WINDOW_SERVICE);
        Window dgWindow = dialog.getWindow();
        WindowManager.LayoutParams wl = dgWindow.getAttributes();
        wl.width = (int) (wm.getDefaultDisplay().getWidth()*scale);
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }
}
