package com.zxtech.ecs.ui.home.quote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.event.EventAction;
import com.zxtech.ecs.model.ContractDeliveryPoints;
import com.zxtech.ecs.model.DropDown;
import com.zxtech.ecs.model.InstallUnit;
import com.zxtech.ecs.model.ProjectBid;
import com.zxtech.ecs.model.ProjectDropDown;
import com.zxtech.ecs.model.ProjectInfo;
import com.zxtech.ecs.model.ProjectProductInfo;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.BasicResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.bid.BidInfoFragment;
import com.zxtech.ecs.ui.home.contractchange.ChangeSelectedDialogFragment;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.DropDownWindow;
import com.zxtech.gks.model.bean.SaveResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/6/22.
 */

public class ProjectInfoFragment extends BaseFragment implements BidBondDialogFragment.BidBondCallBack, PerformanceBondDialogFragment.PerformanceBondCallBack
        , AgentDialogFragment.AgentCallBack, PaymentMethodDialogFragment.PaymentMethodCallBack {

    @BindView(R.id.prject_no_tv)
    TextView prject_no_tv;
    @BindView(R.id.saleman_tv)
    TextView saleman_tv;
    @BindView(R.id.sale_branch_tv)
    TextView sale_branch_tv;
    @BindView(R.id.prject_branch_tv)
    TextView prject_branch_tv;
    @BindView(R.id.prject_name_tv)
    TextView prject_name_tv;
    @BindView(R.id.customer_tv)
    TextView customer_tv;
    @BindView(R.id.fieldProvince)
    TextView fieldProvince; //省
    @BindView(R.id.fieldCity)
    TextView fieldCity;
    @BindView(R.id.fieldArea)
    TextView fieldArea;
    @BindView(R.id.fieldOther)
    TextView fieldOther;
    @BindView(R.id.eq_contract_tv)
    TextView eq_contract_tv;//设备合同
    @BindView(R.id.is_bidding_tv)
    TextView is_bidding_tv;//
    @BindView(R.id.free_insurance_cb)
    CheckBox free_insurance_cb;
    @BindView(R.id.agent_name_tv)
    TextView agent_name_tv;
    @BindView(R.id.transportation_tv)
    TextView transportation_tv;
    @BindView(R.id.install_contract_tv)
    TextView install_contract_tv;
    @BindView(R.id.install_company_tv)
    TextView install_company_tv;//安装单位
    @BindView(R.id.install_rate_tv)
    TextView install_rate_tv;//安装税点
    @BindView(R.id.delivery_address_tv)
    TextView delivery_address_tv; //交货地点
    @BindView(R.id.bid_fee_et)
    EditText bid_fee_et;//中标手续费
    @BindView(R.id.agent_search_iv)
    ImageView agent_search_iv;
    @BindView(R.id.explain_et)
    EditText explain_et;//价审说明
    @BindView(R.id.explain_layout)
    LinearLayout explain_layout;
    @BindView(R.id.save_btn)
    TextView save_btn;
    @BindView(R.id.bid_info_fl)
    FrameLayout bid_info_fl;

    private String projectGuid;
    private String payTypeParam;
    private ProjectDropDown projectDropDown;

    private ProjectInfo projectInfo;
    private ProjectProductInfo.OtherInfo otherInfo;
    private BidInfoFragment bidInfoFragment;

    public static final String QUOTE_ACTIVITY = "quote";
    public static final String CONTRACT_CHANGE_ACTIVITY = "change";
    private boolean isEdit;

    public static ProjectInfoFragment newInstance(String projectGuid, String projectNo, String contractId, String contractType, String activity, boolean isEdit) {
        Bundle args = new Bundle();
        ProjectInfoFragment fragment = new ProjectInfoFragment();
        args.putString("projectGuid", projectGuid);
        args.putString("projectNo", projectNo);
        args.putString("contractId", contractId);
        args.putString("contractType", contractType);
        args.putString("activity", activity);
        args.putBoolean("isEdit", isEdit); //界面是否允许编辑
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_project_info;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        initData();
        initView();


    }

    private void initView() {
        String sourceActivity = getArguments().getString("activity");
        if (CONTRACT_CHANGE_ACTIVITY.equals(sourceActivity)) {
            explain_layout.setVisibility(View.GONE);
        }
        if (!isEdit) {
            eq_contract_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            eq_contract_tv.setEnabled(false);
            free_insurance_cb.setEnabled(false);
            is_bidding_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            is_bidding_tv.setEnabled(false);
            agent_search_iv.setEnabled(false);
            agent_name_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            agent_name_tv.setEnabled(false);
            transportation_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            transportation_tv.setEnabled(false);
            delivery_address_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            delivery_address_tv.setEnabled(false);
            install_contract_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            install_contract_tv.setEnabled(false);
            install_rate_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            install_rate_tv.setEnabled(false);
            install_company_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            install_company_tv.setEnabled(false);
            install_rate_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
            install_rate_tv.setEnabled(false);
            explain_et.setBackgroundResource(R.drawable.bg_round_border_disable);
            explain_et.setEnabled(false);
            save_btn.setVisibility(View.GONE);
        } else {
            String contractType = getArguments().getString("contractType");
            if (!TextUtils.isEmpty(contractType) && (ChangeSelectedDialogFragment.CONTRACT_CHANGE_TYPE_BUSINESS.equals(contractType) || ChangeSelectedDialogFragment.CONTRACT_CHANGE_TYPE_COMBINATION.equals(contractType))) {
                eq_contract_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
                eq_contract_tv.setEnabled(false);
                free_insurance_cb.setEnabled(false);
                install_contract_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
                install_contract_tv.setEnabled(false);
            }
        }

        bidInfoFragment = BidInfoFragment.newInstance();
        bidInfoFragment.setEdit(this.isEdit);
        loadRootFragment(R.id.bid_info_fl, bidInfoFragment);
    }

    @OnClick({R.id.eq_contract_tv, R.id.is_bidding_tv, R.id.transportation_tv, R.id.install_contract_tv, R.id.install_rate_tv, R.id.delivery_address_tv, R.id.save_btn, R.id.install_company_tv
            , R.id.bid_bond_iv, R.id.performance_bond_iv, R.id.payment_method_iv, R.id.agent_search_iv})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            case R.id.eq_contract_tv: //设备合同
                dropDownSubSystem(view, projectDropDown.getEqContractTypeList());
                break;
            case R.id.is_bidding_tv: //是否招投标
                dropDownSubSystem(view, projectDropDown.getPurchaseTypeList());
                break;
            case R.id.transportation_tv: //运输方式
                dropDownSubSystem(view, projectDropDown.getModeOfTransportList());
                break;
            case R.id.install_contract_tv://安装合同
                dropDownSubSystem(view, projectDropDown.getInContractTypeList(projectInfo.getEqContractType()));
                break;
            case R.id.install_rate_tv://安装税点
                dropDownSubSystem(view, projectDropDown.getContractPartyList());
                break;
            case R.id.delivery_address_tv://交货地点
                requestDelivery(view, fieldProvince.getText().toString(), fieldCity.getText().toString(), false);
                break;
            case R.id.install_company_tv://安装单位
                requestUnit(install_company_tv, projectInfo.getAgent(), false);
                break;
            case R.id.save_btn:

                projectInfo.setAgentName(agent_name_tv.getText().toString());
                projectInfo.setBidPoundage(TextUtils.isEmpty(bid_fee_et.getText().toString()) ? null : bid_fee_et.getText().toString());
                projectInfo.setIsFreeInsurance(free_insurance_cb.isChecked());
                otherInfo.setRemarks(explain_et.getText().toString());
                if (checkProject()) {
                    saveProject(false);
                }

                break;
            case R.id.bid_bond_iv: //投标保证金
                BidBondDialogFragment bidBondDialogFragment = BidBondDialogFragment.newInstance();
                bidBondDialogFragment.callBack = this;
                bidBondDialogFragment.setOtherInfo(otherInfo);
                bidBondDialogFragment.setEdit(this.isEdit);
                bidBondDialogFragment.show(getActivity().getFragmentManager(), "bid_bond");
                break;
            case R.id.performance_bond_iv://履约保证金
                PerformanceBondDialogFragment performanceBondDialogFragment = PerformanceBondDialogFragment.newInstance();
                performanceBondDialogFragment.callBack = this;
                performanceBondDialogFragment.setOtherInfo(otherInfo);
                performanceBondDialogFragment.setEdit(this.isEdit);
                performanceBondDialogFragment.show(getActivity().getFragmentManager(), "performance_bond");
                break;
            case R.id.payment_method_iv://付款方式
                PaymentMethodDialogFragment paymentMethodDialogFragment = PaymentMethodDialogFragment.newInstance();
                paymentMethodDialogFragment.setProjectGuid(this.projectGuid);
                paymentMethodDialogFragment.setStandardType(projectInfo.getIsStandardPayType());
                paymentMethodDialogFragment.setPayTypeParam(this.payTypeParam);
                paymentMethodDialogFragment.setEdit(this.isEdit);
                paymentMethodDialogFragment.setInstallContract(!TextUtils.isEmpty(projectInfo.getInContractType()) && !projectInfo.getInContractType().equals("无"));
                paymentMethodDialogFragment.callBack = this;
                paymentMethodDialogFragment.show(getActivity().getFragmentManager(), "payment_method");
                break;
            case R.id.agent_search_iv:
                AgentDialogFragment agentDialogFragment = AgentDialogFragment.newInstance();
                if (projectInfo != null) {
                    agentDialogFragment.setOrgNo(projectInfo.getBranchNo());
                    agentDialogFragment.setOrgName(projectInfo.getBranchName());
                }
                agentDialogFragment.callBack = this;
                agentDialogFragment.show(getActivity().getFragmentManager(), "agent_search");
                break;
        }
    }

    public boolean checkProject() {
        if ("招投标".equals(projectInfo.getPurchaseType())) {
//            //如果是招投标 ，中标手续费不能为空
//            if (TextUtils.isEmpty(bid_fee_et.getText())) {
//                ToastUtil.showLong("招投标，中标手续费不能为空");
//                return false;
//            } else if (BidBondDialogFragment.FALSE.equals(otherInfo.getIsBid())) {
//                ToastUtil.showLong("招投标，投标保证金不能为空");
//                return false;
//            } else if (BidBondDialogFragment.FALSE.equals(otherInfo.getIsPerformanceBond())) {
//                ToastUtil.showLong("招投标，履约保证金不能为空");
//                return false;
//            }
           if (!bidInfoFragment.isRequiredCheck(bidInfoFragment.ALL_CHECK)) {
               return false;
           }
        }
        //安装合同无 - 安装单位必填项
        if (!TextUtils.isEmpty(projectInfo.getInContractType()) && projectInfo.getInContractType().equals("无") && TextUtils.isEmpty(projectInfo.getInstallationUnit())) {
            ToastUtil.showLong("请选择安装单位");
            return false;
        }
        //税点必填项
        if (!TextUtils.isEmpty(projectInfo.getInContractType()) && !projectInfo.getInContractType().equals("无") && TextUtils.isEmpty(install_rate_tv.getText())) {
            ToastUtil.showLong("请选择安装税点");
            return false;
        }

        if (!TextUtils.isEmpty(projectInfo.getEqContractType()) && !TextUtils.isEmpty(projectInfo.getInContractType()) && (projectInfo.getEqContractType().equals("代理") || projectInfo.getEqContractType().equals("经销"))
                && (projectInfo.getInContractType().equals("代理") || projectInfo.getInContractType().equals("经销") || projectInfo.getInContractType().equals("无"))
                && TextUtils.isEmpty(agent_name_tv.getText())) {
            ToastUtil.showLong("请填写代理商");
            return false;
        }
        return true;
    }

    private void submitPriceApproval() {
        HashMap<String, String> map = new HashMap<>();
        map.put("projectGuid", this.projectGuid);
        map.put("userId", getUserId());
        map.put("userNo", getUserNo());
        map.put("userName", getUserName());
        baseResponseObservable = HttpFactory.getApiService().savePriceReview(map);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BasicResponse<SaveResult>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<SaveResult>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BasicResponse<SaveResult> response) {
                        getActivity().setResult(1002);
                        getActivity().finish();
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventAction<String> event) {
        if (event.getCode() == EventAction.QUOTE_SUBMIT) {
            if (checkProject()) {
                saveProject(true);
            }
        }
    }


    private void saveProject(final boolean isSubmit) {

        JsonObject json = new JsonObject();
        json.addProperty("operateType", "save");
        json.addProperty("ContractChangeGuid", getArguments().getString("contractId"));
        json.addProperty("UserId", getUserId());
        json.addProperty("UserNo", getUserNo());
        json.addProperty("UserName", getUserName());
        json.addProperty("DeptName", getUserDeptName());
        json.addProperty("RoleNo", getRoleNo());
        json.addProperty("payTypeParam", this.payTypeParam);
        Gson gson = new Gson();
        otherInfo.setLetterOfIndemnityTerm(null);
        otherInfo.setPaymentType(this.payTypeParam);
        json.add("project", gson.fromJson(gson.toJson(projectInfo), JsonObject.class));
        json.add("projectInfo", gson.fromJson(gson.toJson(otherInfo), JsonObject.class));
        json.add("projectBid", gson.fromJson(gson.toJson(bidInfoFragment.projectBid), JsonObject.class));

        baseResponseObservable = HttpFactory.getApiService().saveProject(json.toString());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BasicResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        EventBus.getDefault().post(new EventAction(EventAction.CONTRACT_CHANGE));
                        if (isSubmit) {
                            submitPriceApproval();
                        } else {
                            ToastUtil.showLong(getString(R.string.saved));
                        }
                    }
                });
    }


    private void requestUnit(final View view, final String partnerNumber, final boolean init) {
        baseResponseObservable = HttpFactory.getApiService().getInstallationUnitList(partnerNumber);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<InstallUnit>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<InstallUnit>>>(getActivity(), init ? false : true) {
                    @Override
                    public void onSuccess(BaseResponse<List<InstallUnit>> response) {
                        final List<InstallUnit> data = response.getData();
                        if (init) {
                            for (int i = 0; i < data.size(); i++) {
                                if (projectInfo.getInstallationUnit() != null && projectInfo.getInstallationUnit().equals(data.get(i).getUnitSAPCode())) {
                                    ((TextView) view).setText(data.get(i).getUnitName());
                                    return;
                                }
                            }
                        } else {
                            dropDownUnit(view, data);
                        }

                    }
                });
    }

    private void dropDownUnit(final View view, final List<InstallUnit> data) {
        new DropDownWindow(mContext, view, (TextView) view, data, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                ((TextView) view).setText(data.get(p).getUnitName());
                projectInfo.setInstallationUnit(data.get(p).getUnitSAPCode());
            }
        };
    }

    // 交货地点
    private void requestDelivery(final View view, String province, String city, final boolean init) {
        if (TextUtils.isEmpty(province) || TextUtils.isEmpty(city)) {
            return;
        }
        baseResponseObservable = HttpFactory.getApiService().getCityList(province, city);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<ContractDeliveryPoints>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<ContractDeliveryPoints>>(getActivity(), init ? false : true) {
                    @Override
                    public void onSuccess(BaseResponse<ContractDeliveryPoints> response) {
                        List<ContractDeliveryPoints.DeliveryPointsListBean> data = response.getData().getDeliveryPointsList();

                        if (init) {
                            for (int i = 0; i < data.size(); i++) {
                                if (projectInfo.getPlaceOfDelivery() != null && projectInfo.getPlaceOfDelivery().equals(data.get(i).getDeliveryPpointsSAPCode())) {
                                    ((TextView) view).setText(data.get(i).getDeliveryPpoints());
                                    EventBus.getDefault().post(new EventAction(EventAction.CONTRACT_CHANGE_PLACE, projectInfo.getPlaceOfDelivery()));
                                    return;
                                }
                            }
                        } else {
                            dropDownDelivery(view, data);
                        }
                    }
                });
    }

    private void dropDownDelivery(final View view, final List<ContractDeliveryPoints.DeliveryPointsListBean> list) {
        new DropDownWindow(mContext, view, (TextView) view, list, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                ((TextView) view).setText(list.get(p).getDeliveryPpoints());
                projectInfo.setPlaceOfDelivery(list.get(p).getDeliveryPpointsSAPCode());
                EventBus.getDefault().post(new EventAction(EventAction.CONTRACT_CHANGE_PLACE, list.get(p).getDeliveryPpointsSAPCode()));
                this.dismiss();
            }
        };
    }


    private void initData() {

        if (isAdded()) {
            projectGuid = getArguments().getString("projectGuid");
            isEdit = getArguments().getBoolean("isEdit");

            baseResponseObservable = HttpFactory.getApiService().getProjectProductInfo(projectGuid);
            baseResponseObservable
                    .compose(this.bindToLifecycle())
                    .compose(RxHelper.<BaseResponse<ProjectProductInfo>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<ProjectProductInfo>>(getActivity(), true) {
                        @Override
                        public void onSuccess(BaseResponse<ProjectProductInfo> response) {

                            projectInfo = response.getData().getReturnProject();
                            otherInfo = (response.getData().getProjectInfo() == null ? new ProjectProductInfo.OtherInfo() : response.getData().getProjectInfo());
                            otherInfo.setLetterOfIndemnityTermNotMap(otherInfo.getLetterOfIndemnityTerm());
                            if (projectInfo.getIsStandardPayType()) {
                                payTypeParam = response.getData().getPayTypeParam();
                            }else{
                                payTypeParam = otherInfo.getPaymentType();
                            }

                            projectInfo.setAgentName(response.getData().getAgentName());
                            if (TextUtils.isEmpty(projectInfo.getEqContractType())) {
                                //默认直销
                                projectInfo.setEqContractType("直销");
                            }
                            prject_no_tv.setText(projectInfo.getProjectNo());
                            saleman_tv.setText(projectInfo.getSalesmanUserName());
                            sale_branch_tv.setText(projectInfo.getBranchName());
                            prject_branch_tv.setText(projectInfo.getProjectAreaNameStr());
                            prject_name_tv.setText(projectInfo.getProjectName());
                            customer_tv.setText(projectInfo.getCustomerName());
                            fieldProvince.setText(projectInfo.getProjectAdd_Province());
                            fieldCity.setText(projectInfo.getProjectAdd_City());
                            fieldArea.setText(projectInfo.getProjectAdd_Area());
                            fieldOther.setText(projectInfo.getProjectAdd_Other());
                            eq_contract_tv.setText(projectInfo.getEqContractType());
                            bid_fee_et.setText(projectInfo.getBidPoundage());
                            free_insurance_cb.setChecked(projectInfo.isIsFreeInsurance());
                            agent_name_tv.setText(projectInfo.getAgentName());
                            is_bidding_tv.setText(projectInfo.getPurchaseType());
                            explain_et.setText(otherInfo.getRemarks());
                            transportation_tv.setText(projectInfo.getModeOfTransportText());

                            if (!TextUtils.isEmpty(projectInfo.getPlaceOfDelivery())) {
                                requestDelivery(delivery_address_tv, projectInfo.getProjectAdd_Province(), projectInfo.getProjectAdd_City(), true);
                            }
                            initDropDown();

                            if (!TextUtils.isEmpty(projectInfo.getAgent())) {
                                requestUnit(install_company_tv, projectInfo.getAgent(), true);
                            } else {
                                install_company_tv.setEnabled(false);
                                install_company_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
                            }
                            bidInfoFragment.setProjectBid(response.getData().getProjectBid()== null ? new ProjectBid() : response.getData().getProjectBid());
                            bidInfoFragment.setBidResultFiles(response.getData().getDrawingFileList());

                            eventChange(is_bidding_tv, projectInfo.getPurchaseType());
                            eventChange(eq_contract_tv, projectInfo.getEqContractType());
                            eventChange(install_contract_tv, projectInfo.getInContractType());
                            eventChange(transportation_tv, projectInfo.getModeOfTransport());

                        }

                        @Override
                        public void onFail() {
                            getActivity().finish();
                        }
                    });
        }
    }

    private void initDropDown() {

        baseResponseObservable = HttpFactory.getApiService().getProjectDropDownList();
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<ProjectDropDown>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<ProjectDropDown>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<ProjectDropDown> response) {
                        projectDropDown = response.getData();

                        List<DropDown> contractPartyList = projectDropDown.getContractPartyList();
                        for (int i = 0; i < contractPartyList.size(); i++) {
                            if (projectInfo.getContractParty() != null && projectInfo.getContractParty().equals(contractPartyList.get(i).getValue())) {
                                install_rate_tv.setText(contractPartyList.get(i).getText());
                                break;
                            }
                        }

                        List<DropDown> inContractTypeList = projectDropDown.getInContractTypeList();
                        for (int i = 0; i < inContractTypeList.size(); i++) {
                            if (projectInfo.getInContractType() != null && projectInfo.getInContractType().equals(inContractTypeList.get(i).getValue())) {
                                install_contract_tv.setText(inContractTypeList.get(i).getText());
                                break;
                            }
                        }


                    }

                });
    }

    protected void dropDownSubSystem(final View view, final List<DropDown> list) {

        if (list == null) {
            return;
        }
        final TextView textView = (TextView) view;


        DropDownWindow mWindow = new DropDownWindow(mContext, view, textView, list, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                String text = list.get(p).getText();
                String value = list.get(p).getValue();
                textView.setText(text);
                textView.setTag(value);
                setProjectValue(view, value);
                eventChange(view, value);
            }

        };
    }

    private void setProjectValue(View view, String value) {

        switch (view.getId()) {
            case R.id.is_bidding_tv:
                projectInfo.setPurchaseType(value);
                break;
            case R.id.install_rate_tv:
                projectInfo.setContractParty(value);
                break;
            case R.id.install_contract_tv:
                projectInfo.setInContractType(value);
                break;
            case R.id.eq_contract_tv:
                projectInfo.setEqContractType(value);
                break;
            case R.id.transportation_tv:
                projectInfo.setModeOfTransport(value);
                break;
        }
    }

    private void eventChange(View view, String value) {
        switch (view.getId()) {
            case R.id.is_bidding_tv: //招投标为“招投标” 启用，其他形式禁用
                if ("招投标".equals(value)) {
                    bid_fee_et.setBackgroundResource(R.drawable.bg_round_border);
                    bid_fee_et.setEnabled(true);
                    bid_info_fl.setVisibility(View.VISIBLE);
                } else {
                    bid_fee_et.setText(null);
                    bid_fee_et.setBackgroundResource(R.drawable.bg_round_border_disable);
                    bid_fee_et.setEnabled(false);
                    bid_info_fl.setVisibility(View.GONE);
                }
                break;
            case R.id.eq_contract_tv:
                if ("直销".equals(value)) { // 代理商文本框内容清空（包括代理商名称、代理商编号、代理商联系人）
                    install_contract_tv.setText("直销");
                    projectInfo.setInContractType("直销");

                    agent_name_tv.setText(null);
                    agent_name_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
                    projectInfo.setAgentName(null);
                    projectInfo.setAgentContact(null);
                    projectInfo.setAgent(null);
                    //清空安装单位
                    install_company_tv.setText(null);
                    install_company_tv.setEnabled(false);
                    install_company_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
                    projectInfo.setInstallationUnit(null);

                    agent_search_iv.setVisibility(View.GONE);
                } else {
                    agent_name_tv.setBackgroundResource(R.drawable.bg_round_border);
                    agent_search_iv.setVisibility(View.VISIBLE);
                    //设备合同选择经销，且安装合同选择无 "是否含免保"复选框状态为未选中，且可编辑
                    if ("经销".equals(value) && !TextUtils.isEmpty(projectInfo.getInContractType()) && projectInfo.getInContractType().equals("无")) {
                        free_insurance_cb.setChecked(false);
                        free_insurance_cb.setEnabled(true);
                    } else {
                        free_insurance_cb.setChecked(true);
                        free_insurance_cb.setEnabled(false);
                    }

                    if (install_contract_tv.getText().toString().equals("直销")){
                        install_contract_tv.setText(null);
                        projectInfo.setInContractType(null);
                    }
                }
                break;
            case R.id.install_contract_tv:
                if ("无".equals(value)) {
                    install_rate_tv.setText("请选择");
                    install_rate_tv.setTag(null);
                    install_rate_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
                    install_rate_tv.setEnabled(false);
                } else {
                    install_rate_tv.setBackgroundResource(R.drawable.bg_round_border);
                    install_rate_tv.setEnabled(true);
                }
                break;
            case R.id.transportation_tv:
                if ("1".equals(value)) {//客户自提
                    delivery_address_tv.setText(null);
                    projectInfo.setPlaceOfDelivery("");
                    delivery_address_tv.setEnabled(false);
                    delivery_address_tv.setBackgroundResource(R.drawable.bg_round_border_disable);
                } else {
                    delivery_address_tv.setEnabled(true);
                    delivery_address_tv.setBackgroundResource(R.drawable.bg_round_border);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Override
    public void bidBondSave(String bidBond, String bidCost, String changePerformbond, String bidBondType, String payee) {
        if (otherInfo != null) {
            otherInfo.setIsBid(bidBond);
            otherInfo.setBidCost(bidCost);
            otherInfo.setIsChangePerformanceBond(changePerformbond);
            otherInfo.setDepositType(bidBondType);
            otherInfo.setPayee(payee);
        }
    }

    @Override
    public void performanceBondSave(String performanceBond, String bidCost, String payType, String payee, String afterPaycost, String letterFormat, String letterTerm, String returnCondition, String remarks) {
        if (otherInfo != null) {
            otherInfo.setIsPerformanceBond(performanceBond);
            otherInfo.setPerformanceBondBidCost(bidCost);
            otherInfo.setPayType(payType);
            otherInfo.setPBPayee(payee);
            otherInfo.setAfterPayCost(afterPaycost);
            otherInfo.setLetterOfIndemnityFormat(letterFormat);
            otherInfo.setLetterOfIndemnityTermNotMap(letterTerm);
            otherInfo.setReturnCondition(returnCondition);
            otherInfo.setPBRemarks(remarks);
        }
    }

    @Override
    public void agentSave(String name, String contact, String number) {
        agent_name_tv.setText(name);
        if (projectInfo != null) {
            projectInfo.setAgentName(name);
            projectInfo.setAgentContact(contact);
            projectInfo.setAgent(number);
            //联动安装单位
            install_company_tv.setText(null);
            install_company_tv.setEnabled(true);
            install_company_tv.setBackgroundResource(R.drawable.bg_round_border);
            projectInfo.setInstallationUnit(null);
        }
    }

    @Override
    public void paymentMethodSave(boolean standardType, String payTypeParam) {
        if (projectInfo != null) {
            projectInfo.setIsStandardPayType(standardType);
            this.payTypeParam = payTypeParam;
        }
    }
}
