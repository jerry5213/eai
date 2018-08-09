package com.zxtech.is.ui.team.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.zxtech.is.model.team.IsSlTeam;
import com.zxtech.is.model.team.Leader;
import com.zxtech.is.service.team.IsSlTeamMemberService;
import com.zxtech.is.service.team.SlinstallationunitService;
import com.zxtech.is.ui.project.activity.ProjectManageDialog;
import com.zxtech.is.ui.team.adapter.InstallationMemberAdpter;
import com.zxtech.is.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by syp692 on 2018/4/21.
 */

public class ElevatorTeamChangeMembersActivity extends BaseActivity implements View.OnClickListener, SlinstallationMembersDialog.Member

{
    private InstallationMemberAdpter installationMemberAdpter;
    private List<IsSlInstallationmember> installationMemberList = new ArrayList<IsSlInstallationmember>();

    private List<Leader> leaders = new ArrayList<Leader>();
    //合作方id
    private String unitGuid;

    public String getTeamGuid() {
        return teamGuid;
    }

    //  安装班组id
    private String teamGuid;
    //  选中人员ids
    private String memberGuids;

    private ProductInformation productInformation = new ProductInformation();

    //  电梯ids
    private String elevatorGuids;

    //  电梯name
    private String elevatorNames;

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

    TextView rvelevatorname;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_elevator_team_change_member;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {


        initTitleBar(mToolbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTeamMembers.setLayoutManager(linearLayoutManager);
        rvTeamMembers.addItemDecoration(new MyItemDecoration());
        installationMemberAdpter = new InstallationMemberAdpter(R.layout.item_elevator_team_member, installationMemberList);
        installationMemberAdpter.bindToRecyclerView(rvTeamMembers);
        rvTeamMembers.setAdapter(installationMemberAdpter);
        initTeamInfo();


    }

    //初始化界面信息
    public void initTeamInfo() {
        productInformation = (ProductInformation) getIntent().getSerializableExtra("productInformation");
        elevatorGuids = productInformation.getElevatorGuid();
        rvelevatorname.setText(productInformation.getArktx());

        IsSlTeamMemberService isSlTeamMemberService = HttpFactory.getService(IsSlTeamMemberService.class);

        isSlTeamMemberService.selectIsSlTeamInfo(productInformation.getElevatorGuid()).compose(RxHelper.<BaseResponse<IsSlTeam>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<IsSlTeam>>(getActivity(), false) {


            @Override
            public void onSuccess(BaseResponse<IsSlTeam> response) {
                InstaillationUnitText.setText(response.getData().getUnitName());

                isLeader.setText(response.getData().getTeamName());
                //合作方id
                unitGuid = response.getData().getUnitguid();
                //安装班组id
                teamGuid = response.getData().getTeamGuid();
                installationMemberList.addAll(response.getData().getIsSlTeamMemberList());
                installationMemberAdpter.notifyDataSetChanged();
            }

            @Override
            public void onFail() {
                super.onFail();

            }

        });
    }


    @OnClick({R2.id.rv_10, R2.id.rv_11, R2.id.rv_7, R2.id.rv_3})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rv_10) {
            affirm();

            //返回
        } else if (id == R.id.rv_11) {
            finish();


            //删除班组
        } else if (id == R.id.rv_3) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.is_reminder));
            builder.setMessage(getResources().getString(R.string.is_sure_delete_teaminfo) + "?");
            builder.setNegativeButton(getResources().getString(R.string.cancel), null);
            builder.setPositiveButton(getResources().getString(R.string.is_confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    IsSlTeamMemberService isSlTeamMemberService = HttpFactory.getService(IsSlTeamMemberService.class);
                    isSlTeamMemberService.deleteTeamInfoInfo(unitGuid, teamGuid, memberGuids, elevatorGuids, leaderId)
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
            });
            builder.show();

            //添加
        } else if (id == R.id.rv_7) {//                //校验
            if ("".equals(teamGuid) || teamGuid == null) {
                ToastUtil.showLong(getResources().getString(R.string.is_choose_leader));
                return;
            }
            if ("".equals(unitGuid) || unitGuid == null) {
                ToastUtil.showLong(getResources().getString(R.string.is_choose_unit));
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


        IsSlTeamMemberService isSlTeamMemberService = HttpFactory.getService(IsSlTeamMemberService.class);

        isSlTeamMemberService.saveTeamMemberInfo(unitGuid, teamGuid, memberGuids, elevatorGuids, leaderId)
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<String> response) {

                        if (response.getFlag().equals("1")) {

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
    public void MemberInfo(List<Map<String, Object>> Memberlist) {

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


}
