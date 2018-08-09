package com.zxtech.is.ui.civilreview.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.MyItemDecoration;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.service.civilreview.CivilReviewService;
import com.zxtech.is.ui.civilreview.adapter.CivilReviewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by syp660 on 2018/4/23.
 */

public class CivilReviewActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener {

    private List<Map<String, Object>> mBeans = new ArrayList<>();
    private CivilReviewAdapter mAdapter;
    private int pageNum = 1;
    private int pageSize = 10;
    private int totalCount = 0;

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.civil_review_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R2.id.civil_review_rv)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_civil_review;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mContext, true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.setDelegate(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new MyItemDecoration());

        mAdapter = new CivilReviewAdapter(R.layout.item_civil_review, mBeans);
        mAdapter.bindToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.beginRefreshing();
        mAdapter.setOnItemClickListener(this);

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        pageNum = 1;
        requestNet();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (mAdapter.getData().size() >= totalCount) {
            mRefreshLayout.endLoadingMore();
            ToastUtil.showLong("没有更多数据了");
        } else {
            pageNum++;
            requestNet();
            return true;
        }
        return false;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    private void requestNet() {
        CivilReviewService civilReviewService = HttpFactory.getService(CivilReviewService.class);
        civilReviewService.taskIsPlanS1Committed(pageNum, pageSize)
                .compose(RxHelper.<PageResponse<List<Map<String, Object>>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<PageResponse<List<Map<String, Object>>>>(getActivity(), true) {

                    @Override
                    public void onSuccess(PageResponse<List<Map<String, Object>>> response) {
                        if (pageNum == 1) { //如果page等于1时 为下拉刷新方法
                            if (response.getData().size() == 0) {
                                ToastUtil.showLong("没有检索到数据");
                                mRefreshLayout.endRefreshing();
                            } else {
                                mBeans.clear();
                                mBeans.addAll(response.getData());
                                mAdapter.notifyDataSetChanged();
                                mRefreshLayout.endRefreshing(); //停止刷新
                                totalCount = response.getRowCount();
                            }
                        } else {
                            mBeans.addAll(response.getData());
                            mAdapter.notifyDataSetChanged();
                            mRefreshLayout.endLoadingMore();//停止加载更多
                            totalCount = response.getRowCount();
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        mRefreshLayout.endRefreshing();
                    }
                });
    }
}
