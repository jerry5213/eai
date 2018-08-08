package com.zxtech.ecs.ui.home;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.AppUpdateContentAdapter;
import com.zxtech.ecs.model.AppVersion;
import com.zxtech.ecs.util.DateUtil;
import com.zxtech.ecs.util.FileProvider7;
import com.zxtech.ecs.util.ToastUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by syp523 on 2018/2/27.
 */

public class UpdateDialogFragment extends DialogFragment implements View.OnClickListener {

    private TextView version_tv;
    private RecyclerView content_rv;
    private Button update_btn;
    private ProgressBar progress_bar;
    private AppVersion appVersion;


    public static UpdateDialogFragment newInstance(AppVersion appVersion) {
        UpdateDialogFragment fragment = new UpdateDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("appVersion", appVersion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加这句话去掉自带的标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        View view = inflater.inflate(R.layout.dialog_update_app, null);
        version_tv = view.findViewById(R.id.version_tv);
        content_rv = view.findViewById(R.id.content_rv);
        update_btn = view.findViewById(R.id.update_btn);
        progress_bar = view.findViewById(R.id.progress_bar);

        update_btn.setOnClickListener(this);

        initView();
        return view;
    }

    private void initView() {
        appVersion = (AppVersion) getArguments().getSerializable("appVersion");
        version_tv.setText("V " + appVersion.getVersion_name());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        content_rv.setLayoutManager(linearLayoutManager);

        List<String> strings = Arrays.asList(appVersion.getUpdate_content().split("/"));
        AppUpdateContentAdapter adapter = new AppUpdateContentAdapter(getActivity(), R.layout.item_app_update, strings);
        content_rv.setAdapter(adapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        WindowManager m = getActivity().getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //params.gravity = Gravity.BOTTOM;
        params.width = (int) (d.getWidth() * 0.7);
        window.setAttributes(params);
        //设置背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_btn:
                installProcess();

                break;
        }
    }


    private void download() {
        update_btn.setVisibility(View.GONE);
        progress_bar.setVisibility(View.VISIBLE);

        final String apkPath = APPConfig.DOWN_LOAD_PATH + DateUtil.getCurrentDate1() + "ecs.apk";

        FileDownloader.getImpl().create(APPConfig.BASE_URL.split("mobileapi")[0] + "app/release.apk")
                .setPath(apkPath)
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        int progress = (int) (soFarBytes * 100f / totalBytes);
                        progress_bar.setProgress(progress);
                    }


                    @Override
                    protected void completed(BaseDownloadTask task) {
                        installApk(apkPath);
                        update_btn.setVisibility(View.VISIBLE);
                        progress_bar.setVisibility(View.INVISIBLE);
                    }


                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        update_btn.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        update_btn.setVisibility(View.VISIBLE);
                    }
                }).start();

    }

    //安装应用的流程
    private void installProcess() {
        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先获取是否有安装未知来源应用的权限
            haveInstallPermission = getActivity().getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {//没有权限
                startInstallPermissionSettingActivity();
                ToastUtil.showLong(getString(R.string.msg13));
                return;
            }
        }
        //有权限，开始安装应用程序
        download();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        Uri packageURI = Uri.parse("package:" + getActivity().getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, 10086);
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
        FileProvider7.setIntentDataAndType(getActivity(),
                intent, "application/vnd.android.package-archive", file, true);
        getActivity().startActivity(intent);
    }
}
