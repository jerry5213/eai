package com.zxtech.is.ui.task.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.BuildConfig;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.attach.Attach;
import com.zxtech.is.model.s1.Project;
import com.zxtech.is.model.s1.ReviewEntity;
import com.zxtech.is.model.s1.S1Response;
import com.zxtech.is.model.s3.S3Elevator;
import com.zxtech.is.model.s3.S3Response;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.service.task.S1Service;
import com.zxtech.is.service.task.S3Service;
import com.zxtech.is.ui.task.adpter.ProjectAttachReviewAdpter;
import com.zxtech.is.ui.task.adpter.S3ElevatorReviewAdpter;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.util.PermissionUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by syp600 on 2018/5/7.
 */

public class S3TaskReviewActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    //项目名称
    @BindView(R2.id.tv_is_project_name)
    TextView tv_is_project_name;

    //代理商
    @BindView(R2.id.tv_is_agent_name)
    TextView tv_is_agent_name;

    //项目地址
    @BindView(R2.id.is_project_address)
    TextView is_project_address;

    //产品台量
    @BindView(R2.id.is_product_units)
    TextView is_product_units;

    //项目附件
    @BindView(R2.id.rv_is_project_attach)
    RecyclerView rv_is_project_attach;

    @BindView(R2.id.tv_is_no_data)
    TextView tv_is_no_data;

    //产品列表
    @BindView(R2.id.rv_is_elevator)
    RecyclerView rv_is_elevator;

    private ProjectAttachReviewAdpter projectAttachReviewAdpter;
    //项目附件
    List<Attach> projectAttachList = new ArrayList<>();

    private S3ElevatorReviewAdpter s3ElevatorReviewAdpter;
    //产品列表
    List<S3Elevator> elevatorList = new ArrayList<>();

    @BindView(R2.id.is_project_info)
    LinearLayout is_project_info;
    @BindView(R2.id.tv_open_project_info)
    TextView tv_open_project_info;
    @BindView(R2.id.is_address_info)
    LinearLayout is_address_info;
    @BindView(R2.id.tv_open_address_info)
    TextView tv_open_address_info;

    @BindString(R2.string.icon_zhe_die_open)
    String icon_open;
    @BindString(R2.string.icon_zhe_die_close)
    String icon_close;

    //项目id
    String projectId;
    //流程key
    String procDefKey;
    //任务id
    String taskId;

    //全选
    @BindView(R2.id.cb_is_checkall)
    CheckBox cb_is_checkall;

    //审批意见
    @BindView(R2.id.et_is_conmment)
    EditText et_is_conmment;

    //交货地址合并
    @BindView(R2.id.tv_is_address_combine)
    TextView tv_is_address_combine;

    //安装地址合并
    @BindView(R2.id.tv_is_install_address_combine)
    TextView tv_is_install_address_combine;

    public void setCb_is_checkall(boolean flag) {
        this.cb_is_checkall.setChecked(flag);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_s3_review;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(mToolbar);

        Intent intent = getIntent();
        projectId = intent.getStringExtra("projectId");
        procDefKey = intent.getStringExtra("procDefKey");
        taskId = intent.getStringExtra("taskId");

        Map<String, String> params = new HashMap<>();
        params.put("projectGuid", projectId);
        params.put("procDefKey", procDefKey);
        params.put("taskId", taskId);


        //项目附件
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_is_project_attach.setLayoutManager(linearLayoutManager);
        rv_is_project_attach.addItemDecoration(new MyItemDecoration());
        projectAttachReviewAdpter = new ProjectAttachReviewAdpter(R.layout.item_is_img, projectAttachList);
        rv_is_project_attach.setAdapter(projectAttachReviewAdpter);

        //产品列表
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        rv_is_elevator.setLayoutManager(linearLayoutManager1);
        rv_is_elevator.addItemDecoration(new MyItemDecoration());
        s3ElevatorReviewAdpter = new S3ElevatorReviewAdpter(R.layout.item_s3_elevator_review, elevatorList);
        rv_is_elevator.setAdapter(s3ElevatorReviewAdpter);

        //加载数据
        loadData(params);

        projectAttachReviewAdpter.setOnItemClickListener(this);
        s3ElevatorReviewAdpter.setOnItemClickListener(this);
    }

    public void loadData(Map<String, String> params){
        S1Service s1Service = HttpFactory.getService(S1Service.class);
        //项目信息
        s1Service.selectProjectInfo(params.get("projectGuid")).compose(RxHelper.<BaseResponse<Project>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<Project>>(getActivity(),true) {
            @Override
            public void onSuccess(BaseResponse<Project> response) {
                //项目信息
                Project projectInfo = response.getData();
                String projectName = projectInfo.getKtext() != null ? projectInfo.getKtext():"";
                String agentName = projectInfo.getAgentName() != null ? projectInfo.getAgentName():"";
                String projectAddress = projectInfo.getShipToAddress() != null ? projectInfo.getShipToAddress():"";
                String productUnits = projectInfo.getKwmeng() != null ? projectInfo.getKwmeng():"";
                tv_is_project_name.setText(projectName);
                tv_is_agent_name.setText(agentName);
                is_project_address.setText(projectAddress);
                is_product_units.setText(productUnits);
            }
        });

        //附件信息
        s1Service.selectProjectAttach(params.get("projectGuid"), params.get("procDefKey")).compose(RxHelper.<BaseResponse<List<Attach>>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<List<Attach>>>(getActivity(),true) {
            @Override
            public void onSuccess(BaseResponse<List<Attach>> response) {
                if(response.getData().size() == 0){
                    tv_is_no_data.setVisibility(View.VISIBLE);
                    rv_is_project_attach.setVisibility(View.GONE);
                }else{
                    //项目附件
                    projectAttachList.clear();
                    projectAttachList.addAll(response.getData());
                    projectAttachReviewAdpter.notifyDataSetChanged();
                }
            }
        });

        //地址信息
        s1Service.selectProjectAddressInfo(params.get("taskId"), params.get("procDefKey")).compose(RxHelper.<BaseResponse<S1Response>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<S1Response>>(getActivity(),true) {
            @Override
            public void onSuccess(BaseResponse<S1Response> response) {
                //交货地址合并
                List<S1Response.AddressBean> addressCombineList = response.getData().getAddressCombineList();
                if(addressCombineList.size()>0){
                    String addressCombine = "";
                    for(int i=0;i<addressCombineList.size();i++){
                        S1Response.AddressBean addressBean = addressCombineList.get(i);
                        addressCombine += addressBean.getElevatorguid()+": " +addressBean.getProjectaddprovince()+" "+addressBean.getProjectaddcity()+" "+addressBean.getProjectaddarea()+" "+addressBean.getProjectaddother()+"\n";
                    }
                    tv_is_address_combine.setText(addressCombine);
                }
                //安装地址合并
                List<S1Response.AddressBean> installAddressCombineList = response.getData().getInstallAddressCombineList();
                if(installAddressCombineList.size()>0){
                    String addressCombine = "";
                    for(int i=0;i<installAddressCombineList.size();i++){
                        S1Response.AddressBean addressBean = installAddressCombineList.get(i);
                        addressCombine += addressBean.getElevatorguid()+": " +addressBean.getProjectaddprovince()+" "+addressBean.getProjectaddcity()+" "+addressBean.getProjectaddarea()+" "+addressBean.getProjectaddother()+"\n";
                    }
                    tv_is_install_address_combine.setText(addressCombine);
                }
            }
        });
        S3Service s3Service = HttpFactory.getService(S3Service.class);
        s3Service.selectISPlanS3(params).compose(RxHelper.<BaseResponse<S3Response>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<S3Response>>(getActivity(),true) {
            @Override
            public void onSuccess(BaseResponse<S3Response> response) {
                //产品信息
                elevatorList.clear();
                elevatorList.addAll(response.getData().getElevatorList());
                s3ElevatorReviewAdpter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R2.id.tv_open_project_info,
            R2.id.tv_open_address_info,
            R2.id.cb_is_checkall,
            R2.id.is_rejected,
            R2.id.is_confirm})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_open_project_info) {
            if (is_project_info.isShown()) {
                is_project_info.setVisibility(View.GONE);
                tv_open_project_info.setText(icon_open);
            } else {
                is_project_info.setVisibility(View.VISIBLE);
                tv_open_project_info.setText(icon_close);
            }

        } else if (i == R.id.tv_open_address_info) {
            if (is_address_info.isShown()) {
                is_address_info.setVisibility(View.GONE);
                tv_open_address_info.setText(icon_open);
            } else {
                is_address_info.setVisibility(View.VISIBLE);
                tv_open_address_info.setText(icon_close);
            }

        } else if (i == R.id.cb_is_checkall) {
            clickSelectAll();

        } else if (i == R.id.is_rejected) {
            submitPlanS3("0");

        } else if (i == R.id.is_confirm) {
            submitPlanS3("1");

        }
    }

    private void submitPlanS3(String checkResult) {
        int count = s3ElevatorReviewAdpter.getSelectCount();
        if (count == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_select_one_msg));
        } else {
            String comment = et_is_conmment.getText().toString();
            if (comment.length() > 50) {
                ToastUtil.showLong(getResources().getString(R.string.is_comments_max_length_msg));
                return;
            }
            List<ReviewEntity> reviewList = new ArrayList<>();
            ReviewEntity review = null;
            for (int i = 0; i < elevatorList.size(); i++) {
                S3Elevator s3Elevator = elevatorList.get(i);
                if (s3Elevator.isCheck()) {
                    review = new ReviewEntity();
                    review.setProcinstid(s3Elevator.getProcinstid());
                    review.setTaskId(s3Elevator.getTaskId());
                    review.setCheckResult(checkResult);
                    review.setComments(comment);
                    reviewList.add(review);
                }
            }
            String param = GsonUtils.toJson(reviewList, false);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
            S3Service s3Service = HttpFactory.getService(S3Service.class);
            s3Service.submitPlanS3(requestBody).compose(RxHelper.<BaseResponse<Boolean>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<Boolean>>(getActivity(), true) {
                @Override
                public void onSuccess(BaseResponse<Boolean> response) {
                    if ("1".equals(response.getFlag())) {
                        boolean flag = response.getData();
                        if (flag) {
                            ToastUtil.showLong(getResources().getString(R.string.is_audit_success));
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                }
            });
        }
    }


    //全选
    private void clickSelectAll() {
        if (cb_is_checkall.isChecked()) {
            s3ElevatorReviewAdpter.setCheckAllFlag(true);
        } else {
            s3ElevatorReviewAdpter.setCheckAllFlag(false);
        }
        for (int i = 0; i < elevatorList.size(); i++) {
            if (s3ElevatorReviewAdpter.isCheckAllFlag()) {
                elevatorList.get(i).setCheck(true);
            } else {
                elevatorList.get(i).setCheck(false);
            }
        }
        s3ElevatorReviewAdpter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof ProjectAttachReviewAdpter) {
            PermissionUtils.checkAndRequestPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE, 0);
            Intent intent = new Intent();
            switch (projectAttachList.get(position).getFiletype()) {
                case "pdf":
//                File file = new File(DOWN_LOAD_PATH, projectAttachList.get(0).getFilename());
//                if (file.exists()) {//如果已存在,直接打开
//                    OfficePoiUtil.openFile(mContext, file);
//                } else {//文件不存在,下载后打开
////                    downloadFile(fileInfoList, file);
//                }
                    break;
                case "jpg":
                case "png":
                    String attachguid = projectAttachList.get(position).getAttachguid();
                    String url = BuildConfig.BASE_URL + "s1/downloadAttach?guid=" + attachguid;
                    intent.putExtra("url", url);
                    intent.setClass(mContext, ShowBigImageSimpleActivity.class);
                    startActivity(intent);
                    break;
            }
        }
        if (adapter instanceof S3ElevatorReviewAdpter) {
            Intent intent = new Intent(mContext, S3ElevatorDetailReviewActivity.class);
            S3Elevator elevator = elevatorList.get(position);
            intent.putExtra("elevator", elevator);
            intent.putExtra("projectGuid", projectId);
            intent.putExtra("procDefKey", procDefKey);
            startActivity(intent);
        }
    }
}
