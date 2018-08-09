package com.zxtech.is.ui.team.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.DropDownWindow;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.team.IsSlInstallationmember;
import com.zxtech.is.model.team.Leader;
import com.zxtech.is.model.team.UsrDeptName;
import com.zxtech.is.service.team.ElaTeamAddPersonService;

import com.zxtech.is.service.team.SlinstallationunitService;
import com.zxtech.is.ui.team.adapter.InstallationTemplateMemberAdpter;
import com.zxtech.is.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by syp692 on 2018/4/21.
 */

public class ElevatorTeamTemplateActivity extends BaseActivity implements View.OnClickListener, SlinstallationunitDialog.Unit,SlinstallationMembersDialog.Member,BaseQuickAdapter.OnItemLongClickListener  {
    //分支结构下拉列表数据
    private List<UsrDeptName> usrDeptNames = new ArrayList<>();

    private InstallationTemplateMemberAdpter installationMemberAdpter;
    private List<IsSlInstallationmember> installationMemberList = new ArrayList<IsSlInstallationmember>();

    private List<Leader> leaders = new ArrayList<Leader>();
    //合作方id
    private String unitGuid;

    //分支机构id
    private String deptGuid;


    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    //班组成员列表
    @BindView(R2.id.is_team_5)
    RecyclerView rvTeamMembers;
    //合作方text
    @BindView(R2.id.Unit_1)
    TextView InstaillationUnitText;
    //班组下拉列表
    @BindView(R2.id.is_team_dept)
    TextView textdept;
    //确认按钮
    @BindView(R2.id.rv_10)
    Button affirm;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_elevator_team_template;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTeamMembers.setLayoutManager(linearLayoutManager);
        rvTeamMembers.addItemDecoration(new MyItemDecoration());
        installationMemberAdpter = new InstallationTemplateMemberAdpter(R.layout.item_elevator_team_template_member, installationMemberList);
        installationMemberAdpter.bindToRecyclerView(rvTeamMembers);

        installationMemberAdpter.setOnItemLongClickListener(this);
        rvTeamMembers.setAdapter(installationMemberAdpter);

        //初始化分支机构下拉列表
        requestGetUsrDeptName();

    }


    @OnClick({R2.id.Unit_1, R2.id.rv_10, R2.id.rv_11, R2.id.rv_7, R2.id.is_team_dept})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.Unit_1) {
            SlinstallationunitDialog updateDialog = SlinstallationunitDialog.newInstance();

            updateDialog.setUnit(this);
            updateDialog.setDeptGuid(deptGuid);
            updateDialog.show(((Activity) mContext).getFragmentManager(), "");


            //下拉列表分支机构
        } else if (id == R.id.is_team_dept) {
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
            if ("".equals(deptGuid) || deptGuid == null) {
                ToastUtil.showLong(getResources().getString(R.string.is_choose_leader));
                return;
            }
            SlinstallationMembersDialog slinstallationMembersDialog = SlinstallationMembersDialog.newInstance();
            slinstallationMembersDialog.setMember(this);
            slinstallationMembersDialog.params.put("unitGuid", unitGuid);
            slinstallationMembersDialog.params.put("teamGuid", deptGuid);
            slinstallationMembersDialog.params.put("installationMemberList", installationMemberList);
            slinstallationMembersDialog.show(((Activity) mContext).getFragmentManager(), "");

        } else {
        }


    }

    //当前登录人所述的分支机构
    private void requestGetUsrDeptName() {
        ElaTeamAddPersonService elaTeamAddPersonService = HttpFactory.getService(ElaTeamAddPersonService.class);
        elaTeamAddPersonService.getUsrDeptName()
                .compose(RxHelper.<BaseResponse<List<UsrDeptName>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<UsrDeptName>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<UsrDeptName>> response) {
                        usrDeptNames.clear();
                        usrDeptNames.addAll(response.getData());
                    }
                });
    }

    //下拉列表选中事件
    protected void dropDownSolve(View view) {

        if (usrDeptNames == null || usrDeptNames.size() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_no_data));
            return;
        }

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        textdept.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, usrDeptNames, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                textdept.setText(usrDeptNames.get(p).getDeptName());
                textdept.setTag(usrDeptNames.get(p).getDeptId());
                deptGuid = usrDeptNames.get(p).getDeptId();
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                textdept.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    @Override
    public void INSTALLATIONUNIT(String guid, String name) {
        InstaillationUnitText.setText(name);
        unitGuid = guid;


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
                isSlInstallationmember.setTelephone((String) Memberlist.get(i).get("telephone"));
                isSlInstallationmember.setCheck(true);
                installationMemberList.add(isSlInstallationmember);
            }

        }
        installationMemberAdpter.notifyDataSetChanged();
    }





    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

        for( int i =0; i< installationMemberList.size();i++)
       {
           installationMemberList.get(i).setLeader(false);
        }
        installationMemberList.get(position).setLeader(true);

        installationMemberAdpter.notifyDataSetChanged();



        return false;
    }


    private void affirm() {

        List<IsSlInstallationmember> installationMemberAffirmList = new ArrayList<IsSlInstallationmember>();
        //生成已经选中的memberslist
        if (installationMemberList.size() > 0) {
            for (int i = 0; i < installationMemberList.size(); i++) {
                if (installationMemberList.get(i).isCheck()) {
                    installationMemberAffirmList.add(installationMemberList.get(i));
                }
            }
        }
        Boolean Flagleader =false;
        if (installationMemberAffirmList.size() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_choose_teammember));
            return;
        }
        else{

            for(int i=0;i<installationMemberAffirmList.size();i++)
            {
               if(installationMemberAffirmList.get(i).isLeader())
               {
                   Flagleader=true;
               }

            }
            if(!Flagleader)
            {
                ToastUtil.showLong("请长按安装工选为安装队长");
            }
        }

        String param = GsonUtils.toJson(installationMemberAffirmList, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);

        SlinstallationunitService slinstallationunitService = HttpFactory.getService(SlinstallationunitService.class);

        slinstallationunitService.saveTeamTemplate(unitGuid,deptGuid,requestBody)
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
}
