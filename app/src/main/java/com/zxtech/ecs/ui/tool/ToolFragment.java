package com.zxtech.ecs.ui.tool;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ToolMenuAdapter;
import com.zxtech.ecs.ui.home.scheme.PackageManager;
import com.zxtech.ecs.ui.tool.decoration.DecorationMatchingActivity;
import com.zxtech.ecs.ui.tool.flowanalysis.FlowAnalysisActivity;
import com.zxtech.ecs.ui.tool.layoutscheme.LayoutSchemeActivity;
import com.zxtech.ecs.util.FileUtil;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by syp523 on 2018/3/28.
 */

public class ToolFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private ToolMenuAdapter mAdapter;
    private Class[] intentCalss = new Class[]{LayoutSchemeActivity.class, DecorationMatchingActivity.class, FlowAnalysisActivity.class, null};

    public static ToolFragment newInstance() {
        Bundle args = new Bundle();
        ToolFragment fragment = new ToolFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tool;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        toolbar_title.setText(getString(R.string.nav_tool));
        toolbar.setNavigationIcon(null);
        initView();
    }

    private void initView() {
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 4);
        linearLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
        List<String> mDatas = new ArrayList<>();
        mDatas.add("布置方案");
        mDatas.add("装潢选配");
        mDatas.add("流量分析");
        mDatas.add("整梯配置");
        mAdapter = new ToolMenuAdapter(R.layout.item_menu_tool, mDatas);
        recycleView.addItemDecoration(new MyItemDecoration());
        recycleView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (intentCalss[position] != null) {
            if (position == 1) {
                downVRModelRes(position);
            } else {
                startActivity(new Intent(mContext, intentCalss[position]));
            }
        } else {
            ToastUtil.showLong(getString(R.string.msg7));
        }

    }

    private void downVRModelRes(final int position) {
        final String fileDirs = APPConfig.ECS_MODEL_RESOURCES;
        if (FileUtil.existsFile(fileDirs + PackageManager.checkFile)) {
            startActivity(new Intent(mContext, intentCalss[position]));
        } else {
            FileUtil.createDirs(fileDirs);
            final String resName = "res.zip";
            showProgress();
            progressDialog.setTint(getString(R.string.downloading));

            FileDownloader.getImpl().create("http://118.190.202.234:2603/app/" + resName)
                    .setPath(fileDirs + resName)
                    .setListener(new FileDownloadSampleListener() {

                        @Override
                        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            progressDialog.setProgress(soFarBytes*100/totalBytes+"%");
                        }


                        @Override
                        protected void completed(BaseDownloadTask task) {
                            PackageManager.UpzipResourceDataFile(mContext, fileDirs + resName, fileDirs);
                            dismissProgress();
                            startActivity(new Intent(mContext, intentCalss[position]));
                        }


                        @Override
                        protected void error(BaseDownloadTask task, Throwable e) {
                            dismissProgress();
                        }

                        @Override
                        protected void warn(BaseDownloadTask task) {
                            dismissProgress();
                        }
                    }).start();
        }

    }
}
