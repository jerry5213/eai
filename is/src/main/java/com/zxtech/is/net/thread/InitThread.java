package com.zxtech.is.net.thread;


import com.zxtech.is.event.EventMessage;
import com.zxtech.is.model.FileBean;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.zxtech.is.APPConfig.DOWN_LOAD_PATH;


/**
 * Created by 坤 on 2016/11/10.
 * 初始化线程
 */
public class InitThread extends Thread {

    private FileBean fileBean;

    public InitThread(FileBean fileBean) {
        this.fileBean = fileBean;
    }

    @Override
    public void run() {
        HttpURLConnection connection = null;
        RandomAccessFile randomAccessFile = null;
        try {
            URL url = new URL(fileBean.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            int fileLength = -1;
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                fileLength = connection.getContentLength();
            }
            if (fileLength <= 0) return;
            File dir = new File(DOWN_LOAD_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, fileBean.getFileName());
            randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.setLength(fileLength);
            fileBean.setLength(fileLength);
            EventMessage eventMessage = new EventMessage(1, fileBean);
            EventBus.getDefault().post(eventMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
