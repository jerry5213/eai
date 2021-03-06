package com.zxtech.is.ui.task.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.is.util.PermissionUtils;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.BuildConfig;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.attach.Attach;
import com.zxtech.is.model.s1.ElevatorContact;
import com.zxtech.is.model.s1.S1Elevator;
import com.zxtech.is.ui.task.adpter.ContactReviewAdpter;
import com.zxtech.is.ui.task.adpter.ElevatorAttachReviewAdpter;
import com.zxtech.is.ui.workflow.activity.TaskHistoryListActivity;
import com.zxtech.is.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp600 on 2018/5/4.
 */

public class S1ElevatorDetailReviewActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    //预计3C日期
    @BindView(R2.id.tv_is_plan3CDate)
    TextView tv_is_plan3CDate;

    //排产量预计收款日期
    @BindView(R2.id.tv_is_moneyS2Date)
    TextView tv_is_moneyS2Date;

    //到货款预计收款日期
    @BindView(R2.id.tv_is_moeny3CDate)
    TextView tv_is_moeny3CDate;

    //井道
    @BindView(R2.id.wellholeItem_selector)
    TextView wellholeItem_selector;
    //告知工地必备条件
    @BindView(R2.id.notifyItem_selector)
    TextView notifyItem_selector;

    //交货地址 所在地区
    @BindView(R2.id.tv_is_location_address)
    TextView tv_is_location_address;
    //交货地址 详细地址
    @BindView(R2.id.tv_is_other_address)
    TextView tv_is_other_address;

    //安装地址 所在地区
    @BindView(R2.id.tv_is_install_location_address)
    TextView tv_is_install_location_address;
    //安装地址 详细地址
    @BindView(R2.id.tv_is_install_other_address)
    TextView tv_is_install_other_address;

    //联系人列表
    @BindView(R2.id.rv_is_contact)
    RecyclerView rv_is_contact;
    List<ElevatorContact> contactList = new ArrayList<>();

    private ContactReviewAdpter contactReviewAdpter;

    private String procInstId;

    //产品附件
    @BindView(R2.id.rv_is_elevator_attach)
    RecyclerView rv_is_elevator_attach;

    private ElevatorAttachReviewAdpter elevatorAttachReviewAdpter;

    //产品附件
    List<Attach> elevatorAttachList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_s1_elevator_detail_review;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);

        //产品附件
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_is_elevator_attach.setLayoutManager(linearLayoutManager);
        rv_is_elevator_attach.addItemDecoration(new com.zxtech.is.widget.MyItemDecoration());
        elevatorAttachReviewAdpter = new ElevatorAttachReviewAdpter(R.layout.item_is_img, elevatorAttachList);
        rv_is_elevator_attach.setAdapter(elevatorAttachReviewAdpter);

        //联系人列表
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        rv_is_contact.setLayoutManager(linearLayoutManager1);
        rv_is_contact.addItemDecoration(new MyItemDecoration());
        contactReviewAdpter = new ContactReviewAdpter(R.layout.item_contact_review, contactList);
        contactReviewAdpter.bindToRecyclerView(rv_is_contact);
        rv_is_contact.setAdapter(contactReviewAdpter);

        elevatorAttachReviewAdpter.setOnItemClickListener(this);
        //加载数据
        loadData();
    }

    private void loadData() {
        S1Elevator elevator = (S1Elevator) getIntent().getSerializableExtra("elevator");
        procInstId = elevator.getProcinstid();
        //联系人
        contactList.addAll(elevator.getContactsList());
        contactReviewAdpter.notifyDataSetChanged();

        //产品附件
        elevatorAttachList.addAll(elevator.getElevatorAttachList());
        elevatorAttachReviewAdpter.notifyDataSetChanged();

        //预计3C日期
        tv_is_plan3CDate.setText(elevator.getPlan3cdate());
        //排产量预计收款日期
        tv_is_moneyS2Date.setText(elevator.getMoneys2date());
        //到货款预计收款日期
        tv_is_moeny3CDate.setText(elevator.getMoeny3cdate());

        //井道
        if ("1".equals(elevator.getWellholeitem())) {
            wellholeItem_selector.setText(getResources().getString(R.string.is_qualified));
            wellholeItem_selector.setTag(elevator.getWellholeitem());
        } else if ("2".equals(elevator.getWellholeitem())) {
            wellholeItem_selector.setText(getResources().getString(R.string.is_unqualified));
            wellholeItem_selector.setTag(elevator.getWellholeitem());
        }

        //告知工地必备条件
        if ("1".equals(elevator.getNotifyitem())) {
            notifyItem_selector.setText(getResources().getString(R.string.is_notice));
            notifyItem_selector.setTag(elevator.getNotifyitem());
        } else if ("2".equals(elevator.getNotifyitem())) {
            notifyItem_selector.setText(getResources().getString(R.string.is_unnotice));
            notifyItem_selector.setTag(elevator.getNotifyitem());
        }


        //交货地址
        String locationAddress = elevator.getProjectAddProvinceName() + " " + elevator.getProjectAddCityName() + " " + elevator.getProjectAddAreaName();
        tv_is_location_address.setText(locationAddress);
        tv_is_other_address.setText(elevator.getProjectAddOther());

        //安装地址
        String installLocationAddress = elevator.getInstlProjectAddProvinceName() + " " + elevator.getInstlProjectAddCityName() + " " + elevator.getInstlProjectAddAreaName();
        tv_is_install_location_address.setText(installLocationAddress);
        tv_is_install_other_address.setText(elevator.getInstlProjectAddOther());

    }

    @OnClick({R2.id.is_abolished,
            R2.id.tv_proc_info})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.is_abolished) {
            finish();

        } else if (i == R.id.tv_proc_info) {
            Intent intent = new Intent(mContext, TaskHistoryListActivity.class);
            intent.putExtra("procInstId", procInstId);
            startActivity(intent);
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PermissionUtils.checkAndRequestPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE, 0);
        Intent intent = new Intent();
        switch (elevatorAttachList.get(position).getFiletype()) {
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
                String attachguid = elevatorAttachList.get(position).getAttachguid();
                String url = "";
                if (attachguid != null) {
                    url = BuildConfig.BASE_URL + "s1/downloadAttach?guid=" + attachguid;
                } else {
                    url = elevatorAttachList.get(position).getFilepath();
                }
                intent.putExtra("url", url);
                intent.setClass(mContext, ShowBigImageSimpleActivity.class);
                startActivity(intent);
                break;
        }
    }
}
