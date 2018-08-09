package com.zxtech.is.ui.task.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.project.Project;
import com.zxtech.is.ui.task.adpter.ProjectAdpter;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;


public class TaskListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener {

    private ProjectAdpter projectAdpter;
    private List<Project> projectList = new ArrayList<>();
    private int totalCount = 10;
    private int page = 0;

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.rl_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R2.id.rvProject)
    RecyclerView mRvProject;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(mToolbar);

        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mContext, true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.setDelegate(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvProject.setLayoutManager(linearLayoutManager);
        mRvProject.addItemDecoration(new MyItemDecoration());

        projectAdpter = new ProjectAdpter(R.layout.item_task_project, projectList);
        projectAdpter.bindToRecyclerView(mRvProject);
        mRvProject.setAdapter(projectAdpter);

        mRefreshLayout.beginRefreshing();

        projectAdpter.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(mContext, ElevatorTaskListActivity.class);

        Project curProject = projectList.get(position);
        intent.putExtra("projectNo", curProject.getProjectName());
        intent.putExtra("category", "team");

        startActivity(intent);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refresh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (projectAdpter.getData().size() >= totalCount) {
            mRefreshLayout.endLoadingMore();
            ToastUtil.showLong("没有更多数据了");
            return false;
        }
        loadMore();
        mRefreshLayout.endLoadingMore();
        return true;
    }

    private void refresh() {

        projectList.clear();

        Project p1 = new Project();
        p1.setProjectName("东文章大夏1");
        p1.setAddress("中国沈阳三好街");
        projectList.add(p1);

        Project p2 = new Project();
        p2.setProjectName("东文章大夏2");
        p2.setAddress("中国沈阳三好街");
        projectList.add(p2);

        Project p3 = new Project();
        p3.setProjectName("东文章大夏3");
        p3.setAddress("中国沈阳三好街");
        projectList.add(p3);

        projectAdpter.notifyDataSetChanged();
        mRefreshLayout.endRefreshing();
        page = 1;


    }

    private void loadMore() {
        Project p4 = new Project();
        p4.setProjectName("东文章大夏1");
        p4.setAddress("中国沈阳三好街");
//        projectList.add(p1);

        Project p5 = new Project();
        p5.setProjectName("东文章大夏2");
        p5.setAddress("中国沈阳三好街");
//        projectList.add(p2);

        Project p6 = new Project();
        p6.setProjectName("东文章大夏4");
        p6.setAddress("中国沈阳三好街");
//        projectList.add(p3);

        projectAdpter.getData().add(p4);
//
    }

}
