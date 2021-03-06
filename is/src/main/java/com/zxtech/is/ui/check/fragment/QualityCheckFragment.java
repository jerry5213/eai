package com.zxtech.is.ui.check.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.zxtech.is.APPConfig;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.BizUtil;
import com.zxtech.is.util.DateUtil;
import com.zxtech.is.util.DownloadUtils;
import com.zxtech.is.util.F;
import com.zxtech.is.util.FileDownloadDialog;
import com.zxtech.is.util.FileProvider7;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.BaseFragment;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.check.QualityCheck;
import com.zxtech.is.model.taskquestion.TaskQuestion;
import com.zxtech.is.service.check.QualityCheckService;
import com.zxtech.is.ui.check.activity.QualityCheckActivity;
import com.zxtech.is.ui.check.adaper.QualityCheckAdaper;
import com.zxtech.is.ui.taskquestion.activity.TaskQuestionActivity;
import com.zxtech.is.util.T;
import com.zxtech.is.util.ZipExtractorTask;
import com.zxtech.is.widget.MyItemDecoration;
import com.zxtech.is.widget.SimpleDialog;
import com.zxtech.mvp.konepluginp.PlayPluginActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by hsz on 2018/4/21.
 */

public class QualityCheckFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener {

    private List<QualityCheck> mBeans = new ArrayList<>();
    private QualityCheckAdaper mAdapter;

    @BindView(R2.id.quality_check_rv)
    RecyclerView mRecyclerView;
    @BindView(R2.id.progress_bar)
    ProgressBar progress_bar;

    private QualityCheckActivity qualityCheckActivity;

    public static QualityCheckFragment newInstance(QualityCheck qualityCheck) {
        Bundle bundle = new Bundle();
        bundle.putString("projectGuid", qualityCheck.getProjectGuid());
        bundle.putString("projectName", qualityCheck.getProjectName());
        bundle.putString("isElevatorGuid", qualityCheck.getIsElevatorGuid());
        bundle.putString("isElevatorName", qualityCheck.getIsElevatorName());
        bundle.putString("optionCode", qualityCheck.getOptionCode());
        bundle.putString("optionName", qualityCheck.getOptionName());
        bundle.putString("isMstCheckQCategory", qualityCheck.getIsMstCheckQCategory());

        QualityCheckFragment fragment = new QualityCheckFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_quality_check;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new MyItemDecoration());

        mAdapter = new QualityCheckAdaper(R.layout.item_quality_check, mBeans);
        mAdapter.bindToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        requestNet();
    }

    private void requestNet() {
        QualityCheck qualityCheck = new QualityCheck();
        Bundle arguments = getArguments();
        qualityCheck.setIsElevatorGuid(arguments.get("isElevatorGuid").toString());
        qualityCheck.setIsMstCheckQCategory(arguments.get("isMstCheckQCategory").toString());
        String param = GsonUtils.toJson(qualityCheck, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        QualityCheckService qualityCheckService = HttpFactory.getService(QualityCheckService.class);
        qualityCheckService.getIsMstCheckQ(requestBody)
                .compose(RxHelper.<BaseResponse<List<QualityCheck>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<QualityCheck>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<QualityCheck>> response) {

                        if (response.getData() != null && response.getData().size() > 0) {
                            mBeans.clear();
                            mBeans.addAll(response.getData());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        String key = mBeans.get(position).getIsMstChkQGuid();
        String value = "-1";
        if (view.getId() == R.id.item_quality_check_right_but) {
            if (view.isSelected()) {
                view.setSelected(false);
            } else {
                view.setSelected(true);
                value = "1";
            }
            View viewByPosition = mAdapter.getViewByPosition(position, R.id.item_quality_check_wrong_but);
            viewByPosition.setSelected(false);
        } else if (view.getId() == R.id.item_quality_check_wrong_but) {
            if (view.isSelected()) {
                view.setSelected(false);
            } else {
                view.setSelected(true);
                value = "0";
            }
            View viewByPosition = mAdapter.getViewByPosition(position, R.id.item_quality_check_right_but);
            viewByPosition.setSelected(false);
        }
        else if (view.getId() == R.id.item_class_play)
        {


                openfile(String.valueOf(mBeans.get(position).getIsMstChkQCourseGuid()));

        }

        qualityCheckActivity.setIsMstCheckQMapFun(key, value);

        if (value.equals("0")) {
            Bundle arguments = getArguments();
            Intent intent = new Intent();
            intent.putExtra("openMode", "1");
            intent.putExtra("projectGuid", arguments.get("projectGuid").toString());
            intent.putExtra("projectName", arguments.get("projectName").toString());
            intent.putExtra("elevatorGuid", arguments.get("isElevatorGuid").toString());
            intent.putExtra("elevatorName", arguments.get("isElevatorName").toString());
            intent.putExtra("taskType", "02");
            intent.putExtra("taskName", getResources().getString(R.string.is_quality_check));
            TextView viewByPosition = (TextView) mAdapter.getViewByPosition(position, R.id.item_quality_check_text_0);
            viewByPosition.getText();
            intent.putExtra("remark", viewByPosition.getText());
            intent.setClass(getActivity(), TaskQuestionActivity.class);
            startActivity(intent);
        }
    }
    //下载课程
    private void downLoadCourse(final String url){




        AlertDialog dialog = SimpleDialog.createDialog(mContext,"提示","是否下载？","是","取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                AlertDialog alertDialog = FileDownloadDialog.createDialog(mContext,"正在下载", (CharSequence) null, new DownloadUtils.DownloadListener() {
                    public void onDownloadComplete(String downloadUrl, String tag) {
                        T.showToast(mContext,"下载完成");

                    }

                    public void onDownloadFailed(String downloadUrl, String tag, int errorCode) {
                        if (errorCode == 1008) {
                        } else {
                            T.showToast(mContext,"下载失败");
                        }

                    }

                    public void onProgress(long totalBytes, long downloadedBytes, int progress) {
                    }
                }, url, mContext.getExternalCacheDir().getAbsolutePath(), DownloadUtils.getFileName(url), url);
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
    //解压播放课程
    private void openfile(String courseGuid) {
        //课件路径写死了，由于调用阿里云的学习路径。
        final String url = "http://etp.5000m.com/lmsFiles/zip/"+courseGuid+".zip";

        final File dir = new File(mContext.getExternalCacheDir(), F.getNoExName(DownloadUtils.getFileName(url)));

        File file = new File(mContext.getExternalCacheDir(), DownloadUtils.getFileName(url));

        if (null != dir && dir.exists()) {
            if (dir.isDirectory()) {
                Intent intent = new Intent(mContext,PlayPluginActivity.class);
//                    filePath = filePath + "/courseFiles/" + fileName + "/index_lms_html5.html";
//                String filePath = Environment.getExternalStorageDirectory().getPath();

//                filePath = filePath + "/courseFiles/" + "Mt.1" + "/index_lms_html5.html";
                intent.putExtra("filePath", dir+"/index_lms_html5.html");//filePath

//                intent.putExtra("filePath", filePath);//

                mContext.startActivity(intent);
            }
        }else{
            if (file.exists()) {
                new ZipExtractorTask(file, dir, mContext, true) {
                    @Override
                    protected void onPostExecute(Long result) {
                        super.onPostExecute(result);
//                    Intent intent = new Intent(context, CourseDetailActivity.class);
//                    intent.putExtra(C.DATA, data);
//                    mcontext.startActivity(intent);


                        Intent intent = new Intent(mContext,PlayPluginActivity.class);
//                    filePath = filePath + "/courseFiles/" + fileName + "/index_lms_html5.html";

                        intent.putExtra("filePath", dir+"/index_lms_html5.html");

                        intent.setAction("com.zxtech.mvp.konepluginp");
                        mContext.startActivity(intent);



                    }
                }.execute();
            }
            else
            {
                downLoadCourse(url);
            }

        }


    }

    //下载插件
    private void download() {


        final String apkPath = APPConfig.DOWN_LOAD_PATH + DateUtil.getCurrentDate1() + "ecs.apk";

//        FileDownloader.getImpl().create(APPConfig.BASE_URL.split("mobileapi")[0] + "app/release.apk")
        FileDownloader.getImpl().create("http://etp.5000m.com/scorm-plugin/lxwalk-release.apk")
                .setPath(apkPath)
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        progress_bar.setVisibility(View.VISIBLE);

                        long progress = soFarBytes * 100 / totalBytes;

                        progress_bar.setProgress((int) progress);
                    }


                    @Override
                    protected void completed(BaseDownloadTask task) {
                        installApk(apkPath);
                        progress_bar.setVisibility(View.GONE);
                    }


                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();

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
        FileProvider7.setIntentDataAndType(mContext,
                intent, "application/vnd.android.package-archive", file, true);
        mContext.startActivity(intent);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        qualityCheckActivity = ((QualityCheckActivity) activity);
    }
}
