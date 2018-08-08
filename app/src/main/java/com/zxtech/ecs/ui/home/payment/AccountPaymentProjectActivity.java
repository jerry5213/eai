package com.zxtech.ecs.ui.home.payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.AccountPaymentProject;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by syp523 on 2018/7/10.
 */

public class AccountPaymentProjectActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshlayout)
    BGARefreshLayout refreshlayout;

    private List<AccountPaymentProject> mDatas = new ArrayList<>();
    private AccountPaymentProjectAdapter mAdapter;

    private int pageIndex = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_payment_project;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar,getString(R.string.project_list));

        refreshlayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshlayout.setDelegate(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerview.addItemDecoration(new MyItemDecoration(15));

        mAdapter = new AccountPaymentProjectAdapter(R.layout.item_account_payment_project, mDatas);
        mAdapter.setOnItemClickListener(this);
        recyclerview.setAdapter(mAdapter);
        refreshlayout.beginRefreshing();
    }


    private void refresh() {
        baseResponseObservable = HttpFactory.getApiService().getProjectToSapListByPage(getUserId(), 1, APPConfig.PAGE_SIZE);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<AccountPaymentProject>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<AccountPaymentProject>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<AccountPaymentProject>> response) {
                        if (response.getData() != null && response.getData().size() > 0) {
                            pageIndex = 1;
                            mDatas.clear();
                            mDatas.addAll(response.getData());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showLong(getString(R.string.msg8));
                        }
                        refreshlayout.endRefreshing();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        refreshlayout.endRefreshing();
                    }
                });
    }

    private void loadingMore() {
        baseResponseObservable = HttpFactory.getApiService().getProjectToSapListByPage(getUserId(),pageIndex + 1, APPConfig.PAGE_SIZE);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<AccountPaymentProject>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<AccountPaymentProject>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<AccountPaymentProject>> response) {
                        if (response.getData() != null && response.getData().size() > 0) {
                            pageIndex++;
                            mDatas.addAll(response.getData());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showLong(getString(R.string.msg8));
                        }
                        refreshlayout.endLoadingMore();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        refreshlayout.endLoadingMore();
                    }
                });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refresh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        loadingMore();
        return true;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AccountPaymentProject accountPaymentProject = mDatas.get(position);
        final String accountGuid = getIntent().getStringExtra("accountGuid");
        final String orderNumber = accountPaymentProject.getOrderNumber();
        baseResponseObservable = HttpFactory.getApiService().allotOrderToAccount(accountGuid,accountPaymentProject.getOrderNumber(),getUserId());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        Intent intent = new Intent(mContext,AccountPaymentDetailActivity.class);
                        intent.putExtra("accountGuid",accountGuid);
                        intent.putExtra("orderNumber",orderNumber);
                        startActivity(intent);
                        finish();
                    }
                });
    }


    class AccountPaymentProjectAdapter extends BaseQuickAdapter<AccountPaymentProject, BaseViewHolder> {

        public AccountPaymentProjectAdapter(int layoutResId, @Nullable List<AccountPaymentProject> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, AccountPaymentProject item) {
            helper.setText(R.id.order_tv, item.getOrderNumber());
            helper.setText(R.id.contract_tv, item.getContractNo());
            helper.setText(R.id.project_tv, item.getProjectName());
            helper.setText(R.id.project_no_tv, item.getProjectNo());
        }
    }


}
