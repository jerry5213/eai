package com.zxtech.gks.ui.record.sale;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.BidAttachment;
import com.zxtech.ecs.model.DropDown;
import com.zxtech.ecs.model.ProjectBid;
import com.zxtech.ecs.net.BasicResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.bid.BidInfoFragment;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.DropDownWindow;
import com.zxtech.gks.IActivity;
import com.zxtech.gks.common.Constants;
import com.zxtech.gks.model.bean.BelongArea;
import com.zxtech.gks.model.bean.City;
import com.zxtech.gks.model.bean.CityBean;
import com.zxtech.gks.model.bean.District;
import com.zxtech.gks.model.bean.DistrictBean;
import com.zxtech.gks.model.bean.DxmZY;
import com.zxtech.gks.model.bean.GetProjectDetailDropDownList;
import com.zxtech.gks.model.bean.ProjectDetailBean;
import com.zxtech.gks.model.bean.Province;
import com.zxtech.gks.model.vo.Customer;
import com.zxtech.gks.model.vo.RecordApproval;
import com.zxtech.gks.model.vo.SaleProjectReportDetail;
import com.zxtech.gks.model.vo.type.BuildingCharacter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SYP521 on 2017/12/28.
 */

public class EditReportActivity extends BaseActivity implements IActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.project_no)
    EditText project_no;
    @BindView(R.id.salesman_name)
    EditText salesman_name;
    @BindView(R.id.sale_branch)
    EditText sale_branch;
    @BindView(R.id.local_branch)
    EditText local_branch;
    @BindView(R.id.tv_customer)
    TextView tv_customer;
    @BindView(R.id.build_attr_selector)
    TextView build_attr_selector;
    @BindView(R.id.project_name)
    EditText project_name;
    @BindView(R.id.address_other)
    EditText address_other;

    @BindView(R.id.province_selector)
    TextView province_selector;
    @BindView(R.id.city_selector)
    TextView city_selector;
    @BindView(R.id.area_selector)
    TextView area_selector;
    @BindView(R.id.bid_selector)
    TextView bid_selector;

    @BindView(R.id.param1_tv)
    TextView param1_tv;
    @BindView(R.id.param2_tv)
    TextView param2_tv;
    @BindView(R.id.param3_tv)
    EditText param3_tv;
    @BindView(R.id.param4_tv)
    EditText param4_tv;
    @BindView(R.id.param5_tv)
    EditText param5_tv;
    @BindView(R.id.param6_tv)
    TextView param6_tv;
    @BindView(R.id.param7_tv)
    TextView param7_tv;
    @BindView(R.id.param8_tv)
    TextView param8_tv;
    @BindView(R.id.param9_tv)
    TextView param9_tv;
    @BindView(R.id.param10_tv)
    TextView param10_tv;
    @BindView(R.id.param11_tv)
    TextView param11_tv;
    @BindView(R.id.param12_tv)
    TextView param12_tv;

    @BindView(R.id.param13_tv)
    TextView param13_tv;
    @BindView(R.id.param14_tv)
    TextView param14_tv;
    @BindView(R.id.param15_tv)
    TextView param15_tv;
    @BindView(R.id.param16_tv)
    TextView param16_tv;
    @BindView(R.id.param17_tv)
    TextView param17_tv;

    @BindView(R.id.param18_tv)
    TextView param18_tv;
    @BindView(R.id.param19_tv)
    EditText param19_tv;
    @BindView(R.id.param20_tv)
    EditText param20_tv;
    @BindView(R.id.param21_tv)
    TextView param21_tv;
    @BindView(R.id.param22_tv)
    TextView param22_tv;
    @BindView(R.id.param23_tv)
    TextView param23_tv;

    //大项目
    @BindView(R.id.dxm_panel)
    LinearLayout dxm_panel;
    @BindView(R.id.dxmzy_selector)
    TextView dxmzy_selector;
    @BindView(R.id.dxmzySalesManager)
    EditText dxmzySalesManager;

    @BindView(R.id.other_panel)
    LinearLayout other_panel;
    @BindView(R.id.base_panel)
    LinearLayout base_panel;
    @BindView(R.id.iv_close)
    ImageView iv_close;

    @BindView(R.id.fl_container)
    FrameLayout fl_container;

    List<BuildingCharacter> buildingCharacters = new ArrayList<>();
    List<Province> provinces = new ArrayList<>();
    List<City> cities = new ArrayList<>();
    List<District> districts = new ArrayList<>();
    List<DxmZY> dxmZies;
    private String purchaseType = "招投标";

    private GetProjectDetailDropDownList dropDownList;

    private String projectGuid;
    private String contractBranchSAPNo;
    private String project_type;
    private String saleBranchNo; //销售员所在销售分公司编号
    private String InstanceGuid; //流程ID
    private RecordApproval project;
    private boolean firstCity = true;
    private boolean firstArea = true;

    private BidInfoFragment fragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_report;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar, getString(R.string.new_project));
        fragment = BidInfoFragment.newInstance();
        fragment.setExpand(true);
        fragment.projectBid = new ProjectBid();
        loadRootFragment(R.id.fl_container, fragment);
        initData();
    }

    private Customer customer = new Customer();
    private String DetailGuid = "";
    private ProjectDetailBean projectDetail;

    public void initData() {

        Intent intent = getIntent();
        String action = intent.getStringExtra("action");
        getOther();
        if ("add".equals(action)) {
            getProvince();
            initBuildingCharacter();
            setTitle("新增报备");
            project_type = intent.getStringExtra("project_type");
            salesman_name.setText(getUserName());
            sale_branch.setText(getUserDeptName());
            getProjectNo();
            //大项目
            if (Constants.PROJ_TYPE_DXM.equals(project_type) || Constants.PROJ_TYPE_XSDXM.equals(project_type)) {
                initDxmzy();
                dxm_panel.setVisibility(View.VISIBLE);
            } else {
                dxm_panel.setVisibility(View.GONE);
            }

        } else if ("modify".equals(action)) {

            setTitle("修改报备");
            String id = intent.getStringExtra("id");

            baseResponseObservable = HttpFactory.getApiService().
                    GetProjectDetail(id);
            baseResponseObservable
                    .compose(this.bindToLifecycle())
                    .compose(RxHelper.<BasicResponse<SaleProjectReportDetail>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BasicResponse<SaleProjectReportDetail>>(this, true) {

                        @Override
                        public void onSuccess(BasicResponse<SaleProjectReportDetail> response) {

                            project = response.getData().getProject();
                            projectDetail = response.getData().getProjectDetail();
                            ProjectBid projectBid = response.getData().getProjectBid();
                            List<BidAttachment> bidAttachments = response.getData().getDrawingFileList();

                            if (project != null) {

                                InstanceGuid = project.getInstanceGuid();
                                //进入修改界面
                                //初始化 #项目地分公司编号
                                contractBranchSAPNo = project.getProjectAreaName();
                                //初始化必要的客户信息
                                customer.setGuid(project.getCustomerId());
                                customer.setCustomerContact(project.getCustomerContact());
                                customer.setPhoneNumber(project.getCustomerPhoneNum());
                                customer.setCustomerAdd(project.getCustomerAddrees());

                                projectGuid = project.getProjectGuid();
                                project_no.setText(project.getProjectNo());
                                salesman_name.setText(project.getSalesmanUserName());
                                sale_branch.setText(project.getBranchName());
                                local_branch.setText(project.getProjectAreaNameStr());
                                tv_customer.setText(project.getCustomerName());
                                project_name.setText(project.getProjectName());
                                address_other.setText(project.getProjectAdd_Other());
                                project_type = project.getProjectType();

                                bid_selector.setText(project.getPurchaseType());
                                bid_selector.setTag(project.getPurchaseType());
                                if(purchaseType.equals(project.getPurchaseType())){
                                    fl_container.setVisibility(View.VISIBLE);
                                }else{
                                    fl_container.setVisibility(View.GONE);
                                }

                                //大项目
                                if (Constants.PROJ_TYPE_DXM.equals(project_type) || Constants.PROJ_TYPE_XSDXM.equals(project_type)) {
                                    initDxmzy();
                                    dxm_panel.setVisibility(View.VISIBLE);
                                    dxmzySalesManager.setText(project.getDxmzySalesManager());
                                } else {
                                    dxm_panel.setVisibility(View.GONE);
                                }
                                getProvince();
                                initBuildingCharacter();
                            } else {
                                finish();
                            }

                            if (projectDetail != null) {
                                DetailGuid = projectDetail.getDetailGuid();
                                param1_tv.setText(projectDetail.getProjectState());
                                param1_tv.setTag(projectDetail.getProjectState());
                                param2_tv.setText(projectDetail.getProjectStage());
                                param2_tv.setTag(projectDetail.getProjectStage());
                                param3_tv.setText(projectDetail.getEleCount());
                                param4_tv.setText(projectDetail.getEscCount());
                                param5_tv.setText(projectDetail.getTotalBidCost());
                                param6_tv.setText(projectDetail.getMainEqType());
                                param6_tv.setTag(projectDetail.getMainEqType());
                                param7_tv.setText(projectDetail.getStartBidDate());
                                param8_tv.setText(projectDetail.getEndBidDate());
                                param9_tv.setText(projectDetail.getExSignDate());
                                param10_tv.setText(projectDetail.getExPayDate());
                                param11_tv.setText(projectDetail.getExDeliveryDate());
                                param12_tv.setText(projectDetail.getExEQSDate());
                                param13_tv.setText(projectDetail.getHLAccount());
                                param13_tv.setTag(projectDetail.getHLAccount());
                                param14_tv.setText(projectDetail.getInfoSource());
                                param14_tv.setTag(projectDetail.getInfoSource());
                                param15_tv.setText(projectDetail.isIsOnlyAgent() ? getString(R.string.yes) : getString(R.string.no));
                                param15_tv.setTag(projectDetail.isIsOnlyAgent());
                                param16_tv.setText(projectDetail.getMainCompA());
                                param16_tv.setTag(projectDetail.getMainCompA());
                                param17_tv.setText(projectDetail.getMainCompB());
                                param17_tv.setTag(projectDetail.getMainCompB());

                                param18_tv.setTag(projectDetail.getBelongArea());
                                param19_tv.setText(projectDetail.getDeveloper());
                                param20_tv.setText(projectDetail.getDevContact());
                                param21_tv.setText(projectDetail.getDevType());
                                param21_tv.setTag(projectDetail.getDevType());
                                param22_tv.setText(projectDetail.getDevTel());
                                param23_tv.setText(projectDetail.getDevAddress());
                            }

                            if(projectBid != null){

                                fragment.projectBid = projectBid;
                                fragment.setBidResultFiles(bidAttachments);
                                fragment.setEdit(true);
                            }
                        }
                    });
        }
        String deptNo = getUserDeptNo();
        getSalesBranch(deptNo);
    }

    @OnClick({R.id.tv_customer, R.id.tv_save, R.id.tv_submit, R.id.build_attr_selector,
            R.id.province_selector, R.id.city_selector, R.id.area_selector, R.id.dxmzy_selector,
            R.id.param1_tv, R.id.param2_tv, R.id.param6_tv, R.id.param7_tv
            , R.id.param8_tv
            , R.id.param9_tv
            , R.id.param10_tv
            , R.id.param11_tv
            , R.id.param12_tv
            , R.id.param13_tv
            , R.id.param14_tv
            , R.id.param15_tv
            , R.id.param16_tv
            , R.id.param17_tv
            , R.id.param18_tv
            , R.id.param21_tv
            , R.id.tv_base_info
            , R.id.tv_other_info
            , R.id.bid_selector
    })
    public void onClick(View view) {
        DropDownWindow mWindow = null;
        switch (view.getId()) {
            case R.id.tv_customer:
                Intent intent = new Intent(this, CustomerLookActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.tv_save:
                saveProjectInfo("save");
                break;
            case R.id.tv_submit:
                if (isCrossRegional())
                    saveProjectInfo("submit");
                break;
            case R.id.build_attr_selector:
                mWindow = new DropDownWindow(mContext, view, (TextView) view, buildingCharacters, view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {

                        build_attr_selector.setText(buildingCharacters.get(p).getText());
                        build_attr_selector.setTag(buildingCharacters.get(p).getValue());
                        this.dismiss();
                    }
                };
                break;
            case R.id.province_selector:
                mWindow = new DropDownWindow(mContext, view, (TextView) view, provinces, view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {

                        Province province = provinces.get(p);
                        province_selector.setText(province.getProvinceName());
                        city_selector.setText("");
                        if (p != 0) {
                            local_branch.setText(province.getContractBranchName());
                            contractBranchSAPNo = province.getContractBranchSAPNo();
                            getCity(province.getProvinceCode());
                        }
                        this.dismiss();
                    }
                };
                break;
            case R.id.city_selector:
                mWindow = new DropDownWindow(mContext, view, (TextView) view, cities, view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {

                        City city = cities.get(p);
                        city_selector.setText(city.getCityName());
                        area_selector.setText("");
                        if (p != 0) {
                            local_branch.setText(city.getContractBranchName());
                            contractBranchSAPNo = city.getContractBranchSAPNo();
                            getArea(city.getProvinceCode(), city.getCityCode());
                        }
                        this.dismiss();
                    }
                };

                break;
            case R.id.area_selector:
                mWindow = new DropDownWindow(mContext, view, (TextView) view, districts, view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {

                        District area = districts.get(p);
                        area_selector.setText(area.getAreaName());
                        if (p != 0) {
                            local_branch.setText(area.getContractBranchName());
                            contractBranchSAPNo = area.getContractBranchSAPNo();
                        }
                        this.dismiss();
                    }
                };
                break;
            case R.id.dxmzy_selector:
                mWindow = new DropDownWindow(mContext, view, (TextView) view, dxmZies, view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        DxmZY dxmZY = dxmZies.get(p);
                        dxmzy_selector.setText(dxmZY.getUserName());
                        dxmzy_selector.setTag(dxmZY.getUserNo());
                        this.dismiss();
                    }
                };
                break;
            case R.id.param1_tv:
                if (dropDownList == null) return;
                mWindow = new DropDownWindow(mContext, view, (TextView) view, dropDownList.getProjectStateList(), view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        DropDown dropDown = dropDownList.getProjectStateList().get(p);
                        param1_tv.setText(dropDown.getText());
                        param1_tv.setTag(dropDown.getValue());
                        this.dismiss();
                    }
                };
                break;
            case R.id.param2_tv:
                if (dropDownList == null) return;
                mWindow = new DropDownWindow(mContext, view, (TextView) view, dropDownList.getProjectStageList(), view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        DropDown dropDown = dropDownList.getProjectStageList().get(p);
                        param2_tv.setText(dropDown.getText());
                        param2_tv.setTag(dropDown.getValue());
                        this.dismiss();
                    }
                };
                break;
            case R.id.param6_tv:
                if (dropDownList == null) return;
                mWindow = new DropDownWindow(mContext, view, (TextView) view, dropDownList.getMainEqType(), view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        DropDown dropDown = dropDownList.getMainEqType().get(p);
                        param6_tv.setText(dropDown.getText());
                        param6_tv.setTag(dropDown.getValue());
                        this.dismiss();
                    }
                };
                break;
            case R.id.param7_tv:
                openTime(param7_tv);
                break;
            case R.id.param8_tv:
                openTime(param8_tv);
                break;
            case R.id.param9_tv:
                openTime(param9_tv);
                break;
            case R.id.param10_tv:
                openTime(param10_tv);
                break;
            case R.id.param11_tv:
                openTime(param11_tv);
                break;
            case R.id.param12_tv:
                openTime(param12_tv);
                break;
            case R.id.param13_tv:
                if (dropDownList == null) return;
                mWindow = new DropDownWindow(mContext, view, (TextView) view, dropDownList.getHlAccountList(), view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        DropDown dropDown = dropDownList.getHlAccountList().get(p);
                        param13_tv.setText(dropDown.getText());
                        param13_tv.setTag(dropDown.getValue());
                        this.dismiss();
                    }
                };
                break;
            case R.id.param14_tv:
                if (dropDownList == null) return;
                mWindow = new DropDownWindow(mContext, view, (TextView) view, dropDownList.getInfoSourceList(), view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        DropDown dropDown = dropDownList.getInfoSourceList().get(p);
                        param14_tv.setText(dropDown.getText());
                        param14_tv.setTag(dropDown.getValue());
                        this.dismiss();
                    }
                };
                break;
            case R.id.param15_tv:
                if (dropDownList == null) return;
                mWindow = new DropDownWindow(mContext, view, (TextView) view, dropDownList.getIsOnlyAgent(), view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        DropDown dropDown = dropDownList.getIsOnlyAgent().get(p);
                        param15_tv.setText(dropDown.getText());
                        param15_tv.setTag(dropDown.getValue());
                        this.dismiss();
                    }
                };
                break;
            case R.id.param16_tv:
                if (dropDownList == null) return;
                mWindow = new DropDownWindow(mContext, view, (TextView) view, dropDownList.getMainCompList(), view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        DropDown dropDown = dropDownList.getMainCompList().get(p);
                        param16_tv.setText(dropDown.getText());
                        param16_tv.setTag(dropDown.getValue());
                        this.dismiss();
                    }
                };
                break;
            case R.id.param17_tv:
                if (dropDownList == null) return;
                mWindow = new DropDownWindow(mContext, view, (TextView) view, dropDownList.getMainCompList(), view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        DropDown dropDown = dropDownList.getMainCompList().get(p);
                        param17_tv.setText(dropDown.getText());
                        param17_tv.setTag(dropDown.getValue());
                        this.dismiss();
                    }
                };
                break;
            case R.id.param18_tv:
                if (dropDownList == null) return;
                mWindow = new DropDownWindow(mContext, view, (TextView) view, dropDownList.getBelongAreaList(), view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        BelongArea belongArea = dropDownList.getBelongAreaList().get(p);
                        param18_tv.setText(belongArea.getDeptName());
                        param18_tv.setTag(belongArea.getDeptNo());
                        this.dismiss();
                    }
                };
                break;
            case R.id.param21_tv:
                if (dropDownList == null) return;
                mWindow = new DropDownWindow(mContext, view, (TextView) view, dropDownList.getDevTypeList(), view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        DropDown dropDown = dropDownList.getDevTypeList().get(p);
                        param21_tv.setText(dropDown.getText());
                        param21_tv.setTag(dropDown.getValue());
                        this.dismiss();
                    }
                };
                break;
            case R.id.bid_selector:
                if (dropDownList == null) return;
                mWindow = new DropDownWindow(mContext, view, (TextView) view, dropDownList.getPurchaseTypeList(), view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        DropDown dropDown = dropDownList.getPurchaseTypeList().get(p);
                        bid_selector.setText(dropDown.getText());
                        bid_selector.setTag(dropDown.getValue());
                        if(purchaseType.equals(dropDown.getValue())){
                            fl_container.setVisibility(View.VISIBLE);
                        }else{
                            fl_container.setVisibility(View.GONE);
                        }
                        this.dismiss();
                    }
                };
                break;
            case R.id.tv_other_info:
                if (view.getTag() == null || (Integer) view.getTag() == 0) {
                    other_panel.setVisibility(View.VISIBLE);
                    view.setTag(1);
                    iv_close.setImageResource(R.drawable.open);
                } else {
                    other_panel.setVisibility(View.GONE);
                    view.setTag(0);
                    iv_close.setImageResource(R.drawable.close);
                }
                break;
        }
    }

    private Calendar cal = Calendar.getInstance();
    private TextView date_tv;

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            if (date_tv != null) {
                date_tv.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }
    };

    private void openTime(final TextView editText) {

        date_tv = editText;
        new DatePickerDialog(mContext, listener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private boolean isCrossRegional() {

        if (TextUtils.isEmpty(contractBranchSAPNo) || TextUtils.isEmpty(saleBranchNo))
            return true;
        if (!contractBranchSAPNo.equals(saleBranchNo) && !dxm_panel.isShown()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.tips));
            builder.setMessage(getString(R.string.toast4));
            builder.setNegativeButton(getString(R.string.cancel), null);
            builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveProjectInfo("submit");
                }
            });
            builder.show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            customer = (Customer) data.getSerializableExtra(Constants.DATA1);
            tv_customer.setText(customer.getCustomerName());
        }
    }

    private void initBuildingCharacter() {

        baseResponseObservable = HttpFactory.getApiService().getBuildingCharacterList();
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BasicResponse<List<BuildingCharacter>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<List<BuildingCharacter>>>(this, true) {

                    @Override
                    public void onSuccess(BasicResponse<List<BuildingCharacter>> response) {

                        buildingCharacters = response.getData();
                        if (buildingCharacters != null && buildingCharacters.size() > 0) {

                            if (project != null) {
                                build_attr_selector.setText(project.getBuildingCharacterName());
                                build_attr_selector.setTag(project.getBuildingCharacter());
                            }
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    private void initDxmzy() {

        baseResponseObservable = HttpFactory.getApiService().getDxmzyUserList();
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BasicResponse<List<DxmZY>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<List<DxmZY>>>(this, true) {

                    @Override
                    public void onSuccess(BasicResponse<List<DxmZY>> response) {

                        dxmZies = response.getData();
                        if (dxmZies != null && dxmZies.size() > 0) {

                            if (project != null) {
                                dxmzy_selector.setText(project.getDxmzyUserName());
                                dxmzy_selector.setTag(project.getDxmzyUserNo());
                            }
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    /***
     * 查询项目编号  新建
     */
    private void getProjectNo() {

        baseResponseObservable = HttpFactory.getApiService().getProjectNo();
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BasicResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {

                    @Override
                    public void onSuccess(BasicResponse response) {

                        String pno = ((Map) response.getData()).get("ProjectNo").toString();
                        pno = pno.replace("\"", "");
                        project_no.setText(pno);
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    /***
     * 查询销售分公司  新建
     */
    private void getSalesBranch(String DeptNo) {

        Map params = new HashMap();
        params.put("DeptNo", DeptNo);

        baseResponseObservable = HttpFactory.getApiService().getSaleBranchNoByDeptNo(params);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BasicResponse<Map>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<Map>>(this, false) {

                    @Override
                    public void onSuccess(BasicResponse<Map> response) {

                        saleBranchNo = response.getData().get("SaleBranchNo").toString();
                        saleBranchNo = saleBranchNo.replace("\"", "");
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    private void getProvince() {

        baseResponseObservable = HttpFactory.getApiService().getProvinceList();
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BasicResponse<List<Province>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<List<Province>>>(this, false) {

                    @Override
                    public void onSuccess(BasicResponse<List<Province>> response) {

                        provinces = response.getData();
                        if (provinces != null && provinces.size() > 0) {
                            //   province_selector.setItems(provinces);
                            if (project != null) {
                                province_selector.setText(project.getProjectAdd_Province());
                                getCity(project.getProjectAdd_Province());
                            } else {
                                province_selector.setText(provinces.get(1).getProvinceName());
                                local_branch.setText(provinces.get(1).getContractBranchName());
                                contractBranchSAPNo = provinces.get(1).getContractBranchSAPNo();
                                getCity(provinces.get(1).getProvinceCode());
                            }
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    private void getCity(String ProvinceCode) {

        Map params = new HashMap();
        params.put("ProvinceCode", ProvinceCode);
        params.put("ProjectType", project_type);

        baseResponseObservable = HttpFactory.getApiService().getCityListByProvinceCode(params);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BasicResponse<CityBean>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<CityBean>>(this, false) {

                    @Override
                    public void onSuccess(BasicResponse<CityBean> response) {

                        cities = response.getData().getCityList();
                        if (cities != null && cities.size() > 0) {
                            //city_selector.setItems(cities);
                            if (project != null && firstCity) {
                                city_selector.setText(project.getProjectAdd_City());
                                firstCity = false;
                                getArea(project.getProjectAdd_Province(), project.getProjectAdd_City());
                            } else {
                                city_selector.setText(cities.get(1).getCityName());
                                getArea(cities.get(1).getProvinceCode(), cities.get(1).getCityCode());
                            }
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    private void getArea(String ProvinceCode, String CityCode) {

        Map params = new HashMap();
        params.put("ProvinceCode", ProvinceCode);
        params.put("CityCode", CityCode);
        params.put("ProjectType", project_type);

        baseResponseObservable = HttpFactory.getApiService().getAreaList(params);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BasicResponse<DistrictBean>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<DistrictBean>>(this, false) {

                    @Override
                    public void onSuccess(BasicResponse<DistrictBean> response) {

                        districts = response.getData().getAreaList();
                        if (districts != null && districts.size() > 0) {
                            // area_selector.setItems(districts);
                            if (project != null && firstArea) {
                                area_selector.setText(project.getProjectAdd_Area());
                                firstArea = false;
                            } else {
                                area_selector.setText(districts.get(1).getAreaName());
                            }
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    private void getOther() {

        baseResponseObservable = HttpFactory.getApiService().getProjectDetailDropDownList();
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BasicResponse<GetProjectDetailDropDownList>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<GetProjectDetailDropDownList>>(this, false) {

                    @Override
                    public void onSuccess(BasicResponse<GetProjectDetailDropDownList> response) {
                        dropDownList = response.getData();
                        if (projectDetail != null) {
                            for (BelongArea belongArea : dropDownList.getBelongAreaList()) {
                                if (belongArea.getDeptNo().equals(projectDetail.getBelongArea())) {
                                    param18_tv.setText(belongArea.getDeptName());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    private void saveProjectInfo(final String OperateType) {

        String pno = project_no.getText().toString();
        String c_name = tv_customer.getText().toString();
        String buildingCharacterName = build_attr_selector.getText().toString();
        String buildingCharacterNo = build_attr_selector.getTag() == null ? "" : build_attr_selector.getTag().toString();
        String p_name = project_name.getText().toString();
        String province_str = province_selector.getText().toString();
        String city_str = city_selector.getText().toString();
        String area_str = area_selector.getText().toString();
        String dxmzyText = dxmzy_selector.getText().toString();
        if (TextUtils.isEmpty(bid_selector.getText())) {
            ToastUtil.showLong("请填写是否招投标");
            return;
        }
        if (dxm_panel.isShown()) {
            if (isNull(pno, c_name, dxmzyText, buildingCharacterName, p_name, province_str, city_str, area_str)) {
                ToastUtil.showLong(getString(R.string.toast2));
                return;
            }
        } else {
            if (isNull(pno, c_name, buildingCharacterName, p_name, province_str)) {
                ToastUtil.showLong(getString(R.string.toast2));
                return;
            }
        }

        if(fl_container.isShown() && !fragment.isRequiredCheck(BidInfoFragment.ALL_CHECK)){
            return;
        }

        JsonObject json = new JsonObject();
        json.addProperty("operateType", OperateType);
        json.addProperty("UserId", getUserId());
        json.addProperty("UserNo", getUserNo());
        json.addProperty("UserName", getUserName());
        json.addProperty("DeptName", getUserDeptName());
        json.addProperty("RoleNo", getRoleNo());
        json.addProperty("payTypeParam", "");
        json.addProperty("ContractChangeGuid", "");

        JsonObject projectJson = new JsonObject();
        if (projectGuid != null) {
            projectJson.addProperty("ProjectGuid", projectGuid);
        }
        projectJson.addProperty("ProjectNo", pno);
        projectJson.addProperty("ProjectName", p_name);
        projectJson.addProperty("ProjectType", project_type);
        projectJson.addProperty("ProjectAdd_Province", province_str);
        projectJson.addProperty("ProjectAdd_City", city_selector.getText().toString());
        projectJson.addProperty("ProjectAdd_Area", area_selector.getText().toString());
        projectJson.addProperty("ProjectAdd_Other", address_other.getText().toString());
        projectJson.addProperty("BranchNo", getUserDeptNo());
        projectJson.addProperty("CustomerId", customer.getGuid());
        projectJson.addProperty("CustomerName", c_name);
        projectJson.addProperty("CustomerContact", customer.getCustomerContact());
        projectJson.addProperty("CustomerPhoneNum", customer.getPhoneNumber());
        projectJson.addProperty("CustomerAddrees", customer.getCustomerAdd());
        projectJson.addProperty("ProjectAreaName", contractBranchSAPNo);
        projectJson.addProperty("BuildingCharacter", buildingCharacterNo);
        projectJson.addProperty("DxmzyUserNo", dxmzy_selector.getTag() == null ? "" : dxmzy_selector.getTag().toString());
        projectJson.addProperty("DxmzySalesManager", dxmzySalesManager.getText().toString());
        if (InstanceGuid != null) {
            projectJson.addProperty("InstanceGuid", InstanceGuid);
        }
        projectJson.addProperty("IsStandardPayType", "true");
        projectJson.addProperty("PurchaseType", bid_selector.getTag() == null ? "" : bid_selector.getTag().toString());

        JsonObject projectDetailJson = new JsonObject();
        /***新增字段**/
        projectDetailJson.addProperty("ProjectState", param1_tv.getTag() == null ? "" : param1_tv.getTag().toString());
        projectDetailJson.addProperty("ProjectStage", param2_tv.getTag() == null ? "" : param2_tv.getTag().toString());
        projectDetailJson.addProperty("EleCount", param3_tv.getText().toString());
        projectDetailJson.addProperty("EscCount", param4_tv.getText().toString());
        projectDetailJson.addProperty("TotalBidCost", param5_tv.getText().toString());
        projectDetailJson.addProperty("MainEqType", param6_tv.getTag() == null ? "" : param6_tv.getTag().toString());
        projectDetailJson.addProperty("StartBidDate", param7_tv.getText().toString());
        projectDetailJson.addProperty("EndBidDate", param8_tv.getText().toString());
        projectDetailJson.addProperty("ExSignDate", param9_tv.getText().toString());
        projectDetailJson.addProperty("ExPayDate", param10_tv.getText().toString());
        projectDetailJson.addProperty("ExDeliveryDate", param11_tv.getText().toString());
        projectDetailJson.addProperty("ExEQSDate", param12_tv.getText().toString());

        projectDetailJson.addProperty("HLAccount", param13_tv.getTag() == null ? "" : param13_tv.getTag().toString());
        projectDetailJson.addProperty("InfoSource", param14_tv.getTag() == null ? "" : param14_tv.getTag().toString());
        projectDetailJson.addProperty("IsOnlyAgent", param15_tv.getTag() == null ? "false" : param15_tv.getTag().toString().toLowerCase());
        projectDetailJson.addProperty("MainCompA", param16_tv.getTag() == null ? "" : param16_tv.getTag().toString());
        projectDetailJson.addProperty("MainCompB", param17_tv.getTag() == null ? "" : param17_tv.getTag().toString());
        projectDetailJson.addProperty("BelongArea", param18_tv.getTag() == null ? "" : param18_tv.getTag().toString());

        projectDetailJson.addProperty("Developer", param19_tv.getText().toString());
        projectDetailJson.addProperty("DevContact", param20_tv.getText().toString());
        projectDetailJson.addProperty("DevType", param21_tv.getTag() == null ? "" : param21_tv.getTag().toString());
        projectDetailJson.addProperty("DevTel", param22_tv.getText().toString());
        projectDetailJson.addProperty("DevAddress", param23_tv.getText().toString());
        if (!TextUtils.isEmpty(DetailGuid)) {
            projectDetailJson.addProperty("DetailGuid", DetailGuid);
        }

        json.add("project", projectJson);
        json.add("projectDetail", projectDetailJson);
        json.add("projectInfo", null);
        json.add("projectBid", fragment.projectBid == null ? null : new Gson().toJsonTree(fragment.projectBid));
        json.add("drawingFileList", new Gson().toJsonTree(fragment.getBidResultFiles()));

        baseResponseObservable = HttpFactory.getApiService().saveProject(json.toString());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BasicResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {

                        if ("save".equals(OperateType)) {
                            ToastUtil.showLong(getString(R.string.saved));
                            setResult(1);
                            finish();
                        } else {
                            ToastUtil.showLong(getString(R.string.submitted));
                            showProgress();
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    dismissProgress();
                                    setResult(1);
                                    finish();
                                }
                            }, 3000);
                        }
                    }
                });
    }


    private boolean isNull(String... value) {

        for (String s : value) {
            if (TextUtils.isEmpty(s) || "请选择".equals(s) || "--请选择--".equals(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取当前屏幕内容的高度
        //int screenHeight = getWindow().getDecorView().getHeight();
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                //获取View可见区域的bottom
                Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                if (bottom != 0 && oldBottom != 0 && bottom - rect.bottom <= 0) {
                    //T.showToast(EditReportActivity.this,"隐藏");
                } else {
                    //T.showToast(EditReportActivity.this,"弹出");
                }
            }
        });
    }
}
