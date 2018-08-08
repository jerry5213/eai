package com.zxtech.gks.ui.pa;

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
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.BasicResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.OfficePoiUtil;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;
import com.zxtech.gks.IActivity;
import com.zxtech.gks.model.vo.PageParamBean;
import com.zxtech.gks.model.vo.PrProduct;

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
 * Created by SYP521 on 2017/10/24.
 */

public class ProjectPriceApprovalListActivity extends BaseActivity implements IActivity, BGARefreshLayout.BGARefreshLayoutDelegate,BaseQuickAdapter.OnItemClickListener,BaseQuickAdapter.OnItemChildClickListener {

    private ProjectPriceApprovalListAdapter adapter;
    private int page = 1;
    private int totalCount = 0;

    @BindView(R.id.rl_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<PrProduct> mData = new ArrayList<>();
    private int location;
    public static final String FILE_FLAG = "QuotationDocument#";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_price_approve_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,getString(R.string.quotatoin_approval));

        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, false);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        rv.addItemDecoration(new MyItemDecoration(15));

        adapter = new ProjectPriceApprovalListAdapter(this,R.layout.item_ppa_list,mData);
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);

        initData();
    }

    private Map getParams(){

        Map params = new HashMap();
        params.put("transactUserNo",getUserNo());
        params.put("pageSize", APPConfig.PAGE_SIZE);
        return params;
    }

    public void initData() {

        mRefreshLayout.beginRefreshing();
    }


    private void refresh(){

        Map params = getParams();
        params.put("pageIndex","1");
        baseResponseObservable = HttpFactory.getApiService().
                getProjectPriceApprovalListByPage(params);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BasicResponse<PageParamBean<PrProduct>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<PageParamBean<PrProduct>>>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse<PageParamBean<PrProduct>> response) {

                        mData.clear();
                        mData.addAll(response.getData().getData());
                        adapter.notifyDataSetChanged();
                        mRefreshLayout.endRefreshing();
                        page = 1;
                        totalCount = response.getData().getTotalCount();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        mRefreshLayout.endRefreshing();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            adapter.getData().remove(location);
            adapter.notifyDataSetChanged();
            setResult(1);
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refresh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

        if(adapter.getData().size()>=totalCount){
            mRefreshLayout.endLoadingMore();
            ToastUtil.showLong(getString(R.string.toast3));
            return false;
        }
        return true;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PrProduct priceApproval = mData.get(position);
        Intent intent = new Intent(mContext, ProjectPriceApprovalDetailActivity.class);
        intent.putExtra("priceReviewGuid", priceApproval.getGuid());
        intent.putExtra("instanceNodeId", priceApproval.getInstanceNodeId());
        startActivityForResult(intent, 1);
        location = position;
    }

    @Override
    public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
        String guid = mData.get(position).getGuid();
        if ((boolean) com.zxtech.gks.common.SPUtils.get(mContext,FILE_FLAG+guid,false)) {
            File file = new File(getFilesDir(),guid+".docx");
            OfficePoiUtil.openFile(mContext, file);
        }else{
            baseResponseObservable = HttpFactory.getApiService().
                    getQuotationDocument(guid);
            baseResponseObservable
                    .compose(this.bindToLifecycle())
                    .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<String>>(this, true) {
                        @Override
                        public void onSuccess(BaseResponse<String> response) {
                            downloadDoc(response.getData().replace("\"",""),mData.get(position).getGuid(),position);
                        }
                    });
        }

    }


    private void downloadDoc(String url, final String fileId,final int position){
        showProgress();
        progressDialog.setTint(getString(R.string.downloading));
        final File file = new File(getFilesDir(),fileId+".docx");
            FileDownloader.getImpl().create(url)
                    .setPath(file.getAbsolutePath())
                    .setListener(new FileDownloadSampleListener() {

                        @Override
                        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            progressDialog.setProgress(soFarBytes * 100 / totalBytes + "%");
                        }

                        @Override
                        protected void completed(BaseDownloadTask task) {
                            dismissProgress();
                            SPUtils.put(mContext,FILE_FLAG+fileId,true);
                            adapter.notifyItemChanged(position);
                            OfficePoiUtil.openFile(mContext, file);
                        }


                        @Override
                        protected void error(BaseDownloadTask task, Throwable e) {
                            dismissProgress();
                        }

                    }).start();
    }
}
