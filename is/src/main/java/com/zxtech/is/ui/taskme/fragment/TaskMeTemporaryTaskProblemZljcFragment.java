package com.zxtech.is.ui.taskme.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.SPUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.MyItemDecoration;
import com.zxtech.is.BaseFragment;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.temporary.TemporaryTask;
import com.zxtech.is.service.common.FragmentService;
import com.zxtech.is.service.temporary.TemporaryTaskService;
import com.zxtech.is.ui.taskme.adapter.TaskMeTemporaryTaskProblemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by syp660 on 2018/4/19.
 */

public class TaskMeTemporaryTaskProblemZljcFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener {

    private List<TemporaryTask> mBeans = new ArrayList<>();
    private TaskMeTemporaryTaskProblemAdapter mAdapter;
    private int pageNum = 1;
    private int pageSize = 5;
    private int totalCount = 0;

    @BindView(R2.id.temporary_task_problem_review_rv)
    RecyclerView mRecyclerView;
    @BindView(R2.id.temporary_task_problem_review_rv_refresh)
    BGARefreshLayout mRefreshLayout;

    public static TaskMeTemporaryTaskProblemZljcFragment newInstance() {
        return new TaskMeTemporaryTaskProblemZljcFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_task_me_temporary_task_list_problem_info;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        //下拉刷新、上拉加载更多
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mContext, true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.setDelegate(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new MyItemDecoration());

        mAdapter = new TaskMeTemporaryTaskProblemAdapter(R.layout.item_task_me_temporary_task_problem, mBeans);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);
    }

    //当前页面加载后执行
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.beginRefreshing();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        FragmentService parentFragment = (FragmentService) getParentFragment();
        parentFragment.openProblemDetail("1", mBeans.get(position));
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
            ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_more_data));
        } else {
            pageNum++;
            requestNet();
            return true;
        }
        return false;
    }

    private void requestNet() {
        String userId = String.valueOf(SPUtils.get(mContext, "is_user_Id", ""));
//        userId = "08034624-5A68-4A2A-B7D2-F03360B1115C";
        TemporaryTaskService temporaryTaskService = HttpFactory.getService(TemporaryTaskService.class);
        temporaryTaskService.selectQuestionInfoByUserId(userId, "02", pageNum, pageSize)
                .compose(RxHelper.<PageResponse<List<TemporaryTask>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<PageResponse<List<TemporaryTask>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(PageResponse<List<TemporaryTask>> response) {
                        if (pageNum == 1) { //如果page等于1时 为下拉刷新方法
                            if (response.getData().size() == 0) {
                                mBeans.clear();
                                mAdapter.notifyDataSetChanged();
                                ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_data));
                                mRefreshLayout.endRefreshing(); //停止刷新
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
