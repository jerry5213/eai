package com.zxtech.is.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zxtech.is.event.EventMessage;
import com.zxtech.is.model.FileBean;
import com.zxtech.is.net.thread.DownloadTask;
import com.zxtech.is.net.thread.InitThread;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kun on 2016/11/10.
 * 下载服务
 */
public class DownloadService extends Service {

    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_PAUSE = "ACTION_PAUSE";
    /**
     * 下载任务集合
     */
    private List<DownloadTask> downloadTasks = new ArrayList<>();
    public static ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(ACTION_START)) {
            FileBean fileBean = (FileBean) intent.getSerializableExtra("FileBean");
            for (DownloadTask downloadTask : downloadTasks) {
                if (downloadTask.getFileBean().getId() == fileBean.getId()) {
                    //如果下载任务中以后该文件的下载任务 则直接返回
                    return super.onStartCommand(intent, flags, startId);
                }
            }
            executorService.execute(new InitThread(fileBean));
        } else if (intent.getAction().equals(ACTION_PAUSE)) {
            FileBean fileBean = (FileBean) intent.getSerializableExtra("FileBean");
            DownloadTask pauseTask = null;
            for (DownloadTask downloadTask : downloadTasks) {
                if (downloadTask.getFileBean().getId() == fileBean.getId()) {
                    downloadTask.pauseDownload();
                    pauseTask = downloadTask;
                    break;
                }
            }
            //将下载任务移除
            downloadTasks.remove(pauseTask);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventMessage(EventMessage eventMessage) {
        switch (eventMessage.getType()) {
            case 1://下载线程初始化完毕
                FileBean fileBean = (FileBean) eventMessage.getObject();
                Log.w("AAA", "length:" + fileBean.getLength());
                //开始下载
                DownloadTask downloadTask = new DownloadTask(this, fileBean, 3);
                downloadTasks.add(downloadTask);
                break;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
