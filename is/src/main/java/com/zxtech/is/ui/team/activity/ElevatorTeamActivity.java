package com.zxtech.is.ui.team.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.DropDownWindow;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.project.ProductInformation;
import com.zxtech.is.model.team.IsSlInstallationmember;
import com.zxtech.is.model.team.Leader;
import com.zxtech.is.service.team.SlinstallationunitService;
import com.zxtech.is.ui.project.activity.ProjectManageDialog;
import com.zxtech.is.ui.project.adpter.ProjectManageAdpter;
import com.zxtech.is.ui.team.adapter.InstallationMemberAdpter;
import com.zxtech.is.ui.team.adapter.SlinstallationunitAdpter;
import com.zxtech.is.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by syp692 on 2018/4/21.
 */

public class ElevatorTeamActivity extends BaseActivity implements View.OnClickListener, SlinstallationunitDialog.Unit, SlinstallationMembersDialog.Member, ProjectManageDialog.PROJECT

{
    private InstallationMemberAdpter installationMemberAdpter;
    private List<IsSlInstallationmember> installationMemberList = new ArrayList<IsSlInstallationmember>();

    private List<Leader> leaders = new ArrayList<Leader>();
    //合作方id
    private String unitGuid;
    //  安装班组id
    private String teamGuid;
    //  选中人员ids
    private String memberGuids;
    //  电梯ids

    private String elevatorGuids;
    //  电梯names
    private String elevatorNames;
    private List<ProductInformation> productInformationList = new ArrayList<ProductInformation>();

    //  leaderId
    private String leaderId;

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    //班组成员列表
    @BindView(R2.id.is_team_5)
    RecyclerView rvTeamMembers;
    //合作方text
    @BindView(R2.id.Unit_1)
    TextView InstaillationUnitText;
    //班组下拉列表
    @BindView(R2.id.is_team_lead)
    TextView isLeader;
    //确认按钮
    @BindView(R2.id.rv_10)
    Button affirm;

    @BindView(R2.id.rv_11)

    Button back;

  @BindView(R2.id.rv_elevator_name)

  TextView  rvelevatorName;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_elevator_team;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {


        initTitleBar(mToolbar);
        productInformationList= (List<ProductInformation>) getIntent().getSerializableExtra("productIC");

        StringBuilder resultElevatorGuid=new StringBuilder();
        StringBuilder resultElevatorName=new StringBuilder();

        boolean flag=false;
        for (ProductInformation productInformation : productInformationList) {
            if (flag) {
                resultElevatorGuid.append(",");
                resultElevatorName.append(",");
            }else {
                flag=true;
            }
            resultElevatorGuid.append(productInformation.getElevatorGuid());
            resultElevatorName.append(productInformation.getArktx());
        }
        elevatorGuids=  resultElevatorGuid.toString();
        elevatorNames=  resultElevatorName.toString();
        rvelevatorName.setText(elevatorNames);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTeamMembers.setLayoutManager(linearLayoutManager);
        rvTeamMembers.addItemDecoration(new MyItemDecoration());
        installationMemberAdpter = new InstallationMemberAdpter(R.layout.item_elevator_team_member, installationMemberList);
        installationMemberAdpter.bindToRecyclerView(rvTeamMembers);
        rvTeamMembers.setAdapter(installationMemberAdpter);


    }


    @OnClick({R2.id.Unit_1, R2.id.is_team_lead, R2.id.rv_10, R2.id.rv_11, R2.id.rv_7})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.Unit_1) {
            SlinstallationunitDialog updateDialog = SlinstallationunitDialog.newInstance();

            updateDialog.setUnit(this);

            updateDialog.show(((Activity) mContext).getFragmentManager(), "");


            //下拉列表
        } else if (id == R.id.is_team_lead) {
            if ("".equals(unitGuid) || unitGuid == null) {
                ToastUtil.showLong(getResources().getString(R.string.is_choose_unit));
                return;
            }
            dropDownSolve(view);

            //确认
        } else if (id == R.id.rv_10) {
            affirm();

            //返回
        } else if (id == R.id.rv_11) {
            finish();

            //添加
        } else if (id == R.id.rv_7) {//校验

            if ("".equals(unitGuid) || unitGuid == null) {
                ToastUtil.showLong(getResources().getString(R.string.is_choose_unit));
                return;
            }
            if ("".equals(teamGuid) || teamGuid == null) {
                ToastUtil.showLong(getResources().getString(R.string.is_choose_leader));
                return;
            }
            SlinstallationMembersDialog slinstallationMembersDialog = SlinstallationMembersDialog.newInstance();
            slinstallationMembersDialog.setMember(this);
            slinstallationMembersDialog.params.put("unitGuid", unitGuid);
            slinstallationMembersDialog.params.put("teamGuid", teamGuid);
            slinstallationMembersDialog.params.put("installationMemberList", installationMemberList);
            slinstallationMembersDialog.show(((Activity) mContext).getFragmentManager(), "");

        } else {
        }


    }

    //下拉列表安装队长内容获取
    private void initSolve() {

        leaders = new ArrayList<Leader>();


        SlinstallationunitService slinstallationunitService = HttpFactory.getService(SlinstallationunitService.class);

        slinstallationunitService.selectUnitLeaders(unitGuid).compose(RxHelper.<BaseResponse<List<Leader>>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<List<Leader>>>(getActivity(), false) {


            @Override
            public void onSuccess(BaseResponse<List<Leader>> response) {
                if(response.getData().size()==0){
                    //如果没有对应的安装队长，则清空原来的安装班组，班组成员
                    isLeader.setText("");
                    isLeader.setTag("");
                    installationMemberList.clear();
                    installationMemberAdpter.notifyDataSetChanged();
                }
                else{
                    leaders.addAll(response.getData());
                }



            }

            @Override
            public void onFail() {
                super.onFail();

            }

        });


    }

    //班组成员内容获取
    private void initMembers() {
        SlinstallationunitService slinstallationunitService = HttpFactory.getService(SlinstallationunitService.class);

        slinstallationunitService.selectMembersByTeamGuid(unitGuid, teamGuid).compose(RxHelper.<BaseResponse<List<IsSlInstallationmember>>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<List<IsSlInstallationmember>>>(getActivity(), false) {


            @Override
            public void onSuccess(BaseResponse<List<IsSlInstallationmember>> response) {
                installationMemberList.clear();
                installationMemberList.addAll(response.getData());
                if (installationMemberList.size() == 0) {
                    ToastUtil.showLong(getResources().getString(R.string.is_no_retrieved_data) );
                    return;
                }

                for (int i = 0; i < installationMemberList.size(); i++) {

                    installationMemberList.get(i).setUnitName(InstaillationUnitText.getText().toString());

                }
                installationMemberAdpter.notifyDataSetChanged();


            }


            @Override
            public void onFail() {
                super.onFail();

            }

        });


    }

    protected void dropDownSolve(View view) {

        if (leaders == null || leaders.size() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_no_data) );
            return;
        }

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        isLeader.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, leaders, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                isLeader.setText(leaders.get(p).getValue());
                isLeader.setTag(leaders.get(p).getCode());
                teamGuid = leaders.get(p).getCode();
                //调用获取班组成员的方法
                initMembers();
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                isLeader.setCompoundDrawables(null, null, image, null);
            }
        };
    }


    private void affirm() {
        memberGuids = "";
        List<IsSlInstallationmember> installationMemberAffirmList = new ArrayList<IsSlInstallationmember>();
        //生成已经选中的memberslist
        if (installationMemberList.size() > 0) {
            for (int i = 0; i < installationMemberList.size(); i++) {
                if (installationMemberList.get(i).isCheck()) {
                    installationMemberAffirmList.add(installationMemberList.get(i));
                }
            }
        }
        if (installationMemberAffirmList.size() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_choose_teammember));
            return;
        }
        //拼接成members字符串传递后台
        else {
            for (int i = 0; i < installationMemberAffirmList.size(); i++) {
                memberGuids = memberGuids + installationMemberAffirmList.get(i).getGuid() + ",";
                if (installationMemberAffirmList.get(i).isLeader()) {
                    leaderId = installationMemberAffirmList.get(i).getGuid();
                }
            }
            //去除最后一个,
            memberGuids = memberGuids.substring(0, memberGuids.length() - 1);


        }


        SlinstallationunitService slinstallationunitService = HttpFactory.getService(SlinstallationunitService.class);

        slinstallationunitService.saveTeamInfo(unitGuid, teamGuid, memberGuids, elevatorGuids, leaderId)
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {

                        if (response.getFlag().equals("1")) {
                            setResult(RESULT_OK);
                            ToastUtil.showLong(getResources().getString(R.string.is_successfully_save));
                            finish();
                        }
                    }


                    @Override
                    public void onFail() {
                        super.onFail();

                    }

                });

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void INSTALLATIONUNIT(String guid, String name) {
        InstaillationUnitText.setText(name);
        unitGuid = guid;
        initSolve();

    }


    @Override
    public void MemberInfo(List<Map<String, Object>> Memberlist) {
        // installationMemberList
//        IsSlInstallationmember  isSlInstallationmember =new  IsSlInstallationmember();
        if (Memberlist.size() > 0) {
            for (int i = 0; i < Memberlist.size(); i++) {
                for (int j = 0; j < installationMemberList.size(); j++) {


                    if (installationMemberList.get(j).getGuid().equals(Memberlist.get(i).get("guid").toString())) {
                        Memberlist.remove(i);


                    }

                }

            }
        }
        //向列表中增加人员
        if (Memberlist.size() > 0) {
            for (int i = 0; i < Memberlist.size(); i++) {

                IsSlInstallationmember isSlInstallationmember = new IsSlInstallationmember();
                isSlInstallationmember.setName((String) Memberlist.get(i).get("name"));
                isSlInstallationmember.setGuid((String) Memberlist.get(i).get("guid"));
                isSlInstallationmember.setUnitName((String) Memberlist.get(i).get("unitName"));
                isSlInstallationmember.setCheck(true);
                installationMemberList.add(isSlInstallationmember);
            }

        }


        installationMemberAdpter.notifyDataSetChanged();

    }

    @Override
    public void Projectinfo(String projectId, String projectName) {

    }
}
