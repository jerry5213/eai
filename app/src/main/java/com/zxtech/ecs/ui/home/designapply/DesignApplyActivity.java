package com.zxtech.ecs.ui.home.designapply;

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
import com.zxtech.ecs.adapter.DesignApplyAdatper;
import com.zxtech.ecs.model.DesignApply;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.company.activity.ShowBigImageActivity;
import com.zxtech.ecs.util.OfficePoiUtil;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by syp523 on 2018/4/5.
 */

public class DesignApplyActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private DesignApplyAdatper mAdapter;
    private List<DesignApply> mDatas = new ArrayList<>();
    public static final String FILE_FLAG = "DesignDraw#";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_design_apply;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar,getString(R.string.rendering_picture));
        BGARefreshViewHolder rv1 = new BGANormalRefreshViewHolder(this, false);
        refreshLayout.setRefreshViewHolder(rv1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.addItemDecoration(new MyItemDecoration(15));

        refreshLayout.setDelegate(this);

        mAdapter = new DesignApplyAdatper(R.layout.item_design_apply, mDatas);
        recycleView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);

        refreshLayout.beginRefreshing();
    }


    private void refesh() {
        baseResponseObservable = HttpFactory.getApiService().getDesignApplyList(getUserNo());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<DesignApply>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<DesignApply>>>(this, false) {
                    @Override
                    public void onSuccess(BaseResponse<List<DesignApply>> response) {
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
        DesignApply designApply = mDatas.get(position);
        final String guid = designApply.getGuid();

//        if ((boolean) com.zxtech.gks.common.SPUtils.get(mContext, FILE_FLAG + guid, false)) {
//            File file = new File(getFilesDir(), guid + ".pdf");
//            OfficePoiUtil.openFile(mContext, file);
//        } else {
        baseResponseObservable = HttpFactory.getApiService().
                getDrawDocument(designApply.getTaskGuId());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<ArrayList<String>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<ArrayList<String>>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<ArrayList<String>> response) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, ShowBigImageActivity.class);
                        intent.putExtra("images", response.getData());
                        startActivity(intent);
                    }
                });
//        }
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
}
