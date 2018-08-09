package com.zxtech.is.ui.taskme.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.MyItemDecoration;
import com.zxtech.is.BaseFragment;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.service.taskme.TaskMeService;
import com.zxtech.is.ui.project.activity.ProjectItemAssignedActivity;
import com.zxtech.is.ui.smt.activity.ProjectInstaWayRecordsConfirmActivity;
import com.zxtech.is.ui.taskme.adapter.TaskMeItemAssignedAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by syp661 on 2018/4/19.
 * <p>
 * 开工确认
 */

public class TaskMeItemAssignedFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener {

    private static final int REQUEST = 0;
    private List<Map<String, Object>> mBeans = new ArrayList<>();
    private TaskMeItemAssignedAdapter mAdapter;
    private int pageNum = 1;
    private int pageSize = 10;
    private int totalCount = 0;

    @BindView(R2.id.civil_review_rv)
    RecyclerView mRecyclerView;
    @BindView(R2.id.civil_review_r_refresh)
    BGARefreshLayout mRefreshLayout;

    public static TaskMeItemAssignedFragment newInstance() {
        return new TaskMeItemAssignedFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_task_me_civilreview;
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
        mAdapter = new TaskMeItemAssignedAdapter(R.layout.item_task_me_civilreview, mBeans);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.beginRefreshing();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //if (resultCode == getActivity().RESULT_OK) {
        mRefreshLayout.beginRefreshing();
        super.onActivityResult(requestCode, resultCode, data);
        //}
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //获取项目ID
        String prjId = String.valueOf(mBeans.get(position).get("prjId"));
        //打开下一个activity  团队分配
        Intent intent = new Intent(mContext, ProjectItemAssignedActivity.class);
        intent.putExtra("projectId", prjId);
        intent.putExtra("checked", "1"); //TODO 1: 任务口 2：项目口
        startActivityForResult(intent, REQUEST);
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
            ToastUtil.showLong(mContext.getResources().getString(R.string.is_no_more_data));
        } else {
            pageNum++;
            requestNet();
            return true;
        }
        return false;
    }

    private void requestNet() {
        TaskMeService taskMeService = HttpFactory.getService(TaskMeService.class);
        taskMeService.taskMeCommon("projectTeam", pageNum, pageSize)
                .compose(RxHelper.<PageResponse<List<Map<String, Object>>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<PageResponse<List<Map<String, Object>>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(PageResponse<List<Map<String, Object>>> response) {
                        if (pageNum == 1) {
                            if (response.getData().size() == 0) {
                                mBeans.clear();
                                mRefreshLayout.endRefreshing();
                                mAdapter.notifyDataSetChanged();
                                ToastUtil.showLong(mContext.getResources().getString(R.string.is_no_retrieved_data));
                            } else {
                                mBeans.clear();
                                mBeans.addAll(response.getData());
                                mAdapter.notifyDataSetChanged();
                                mRefreshLayout.endRefreshing();
                                totalCount = response.getRowCount();
                            }
                        } else {
                            mBeans.addAll(response.getData());
                            mAdapter.notifyDataSetChanged();
                            mRefreshLayout.endLoadingMore();
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