package com.zxtech.is.ui.task.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.PermissionUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.MyItemDecoration;
import com.zxtech.is.BuildConfig;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.s1.S1Elevator;
import com.zxtech.is.model.s1.S1Response;
import com.zxtech.is.service.task.S1Service;
import com.zxtech.is.ui.task.adpter.ProjectAttachAdpter;
import com.zxtech.is.ui.task.adpter.S1ElevatorAdpter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by syp600 on 2018/4/19.
 */

public class S1TaskListActivity extends TaskListCommonActivity {

    private static final int REQUEST_DETAIL = 2;

    //产品列表
    @BindView(R2.id.rv_is_elevator)
    RecyclerView rv_is_elevator;

    private S1ElevatorAdpter s1ElevatorAdpter;
    //产品列表
    List<S1Elevator> elevatorList = new ArrayList<>();

    protected void initViewElevotorList() {
        //产品列表
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        rv_is_elevator.setLayoutManager(linearLayoutManager1);
        rv_is_elevator.addItemDecoration(new MyItemDecoration());
        s1ElevatorAdpter = new S1ElevatorAdpter(R.layout.item_s1_elevator, elevatorList);
        rv_is_elevator.setAdapter(s1ElevatorAdpter);

        //加载数据
        loadElevotorList(params);
        s1ElevatorAdpter.setOnItemClickListener(this);
    }

    public void loadElevotorList(Map<String, String> params) {
        S1Service s1Service = HttpFactory.getService(S1Service.class);
        s1Service.selectISPlanS1(params).compose(RxHelper.<BaseResponse<S1Response>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<S1Response>>(getActivity(), true) {
            @Override
            public void onSuccess(BaseResponse<S1Response> response) {
                //产品信息
                S1TaskListActivity.super.elevatorList = response.getData().getElevatorList();
                elevatorList.clear();
                elevatorList.addAll(response.getData().getElevatorList());
                s1ElevatorAdpter.notifyDataSetChanged();
            }
        });
    }


    @OnClick({R2.id.tv_open_project_info,
            R2.id.iv_is_add_img,
            R2.id.cb_is_checkall,
            R2.id.is_btn_install_address,
            R2.id.is_btn_delivery_address,
            R2.id.is_btn_contact_address})
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

        } else if (i == R.id.iv_is_add_img) {
            addAttach();

        } else if (i == R.id.cb_is_checkall) {
            clickSelectAll();

        } else if (i == R.id.is_btn_install_address) {
            clickEditInstallAdress();

        } else if (i == R.id.is_btn_delivery_address) {
            clickEditAdress();

        } else if (i == R.id.is_btn_contact_address) {
            clickEditContacts();

        }
    }

    //全选
    private void clickSelectAll() {
        if (cb_is_checkall.isChecked()) {
            s1ElevatorAdpter.setCheckAllFlag(true);
        } else {
            s1ElevatorAdpter.setCheckAllFlag(false);
        }
        for (int i = 0; i < elevatorList.size(); i++) {
            if (s1ElevatorAdpter.isCheckAllFlag()) {
                elevatorList.get(i).setCheck(true);
            } else {
                elevatorList.get(i).setCheck(false);
            }
        }
        s1ElevatorAdpter.notifyDataSetChanged();
    }

    //批量编辑交货地址
    public void clickEditAdress() {
        if (s1ElevatorAdpter.getSelectCount() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_select_one_msg));
        } else {
            openAdressDialog();
        }
    }

    //批量编辑安装地址
    public void clickEditInstallAdress() {
        if (s1ElevatorAdpter.getSelectCount() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_select_one_msg));
        } else {
            openInstallAdressDialog();
        }
    }

    //批量编辑联系人
    public void clickEditContacts() {
        if (s1ElevatorAdpter.getSelectCount() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_select_one_msg));
        } else {
            openContactsDialog();
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof ProjectAttachAdpter) {
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
        if (adapter instanceof S1ElevatorAdpter) {
            Intent intent = new Intent(mContext, S1ElevatorDetailActivity.class);
            S1Elevator elevator = elevatorList.get(position);
            intent.putExtra("elevator", elevator);
            intent.putExtra("projectGuid", projectId);
            intent.putExtra("procDefKey", procDefKey);
            startActivityForResult(intent, REQUEST_DETAIL);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_DETAIL && resultCode == RESULT_OK) {
            if (elevatorList.size() == 1) {
                setResult(RESULT_OK);
                finish();
            } else {
                String finish_taskId = data.getStringExtra("finish_taskId");
                for (int i = 0; i < elevatorList.size(); i++) {
                    if (finish_taskId.equals(elevatorList.get(i).getTaskId())) {
                        elevatorList.remove(i);
                        break;
                    }
                }
                //刷新产品
                s1ElevatorAdpter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void refreshElevator() {
        //刷新产品
        s1ElevatorAdpter.notifyDataSetChanged();
    }
}
