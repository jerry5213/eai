package com.zxtech.mt.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.zxtech.mt.activity.MtCheckItemActivity;
import com.zxtech.mt.common.DownloadUtils;
import com.zxtech.mt.common.F;
import com.zxtech.mt.common.FileDownloadDialog;
import com.zxtech.mt.common.T;
import com.zxtech.mt.common.ZipExtractorTask;
import com.zxtech.mt.utils.APPConfig;
import com.zxtech.mt.utils.BizUtil;
import com.zxtech.mt.utils.DateUtil;
import com.zxtech.mt.utils.FileProvider7;
import com.zxtech.mt.widget.SimpleDialog;
import com.zxtech.mtos.R;



import com.zxtech.mt.entity.MtWorkItem;
import com.zxtech.mvp.konepluginp.PlayPluginActivity;


import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by Chw on 2016/6/27.
 */
public class MtInfoAdapter extends CommonAdapter<MtWorkItem> {
    Context mcontext;
    ViewHolder   mholder;

//    private ProgressBar progress_bar;

    public MtInfoAdapter(Context context, List<MtWorkItem> datas, int layoutId) {
        super(context, datas, layoutId);
        mcontext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final MtWorkItem checkItem, final int position) {
        mholder= holder;
        holder.setText(R.id.content_textview, checkItem.getItem_content());
        holder.setText(R.id.desc_textview, checkItem.getRemark());

//        if (checkItem.getItem_content().equals("机房、滑轮间环境") || checkItem.getItem_content().equals("轿顶") || checkItem.getItem_content().equals("井道照明") || checkItem.getItem_content().equals("对重/平衡重块及其压板")) {
//            holder.setImageViewBackground(R.id.content_imgfirsttview, R.drawable.img_play);
//        }
        holder.getView(R.id.content_imgfirsttview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String filePath = Environment.getExternalStorageDirectory().getPath();
                String fileName = "";

                if (checkItem.getCourseguid()!=null&&checkItem.getCourseguid()!="") {
                    fileName = checkItem.getCourseguid();
                }

                        openfile(fileName);


            }
        });


        if (checkItem.getCourseguid()!=null&&checkItem.getCourseguid()!="") {
            holder.getView(R.id.content_imgfirsttview).setVisibility(View.VISIBLE);
            holder.setImageViewBackground(R.id.content_imgfirsttview, R.drawable.img_play);
        }  else {
            holder.getView(R.id.content_imgfirsttview).setVisibility(View.INVISIBLE);
        }


        if ("1".equals(checkItem.getResult())) {
            holder.setTextBackground(R.id.yes_or_no_textview, R.drawable.true_selected);
        } else if ("2".equals(checkItem.getResult())) {
            holder.setTextBackground(R.id.yes_or_no_textview, R.drawable.false_selected);
        } else if ("3".equals(checkItem.getResult())) {
            holder.setTextBackground(R.id.yes_or_no_textview, R.drawable.mt_status1);
        } else if ("4".equals(checkItem.getResult())) {
            holder.setTextBackground(R.id.yes_or_no_textview, R.drawable.mt_status2);
        } else {
            holder.setTextBackground(R.id.yes_or_no_textview, R.drawable.true_unselected);

        }

    }

    private void download() {


        final String apkPath = APPConfig.DOWN_LOAD_PATH + DateUtil.getCurrentDate1() + "ecs.apk";

//        FileDownloader.getImpl().create(APPConfig.BASE_URL.split("mobileapi")[0] + "app/release.apk")
        FileDownloader.getImpl().create("http://etp.5000m.com/scorm-plugin/lxwalk-release.apk")
                .setPath(apkPath)
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        ((MtCheckItemActivity) mContext).getProgress_bar().setVisibility(View.VISIBLE);

                        long progress = soFarBytes * 100 / totalBytes;

                        ((MtCheckItemActivity) mContext).getProgress_bar().setProgress((int) progress);
                    }


                    @Override
                    protected void completed(BaseDownloadTask task) {
                        installApk(apkPath);
                        ((MtCheckItemActivity) mContext).getProgress_bar().setVisibility(View.GONE);
                    }


                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();

    }




    private void downLoadCourse(final String url){

//            final String url = "http://etp.5000m.com/lmsFiles/zip/ddbf7a81-38c4-4973-aad3-79473edc944a.zip";
//        final String url = "http://etp.5000m.com/lmsFiles/zip/e6703fee-dbd8-4f36-b333-6beb733593c5.zip";



            AlertDialog dialog = SimpleDialog.createDialog(mcontext,"提示","是否下载？","是","取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    AlertDialog alertDialog = FileDownloadDialog.createDialog(mcontext,"正在下载", (CharSequence) null, new DownloadUtils.DownloadListener() {
                        public void onDownloadComplete(String downloadUrl, String tag) {
                            T.showToast(mcontext,"下载完成");

                        }

                        public void onDownloadFailed(String downloadUrl, String tag, int errorCode) {
                            if (errorCode == 1008) {
                            } else {
                                T.showToast(mcontext,"下载失败");
                            }

                        }

                        public void onProgress(long totalBytes, long downloadedBytes, int progress) {
                        }
                    }, url, mcontext.getExternalCacheDir().getAbsolutePath(), DownloadUtils.getFileName(url), url);
                    if (alertDialog != null) {
                        alertDialog.show();
                    }

                }
            },new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            },false);
            dialog.show();

        }

    private void openfile(String fileName ) {
        String url = "http://etp.5000m.com/lmsFiles/zip/"+fileName+".zip";
        final File dir = new File(mcontext.getExternalCacheDir(), F.getNoExName(DownloadUtils.getFileName(url)));

        File file = new File(mcontext.getExternalCacheDir(), DownloadUtils.getFileName(url));

        if (null != dir && dir.exists()) {
            if (dir.isDirectory()) {
                Intent intent = new Intent(mcontext,PlayPluginActivity.class);


                String   filePath = dir + "/index_lms_html5.html";

                intent.putExtra("filePath", filePath);

                mcontext.startActivity(intent);
            }
        }else{
            if (file.exists()) {
                new ZipExtractorTask(file, dir, mcontext, true) {
                    @Override
                    protected void onPostExecute(Long result) {
                        super.onPostExecute(result);
//                    Intent intent = new Intent(context, CourseDetailActivity.class);
//                    intent.putExtra(C.DATA, data);
//                    mcontext.startActivity(intent);


//                        Intent intent = new Intent();
////                    filePath = filePath + "/courseFiles/" + fileName + "/index_lms_html5.html";
//
//                        intent.putExtra("filePath", dir+"/index_lms_html5.html");
//
//                        intent.setAction("com.zxtech.mvp.konepluginp");
//                        mcontext.startActivity(intent);



                        Intent intent = new Intent(mcontext,PlayPluginActivity.class);

//                        String filePath = getExternalCacheDir().getAbsolutePath();
//                        String fileName = DownloadUtils.getFileName(URL.getInstance().getFullUrl(data.getFile_url()));
//                        fileName = F.getNoExName(fileName);
                        String   filePath = dir + "/index_lms_html5.html";

                        intent.putExtra("filePath", filePath);
                        //intent.setAction(plugins.get("packageName"));
                        //intent.setAction("com.zxtech.mvp.konepluginp");
                        mcontext.startActivity(intent);


                    }
                }.execute();
            }
            else
            {
                downLoadCourse(url);
            }

        }


    }



    /*
    * 安装下载的apk文件
    */
    private void installApk(String apkPath) {
        File file = new File(apkPath);
        if (!file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        FileProvider7.setIntentDataAndType(mcontext,
                intent, "application/vnd.android.package-archive", file, true);
        mcontext.startActivity(intent);
    }


}
