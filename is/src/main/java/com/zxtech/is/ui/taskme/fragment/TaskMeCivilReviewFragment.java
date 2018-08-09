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
import com.zxtech.is.ui.task.activity.S1TaskListActivity;
import com.zxtech.is.ui.task.activity.S1TaskReviewActivity;
import com.zxtech.is.ui.taskme.adapter.TaskMeCivilReviewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by syp660 on 2018/4/19.
 */

public class TaskMeCivilReviewFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener {

    private static final int REQUEST = 0;
    private List<Map<String, Object>> mBeans = new ArrayList<>();
    private TaskMeCivilReviewAdapter mAdapter;
    private int pageNum = 1;
    private int pageSize = 10;
    private int totalCount = 0;

    @BindView(R2.id.civil_review_rv)
    RecyclerView mRecyclerView;
    @BindView(R2.id.civil_review_r_refresh)
    BGARefreshLayout mRefreshLayout;

    public static TaskMeCivilReviewFragment newInstance() {
        return new TaskMeCivilReviewFragment();
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

        mAdapter = new TaskMeCivilReviewAdapter(R.layout.item_task_me_civilreview, mBeans);
        mAdapter.bindToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);
        //载入调用刷新方法 在懒加载中onLazyInitView 调用请求
        // mRefreshLayout.beginRefreshing();
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //     requestNet();
        mRefreshLayout.beginRefreshing();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Map<String, Object> m = mBeans.get(position);
        String taskDefKey = m.get("taskDefKey").toString();
        if (taskDefKey.equals("s1_fe")) {
            Intent intent = new Intent(mContext, S1TaskListActivity.class);
            intent.putExtra("projectId", m.get("prjId").toString());
            intent.putExtra("procDefKey", "s1");
            intent.putExtra("taskId", m.get("id").toString());
            startActivityForResult(intent, REQUEST);
        } else {
            Intent intent = new Intent(mContext, S1TaskReviewActivity.class);
            intent.putExtra("projectId", m.get("prjId").toString());
            intent.putExtra("procDefKey", "s1");
            intent.putExtra("taskId", m.get("id").toString());
            startActivityForResult(intent, REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mRefreshLayout.beginRefreshing();
        super.onActivityResult(requestCode, resultCode, data);

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
            ToastUtil.showLong(getResources().getString(R.string.is_no_more_data));

        } else {
            pageNum++;
            requestNet();
            return true;
        }
        return false;
    }


    private void requestNet() {
        TaskMeService taskMeService = HttpFactory.getService(TaskMeService.class);
        taskMeService.taskMeCommon("s1", pageNum, pageSize)
                .compose(RxHelper.<PageResponse<List<Map<String, Object>>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<PageResponse<List<Map<String, Object>>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(PageResponse<List<Map<String, Object>>> response) {
                        if (pageNum == 1) { //如果page等于1时 为下拉刷新方法
                            if (response.getData().size() == 0) {
                                mBeans.clear();
                                mAdapter.notifyDataSetChanged();
                                ToastUtil.showLong(getResources().getString(R.string.is_no_retrieved_data));
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
