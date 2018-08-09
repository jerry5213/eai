package com.zxtech.is.ui.person.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.common.net.PageResponse;
import com.zxtech.is.model.person.PersonMember;
import com.zxtech.is.model.project.Project;
import com.zxtech.is.model.team.IsSlInstallationmember;
import com.zxtech.is.service.person.PersonCheckService;
import com.zxtech.is.service.project.ProjectManageService;
import com.zxtech.is.ui.person.adpter.PersonCheckAdpter;
import com.zxtech.is.ui.project.activity.ProjectItemAssignedActivity;
import com.zxtech.is.ui.project.activity.ProjectManageDialog;
import com.zxtech.is.ui.project.adpter.ProjectManageAdpter;
import com.zxtech.is.ui.task.activity.ElevatorTaskListActivity;
import com.zxtech.is.ui.team.activity.ElaTeamAddPersonActivity;
import com.zxtech.is.ui.team.activity.SlinstallationunitDialog;
import com.zxtech.is.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by syp692 on 2018/5/2.
 */
@Route(path = "/is/personcheck")
public class PersonCheckActivity  extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener,View.OnClickListener, SlinstallationunitDialog.Unit,ProjectManageDialog.PROJECT{

    private PersonCheckAdpter personCheckAdpter;
    private List<PersonMember> personMemberList = new ArrayList<PersonMember>();
    private int totalCount = 0;
    private String  idCard;
    private int page = 1;
    private int pageSize = 10;
    //合作方id
    private String unitGuid;

    //项目id
    private String projectGuid;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.rl_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R2.id.is_person_5)
    RecyclerView rvPerson;
    @BindView(R2.id.is_person_1)
    TextView InstaillationUnitText;
    @BindView(R2.id.is_person_2)
    TextView ProjectText;
    @BindView(R2.id.is_person_6)
    ImageView searchButton;
    @BindView(R2.id.is_person_7)
    TextView idCardView;
    @BindView(R2.id.is_person_10)
    ImageView  closeunit;
    @BindView(R2.id.is_person_9)
    ImageView  closeproject;
    @BindView(R2.id.rv_7)
    ImageView  addperson;








    @Override
    protected int getLayoutId() {
        return  R.layout.activity_person_check;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(mToolbar);

        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mContext, true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.setDelegate(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPerson.setLayoutManager(linearLayoutManager);
        rvPerson.addItemDecoration(new MyItemDecoration());
        personCheckAdpter = new PersonCheckAdpter(R.layout.item_person_check_member, personMemberList);
        personCheckAdpter.bindToRecyclerView(rvPerson);
        rvPerson.setAdapter(personCheckAdpter);
        personCheckAdpter.setOnItemClickListener(this);
        mRefreshLayout.beginRefreshing();

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refesh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {


        if (personCheckAdpter.getData().size() >= totalCount) {
            mRefreshLayout.endLoadingMore();
            ToastUtil.showLong(getResources().getString(R.string.is_no_more_data));
        }
        else{
            loadMore();
            mRefreshLayout.endLoadingMore();
            return true;
        }



        return false;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if(personMemberList.get(position).getBlackflag()>0){
            ToastUtil.showLong(getResources().getString(R.string.is_black_now));
            return;
        }
        PersonMember personMember = personMemberList.get(position);
        Intent intent = new Intent(mContext, PersonCheckDetailedActivity.class);

        intent.putExtra("guid",personMember.getGuid());
        startActivity(intent);

    }

    @Override
    @OnClick({R2.id.is_person_1,R2.id.is_person_2,R2.id.is_person_6, R2.id.is_person_10, R2.id.is_person_9,R2.id.rv_7})

    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.is_person_1) {
            SlinstallationunitDialog unitDialog = SlinstallationunitDialog.newInstance();

            unitDialog.setUnit(this);

            unitDialog.show(((Activity) mContext).getFragmentManager(), "");


            //查询框
        } else if (id == R.id.is_person_6) {
            refesh();


            //Dialog项目
        } else if (id == R.id.is_person_2) {
            ProjectManageDialog projectDialog = ProjectManageDialog.newInstance();

            projectDialog.setProject(this);

            projectDialog.show(((Activity) mContext).getFragmentManager(), "");


            //清除合作方
        } else if (id == R.id.is_person_10) {
            InstaillationUnitText.setText("");
            unitGuid = "";
            closeunit.setVisibility(View.INVISIBLE);


            //清除项目
        } else if (id == R.id.is_person_9) {
            projectGuid = "";
            ProjectText.setText("");
            closeproject.setVisibility(View.INVISIBLE);


            //添加新安装工
        } else if (id == R.id.rv_7) {
            Intent intent = new Intent(mContext, ElaTeamAddPersonActivity.class);


            startActivity(intent);

        } else {
        }
    }

    private void refesh() {
        page = 1;
        PersonCheckService personCheckService = HttpFactory.getService(PersonCheckService.class);
        idCard= String.valueOf(idCardView.getText());
        personCheckService.selectPersonCheckList(unitGuid, idCard,projectGuid, page, pageSize).compose(RxHelper.<PageResponse<List<PersonMember>>>rxSchedulerHelper()).subscribe(new DefaultObserver<PageResponse<List<PersonMember>>>(getActivity(), false) {


            @Override
            public void onSuccess(PageResponse<List<PersonMember>> response) {
                personMemberList.clear();
                personMemberList.addAll(response.getData());
                personCheckAdpter.notifyDataSetChanged();
                mRefreshLayout.endRefreshing();

                totalCount = response.getRowCount();
                if (totalCount==0)
                {
                    ToastUtil.showLong(getResources().getString(R.string.is_no_retrieved_data));
                }
            }



            @Override
            public void onFail() {
                super.onFail();
                mRefreshLayout.endRefreshing();
            }

        });


    }
@Override
    public void onResume()
    {
        super.onResume();
        refesh();
    }

    private void loadMore() {

        PersonCheckService personCheckService = HttpFactory.getService(PersonCheckService.class);
        idCard= String.valueOf(idCardView.getText());
        personCheckService.selectPersonCheckList(unitGuid, idCard,projectGuid, page+1, pageSize).compose(RxHelper.<PageResponse<List<PersonMember>>>rxSchedulerHelper()).subscribe(new DefaultObserver<PageResponse<List<PersonMember>>>(getActivity(), false) {


            @Override
            public void onSuccess(PageResponse<List<PersonMember>> response) {
                personMemberList.addAll(response.getData());
                personCheckAdpter.notifyDataSetChanged();
                mRefreshLayout.endLoadingMore();
                page++;
                totalCount = response.getRowCount();

                if (totalCount==0)
                {
                    ToastUtil.showLong(getResources().getString(R.string.is_no_retrieved_data));
                }
            }



            @Override
            public void onFail() {
                super.onFail();
                mRefreshLayout.endRefreshing();
            }

        });


    }


    @Override
    public void INSTALLATIONUNIT(String guid, String name) {

        InstaillationUnitText.setText(name);
        unitGuid = guid;
        closeunit.setVisibility(View.VISIBLE);

    }

    @Override
    public void Projectinfo(String projectId, String projectName) {

        ProjectText.setText(projectName);
        projectGuid = projectId;
        closeproject.setVisibility(View.VISIBLE);


    }
}
