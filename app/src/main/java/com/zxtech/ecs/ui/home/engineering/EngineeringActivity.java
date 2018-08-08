package com.zxtech.ecs.ui.home.engineering;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.EngineeringAdatper;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.model.Engineering;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.OfficePoiUtil;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * 土建申请
 * Created by syp523 on 2018/3/31.
 */

public class EngineeringActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate,BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private EngineeringAdatper mAdapter;
    private List<Engineering> mDatas = new ArrayList<>();
    public static final String FILE_FLAG = "EngineeringDraw#";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_engineering;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar,getString(R.string.generate_layout));
        BGARefreshViewHolder rv1 = new BGANormalRefreshViewHolder(this, false);
        refreshLayout.setRefreshViewHolder(rv1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.addItemDecoration(new MyItemDecoration(15));

        refreshLayout.setDelegate(this);

        mAdapter = new EngineeringAdatper(R.layout.item_engineering, mDatas);
        recycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);

        refreshLayout.beginRefreshing();
    }


    private void refesh() {

        baseResponseObservable = HttpFactory.getApiService().getEngineeringList(getUserNo(), getRoleNo());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<Engineering>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<Engineering>>>(this, false) {
                    @Override
                    public void onSuccess(BaseResponse<List<Engineering>> response) {
                        if (response.getData() != null && response.getData().size() > 0) {
                            mDatas.clear();
                            mDatas.addAll(response.getData());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showLong(getString(R.string.msg8));
                        }
                        refreshLayout.endRefreshing();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        refreshLayout.endRefreshing();
                    }
                });
    }

    private void loadMore() {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refesh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        loadMore();
        return true;
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
        Engineering engineering = mDatas.get(position);
        final String guid = engineering.getGuid();

        if ((boolean) com.zxtech.gks.common.SPUtils.get(mContext, FILE_FLAG + guid, false)) {
            File file = new File(getFilesDir(), guid + ".pdf");
            OfficePoiUtil.openFile(mContext, file);
        } else {
            baseResponseObservable = HttpFactory.getApiService().
                    getEngineeringDocument(engineering.getTaskGuId());
            baseResponseObservable
                    .compose(this.bindToLifecycle())
                    .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<String>>(this, true) {
                        @Override
                        public void onSuccess(BaseResponse<String> response) {
                            downloadDoc(response.getData().replace("\"", ""), guid, position);
                        }
                    });
        }
    }


    private void downloadDoc(String url, final String fileId, final int position) {
        showProgress();
        progressDialog.setTint(getString(R.string.downloading));
        final File file = new File(getFilesDir(), fileId + ".pdf");
        BaseDownloadTask baseDownloadTask = FileDownloader.getImpl().create(url)
                .setPath(file.getAbsolutePath())
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        progressDialog.setProgress(soFarBytes * 100 / totalBytes + "%");
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        dismissProgress();
                        SPUtils.put(mContext, FILE_FLAG + fileId, true);
                        mAdapter.notifyItemChanged(position);
                        OfficePoiUtil.openFile(mContext, file);
                    }


                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        dismissProgress();
                    }

                });

        baseDownloadTask.start();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final Engineering engineering = mDatas.get(position);

        baseResponseObservable = HttpFactory.getApiService().getProductParams(engineering.getElevatorAssignGuid());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<Map<String, String>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Map<String, String>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<Map<String, String>> response) {
                        Intent intent = new Intent(mContext, EngineeringEditActivity.class);

                        HashMap<String, String> params = new HashMap<>();
                        params.put(Constants.CODE_ELEVATORTYPE,engineering.getElevatorType());
                        params.put(Constants.CODE_ELEVATORPRODUCT,engineering.getElevatorProduct());
                        params.put("TypeId",engineering.getTypeId());
                        params.put("ProductGuid",engineering.getGuid());
                        params.put("ProjectGuid",engineering.getProjectGuid());
                        params.put("UserGuid",getUserId());
                        params.put("UserNo",getUserNo());
                        params.put("UserName",getUserName());
                        params.put("RoleNo",getRoleNo());
                        params.putAll(response.getData());
                        intent.putExtra("datas", params);
                        startActivity(intent);
                    }
                });

    }
}
