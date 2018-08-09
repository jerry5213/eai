package com.zxtech.is.ui.Engineer.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.PermissionUtils;
import com.zxtech.is.widget.MyItemDecoration;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.BuildConfig;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.attach.Attach;
import com.zxtech.is.model.s1.ElevatorContact;
import com.zxtech.is.model.s1.Project;
import com.zxtech.is.model.s1.S1Elevator;
import com.zxtech.is.model.s1.S1Response;
import com.zxtech.is.service.task.S1Service;
import com.zxtech.is.ui.Engineer.adapter.CommittedTaskDetailElevatorAttachAdpter;
import com.zxtech.is.ui.Engineer.adapter.CommittedTaskDetailProjectAttachAdpter;
import com.zxtech.is.ui.task.activity.ShowBigImageSimpleActivity;
import com.zxtech.is.ui.task.adpter.ContactReviewAdpter;
import com.zxtech.is.ui.workflow.activity.TaskHistoryListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp600 on 2018/4/19.
 */

public class CommittedTaskDetailActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    //项目名称
    @BindView(R2.id.tv_is_project_name)
    TextView tv_is_project_name;
    //产品台量
    @BindView(R2.id.is_product_units)
    TextView is_product_units;

    //代理商
    @BindView(R2.id.tv_is_agent_name)
    TextView tv_is_agent_name;
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
    //产品附件
    @BindView(R2.id.rv_is_elevator_attach)
    RecyclerView rv_is_elevator_attach;
    //产品附件
    @BindView(R2.id.rv_is_contact)
    RecyclerView rv_is_contact;

    //s2 s3 信息预计3C日期
    @BindView(R2.id.tv_is_plan3CDate_s2)
    TextView tv_is_plan3CDate_s2;
    //排产量预计收款日期
    @BindView(R2.id.tv_is_moneyS2Date_s2)
    TextView tv_is_moneyS2Date_s2;
    //到货款预计收款日期
    @BindView(R2.id.tv_is_moeny3CDate_s2)
    TextView tv_is_moeny3CDate_s2;
    //井道
    @BindView(R2.id.wellholeItem_selector_s2)
    TextView wellholeItem_selector_s2;
    //入场通道
    @BindView(R2.id.enterItem_selector)
    TextView enterItem_selector;
    //堆场
    @BindView(R2.id.yardItem_selector)
    TextView yardItem_selector;
    //安装队伍
    @BindView(R2.id.installTemItem_selector)
    TextView installTemItem_selector;
    //库房
    @BindView(R2.id.storehouseItem_selector)
    TextView storehouseItem_selector;
    //施工条件
    @BindView(R2.id.constructionItem_selector)
    TextView constructionItem_selector;

    //项目附件
    @BindView(R2.id.rv_is_project_attach)
    RecyclerView rv_is_project_attach;

    private ContactReviewAdpter contactReviewAdpter;

    private CommittedTaskDetailProjectAttachAdpter s1TaskDetailProjectAttachAdpter;
    //项目附件
    List<Attach> projectAttachList = new ArrayList<>();

    private CommittedTaskDetailElevatorAttachAdpter s1TaskDetailElevatorAttachAdpter;
    //项目附件
    List<Attach> elevatorAttachList = new ArrayList<>();

    //联系人List
    List<ElevatorContact> contactList = new ArrayList<>();

    //项目信息
    @BindView(R2.id.is_project_info)
    LinearLayout is_project_info;
    @BindView(R2.id.is_elevator_info)
    LinearLayout is_elevator_info;
    @BindView(R2.id.is_elevator_s2_detail)
    LinearLayout is_elevator_s2_detail;
    @BindView(R2.id.is_elevator_info_s1)
    RelativeLayout is_elevator_info_s1;
    @BindView(R2.id.is_elevator_info_s2)
    RelativeLayout is_elevator_info_s2;
    @BindView(R2.id.tv_open_project_info)
    TextView tv_open_project_info;
    @BindView(R2.id.rv_is_contact_list)
    LinearLayout rv_is_contact_list;
    @BindView(R2.id.is_elevator_handle_address)
    LinearLayout is_elevator_handle_address;
    @BindView(R2.id.tv_open_elevator_info)
    TextView tv_open_elevator_info;
    @BindView(R2.id.tv_open_elevator_info_s2)
    TextView tv_open_elevator_info_s2;
    @BindView(R2.id.tv_open_contact_info)
    TextView tv_open_contact_info;
    @BindView(R2.id.is_address)
    TextView is_address;
    @BindView(R2.id.is_project_address)
    TextView is_project_address;
    //交货地址
    @BindView(R2.id.is_elevator_address)
    TextView is_elevator_address;
    //详细交货地址
    @BindView(R2.id.is_elevator_address_other)
    TextView is_elevator_address_other;
    //安装地址
    @BindView(R2.id.is_elevator_install_adress)
    TextView is_elevator_install_adress;
    //详细安装地址
    @BindView(R2.id.is_elevator_install_adress_other)
    TextView is_elevator_install_adress_other;
    @BindView(R2.id.tv_is_no_data)
    TextView tv_is_no_data;
    @BindString(R2.string.icon_zhe_die_open)
    String icon_open;
    @BindString(R2.string.icon_zhe_die_close)
    String icon_close;

    //项目id
    String projectId;
    //产品id
    String eleGuid;
    //流程key
    String procDefKey;
    //流程实例key
    String procInstId;

    @Override
    protected int getLayoutId() {
        return R.layout.s1_task_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(mToolbar);

        Intent intent = getIntent();
        projectId = intent.getStringExtra("projectId");
        eleGuid = intent.getStringExtra("eleGuid");
        procDefKey = intent.getStringExtra("procDefKey");
        procInstId = intent.getStringExtra("procInstId");

        //项目附件
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager linearLayoutManagerEle = new LinearLayoutManager(this);
        linearLayoutManagerEle.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager linearLayoutManagerContact = new LinearLayoutManager(this);
        linearLayoutManagerContact.setOrientation(LinearLayoutManager.VERTICAL);
        rv_is_project_attach.setLayoutManager(linearLayoutManager);
        rv_is_project_attach.addItemDecoration(new MyItemDecoration());
        s1TaskDetailProjectAttachAdpter = new CommittedTaskDetailProjectAttachAdpter(R.layout.item_img_detail_common, projectAttachList);
        rv_is_project_attach.setAdapter(s1TaskDetailProjectAttachAdpter);
        rv_is_elevator_attach.setLayoutManager(linearLayoutManagerEle);
        rv_is_elevator_attach.addItemDecoration(new MyItemDecoration());
        s1TaskDetailElevatorAttachAdpter = new CommittedTaskDetailElevatorAttachAdpter(R.layout.item_img_detail_common, elevatorAttachList);
        rv_is_elevator_attach.setAdapter(s1TaskDetailElevatorAttachAdpter);
        rv_is_contact.setLayoutManager(linearLayoutManagerContact);
        rv_is_contact.addItemDecoration(new MyItemDecoration());
        contactReviewAdpter = new ContactReviewAdpter(R.layout.item_contact_review, contactList);
        rv_is_contact.setAdapter(contactReviewAdpter);


        //加载数据
        loadData(projectId, eleGuid);

        s1TaskDetailProjectAttachAdpter.setOnItemClickListener(this);
        s1TaskDetailElevatorAttachAdpter.setOnItemClickListener(this);
    }

    public void loadData(String projectId, String eleGuid) {
        S1Service s1Service = HttpFactory.getService(S1Service.class);
        s1Service.selectISPlanS1Deatail(projectId, eleGuid, procDefKey).compose(RxHelper.<BaseResponse<S1Response>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<S1Response>>(getActivity(), false) {
            @Override
            public void onSuccess(BaseResponse<S1Response> response) {
                //项目信息
                Project projectInfo = response.getData().getProjectInfo();
                //产品信息
                S1Elevator elevatorInfo = response.getData().getElevatorInfo();
                String projectName = projectInfo.getKtext() != null ? projectInfo.getKtext() : "";
                String agentName = projectInfo.getAgentName() != null ? projectInfo.getAgentName() : "";
                String address = projectInfo.getShipToAddress() != null ? projectInfo.getShipToAddress() : "";
                String productUnits = projectInfo.getKwmeng() != null ? projectInfo.getKwmeng() : "";
                String tvIsPlan3CDate = elevatorInfo.getPlan3cdate() != null ? elevatorInfo.getPlan3cdate() : "";
                String tvTsMoneyS2Date = elevatorInfo.getMoneys2date() != null ? elevatorInfo.getMoneys2date() : "";
                String tvIsMoeny3CDate = elevatorInfo.getMoeny3cdate() != null ? elevatorInfo.getMoeny3cdate() : "";
                String wellholeItemSelector = elevatorInfo.getWellholeitem() != null ? elevatorInfo.getWellholeitem() : "";
                String notifyItemSelector = elevatorInfo.getNotifyitem() != null ? elevatorInfo.getNotifyitem() : "";
                //产品交货地址
                String elevatorAdress = elevatorInfo.getProjectAddOther() != null ? elevatorInfo.getProjectAddOther() : "";
                String elevattorAdressProvince = elevatorInfo.getProjectAddProvinceName() != null ? elevatorInfo.getProjectAddProvinceName() : "";
                String elevatorAdressCity = elevatorInfo.getProjectAddCityName() != null ? elevatorInfo.getProjectAddCityName() : "";
                String elevatorAdressArea = elevatorInfo.getProjectAddAreaName() != null ? elevatorInfo.getProjectAddAreaName() : "";
                //产品安装地址
                String elevatorInstallAdress = elevatorInfo.getInstlProjectAddOther() != null ? elevatorInfo.getInstlProjectAddOther() : "";
                String elevatorInstallAdressProvince = elevatorInfo.getInstlProjectAddProvinceName() != null ? elevatorInfo.getInstlProjectAddProvinceName() : "";
                String elevatorInstallAdressCity = elevatorInfo.getInstlProjectAddCityName() != null ? elevatorInfo.getInstlProjectAddCityName() : "";
                String elevatorInstallAdressArea = elevatorInfo.getInstlProjectAddAreaName() != null ? elevatorInfo.getInstlProjectAddAreaName() : "";
                is_elevator_address.setText(elevattorAdressProvince + elevatorAdressCity + elevatorAdressArea);
                is_elevator_address_other.setText(elevatorAdress);
                is_elevator_install_adress.setText(elevatorInstallAdressProvince + elevatorInstallAdressCity + elevatorInstallAdressArea);
                is_elevator_install_adress_other.setText(elevatorInstallAdress);
                tv_is_project_name.setText(projectName);
                tv_is_agent_name.setText(agentName);
                is_project_address.setText(address);
                is_product_units.setText(productUnits);
                if ("1".equals(notifyItemSelector)) {
                    notifyItem_selector.setText(getResources().getString(R.string.is_notice));
                } else {
                    notifyItem_selector.setText(getResources().getString(R.string.is_unnotice));
                }
                if ("s1".equals(procDefKey)) {
                    is_elevator_info_s2.setVisibility(View.GONE);
                    is_elevator_s2_detail.setVisibility(View.GONE);
                    tv_is_plan3CDate.setText(tvIsPlan3CDate);
                    tv_is_moneyS2Date.setText(tvTsMoneyS2Date);
                    tv_is_moeny3CDate.setText(tvIsMoeny3CDate);
                    if ("1".equals(wellholeItemSelector)) {
                        wellholeItem_selector.setText(getResources().getString(R.string.is_qualified));
                    } else {
                        wellholeItem_selector.setText(getResources().getString(R.string.is_unqualified));
                    }
                } else {
                    is_elevator_info_s1.setVisibility(View.GONE);
                    is_elevator_info.setVisibility(View.GONE);
                    String enterItem = elevatorInfo.getEnterItem() != null ? elevatorInfo.getEnterItem() : "";
                    String yardItem = elevatorInfo.getYardItem() != null ? elevatorInfo.getYardItem() : "";
                    String storehouseItem = elevatorInfo.getStorehouseItem() != null ? elevatorInfo.getStorehouseItem() : "";
                    String constructionItem = elevatorInfo.getConstructionItem() != null ? elevatorInfo.getConstructionItem() : "";
                    String installTemItem = elevatorInfo.getInstallTemItem() != null ? elevatorInfo.getInstallTemItem() : "";
                    if ("1".equals(enterItem)) {
                        enterItem_selector.setText(getResources().getString(R.string.is_impl));
                    } else {
                        enterItem_selector.setText(getResources().getString(R.string.is_unimpl));
                    }
                    if ("1".equals(yardItem)) {
                        yardItem_selector.setText(getResources().getString(R.string.is_impl));
                    } else {
                        yardItem_selector.setText(getResources().getString(R.string.is_unimpl));
                    }
                    if ("1".equals(storehouseItem)) {
                        storehouseItem_selector.setText(getResources().getString(R.string.is_impl));
                    } else {
                        storehouseItem_selector.setText(getResources().getString(R.string.is_unimpl));
                    }
                    if ("1".equals(constructionItem)) {
                        constructionItem_selector.setText(getResources().getString(R.string.is_impl));
                    } else {
                        constructionItem_selector.setText(getResources().getString(R.string.is_unimpl));
                    }
                    if ("1".equals(installTemItem)) {
                        installTemItem_selector.setText(getResources().getString(R.string.is_impl));
                    } else {
                        installTemItem_selector.setText(getResources().getString(R.string.is_unimpl));
                    }
                    is_elevator_info.setVisibility(View.GONE);
                    tv_is_plan3CDate_s2.setText(tvIsPlan3CDate);
                    tv_is_moneyS2Date_s2.setText(tvTsMoneyS2Date);
                    tv_is_moeny3CDate_s2.setText(tvIsMoeny3CDate);
                    if ("1".equals(wellholeItemSelector)) {
                        wellholeItem_selector_s2.setText(getResources().getString(R.string.is_qualified));
                    } else {
                        wellholeItem_selector_s2.setText(getResources().getString(R.string.is_unqualified));
                    }
                }
                //产品附件
                elevatorAttachList.clear();
                elevatorAttachList.addAll(response.getData().getElevatorAttachList());
                s1TaskDetailElevatorAttachAdpter.notifyDataSetChanged();
                //项目附件
                if (response.getData().getProjectAttachList().size() == 0) {
                    tv_is_no_data.setVisibility(View.VISIBLE);
                    rv_is_project_attach.setVisibility(View.GONE);
                } else {
                    //项目附件
                    projectAttachList.clear();
                    projectAttachList.addAll(response.getData().getProjectAttachList());
                    s1TaskDetailProjectAttachAdpter.notifyDataSetChanged();
                }
                //联系人
                //项目附件
                contactList.clear();
                contactList.addAll(response.getData().getContactList());
                contactReviewAdpter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R2.id.tv_open_project_info, R2.id.tv_open_elevator_info, R2.id.tv_open_elevator_info_s2, R2.id.tv_open_contact_info, R2.id.is_address, R2.id.tv_proc_info})
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

        } else if (i == R.id.tv_open_elevator_info) {
            if (is_elevator_info.isShown()) {
                is_elevator_info.setVisibility(View.GONE);
                tv_open_elevator_info.setText(icon_open);
            } else {
                is_elevator_info.setVisibility(View.VISIBLE);
                tv_open_elevator_info.setText(icon_close);
            }

        } else if (i == R.id.tv_open_elevator_info_s2) {
            if (is_elevator_s2_detail.isShown()) {
                is_elevator_s2_detail.setVisibility(View.GONE);
                tv_open_elevator_info_s2.setText(icon_open);
            } else {
                is_elevator_s2_detail.setVisibility(View.VISIBLE);
                tv_open_elevator_info_s2.setText(icon_close);
            }

        } else if (i == R.id.tv_open_contact_info) {
            if (rv_is_contact_list.isShown()) {
                rv_is_contact_list.setVisibility(View.GONE);
                tv_open_contact_info.setText(icon_open);
            } else {
                rv_is_contact_list.setVisibility(View.VISIBLE);
                tv_open_contact_info.setText(icon_close);
            }

        } else if (i == R.id.is_address) {
            if (is_elevator_handle_address.isShown()) {
                is_elevator_handle_address.setVisibility(View.GONE);
                is_address.setText(icon_open);
            } else {
                is_elevator_handle_address.setVisibility(View.VISIBLE);
                is_address.setText(icon_close);
            }

        } else if (i == R.id.tv_proc_info) {
            Intent intent = new Intent(mContext, TaskHistoryListActivity.class);
            intent.putExtra("procInstId", procInstId);
            startActivity(intent);

        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof CommittedTaskDetailProjectAttachAdpter) {
            PermissionUtils.checkAndRequestPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE, 0);
            Intent intent = new Intent();
            String s = projectAttachList.get(position).getFiletype();
            if (s.equals("pdf")) {//                File file = new File(DOWN_LOAD_PATH, projectAttachList.get(0).getFilename());
//                if (file.exists()) {//如果已存在,直接打开
//                    OfficePoiUtil.openFile(mContext, file);
//                } else {//文件不存在,下载后打开
////                    downloadFile(fileInfoList, file);
//                }

            } else if (s.equals("jpg") || s.equals("png")) {
                String attachguid = projectAttachList.get(position).getAttachguid();
                String url = BuildConfig.BASE_URL + "s1/downloadAttach?guid=" + attachguid;
                intent.putExtra("url", url);
                intent.setClass(mContext, ShowBigImageSimpleActivity.class);
                startActivity(intent);

            }
        }
        if (adapter instanceof CommittedTaskDetailElevatorAttachAdpter) {
            PermissionUtils.checkAndRequestPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE, 0);
            Intent intent = new Intent();
            String s = elevatorAttachList.get(position).getFiletype();
            if (s.equals("pdf")) {//                File file = new File(DOWN_LOAD_PATH, projectAttachList.get(0).getFilename());
//                if (file.exists()) {//如果已存在,直接打开
//                    OfficePoiUtil.openFile(mContext, file);
//                } else {//文件不存在,下载后打开
////                    downloadFile(fileInfoList, file);
//                }

            } else if (s.equals("jpg") || s.equals("png")) {
                String attachguid = elevatorAttachList.get(position).getAttachguid();
                String url = BuildConfig.BASE_URL + "s1/downloadAttach?guid=" + attachguid;
                intent.putExtra("url", url);
                intent.setClass(mContext, ShowBigImageSimpleActivity.class);
                startActivity(intent);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
//            S1Service s1Service = HttpFactory.getService(S1Service.class);
//            s1Service.selectElevatorList(taskId, procDefKey).compose(RxHelper.<BaseResponse<List<S1Elevator>>>rxSchedulerHelper()).subscribe(new DefaultObserver<BaseResponse<List<S1Elevator>>>(getActivity(), false) {
//                @Override
//                public void onSuccess(BaseResponse<List<S1Elevator>> response) {
//                    elevatorList.clear();
//                    elevatorList.addAll(response.getData());
//                    //刷新产品
//                    s1ElevatorAdpter.notifyDataSetChanged();
//                }
//            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
    }

    //动态的ImageView
    private ImageView getImageView(int position) {
        ImageView iv = new ImageView(getActivity());
        //宽高
        iv.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //设置Padding
        iv.setPadding(40, 40, 40, 40);
        String url = BuildConfig.BASE_URL + "s1/downloadAttach?guid=" + projectAttachList.get(position).getAttachguid();
        //imageView设置图片
//        Drawable drawable = BitmapDrawable.createFromPath(url);
//        iv.setImageDrawable(drawable);
        Glide.with(mContext).load(url).into(iv);
        return iv;
    }

}
