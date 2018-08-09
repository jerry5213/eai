package com.zxtech.is.ui.smt.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.project.ProductInformation;
import com.zxtech.is.model.smt.IsSmtInstallationElevator;
import com.zxtech.is.service.project.ProjectItemAssignedService;
import com.zxtech.is.service.smt.ProjectInstaWayRecordsConfirmService;
import com.zxtech.is.service.taskme.TaskMeService;
import com.zxtech.is.ui.smt.adpter.ProjectInstaWayRecordsApprovalAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by syp661 on 2018/4/23.
 * <p>
 * 安装方式报备 任务口
 */

public class ProjectInstaWayRecordsApprovalActivity extends BaseActivity {

    private List<ProductInformation> productInformationList = new ArrayList<ProductInformation>();
    private ProjectInstaWayRecordsApprovalAdapter mAdapter;
    private String projectId; //项目ID
    private String checked; //1:任务口 2：项目口

    @BindView(R2.id.civil_review_rv)
    RecyclerView mRecyclerView;

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

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
        return R.layout.install_way_approval;
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
        mAdapter = new ProjectInstaWayRecordsApprovalAdapter(R.layout.install_way_records_rv, productInformationList);
        mAdapter.setChecked(checked);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestNet();

    }

    @OnClick({R2.id.is_check_all, R2.id.is_pass, R2.id.is_rejected})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.is_check_all) {
            checkSelectAll();

        } else if (i == R.id.is_pass) {
            instaSelect(1);

        } else if (i == R.id.is_rejected) {
            instaSelect(2);

        }
    }

    private void requestNet() {
        TaskMeService taskMeService = HttpFactory.getService(TaskMeService.class);
        taskMeService.getTaskMeItemAssignedList(projectId, checked, "2")
                .compose(RxHelper.<BaseResponse<List<ProductInformation>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ProductInformation>>>(getActivity(), true) {

                    @Override
                    public void onSuccess(BaseResponse<List<ProductInformation>> response) {

                        productInformationList.clear();
                        if (response.getData() != null) {
                            productInformationList.addAll(response.getData());
                            mAdapter.notifyDataSetChanged();
                            mAdapter.selectAll(false);
                        } else {
                            ToastUtil.showLong(mContext.getResources()
                                    .getString(R.string.is_no_retrieved_data));
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
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

    //安装方式报备 已选择
    private void instaSelect(int flag) {
        List<IsSmtInstallationElevator> isieList = new ArrayList<>();
        isieList.clear();
        Map<Integer, Boolean> checkMap1 = mAdapter.getCheckMap();
        //循环选择是true的信息
        for (int s : checkMap1.keySet()) {
            if (checkMap1.get(s)) {
                IsSmtInstallationElevator isie = new IsSmtInstallationElevator();
                isie.setIsvalid(flag);
                productInformationList.get(s);
                isie.setProcinstid(productInformationList.get(s).getSmtprocInstId());
                isie.setTaskId(productInformationList.get(s).getTaskId());
                isieList.add(isie);
            }
        }
        if (isieList.size() > 0) {
            installWayApproval(isieList);
        } else {
            showAlertDialog(R.string.is_install_msg2, false);
        }
    }

    //安装方式报备审批
    private void installWayApproval(List<IsSmtInstallationElevator> isie) {

        String param = GsonUtils.toJson(isie, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);

        ProjectInstaWayRecordsConfirmService piwcService = HttpFactory.getService(ProjectInstaWayRecordsConfirmService.class);
        piwcService.installWayApproval(requestBody)
                .compose(RxHelper.<BaseResponse<Integer>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Integer>>(getActivity(), true) {

                    @Override
                    public void onSuccess(BaseResponse<Integer> response) {

                        if (response.getData() > 0) {
                            showAlertDialog(R.string.is_install_msg8, true);
                        } else {
                            showAlertDialog(R.string.is_install_msg3, false);
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        showAlertDialog(R.string.is_install_msg3, false);
                    }
                });
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

    private void showAlertDialog(int msg, final boolean flag1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.is_reminder);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.is_sure, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (flag1) {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        builder.show();
    }
}