package com.zxtech.ecs.ui.home.quote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.QuotedListAdapter;
import com.zxtech.ecs.model.PriceApproval;
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
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by syp523 on 2018/5/31.
 */

public class QuotedFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private QuotedListAdapter mAdapter;
    private List<PriceApproval> mDatas = new ArrayList<>();
    public static final String FILE_FLAG = "QuotationDocument#";

    public static QuotedFragment newInstance() {
        Bundle args = new Bundle();
        QuotedFragment fragment = new QuotedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_quoted;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

        BGARefreshViewHolder rv1 = new BGANormalRefreshViewHolder(getContext(), false);
        refreshLayout.setRefreshViewHolder(rv1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.addItemDecoration(new MyItemDecoration(15));

        refreshLayout.setDelegate(this);

        mAdapter = new QuotedListAdapter(R.layout.item_quoted, mDatas);
        recycleView.setAdapter(mAdapter);


        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        refreshLayout.beginRefreshing();
    }

    private void refresh() {
        baseResponseObservable = HttpFactory.getApiService().
                getProjectPriceApprovalList(getUserNo());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<PriceApproval>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<PriceApproval>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<PriceApproval>> response) {
                        mDatas.clear();
                        if (response.getData() != null && response.getData().size() > 0) {
                            mDatas.addAll(response.getData());
                        }else {
                            ToastUtil.showLong(getString(R.string.msg8));
                        }
                        mAdapter.notifyDataSetChanged();
                        refreshLayout.endRefreshing();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        refreshLayout.endRefreshing();
                    }
                });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refresh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return true;
    }


    @Override
    public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
        String guid = mDatas.get(position).getGuid();
        if ((boolean) com.zxtech.gks.common.SPUtils.get(mContext, FILE_FLAG + guid, false)) {
            File file = new File(mContext.getFilesDir(), guid + ".docx");
            OfficePoiUtil.openFile(mContext, file);
        } else {
            baseResponseObservable = HttpFactory.getApiService().
                    getQuotationDocument(guid);
            baseResponseObservable
                    .compose(this.bindToLifecycle())
                    .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<String>>(getActivity(), true) {
                        @Override
                        public void onSuccess(BaseResponse<String> response) {
                            downloadDoc(response.getData().replace("\"", ""), mDatas.get(position).getGuid(), position);
                        }
                    });
        }

    }


    private void downloadDoc(String url, final String fileId, final int position) {
        showProgress();
        progressDialog.setTint(getString(R.string.downloading));
        final File file = new File(mContext.getFilesDir(), fileId + ".docx");
        BaseDownloadTask baseDownloadTask = FileDownloader.getImpl().create(url)
                .setPath(file.getAbsolutePath())
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
        try {
            //
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        baseDownloadTask.start();
    }

}
