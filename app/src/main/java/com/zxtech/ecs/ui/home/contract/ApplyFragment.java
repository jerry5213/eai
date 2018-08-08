package com.zxtech.ecs.ui.home.contract;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ContractApplyAdapter;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.OfficePoiUtil;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;
import com.zxtech.gks.model.vo.contract.ContractData;

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
 * 合同申请-已申请
 * Created by syp523 on 2018/5/31.
 */

public class ApplyFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemChildClickListener {

    private ContractApplyAdapter adapter;
    private List<ContractData> mDatas = new ArrayList<>();

    @BindView(R.id.rl_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    public static final String FILE_FLAG = "ContractDocument#";

    public static ApplyFragment newInstance() {
        Bundle args = new Bundle();
        ApplyFragment fragment = new ApplyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_apply;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {


        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), false);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration(15));

        adapter = new ContractApplyAdapter(R.layout.item_contract_apply, mDatas);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemChildClickListener(this);


    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.beginRefreshing();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refesh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return true;
    }


    private void refesh() {


        baseResponseObservable = HttpFactory.getApiService().getContractByApply(getUserNo());
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<ContractData>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ContractData>>>(getActivity(), true) {

                    @Override
                    public void onSuccess(BaseResponse<List<ContractData>> response) {

                        if (response.getData() != null && response.getData().size() > 0) {
                            mDatas.clear();
                            mDatas.addAll(response.getData());
                            adapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showLong(getString(R.string.msg8));
                        }
                        mRefreshLayout.endRefreshing();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        mRefreshLayout.endRefreshing();
                    }
                });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
        ContractData contractData = mDatas.get(position);
        if (view.getId() == R.id.file_download_iv) {

            final String fileId = contractData.getContractGuid() + "1";//标准设备合同

            if ((boolean) com.zxtech.gks.common.SPUtils.get(mContext, FILE_FLAG + fileId, false)) {
                File file = new File(mContext.getFilesDir(), fileId + ".pdf");
                OfficePoiUtil.openFile(mContext, file);
            } else {
                baseResponseObservable = HttpFactory.getApiService().
                        getContractDocument(contractData.getContractGuid(), contractData.getContractType(), getUserId());
                baseResponseObservable
                        .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                        .subscribe(new DefaultObserver<BaseResponse<String>>(getActivity(), true) {
                            @Override
                            public void onSuccess(BaseResponse<String> response) {
                                downloadDoc(response.getData().replace("\"", ""), fileId, position);
                            }
                        });
            }
        } else if (view.getId() == R.id.review_iv) {
            baseResponseObservable = HttpFactory.getApiService().
                    getProjectProgressInfo(contractData.getProjectNo());
            baseResponseObservable
                    .compose(RxHelper.<BaseResponse<Map<String, String>>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<Map<String, String>>>(getActivity(), true) {
                        @Override
                        public void onSuccess(BaseResponse<Map<String, String>> response) {
                        }
                    });
        } else if (view.getId() == R.id.sync_iv) {
            Map<String, String> params = new HashMap<>();
            params.put("contractGuid", contractData.getContractGuid());
            params.put("projectNo", contractData.getProjectNo());
            params.put("projectName", contractData.getProjectName());
            params.put("branchNo", contractData.getBranchNo());
            params.put("address", contractData.getAddress());

            baseResponseObservable = HttpFactory.getApiService().
                    sendProjectData(params);
            baseResponseObservable
                    .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            ToastUtil.showLong("同步成功");
                        }
                    });
        }

    }


    private void downloadDoc(String url, final String fileId, final int position) {
        showProgress();
        progressDialog.setTint(getString(R.string.downloading));
        final File file = new File(mContext.getFilesDir(), fileId + ".pdf");
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
                        adapter.notifyItemChanged(position);
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