package com.zxtech.is.ui.task.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.BaseFragment;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.project.Project;
import com.zxtech.is.service.TestService;
import com.zxtech.is.ui.task.activity.ElevatorTaskListActivity;
import com.zxtech.is.ui.task.activity.S1TaskDetailActivity;
import com.zxtech.is.ui.task.adpter.TeamTaskAdpter;
import com.zxtech.is.ui.task.dialog.TestDialogFragment;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;


import static com.zxtech.is.net.RxHelper.rxSchedulerHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamTaskFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener {

    private TeamTaskAdpter teamTaskAdpter;
    private List<Project> projectList = new ArrayList<>();

    private int totalCount = 10;
    private int page = 0;


    @BindView(R2.id.rl_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R2.id.rvTeamTask)
    RecyclerView mRvTeamTask;

    public static TeamTaskFragment newInstance() {
        Bundle args = new Bundle();
        TeamTaskFragment fragment = new TeamTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_team_task;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {


        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this.getContext(), true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.setDelegate(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvTeamTask.setLayoutManager(linearLayoutManager);
        mRvTeamTask.addItemDecoration(new MyItemDecoration());

        teamTaskAdpter = new TeamTaskAdpter(R.layout.item_team_task, projectList);
        teamTaskAdpter.bindToRecyclerView(mRvTeamTask);
        mRvTeamTask.setAdapter(teamTaskAdpter);

        mRefreshLayout.beginRefreshing();

        teamTaskAdpter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

//        Intent intent = new Intent(mContext, S1TaskDetailActivity.class);
//
//        Project curProject = projectList.get(position);
//        intent.putExtra("projectNo",curProject.getProjectName());
//        intent.putExtra("category","team");
//
//        startActivity(intent);
//        TestDialogFragment dialog = TestDialogFragment.newInstance();
//        dialog.show(getFragmentManager(),"");

        TestService testService = HttpFactory.getService(TestService.class);
        testService.test("1").compose(RxHelper.<BaseResponse<Project>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<Project>>(getActivity(), false) {

            @Override
            public void onSuccess(BaseResponse<Project> response) {
                Log.d("test", response.getData().getProjectName());
            }
        });
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(mContext);
        normalDialog.setIcon(R.drawable.add_attach);
        normalDialog.setTitle("我是一个普通Dialog");
        normalDialog.setMessage("你要点击哪一个按钮呢?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refresh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (teamTaskAdpter.getData().size() >= totalCount) {
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

        teamTaskAdpter.notifyDataSetChanged();
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

        teamTaskAdpter.getData().add(p4);
//        teamTaskAdpter.getData().add(p5);
//        teamTaskAdpter.getData().add(p6);
        teamTaskAdpter.notifyDataSetChanged();

        page++;
    }
}
