package com.zxtech.ecs.ui.home.projecttrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.adapter.ProjectTrackListAdapter;
import com.zxtech.ecs.model.ProjectInfo;
import com.zxtech.ecs.model.ProjectTrack;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by syp600 on 2018/7/6.
 */

public class ProjectTrackFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @BindView(R.id.tv_add)
    TextView tv_add;

    private int pageIndex = 1;
    private int totalCount = 0;
    private boolean flag = false;

    private ProjectTrackListAdapter mAdapter;
    private List<ProjectTrack> mDatas = new ArrayList<>();

    public static ProjectTrackFragment newInstance(ProjectInfo projectInfo) {
        Bundle bundle = new Bundle();
        bundle.putString("projectGuid", projectInfo.getProjectGuid());
        bundle.putString("workFlowNodeName", projectInfo.getWorkFlowNodeName());
        bundle.putString("contactState", projectInfo.getContactState());
        ProjectTrackFragment fragment = new ProjectTrackFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_project_track;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        BGARefreshViewHolder rv1 = new BGANormalRefreshViewHolder(getActivity(), true);
        refreshLayout.setRefreshViewHolder(rv1);
        refreshLayout.setDelegate(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.addItemDecoration(new MyItemDecoration(15));

        refreshLayout.setDelegate(this);

        mAdapter = new ProjectTrackListAdapter(R.layout.item_project_track, mDatas);
        recycleView.setAdapter(mAdapter);


        mAdapter.setOnItemChildClickListener(this);

        Bundle arguments = getArguments();

        String workflow = arguments.get("workFlowNodeName") != null ? arguments.get("workFlowNodeName").toString() : "";
        String contactState = arguments.get("contactState") != null ? arguments.get("contactState").toString() : "";
        //报备-完成,   价审-准备,   价审中,    价审-完成,
        if ("P-End-Y".equals(workflow)
                || "R-Ready".equals(workflow)
                || "R-Doing".equals(workflow)
                || "R-End-Y".equals(workflow)
                // 合同评审-准备,
                // 合同评审中,
                // 合同评审-完成
                || "C-Ready".equals(workflow)
                || "C-Doing".equals(workflow)
                || "C-End-Y".equals(workflow)) {

            if (contactState.equals("已签约") || contactState.equals("已失单")) {
                tv_add.setBackgroundColor(getResources().getColor(R.color.color_background_gray));
                flag = false;
            } else {
                flag = true;
            }
        } else {
            //ToastUtil.showLong("请选择审批状态为报备完成后的项目!");
            tv_add.setBackgroundColor(getResources().getColor(R.color.color_background_gray));
            flag = false;
        }
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        refreshLayout.beginRefreshing();
    }

    private void refresh() {
        Bundle arguments = getArguments();
        String projectGuid = arguments.get("projectGuid").toString();
        int pageSize = 10;
        baseResponseObservable = HttpFactory.getApiService().
                getProjectTracklList(projectGuid, pageIndex, pageSize);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<ProjectTrack>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ProjectTrack>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<ProjectTrack>> response) {

                        if (response.getData() != null && response.getData().size() > 0) {
                            if (pageIndex == 1) { //如果page等于1时 为下拉刷新方法
                                if (response.getData().size() == 0) {
                                    mDatas.clear();
                                    mAdapter.notifyDataSetChanged();
//                                    ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_data));
                                    refreshLayout.endRefreshing(); //停止刷新
                                } else {
                                    mDatas.clear();
                                    mDatas.addAll(response.getData());
                                    mAdapter.notifyDataSetChanged();
                                    refreshLayout.endRefreshing(); //停止刷新
                                    totalCount = 3;
                                }
                            } else {
                                mDatas.addAll(response.getData());
                                mAdapter.notifyDataSetChanged();
                                refreshLayout.endLoadingMore();//停止加载更多
                                totalCount = 3;
                            }
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

    @OnClick({R.id.tv_add})
    public void onClick(View view) {
        int i = view.getId();
        Bundle arguments = getArguments();
        String projectGuid = arguments.get("projectGuid") != null ? arguments.get("projectGuid").toString() : "";
        if (i == R.id.tv_add) {
            if (flag) {
                Intent intent = new Intent(mContext, ProjectTrackInfoActivity.class);
                intent.putExtra("projectGuid", projectGuid);
                startActivityForResult(intent, 100);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            pageIndex = 1;
            refresh();
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        pageIndex = 1;
        refresh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (mAdapter.getData().size() >= totalCount) {
            refreshLayout.endLoadingMore();
//            ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_more_data));
        } else {
            pageIndex++;
            refresh();
            return true;
        }
        return false;
    }


    @Override
    public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {

    }
}
