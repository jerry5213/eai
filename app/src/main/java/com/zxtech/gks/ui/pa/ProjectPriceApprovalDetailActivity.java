package com.zxtech.gks.ui.pa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.net.BasicResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.ui.home.bid.BidInfoFragment;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.MyItemDecoration;
import com.zxtech.gks.IActivity;
import com.zxtech.gks.common.Constants;
import com.zxtech.gks.model.vo.CommissionRate;
import com.zxtech.gks.model.vo.PrProductDetail.PermissionListBean;
import com.zxtech.gks.model.vo.PrProductDetail.PrProductDetail;
import com.zxtech.gks.model.vo.PrProductDetail.PriceTableListBean;
import com.zxtech.gks.model.vo.PrProductDetail.ServiceChargeVO;
import com.zxtech.gks.model.vo.PrProductDetail.SubmitOption;
import com.zxtech.gks.model.vo.ProjectInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by syp521 on 2017/10/27.
 */

public class ProjectPriceApprovalDetailActivity extends BaseActivity implements IActivity {

    private final String pageNo = "navPRPriceReviewEditPhone";

    @BindViews({R.id.project_name, R.id.project_attribute, R.id.agent_name, R.id.product_total, R.id.payment_type,
            R.id.bid_cost, R.id.deposit_type, R.id.recovery_time, R.id.PerformanceBondBidCost, R.id.PayType, R.id.ReturnCondition,
            R.id.EqContractTotal, R.id.inst_contract_total, R.id.TotalPrice, R.id.EquiObligatePriceForSale, R.id.InstOtherPriceForSale,
            R.id.FloatRate, R.id.flcm2_rate, R.id.fccm2_rate, R.id.project_real_rate, R.id.project_fl_eq_cm2,
            R.id.project_fl_inst_cm2, R.id.project_fl_cm2, R.id.tv_approve_person, R.id.tv_BidPoundage, R.id.flcm2_price,
            R.id.FCCMIIRateEqui, R.id.FCCMIIRate, R.id.FCCMII, R.id.promisesCount})
    TextView[] tvs;

    //服务费审批
    @BindView(R.id.tv_float_rate)
    TextView tv_float_rate;
    @BindView(R.id.tv_save)
    TextView tv_save;
    @BindView(R.id.lv_front_fwf)
    RecyclerView frontServiceFeeRv;
    @BindView(R.id.rv2)
    RecyclerView flowReviewRv;
    @BindView(R.id.tv_open_tb)
    ImageView tv_open_tb;
    @BindView(R.id.tv_open_ht_xx)
    ImageView tv_open_ht_xx;
    @BindView(R.id.tv_open_ht_pc)
    ImageView tv_open_ht_pc;
    @BindView(R.id.tv_open_fk)
    ImageView tv_open_fk;

    @BindView(R.id.tb_panel)
    LinearLayout tb_panel;
    @BindView(R.id.ht_xx_panel)
    LinearLayout ht_xx_panel;
    @BindView(R.id.ht_pc_panel)
    LinearLayout ht_pc_panel;
    @BindView(R.id.fk_panel)
    LinearLayout fk_panel;
    @BindView(R.id.fwfsp_panel)
    LinearLayout fwfsp_panel;
    @BindView(R.id.panel_toubiaoxinxi)
    LinearLayout panel_toubiaoxinxi;

    @BindView(R.id.et_opinion)
    EditText et_opinion;
    @BindView(R.id.et_price)
    EditText et_price;
    @BindView(R.id.et_agentCommissionRate)
    EditText et_agentCommissionRate;

    private String old_price = ""; //原始金额
    private String old_agent_commission_rate = ""; //原始服务费率
    private String new_price = "";
    private String new_commission_rate = "";

    @BindView(R.id.tv_ok)
    TextView tv_ok;
    @BindView(R.id.tv_refuse)
    TextView tv_refuse;
    @BindView(R.id.report)
    LinearLayout report;
    @BindView(R.id.switch_btn)
    SwitchCompat switch_btn;
    @BindView(R.id.input_panel)
    LinearLayout input_panel;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.l_layout)
    FrameLayout l_layout;

    private PermissionListBean permissionListBean;
    private ProjectInfo projectInfo;
    private List<PriceTableListBean> priceTableList;
    private String priceReviewGuid;
    private String instanceNodeId;
    private WorkFlowNodeListAdapter workFlowNodeListAdapter;
    private ServiceChargeAdapter serviceChargeAdapter;
    private String transactDutyNo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_price_approval;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,getString(R.string.approval_details));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        flowReviewRv.setLayoutManager(linearLayoutManager);
        flowReviewRv.addItemDecoration(new MyItemDecoration());

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        frontServiceFeeRv.setLayoutManager(linearLayoutManager2);
        frontServiceFeeRv.addItemDecoration(new MyItemDecoration());
        initData();
    }

    private String purchaseType = "招投标";

    public void initData() {

        priceReviewGuid = getIntent().getStringExtra("priceReviewGuid");
        instanceNodeId = getIntent().getStringExtra("instanceNodeId");

        Map params = new HashMap();
        params.put("priceReviewGuid", priceReviewGuid);
        params.put("instanceNodeId", instanceNodeId);
        params.put("uiFuncNo", pageNo);

        baseResponseObservable = HttpFactory.getApiService().getProjectPriceApprovalDetail(params);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse<PrProductDetail>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<PrProductDetail>>(this, true) {

                    @Override
                    public void onSuccess(BasicResponse<PrProductDetail> detail) {

                        if(purchaseType.equals(detail.getData().getPurchaseType())){
                            l_layout.setVisibility(View.VISIBLE);
                            BidInfoFragment fragment = BidInfoFragment.newInstance();
                            fragment.projectBid = detail.getData().getProjectBid();
                            fragment.setEdit(false);
                            loadRootFragment(R.id.l_layout, fragment);
                        }else{
                            l_layout.setVisibility(View.GONE);
                        }

                        //权限控制界面显示
                        permissionListBean = detail.getData().getPermissionList();
                        permissionCtrlViews(permissionListBean);

                        PrProductDetail datas = detail.getData();
                        /**项目信息**/
                        tvs[0].setText(datas.getProjectName());
                        String p_attr = datas.getEqContractTypeName() + "+" + datas.getInContractTypeName();
                        tvs[1].setText(p_attr);
                        tvs[2].setText(datas.getAgentName());
                        tvs[3].setText(datas.getProductList());
                        tvs[29].setText(datas.getPromisesCount());

                        projectInfo = new ProjectInfo();
                        projectInfo.setProject_name(datas.getProjectName());
                        projectInfo.setProject_attr(p_attr);
                        projectInfo.setAgent_name(datas.getAgentName());
                        projectInfo.setProduct_list(datas.getProductList());
                        projectInfo.setProject_no(datas.getProjectNo());
                        projectInfo.setProject_type(datas.getProjectType());
                        projectInfo.setGk_user(datas.getSalesmanUserName());
                        projectInfo.setSale_branch(datas.getBranchName());
                        projectInfo.setPromise_Count(datas.getPromisesCount());
                        if (datas.isIsKQ()) {
                            projectInfo.setIs_kq("是");
                        } else {
                            projectInfo.setIs_kq("否");
                        }
                        /**付款条件**/
                        String payment = datas.getPaymentType() != null ? datas.getPaymentType().replace("\\r\\n", "\n") : "";
                        tvs[4].setText(payment);
                        /**投标信息**/
                        if ("一般购买".equals(datas.getPurchaseType())) {
                            panel_toubiaoxinxi.setVisibility(View.GONE);
                        } else {
                            panel_toubiaoxinxi.setVisibility(View.VISIBLE);
                        }
                        tvs[5].setText(datas.getBidCost() == null ? "" : datas.getBidCost().toString());
                        tvs[6].setText(datas.getDepositType());
                        tvs[7].setText(datas.getRecoveryTime());
                        tvs[8].setText(datas.getPerformanceBondBidCost() == null ? "" : datas.getPerformanceBondBidCost().toString());
                        tvs[9].setText(datas.getPayType());
                        tvs[10].setText(datas.getReturnCondition() == null ? "" : datas.getReturnCondition().toString());
                        /**合同信息**/
                        priceTableList = datas.getPriceTableList();
                        PriceTableListBean bean = priceTableList.get(priceTableList.size() - 1);
                        tvs[11].setText(bean.getRealPrice_Equi());
                        tvs[12].setText(bean.getRealPrice_Inst());
                        tvs[13].setText(bean.getTotalPrice());
                        tvs[14].setText(datas.getEquiObligatePriceForSale());
                        tvs[15].setText(datas.getInstOtherPriceForSale());
                        tvs[16].setText(datas.getFloatRate());
                        tv_float_rate.setText(datas.getFloatRate());
                        tvs[25].setText(bean.getFLCMII());
                        tvs[17].setText(bean.getFLCMIIRate());
                        tvs[18].setText(bean.getFCCMIIRate());
                        /**中标手续费**/
                        tvs[24].setText(datas.getBidPoundage());
                        /**合同批次信息**/
                        tvs[19].setText(datas.getFloatRate());
                        tvs[20].setText(bean.getFLCMIIRateEqui());
                        tvs[21].setText(bean.getCMIIRateInst());
                        tvs[22].setText(bean.getFLCMIIRate());

                        tvs[26].setText(bean.getFCCMIIRateEqui());
                        tvs[27].setText(bean.getCMIIRateInst());
                        tvs[28].setText(bean.getFCCMIIRate());

                        /**服务费审批**/
                        /****/
                        if ("代理".equals(datas.getEqContractTypeName())) {
                            fwfsp_panel.setVisibility(View.VISIBLE);
                            tvs[23].setText(getUserName());
                        } else {
                            fwfsp_panel.setVisibility(View.GONE);
                        }

                        workFlowNodeListAdapter = new WorkFlowNodeListAdapter(ProjectPriceApprovalDetailActivity.this, R.layout.item_work_flow_node_list, datas.getWorkFlowNodeList());
                        flowReviewRv.setAdapter(workFlowNodeListAdapter);
                        //ToolUtils.measureListViewHeight(rv2,ProjectPriceApprovalDetailActivity.this);

                        /**解析服务费数据*/
                        String roles = getRoleNo();
                        List<String> roleList = Arrays.asList(roles.split(","));
                        if (roleList.contains("JS-DZHZC-SP") || roleList.contains("JS-ZC-SP")) {
                            input_panel.setVisibility(View.GONE);
                        }

                        List<ServiceChargeVO> chargeVOs = detail.getData().getChargeList();
                        if (chargeVOs != null && chargeVOs.size() > 0) {

                            int len = chargeVOs.size();
                            ServiceChargeVO vo = chargeVOs.get(len - 1);
                            old_price = vo.getAgentCommission();
                            old_agent_commission_rate = vo.getAgentCommissionRate();
                            et_price.setText(vo.getAgentCommission());
                            et_agentCommissionRate.setText(vo.getAgentCommissionRate());
                            if (len > 1) {
                                chargeVOs.remove(len - 1);
                                serviceChargeAdapter = new ServiceChargeAdapter(ProjectPriceApprovalDetailActivity.this, R.layout.item_service_charge, chargeVOs);
                                frontServiceFeeRv.setAdapter(serviceChargeAdapter);
                                serviceChargeAdapter.notifyDataSetChanged();
                                //T.measureListViewHeight(lv_front_fwf,ProjectPriceApprovalDetailActivity.this);
                            }
                        }

                        /**流程提交按钮控制**/
                        List<SubmitOption> options = detail.getData().getSubmitOption();
                        workflowCtrlSubmit(options);

                        transactDutyNo = detail.getData().getTransactDutyNo();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        ToastUtil.showLong("获取数据失败");
                        finish();
                    }
                });

    }

    private void permissionCtrlViews(PermissionListBean bean) {

        String[] group1 = {};
        String[] group2 = {};
        String[] group3 = {};

        if (!TextUtils.isEmpty(bean.getVisibleFunctionNoList())) {

        }
        if (!TextUtils.isEmpty(bean.getDisabledFunctionNo())) {
            group2 = bean.getDisabledFunctionNo().split(",");
        }
        if (!TextUtils.isEmpty(bean.getHiddenFunctionNo())) {
            group3 = bean.getHiddenFunctionNo().split(",");
        }

        for (View view : tvs) {
            if (null != view.getTag()) {
                String showId = view.getTag().toString();
                if (Arrays.asList(group1).contains(showId)) {
                    // 可见可用
                } else if (Arrays.asList(group2).contains(showId)) {
                    // 可见不可用
                    view.setEnabled(false);
                } else if (Arrays.asList(group3).contains(showId)) {
                    // 不可见
                    ((View) view.getParent()).setVisibility(View.GONE);
                }
            }
        }
    }

    private void workflowCtrlSubmit(List<SubmitOption> options) {

        if (options != null && options.size() > 0) {
            for (SubmitOption ob : options) {
                switch (ob.getResult()) {
                    case 0:
                        tv_ok.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        tv_refuse.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        break;
                    case 1:
                        report.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    }

    @OnCheckedChanged(R.id.switch_btn)
    public void onSwitchCheck(boolean isChecked) {

        if (isChecked) {
            //T.showToast(this,"开启");
        } else {
            //T.showToast(this,"关闭");
        }
    }

    @OnClick({R.id.iv_close, R.id.tv_ok, R.id.tv_refuse, R.id.tv_open_tb, R.id.tv_open_ht_xx,
            R.id.tv_open_ht_pc, R.id.tv_fl_detail, R.id.tv_open_fk, R.id.tv_save})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.iv_close:
                Intent intent = new Intent(view.getContext(), ProjectInfoDetailActivity.class);
                intent.putExtra(Constants.DATA1, projectInfo);
                startActivity(intent);
                break;
            case R.id.tv_open_tb:
                if (tb_panel.isShown()) {
                    tv_open_tb.setImageResource(R.drawable.open);
                    tb_panel.setVisibility(View.GONE);
                } else {
                    tv_open_tb.setImageResource(R.drawable.close);
                    tb_panel.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_open_ht_xx:
                if (ht_xx_panel.isShown()) {
                    tv_open_ht_xx.setImageResource(R.drawable.open);
                    ht_xx_panel.setVisibility(View.GONE);
                } else {
                    tv_open_ht_xx.setImageResource(R.drawable.close);
                    ht_xx_panel.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_open_ht_pc:
                if (ht_pc_panel.isShown()) {
                    tv_open_ht_pc.setImageResource(R.drawable.open);
                    ht_pc_panel.setVisibility(View.GONE);
                } else {
                    tv_open_ht_pc.setImageResource(R.drawable.close);
                    ht_pc_panel.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_open_fk:
                if (fk_panel.isShown()) {
                    tv_open_fk.setImageResource(R.drawable.open);
                    fk_panel.setVisibility(View.GONE);
                } else {
                    tv_open_fk.setImageResource(R.drawable.close);
                    fk_panel.setVisibility(View.VISIBLE);
                }
                break;
            /** 合同批次信息
             * **/
            case R.id.tv_fl_detail:
                tpContractBatchInfoFLDetailActivity();
                break;
            case R.id.tv_save:
                String str = tv_save.getText().toString();
                if (getString(R.string.save).equals(str)) {
                    upCommissionRate();
                } else if (getString(R.string.calculation).equals(str)) {
                    upParam();
                }
                break;
            case R.id.tv_ok:
                if (tv_ok.isEnabled()) {
                    if (switch_btn.isChecked()) {
                        submitPriceReview(priceReviewGuid, getUserNo(), "1");
                    } else {
                        submitPriceReview(priceReviewGuid, getUserNo(), "0");
                    }
                } else {
                    ToastUtil.showLong("已完成批复");
                }
                break;
            case R.id.tv_refuse:
                if (tv_refuse.isEnabled()) {
                    if (switch_btn.isChecked()) {
                        ToastUtil.showLong("已经选择上报，无法驳回");
                    } else {
                        submitPriceReview(priceReviewGuid, getUserNo(), "2");
                    }
                } else {
                    ToastUtil.showLong("已完成批复");
                }
                break;
        }
    }

    private void upParam() {

        Map params = new HashMap();
        if (!new_price.equals(old_price)) {
            params.put("PRProductGuid", priceReviewGuid);
            params.put("AgentCommission", new_price);
            baseResponseObservable = HttpFactory.getApiService().getCalculateAgentCommissionRateAndRealFloatingRate(params);
        } else {
            if (!new_commission_rate.equals(old_agent_commission_rate)) {
                params.put("PRProductGuid", priceReviewGuid);
                params.put("AgentCommissionRate", new_commission_rate);
                baseResponseObservable = HttpFactory.getApiService().getCalculateAgentCommissionAndRealFloatingRate(params);
            }
        }

        baseResponseObservable
                .compose(RxHelper.<BasicResponse<List<CommissionRate>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<List<CommissionRate>>>(this, false) {

                    @Override
                    public void onSuccess(BasicResponse<List<CommissionRate>> response) {

                        List<CommissionRate> datas = response.getData();
                        if (!new_price.equals(old_price)) {
                            tv_float_rate.setText(datas.get(0).getValue());
                            et_agentCommissionRate.setText(datas.get(1).getValue());
                            old_price = new_price;
                        } else {
                            if (!new_commission_rate.equals(old_agent_commission_rate)) {
                                tv_float_rate.setText(datas.get(0).getValue());
                                et_price.setText(datas.get(1).getValue());
                                old_agent_commission_rate = new_commission_rate;
                            }
                        }
                        tv_save.setText("保存");
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    private void upCommissionRate() {

        String s1 = et_price.getText().toString();
        String s2 = et_agentCommissionRate.getText().toString();
        if (TextUtils.isEmpty(s1) || TextUtils.isEmpty(s2)) {
            ToastUtil.showLong("输入项不能为空");
            return;
        }

        Map params = new HashMap();
        params.put("PriceReviewGuid", priceReviewGuid);
        params.put("TransactorGuid", getUserId());
        params.put("RoleNo", transactDutyNo);
        params.put("AgentCommission", new_price);
        params.put("AgentCommissionRate", new_commission_rate);
        String float_rate = tv_float_rate.getText().toString();
        if (float_rate.contains("%")) {
            float_rate = float_rate.replace("%", "");
        }
        params.put("RealFloatingRate", float_rate);

        baseResponseObservable = HttpFactory.getApiService().saveTransactoResult(params);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse>(this, false) {

                    @Override
                    public void onSuccess(BasicResponse response) {

                        ToastUtil.showLong("保存成功");
                        dismissProgress();
                        initData();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });

    }

    @OnTextChanged(value = R.id.et_price, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(Editable s) {

        new_price = et_price.getText().toString();
        if (!new_price.equals(old_price)) {
            tv_save.setText(getString(R.string.calculation));
            tv_save.setEnabled(true);
        } else {
            tv_save.setText(getString(R.string.save));
        }
    }

    @OnTextChanged(value = R.id.et_agentCommissionRate, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged2(Editable s) {

        new_commission_rate = et_agentCommissionRate.getText().toString();
        if (!new_commission_rate.equals(old_agent_commission_rate)) {
            tv_save.setText(getString(R.string.calculation));
            tv_save.setEnabled(true);
        } else {
            tv_save.setText(getString(R.string.save));
        }
    }

    private void tpContractBatchInfoFLDetailActivity() {
        if (priceTableList != null && priceTableList.size() > 0) {
            Intent intent2 = new Intent(this, ContractBatchInfoFLDetailActivity.class);
            intent2.putExtra(Constants.DATA1, new ArrayList(priceTableList));
            intent2.putExtra("PermissionListBean", permissionListBean);
            startActivity(intent2);
        } else {
            ToastUtil.showLong("没有合同批次信息");
        }
    }

    /**
     * 提交价审信息
     *
     * @param prProductGuid
     * @param userNo
     * @param submitResult  0 通过  2 驳回
     */
    void submitPriceReview(String prProductGuid, String userNo, String submitResult) {

        String submitDescription = et_opinion.getText().toString();
        try {
            submitDescription = URLEncoder.encode(submitDescription, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map params = new HashMap();
        params.put("prProductGuid", prProductGuid);
        params.put("userNo", userNo);
        params.put("submitResult", submitResult);
        params.put("submitDescription", submitDescription);

        baseResponseObservable = HttpFactory.getApiService().submitPriceReview(params);
        baseResponseObservable
                .compose(RxHelper.<BasicResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {

                    @Override
                    public void onSuccess(BasicResponse response) {

                        ToastUtil.showLong(getString(R.string.toast13));
                        setResult(1);
                        finish();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }
}