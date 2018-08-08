package com.zxtech.gks.ui.cr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ContractReview;
import com.zxtech.ecs.net.BasicResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;
import com.zxtech.gks.IActivity;
import com.zxtech.gks.model.vo.PageParamBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * 合同評審
 * Created by SYP521 on 2017/12/13.
 */

public class ContractReviewActivity extends BaseActivity implements IActivity, BGARefreshLayout.BGARefreshLayoutDelegate,BaseQuickAdapter.OnItemClickListener {

    private ContractReviewAdapter adapter;
    private int page = 1;
    private int totalCount = 0;
    private List<ContractReview> mDatas = new ArrayList<>();
    private int location;

    @BindView(R.id.rl_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_price_approve_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,getString(R.string.contract_review));

        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration(15));

        adapter = new ContractReviewAdapter(R.layout.item_contract_review, mDatas);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);

        mRefreshLayout.beginRefreshing();
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

    private Map getParams() {

        Map params = new HashMap();
        params.put("transactUserNo", getUserNo());
        params.put("pageSize", APPConfig.PAGE_SIZE);
        return params;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refesh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (adapter.getData().size() >= totalCount) {
            mRefreshLayout.endLoadingMore();
            ToastUtil.showLong(getString(R.string.toast3));
            return false;
        }
        loadMore();
        return true;
    }

    private void loadMore() {

        Map params = getParams();
        params.put("pageIndex", page + 1 + "");

        baseResponseObservable = HttpFactory.getApiService().getContractByPage(params);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BasicResponse<PageParamBean<ContractReview>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<PageParamBean<ContractReview>>>(this, false) {

                    @Override
                    public void onSuccess(BasicResponse<PageParamBean<ContractReview>> response) {

                        adapter.getData().addAll(response.getData().getData());
                        adapter.notifyDataSetChanged();
                        mRefreshLayout.endLoadingMore();
                        page++;
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        mRefreshLayout.endLoadingMore();
                    }
                });
    }

    private void refesh() {

        Map params = getParams();
        params.put("pageIndex", "1");

        baseResponseObservable = HttpFactory.getApiService().getContractByPage(params);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BasicResponse<PageParamBean<ContractReview>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<PageParamBean<ContractReview>>>(this, false) {

                    @Override
                    public void onSuccess(BasicResponse<PageParamBean<ContractReview>> response) {

                        mDatas.clear();
                        if(response.getData().getData() != null){
                            mDatas.addAll(response.getData().getData());
                            adapter.notifyDataSetChanged();
                        }
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ContractReview bean = mDatas.get(position);
        Intent intent = new Intent(ContractReviewActivity.this, ContractReviewDetailActivity.class);
        intent.putExtra("id", bean.getContractGuid());
        intent.putExtra("instanceNodeId", bean.getInstanceNodeId());
        startActivityForResult(intent, 1);
        location = position;
    }
}
