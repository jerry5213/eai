package com.zxtech.common.download;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.thin.downloadmanager.ThinDownloadManager;
import com.zxtech.esp.R;


/**
 * Created by Administrator on 2016/8/4.
 */
public class FileDownloadDialog {
    private FileDownloadDialog() {
    }

    /**
     * 创建文件下载对话框
     *
     * @param context
     * @param title
     * @param msg
     * @return
     */
    public static AlertDialog createDialog(Context context, CharSequence title, CharSequence msg, final DownloadUtils.DownloadListener downloadListener, final String downloadUrl, final String fileDir, final String fileName, final String tag) {

        if (context == null || !(context instanceof Activity) || ((Activity) context).isFinishing())
            return null;
        if (!downloadUrl.startsWith("http"))
        {
            Toast.makeText(context,"下载地址有误",Toast.LENGTH_SHORT).show();
            return null;
        }

        final ThinDownloadManager downloadManager = new ThinDownloadManager(1);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog_file_download, null);
        final TextView tv_info = (TextView) view.findViewById(R.id.tv_info);
        final NumberProgressBar npb_progress = (NumberProgressBar) view.findViewById(R.id.npb_progress);
        builder.setView(view);
        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);
        if (!TextUtils.isEmpty(msg))
            builder.setMessage(msg);
        builder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (downloadManager != null) {
                    downloadManager.cancelAll();
                }
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

        final String downloaded = context.getResources().getString(R.string.downloaded);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                DownloadUtils.download(downloadManager, new DownloadUtils.DownloadListener() {
                    @Override
                    public void onDownloadComplete(String downloadUrl, String tag) {
                        if (downloadListener != null)
                            downloadListener.onDownloadComplete(downloadUrl, tag);
                        dialog.dismiss();
                    }

                    @Override
                    public void onDownloadFailed(String downloadUrl, String tag, int errorCode) {
                        if (downloadListener != null)
                            downloadListener.onDownloadFailed(downloadUrl, tag, errorCode);
                        dialog.dismiss();
                    }

                    @Override
                    public void onProgress(long totalBytes, long downloadedBytes, int progress) {
                        if (downloadListener != null)
                            downloadListener.onProgress(totalBytes, downloadedBytes, progress);
                        tv_info.setText(downloaded + DownloadUtils.getBytesDownloaded(progress, totalBytes));
                        npb_progress.setProgress(progress);
                    }
                }, downloadUrl, fileDir, fileName, tag);
            }
        });

        return dialog;
    }
}
