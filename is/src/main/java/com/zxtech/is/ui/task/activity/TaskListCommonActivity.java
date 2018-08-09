package com.zxtech.is.ui.task.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.util.ImageUtil;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.MyItemDecoration;
import com.zxtech.is.widget.SelectDialog;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.activity.BasePhotoActivity;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.attach.Attach;
import com.zxtech.is.model.s1.ElevatorAddress;
import com.zxtech.is.model.s1.ElevatorBase;
import com.zxtech.is.model.s1.ElevatorContact;
import com.zxtech.is.model.s1.Project;
import com.zxtech.is.model.s1.S1Request;
import com.zxtech.is.model.s1.S1Response;
import com.zxtech.is.service.task.S1Service;
import com.zxtech.is.ui.task.adpter.ProjectAttachAdpter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by syp600 on 2018/5/15.
 */

public abstract class TaskListCommonActivity extends BasePhotoActivity implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, AddressDialog.BackResult, ContactsDialog.BackResult {

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

    //产品列表
    @BindView(R2.id.rv_is_elevator)
    RecyclerView rv_is_elevator;

    private ProjectAttachAdpter projectAttachAdpter;
    //项目附件
    List<Attach> projectAttachList = new ArrayList<>();

    //产品列表
    List<? extends ElevatorBase> elevatorList = new ArrayList<>();

    @BindView(R2.id.is_project_info)
    LinearLayout is_project_info;
    @BindView(R2.id.tv_open_project_info)
    TextView tv_open_project_info;

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

    public void setCb_is_checkall(boolean flag) {
        this.cb_is_checkall.setChecked(flag);
    }

    Map<String, String> params = null;

    //批量编辑安装地址
    @BindView(R2.id.tv_edit_install_address)
    TextView tv_edit_install_address;
    //批量编辑交货地址
    @BindView(R2.id.tv_edit_delivery_address)
    TextView tv_edit_delivery_address;
    //批量编辑联系人
    @BindView(R2.id.tv_edit_contact)
    TextView tv_edit_contact;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);

        Intent intent = getIntent();
        projectId = intent.getStringExtra("projectId");
        procDefKey = intent.getStringExtra("procDefKey");
        taskId = intent.getStringExtra("taskId");

        params = new HashMap<>();
        params.put("projectGuid", projectId);
        params.put("procDefKey", procDefKey);
        params.put("taskId", taskId);

        //项目附件
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_is_project_attach.setLayoutManager(linearLayoutManager);
        rv_is_project_attach.addItemDecoration(new MyItemDecoration());
        projectAttachAdpter = new ProjectAttachAdpter(R.layout.item_img_common, projectAttachList);
        rv_is_project_attach.setAdapter(projectAttachAdpter);

        //初始化产品
        initViewElevotorList();
        //加载数据
        loadData(params);

        projectAttachAdpter.setOnItemClickListener(this);
        projectAttachAdpter.setOnItemChildClickListener(this);
    }

    public void loadData(Map<String, String> params){
        S1Service s1Service = HttpFactory.getService(S1Service.class);
        //项目信息
        s1Service.selectProjectInfo(params.get("projectGuid")).compose(RxHelper.<BaseResponse<Project>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<Project>>(getActivity(),true) {
            @Override
            public void onSuccess(BaseResponse<Project> response) {
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
                //项目附件
                projectAttachList.clear();
                projectAttachList.addAll(response.getData());
                projectAttachAdpter.notifyDataSetChanged();
            }
        });
    }

    public void addAttach() {
        List<String> names = new ArrayList<>();
        names.add(getResources().getString(R.string.is_take_photo));
        names.add(getResources().getString(R.string.is_take_image));
        showSelectDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    RxPermissions rxPermissions = new RxPermissions(getActivity());
                    rxPermissions.requestEach(Manifest.permission.CAMERA)
                            .subscribe(new Consumer<Permission>() {
                                @Override
                                public void accept(Permission permission) throws Exception {
                                    if (permission.granted) {
                                        // 用户已经同意该权限
                                        takePhoto();
                                    } else if (permission.shouldShowRequestPermissionRationale) {
                                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                        ToastUtil.showLong(getResources().getString(R.string.is_user_denial_authority));
                                    } else {
                                        // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                                        ToastUtil.showLong(getResources().getString(R.string.is_denial_authority_operation));
                                    }
                                }
                            });

                } else if (position == 1) {
                    takeImage();

                }
            }
        }, names);
    }

    //批量编辑交货地址
    public void openAdressDialog() {
        String elevatorGuidStr = "";
        String elevatorNoStr = "";
        String procInstIdStr = "";
        String elevatorno = "";
        String msg = "";
        String projectAddProvince = "";
        String projectAddCity = "";
        String projectAddArea = "";
        String projectAddOther = "";
        String content = "";
        String elevatorAddressMsg = "";
        //是否全部地址都为空
        boolean allEmptyFlag = true;
        for (int i = 0; i < elevatorList.size(); i++) {
            elevatorAddressMsg = getResources().getString(R.string.is_elevator_address_msg);
            if (elevatorList.get(i).isCheck()) {
                elevatorGuidStr += elevatorList.get(i).getElevatorguid() + ",";
                elevatorNoStr += elevatorList.get(i).getARKTX() + ",";
                procInstIdStr += elevatorList.get(i).getProcinstid() + ",";

                elevatorno = elevatorList.get(i).getARKTX();
                projectAddProvince = elevatorList.get(i).getProjectAddProvinceName();
                projectAddCity = elevatorList.get(i).getProjectAddCityName();
                projectAddArea = elevatorList.get(i).getProjectAddAreaName();
                projectAddOther = elevatorList.get(i).getProjectAddOther();
                if (projectAddProvince != null) {
                    allEmptyFlag = false;
                    content = projectAddProvince + " " + projectAddCity + " " + projectAddArea + " " + projectAddOther + "\n";
                } else {
                    content = getResources().getString(R.string.is_null) + " \n";
                }
                elevatorAddressMsg = elevatorAddressMsg.replace("no", elevatorno).replace("content", content);
                msg += elevatorAddressMsg;
            }
        }
        elevatorGuidStr = elevatorGuidStr.substring(0, elevatorGuidStr.length() - 1);
        elevatorNoStr = elevatorNoStr.substring(0, elevatorNoStr.length() - 1);
        procInstIdStr = procInstIdStr.substring(0, procInstIdStr.length() - 1);

        //参数
        Map<String, String> params = new HashMap<>();
        params.put("elevatorGuid", elevatorGuidStr);
        params.put("procDefKey", procDefKey);
        params.put("elevatorNo", elevatorNoStr);
        params.put("procInstId", procInstIdStr);
        params.put("checkedId", "1");
        params.put("dialogType", "1");//地址
        if (!allEmptyFlag) {
            msg += getResources().getString(R.string.is_sure_override_msg);
            //地址弹出框初始化
            showComfimDialog(msg, params);
        } else {
            //地址弹出框初始化
            showEditAddressDialog(params);
        }
    }

    //批量编辑安装地址
    public void openInstallAdressDialog() {
        String elevatorGuidStr = "";
        String elevatorNoStr = "";
        String procInstIdStr = "";
        String elevatorno = "";
        String msg = "";
        String projectAddProvince = "";
        String projectAddCity = "";
        String projectAddArea = "";
        String projectAddOther = "";
        String content = "";
        String elevatorAddressMsg = "";
        //是否全部地址都为空
        boolean allEmptyFlag = true;
        for (int i = 0; i < elevatorList.size(); i++) {
            elevatorAddressMsg = getResources().getString(R.string.is_elevator_install_address_msg);
            if (elevatorList.get(i).isCheck()) {
                elevatorGuidStr += elevatorList.get(i).getElevatorguid() + ",";
                elevatorNoStr += elevatorList.get(i).getARKTX() + ",";
                procInstIdStr += elevatorList.get(i).getProcinstid() + ",";

                elevatorno = elevatorList.get(i).getARKTX();
                projectAddProvince = elevatorList.get(i).getInstlProjectAddProvinceName();
                projectAddCity = elevatorList.get(i).getInstlProjectAddCityName();
                projectAddArea = elevatorList.get(i).getInstlProjectAddAreaName();
                projectAddOther = elevatorList.get(i).getInstlProjectAddOther();
                if (projectAddProvince != null) {
                    allEmptyFlag = false;
                    content = projectAddProvince + " " + projectAddCity + " " + projectAddArea + " " + projectAddOther + "\n";
                } else {
                    content = getResources().getString(R.string.is_null) + " \n";
                }
                elevatorAddressMsg = elevatorAddressMsg.replace("no", elevatorno).replace("content", content);
                msg += elevatorAddressMsg;
            }
        }
        elevatorGuidStr = elevatorGuidStr.substring(0, elevatorGuidStr.length() - 1);
        elevatorNoStr = elevatorNoStr.substring(0, elevatorNoStr.length() - 1);
        procInstIdStr = procInstIdStr.substring(0, procInstIdStr.length() - 1);

        //参数
        Map<String, String> params = new HashMap<>();
        params.put("elevatorGuid", elevatorGuidStr);
        params.put("procDefKey", procDefKey);
        params.put("elevatorNo", elevatorNoStr);
        params.put("procInstId", procInstIdStr);
        params.put("checkedId", "2");
        params.put("dialogType", "1");//地址

        if (!allEmptyFlag) {
            msg += getResources().getString(R.string.is_sure_override_msg);
            //地址弹出框初始化
            showComfimDialog(msg, params);
        } else {
            //地址弹出框初始化
            showEditAddressDialog(params);
        }
    }

    //批量编辑联系人
    public void openContactsDialog() {
        String elevatorGuidStr = "";
        String elevatorNoStr = "";
        String procInstIdStr = "";
        String elevatorno = "";
        String msg = "";
        String name = "";
        String post = "";
        String telephone = "";
        String content = "";
        String elevatorContactMsg = "";
        //是否全部联系人都为空
        boolean allEmptyFlag = true;
        String contentName = getResources().getString(R.string.is_name);
        String contentPost = getResources().getString(R.string.is_post);
        String contentTelephone = getResources().getString(R.string.is_telephone);
        for (int i = 0; i < elevatorList.size(); i++) {
            elevatorContactMsg = getResources().getString(R.string.is_elevator_contact_msg);
            if (elevatorList.get(i).isCheck()) {
                elevatorGuidStr += elevatorList.get(i).getElevatorguid() + ",";
                elevatorNoStr += elevatorList.get(i).getARKTX() + ",";
                procInstIdStr += elevatorList.get(i).getProcinstid() + ",";
                elevatorno = elevatorList.get(i).getARKTX();
                List<ElevatorContact> contactsList = elevatorList.get(i).getContactsList();
                if (contactsList != null && contactsList.size() != 0) {
                    for (int j = 0; j < contactsList.size(); j++) {
                        allEmptyFlag = false;
                        name = contactsList.get(j).getName();
                        post = contactsList.get(j).getPost();
                        telephone = contactsList.get(j).getTelephone();
                        content = contentName + ":" + name + " ," + contentTelephone + ":" + telephone + " ," + contentPost + ":" + post + "\n";
                    }
                } else {
                    content = getResources().getString(R.string.is_null) + " \n";
                }
                elevatorContactMsg = elevatorContactMsg.replace("no", elevatorno).replace("content", content);
                msg += elevatorContactMsg;
            }
        }
        elevatorGuidStr = elevatorGuidStr.substring(0, elevatorGuidStr.length() - 1);
        elevatorNoStr = elevatorNoStr.substring(0, elevatorNoStr.length() - 1);
        procInstIdStr = procInstIdStr.substring(0, procInstIdStr.length() - 1);

        //参数
        Map<String, String> params = new HashMap<>();
        params.put("elevatorGuid", elevatorGuidStr);
        params.put("procDefKey", procDefKey);
        params.put("elevatorNo", elevatorNoStr);
        params.put("procInstId", procInstIdStr);
        params.put("dialogType", "2");//联系人
        if (!allEmptyFlag) {
            msg += getResources().getString(R.string.is_sure_override_msg);
            //地址弹出框初始化
            showComfimDialog(msg, params);
        } else {
            //地址弹出框初始化
            showEditContactDialog(params);
        }
    }


    @Override
    protected void picSuccess(Bitmap bitmap) {
        S1Request s1Request = new S1Request();
        s1Request.setProjectGuid(projectId);
        s1Request.setProcDefKey(procDefKey);
        s1Request.setTaskId(taskId);
        //本地图片路径
        String mPhotoPath = ImageUtil.saveImageToGallery(TaskListCommonActivity.this, bitmap);
        File uploadFile = new File(mPhotoPath);
        //转换
        RequestBody photo = RequestBody.create(MediaType.parse("application/octet-stream"), uploadFile);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", uploadFile.getName(), photo);
        String param = GsonUtils.toJson(s1Request, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);

        S1Service s1Service = HttpFactory.getService(S1Service.class);
        s1Service.uploadProjectAttach(requestBody, filePart).compose(RxHelper.<BaseResponse<List<Attach>>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<List<Attach>>>(getActivity(), true) {
            @Override
            public void onSuccess(BaseResponse<List<Attach>> response) {
                ToastUtil.showLong(getResources().getString(R.string.is_successfully_upload));
                projectAttachList.clear();
                projectAttachList.addAll(response.getData());
                //刷新附件
                projectAttachAdpter.notifyDataSetChanged();
            }
        });
    }

    private void showComfimDialog(String msg, final Map<String, String> params) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.is_reminder));
        builder.setMessage(msg);
        builder.setNegativeButton(getResources().getString(R.string.cancel), null);
        builder.setPositiveButton(getResources().getString(R.string.is_sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ("1".equals(params.get("dialogType"))) {
                    showEditAddressDialog(params);
                } else {
                    showEditContactDialog(params);
                }
            }
        });
        builder.show();
    }

    //弹出地址编辑页面
    private void showEditAddressDialog(Map<String, String> params) {
        AddressDialog addressDialog = AddressDialog.newInstance();
        addressDialog.setParams(params);
        addressDialog.setBackResult(this);
        addressDialog.show(((Activity) mContext).getFragmentManager(), "");
    }

    //弹出联系人编辑页面
    private void showEditContactDialog(Map<String, String> params) {
        ContactsDialog contactsDialog = ContactsDialog.newInstance();
        contactsDialog.setParams(params);
        contactsDialog.setBackResult(this);
        contactsDialog.show(((Activity) mContext).getFragmentManager(), "");
    }

    @Override
    public void changeElevatorByAddress(String type, ElevatorAddress elevatorAddress) {
        String[] elevatorGuidArr = elevatorAddress.getElevatorGuid().split(",");
        //交货地址
        if ("1".equals(type)) {
            for (int i = 0; i < elevatorList.size(); i++) {
                for (int j = 0; j < elevatorGuidArr.length; j++) {
                    if (elevatorGuidArr[j].equals(elevatorList.get(i).getElevatorguid())) {
                        elevatorList.get(i).setProjectAddProvince(elevatorAddress.getProvince());
                        elevatorList.get(i).setProjectAddProvinceName(elevatorAddress.getProvinceName());
                        elevatorList.get(i).setProjectAddCity(elevatorAddress.getCity());
                        elevatorList.get(i).setProjectAddCityName(elevatorAddress.getCityName());
                        elevatorList.get(i).setProjectAddArea(elevatorAddress.getArea());
                        elevatorList.get(i).setProjectAddAreaName(elevatorAddress.getAreaName());
                        elevatorList.get(i).setProjectAddOther(elevatorAddress.getOtherAddress());
                    }
                }
            }
        } else if ("2".equals(type)) {//安装地址
            for (int i = 0; i < elevatorList.size(); i++) {
                for (int j = 0; j < elevatorGuidArr.length; j++) {
                    if (elevatorGuidArr[j].equals(elevatorList.get(i).getElevatorguid())) {
                        elevatorList.get(i).setInstlProjectAddProvince(elevatorAddress.getProvince());
                        elevatorList.get(i).setInstlProjectAddProvinceName(elevatorAddress.getProvinceName());
                        elevatorList.get(i).setInstlProjectAddCity(elevatorAddress.getCity());
                        elevatorList.get(i).setInstlProjectAddCityName(elevatorAddress.getCityName());
                        elevatorList.get(i).setInstlProjectAddArea(elevatorAddress.getArea());
                        elevatorList.get(i).setInstlProjectAddAreaName(elevatorAddress.getAreaName());
                        elevatorList.get(i).setInstlProjectAddOther(elevatorAddress.getOtherAddress());
                    }
                }
            }
        }
        //刷新产品
        refreshElevator();
    }

    @Override
    public void changeElevatorByContact(List<ElevatorContact> contactList) {
        String[] elevatorGuidArr = contactList.get(0).getElevatorguids().split(",");
        for (int i = 0; i < elevatorList.size(); i++) {
            for (int j = 0; j < elevatorGuidArr.length; j++) {
                if (elevatorGuidArr[j].equals(elevatorList.get(i).getElevatorguid())) {
                    elevatorList.get(i).setContactsList(contactList);
                }
            }
        }
        //刷新产品
        refreshElevator();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        int i = view.getId();
        if (i == R.id.common_img_close) {
            removeAttach(position);

        } else {
        }
    }

    private void removeAttach(final int position) {
        String msg = getResources().getString(R.string.is_sure_delete_attach_msg);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.is_reminder));
        builder.setMessage(msg);
        builder.setNegativeButton(getResources().getString(R.string.cancel), null);
        builder.setPositiveButton(getResources().getString(R.string.is_sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String attachGuid = projectAttachList.get(position).getAttachguid();
                S1Service s1Service = HttpFactory.getService(S1Service.class);
                s1Service.deleteAttach(attachGuid).compose(RxHelper.<BaseResponse<Boolean>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<Boolean>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<Boolean> response) {
                        if (response.getData()) {
                            ToastUtil.showLong(getResources().getString(R.string.is_delete_success));
                            projectAttachList.remove(position);
                            projectAttachAdpter.notifyItemRemoved(position);
                            projectAttachAdpter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
        builder.show();
    }

    //初始化产品
    protected abstract void initViewElevotorList();

    //刷新产品
    protected abstract void refreshElevator();
}
