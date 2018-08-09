package com.zxtech.is.ui.project.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.login.Login;
import com.zxtech.is.model.project.ProductInformation;
import com.zxtech.is.service.login.LoginService;
import com.zxtech.is.service.project.ProjectItemAssignedService;
import com.zxtech.is.service.taskme.TaskMeService;
import com.zxtech.is.ui.project.adpter.ProjectItemAssignedAdapter;
import com.zxtech.is.ui.smt.activity.ProjectInstaWayRecordsConfirmActivity;
import com.zxtech.is.ui.team.activity.ElevatorTeamActivity;
import com.zxtech.is.ui.team.activity.ElevatorTeamChangeMembersActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by syp661 on 2018/4/23.
 * <p>
 * 项目团队人员分配
 */

public class ProjectItemAssignedActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener {
    // 权限控制测试 begin
//    private Map<String, Object> securityMap = new HashMap<>();
    // 权限控制测试 end

    private static final int REQUEST = 0;
    private List<ProductInformation> productInformationList = new ArrayList<ProductInformation>();
    private List<ProductInformation> productIC = new ArrayList<ProductInformation>();
    private ProjectItemAssignedAdapter mAdapter;
    private String projectId; //项目ID
    private String checked; //1:任务口 2：项目口

    @BindView(R2.id.civil_review_rv)
    RecyclerView mRecyclerView;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    //班组分配 按钮
    @BindView(R2.id.is_citem_assigned)
    LinearLayout is_citem_assigned;
    //安装方式报备 按钮
    @BindView(R2.id.is_insta_way_records)
    LinearLayout is_insta_way_records;
    //项目名
    @BindView(R2.id.is_project_name)
    TextView is_project_name;
    //项目地址
    @BindView(R2.id.is_project_address)
    TextView is_project_address;
    //代理商
    @BindView(R2.id.is_agency)
    TextView is_agency;
    //产品台量
    @BindView(R2.id.is_product_units)
    TextView is_product_units;
    //全选框
    @BindView(R2.id.is_check_all)
    CheckBox is_check_all;

    public void set_is_checkall(boolean flag) {
        this.is_check_all.setChecked(flag);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.task_item_assigned;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);
        projectId = String.valueOf(getIntent().getSerializableExtra("projectId"));
        checked = String.valueOf(getIntent().getSerializableExtra("checked"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new com.zxtech.is.widget.MyItemDecoration());
        mAdapter = new ProjectItemAssignedAdapter(R.layout.item_task_elevator_info, productInformationList);
        mAdapter.setChecked(checked);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
        //判断 是项目口进还是任务口进
        if ("1".equals(checked)) { //任务口
            is_citem_assigned.setVisibility(View.GONE);
            is_insta_way_records.setVisibility(View.GONE);
            ((TextView) findViewById(R.id.toolbar_title)).setText(R.string.title_activity_task);

        } else {
            ((TextView) findViewById(R.id.toolbar_title)).setText(R.string.is_activity_project_manage);
        }

        // 权限控制测试 begin
//        Login login = new Login();
//        List<String> funcControlList = new ArrayList<>();
//        funcControlList.add("colFLCMIIRate");
//        funcControlList.add("btnContractChangePreview");
//        funcControlList.add("EcsMainPage");
//        login.setFuncControlList(funcControlList);
//        String param = GsonUtils.toJson(login, false);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
//        LoginService loginService1 = HttpFactory.getService(LoginService.class);
//        loginService1.judgeFuncControlType(requestBody)
//                .compose(RxHelper.<BaseResponse<Map<String, Object>>>rxSchedulerHelper())
//                .subscribe(new DefaultObserver<BaseResponse<Map<String, Object>>>(getActivity(),true) {
//
//                    @Override
//                    public void onSuccess(BaseResponse<Map<String, Object>> response) {
//
//                        Map<String, Object> map = response.getData();
//                        for(Object key : map.keySet()){
//                            System.out.println(key + "=" + map.get(key));
//                        }
//
//                        if(response.getData() != null){
//                            securityMap =  response.getData();
//                        }
//                        is_insta_way_records.setVisibility(View.GONE);
//                        is_citem_assigned.setClickable(false);
//                        mAdapter.setSecurityMapFun(securityMap);
//
//                        requestNet();
//                    }
//                });
        // 权限控制测试 end

        requestNet();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            is_check_all.setChecked(false);
            requestNet();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick({R2.id.is_check_all, R2.id.item_assigned, R2.id.is_citem_assigned,
            R2.id.is_insta_way_records})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.is_check_all) {
            checkSelectAll();

        } else if (i == R.id.item_assigned) {
            itemAssignedCheck();

        } else if (i == R.id.is_citem_assigned) {// 权限控制测试 begin
//                if(securityMap.get("colFLCMIIRate").equals("1")){
//                    ToastUtil.showLong("无权限，请联系管理员");
//                    break;
//                }
            // 权限控制测试 end
            citemAssigned();

        } else if (i == R.id.is_insta_way_records) {
            instaSelect();

        }
    }

    private void requestNet() {
        TaskMeService taskMeService = HttpFactory.getService(TaskMeService.class);
        taskMeService.getTaskMeItemAssignedList(projectId, checked, "1")//1：表示获取项目团队分配信息
                .compose(RxHelper.<BaseResponse<List<ProductInformation>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ProductInformation>>>(getActivity(), true) {

                    @Override
                    public void onSuccess(BaseResponse<List<ProductInformation>> response) {
                        productInformationList.clear();
                        if (response.getData() != null) {
                            productInformationList.addAll(response.getData());
                            int num = response.getData().size();
                            if (num == 0) {
                                ToastUtil.showLong(mContext.getResources().getString(R.string.is_temporary_task_no_data));
                            }
                        } else {//没有数据 返回到上一个页面
                            setResult(RESULT_OK);
                            finish();
                        }
                        mAdapter.notifyDataSetChanged();
                        mAdapter.selectAll(false);
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        ToastUtil.showLong(mContext.getResources().getString(R.string.is_temporary_task_no_data));
                    }
                });

        ProjectItemAssignedService projectItemAssignedService = HttpFactory.getService(ProjectItemAssignedService.class);
        projectItemAssignedService.getProjecctInformaiton(projectId)
                .compose(RxHelper.<BaseResponse<Map<String, Object>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Map<String, Object>>>(getActivity(), false) {

                    @Override
                    public void onSuccess(BaseResponse<Map<String, Object>> response) {
                        is_project_name.setText(String.valueOf(response.getData().get("ktext")));
                        is_project_address.setText(String.valueOf(response.getData().get("shipToAddress")));
                        is_agency.setText(String.valueOf(response.getData().get("agentName")));
                        String kwmeng = String.valueOf(response.getData().get("kwmeng"));
                        is_product_units.setText(kwmeng.substring(0, kwmeng.indexOf(".")));

                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    //团队确认，验证
    private void itemAssignedCheck() {
        ProductInformation pi = new ProductInformation();
        String taskCheck = "";
        //判断有没有分人
        Boolean checkforeach = false;
        Boolean checkSize = true;
        Boolean checkNext = false;
        productIC.clear();
        Map<Integer, Boolean> checkMap1 = mAdapter.getCheckMap();

        //判断有无数据
        if (productInformationList.size() > 0) {
            //循环选择是true的信息
            for (int s : checkMap1.keySet()) {
                if (checkMap1.get(s)) {
                    pi = productInformationList.get(s);
                    //判断选中的产品信息是否以开启流程、及流程是否属于同类
                    if (taskCheck == "") {
                        taskCheck = pi.getTaskCheck();
                        if ("3".equals(taskCheck)) {
                            showAlertDialog(R.string.is_team_assigned_msg1, false);
                            checkNext = false;
                            checkSize = false;
                            break;
                        }
                    } else if ("3".equals(pi.getTaskCheck())) {
                        showAlertDialog(R.string.is_team_assigned_msg1, false);
                        checkNext = false;
                        checkSize = false;
                        break;
                    } else if (!taskCheck.equals(pi.getTaskCheck())) {
                        showAlertDialog(R.string.is_team_assigned_msg2, false);
                        checkNext = false;
                        checkSize = false;
                        break;
                    }
                    if (!checkforeach) {
                        if (pi.getPeAssignee() != null && pi.getPeAssignee() != "") {
                            checkforeach = true;
                        }
                    }
                    productIC.add(productInformationList.get(s));
                    //以分配任务警告
                    if (checkforeach) {
                        checkNext = true;
                    }
                }
            }
            if (checkSize) {
                if (productIC.size() > 0) {
                    if (checkNext) {
                        showAlertDialog(R.string.is_team_assigned_msg3, true);
                    } else {
                        Intent intent = new Intent(mContext, ProjectItemAssignedConfirmActivity.class);
                        intent.putExtra("productIC", new ArrayList<>(productIC));
                        intent.putExtra("projectId", projectId);
                        startActivityForResult(intent, REQUEST);
                    }
                } else {
                    showAlertDialog(R.string.is_select_one_msg, false);
                }
            }
        } else {
            showAlertDialog(R.string.is_team_assigned_msg8, false);
        }
    }

    //班组分配 已选择
    private void citemAssigned() {
        productIC.clear();
        Map<Integer, Boolean> checkMap1 = mAdapter.getCheckMap();
        //循环选择是true的信息
        for (int s : checkMap1.keySet()) {
            if (checkMap1.get(s)) {
                productIC.add(productInformationList.get(s));
            }
        }
        if (productIC.size() > 0) {
            Intent intent = new Intent(mContext, ElevatorTeamActivity.class);//班组分配页面
            intent.putExtra("productIC", (Serializable) productIC);
            startActivityForResult(intent, REQUEST);
        } else {
            //班组分配 选择为空
            showAlertDialog(R.string.is_install_msg5, false);
        }
    }

    //安装方式报备 已选择
    private void instaSelect() {
        boolean flag1 = true;
        productIC.clear();
        Map<Integer, Boolean> checkMap1 = mAdapter.getCheckMap();
        //循环选择是true的信息
        for (int s : checkMap1.keySet()) {
            if (checkMap1.get(s)) {
                if (productInformationList.get(s).getSmtType() == 1
                        || productInformationList.get(s).getSmtType() == 2) {
                    flag1 = false;
                }
                productIC.add(productInformationList.get(s));
            }
        }

        if (productIC.size() > 0) {
            //审批中和已通过状态不允许再报备
            if (!flag1) {
                showAlertDialog(R.string.is_install_msg6, false);
            } else {
                Intent intent = new Intent(mContext, ProjectInstaWayRecordsConfirmActivity.class);
                intent.putExtra("productIC", (Serializable) productIC);
                startActivityForResult(intent, REQUEST);
            }
        } else {
            showAlertDialog(R.string.is_install_msg5, false);
        }

    }

    //全选
    private void checkSelectAll() {
        if (!is_check_all.isChecked()) {
            mAdapter.selectAll(false);
        } else {
            mAdapter.selectAll(true);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void showAlertDialog(int msg, final boolean flag2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.is_reminder);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.is_sure, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (flag2) {
                    Intent intent = new Intent(mContext, ProjectItemAssignedConfirmActivity.class);
                    intent.putExtra("productIC", (Serializable) productIC);
                    intent.putExtra("projectId", projectId);
                    startActivityForResult(intent, REQUEST);
                }
            }
        });
        builder.show();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i1 = view.getId();
        if (i1 == R.id.lable1) {
            ProductInformation pif2 = productInformationList.get(i);
            if ("2".equals(pif2.getTaskCheck())) {
                Intent intent2 = new Intent(mContext, ProjectItemAssignedConfirmActivity.class);
                intent2.putExtra("productInformation", pif2);
                startActivityForResult(intent2, REQUEST);
            } else {//不允许查看团队分配
                showAlertDialog(R.string.is_team_assigned_msg9, false);
            }

        } else if (i1 == R.id.lable2) {
            ProductInformation pif1 = productInformationList.get(i);
            if (pif1.isSlTeam()) {
                Intent intent1 = new Intent(mContext, ElevatorTeamChangeMembersActivity.class);
                intent1.putExtra("productInformation", pif1);
                startActivityForResult(intent1, REQUEST);
            } else {//不允查看班组分配
                showAlertDialog(R.string.is_install_msg7, false);
            }

        } else if (i1 == R.id.lable3) {// 权限控制测试 begin
//                if(securityMap.get("colFLCMIIRate").equals("1")){
//                    ToastUtil.showLong("无权限，请联系管理员");
//                    break;
//                }
            // 权限控制测试 end
            ProductInformation pif = productInformationList.get(i);
            if (pif.getSmtType() == 1 || pif.getSmtType() == 2 || pif.getSmtType() == 4) {
                //查询安装方式报备详情
                Intent intent = new Intent(mContext, ProjectInstaWayRecordsConfirmActivity.class);
                intent.putExtra("productInformation", pif);
                startActivityForResult(intent, REQUEST);
            } else {//不允许查看详情
                showAlertDialog(R.string.is_install_msg4, false);
            }

        } else {
        }
    }
}
