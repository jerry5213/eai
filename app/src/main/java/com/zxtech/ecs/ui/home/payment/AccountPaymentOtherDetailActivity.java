package com.zxtech.ecs.ui.home.payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.AccountPayment;
import com.zxtech.ecs.model.AccountPaymentBasic;
import com.zxtech.ecs.model.DropDown;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.DropDownWindow;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/7/10.
 */

public class AccountPaymentOtherDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.serial_number_tv)
    TextView serial_number_tv;
    @BindView(R.id.term_number_tv)
    TextView term_number_tv;
    @BindView(R.id.original_money_tv)
    TextView original_money_tv;
    @BindView(R.id.branch_tv)
    TextView branch_tv;
    @BindView(R.id.import_place_tv)
    TextView import_place_tv;
    @BindView(R.id.pay_date_tv)
    TextView pay_date_tv;
    @BindView(R.id.remittance_unit_tv)
    TextView remittance_unit_tv;
    @BindView(R.id.remark_tv)
    TextView remark_tv;
    @BindView(R.id.invoice_attribution_tv)
    TextView invoice_attribution_tv;
    @BindView(R.id.rest_money_tv)
    TextView rest_money_tv;
    @BindView(R.id.payment_remark_tv)
    TextView payment_remark_tv;
    @BindView(R.id.allot_et)
    EditText allot_et;
    @BindView(R.id.info_supporter_et)
    EditText info_supporter_et;
    @BindView(R.id.invoice_unit_et)
    EditText invoice_unit_et;
    @BindView(R.id.contract_no_et)
    EditText contract_no_et;
    @BindView(R.id.project_name_et)
    EditText project_name_et;
    @BindView(R.id.archives_no_et)
    EditText archives_no_et;

    private AccountPayment accountPaymentBasic;
    private List<DropDown> paymentRemarkDownList = new ArrayList<>();
    private List<DropDown> invoiceAttrDownList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_payment_other_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.payment_details));
        this.accountPaymentBasic = (AccountPayment) getIntent().getSerializableExtra("accountPayment");
        initData();
    }

    private void initData() {
        baseResponseObservable = HttpFactory.getApiService().getAccountDetail(this.accountPaymentBasic.getGuid());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<AccountPayment>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<AccountPayment>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<AccountPayment> response) {
                        AccountPayment data = response.getData();
                        accountPaymentBasic.setEquiMoney(data.getEquiMoney());
                        accountPaymentBasic.setInstMoney(data.getInstMoney());
                        accountPaymentBasic.setContractAllotTotalMoney(data.getContractAllotTotalMoney());
                        accountPaymentBasic.setToReceiveMoney(data.getToReceiveMoney());
                        accountPaymentBasic.setRestMoney(data.getRestMoney());
                        if (TextUtils.isEmpty(data.getInfoSupporter())) {
                            accountPaymentBasic.setInfoSupporter(getUserName());
                        }else{
                            accountPaymentBasic.setInfoSupporter(data.getInfoSupporter());
                        }
                        if (TextUtils.isEmpty(data.getSaleBranchName())) {
                            accountPaymentBasic.setSaleBranchName(getUserDeptName());
                        }else{
                            accountPaymentBasic.setSaleBranchName(data.getSaleBranchName());
                        }
                        initView(accountPaymentBasic);
                        initPaymentRemark(accountPaymentBasic);
                        initInvoiceAttr(accountPaymentBasic);
                    }
                });
    }

    //付款说明
    private void initPaymentRemark(final AccountPayment data) {
        baseResponseObservable = HttpFactory.getApiService().getSelectList(31);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<DropDown>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<DropDown>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<DropDown>> response) {
                        paymentRemarkDownList = response.getData();
                        for (DropDown dropDown : paymentRemarkDownList) {
                            if (dropDown.getValue().equals(data.getPaymentRemark())) {
                                payment_remark_tv.setText(dropDown.getText());
                            }
                        }
                    }
                });
    }

    //款项属性
    private void initInvoiceAttr(final AccountPayment data) {
        baseResponseObservable = HttpFactory.getApiService().getSelectList(41);
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<DropDown>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<DropDown>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<DropDown>> response) {
                        for (DropDown dropDown : response.getData()) {
                            if ("0".equals(dropDown.getValue())) continue;
                            invoiceAttrDownList.add(dropDown);
                            if (dropDown.getValue().equals(data.getCopyType())) {
                                invoice_attribution_tv.setText(dropDown.getText());
                            }
                        }
                    }
                });
    }

    private void initView(AccountPayment data) {

        serial_number_tv.setText(data.getSerialNo());
        term_number_tv.setText(data.getFileName());
        original_money_tv.setText(data.getOriginalMoney());
        branch_tv.setText(data.getSaleBranchName());
        import_place_tv.setText(data.getImportPlace());
        pay_date_tv.setText(data.getPayDate());
        remittance_unit_tv.setText(data.getRemittanceUnit());
        remark_tv.setText(data.getRemark());

        rest_money_tv.setText(data.getRestMoney());
       // invoice_attribution_tv.setText(data.getInvoiceAttribution());
        payment_remark_tv.setText(data.getPaymentRemark());
        info_supporter_et.setText(data.getInfoSupporter());
        invoice_unit_et.setText(data.getInvoiceUnit());
        contract_no_et.setText(data.getContractNo());
        project_name_et.setText(data.getProjectName());
        archives_no_et.setText(data.getContractArchivesNo());
    }


    @OnClick({R.id.payment_remark_tv,R.id.invoice_attribution_tv,R.id.save_btn})
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.payment_remark_tv:
                new DropDownWindow(mContext, view, (TextView) view, paymentRemarkDownList, view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {

                        payment_remark_tv.setText(paymentRemarkDownList.get(p).getText());
                        accountPaymentBasic.setPaymentRemark(paymentRemarkDownList.get(p).getValue());
                    }
                };
                break;
            case R.id.invoice_attribution_tv:
                new DropDownWindow(mContext, view, (TextView) view, invoiceAttrDownList, view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {

                        invoice_attribution_tv.setText(invoiceAttrDownList.get(p).getText());
                        accountPaymentBasic.setCopyType(invoiceAttrDownList.get(p).getValue());
                    }
                };
                break;
            case R.id.save_btn:
                save();
                break;
        }

    }

    private void save() {
        if (TextUtils.isEmpty(accountPaymentBasic.getCopyType())) {
            ToastUtil.showLong(getString(R.string.msg52));
            return;
        }
        if (TextUtils.isEmpty(allot_et.getText())) {
            ToastUtil.showLong(getString(R.string.msg53));
            return;
        }
        BigDecimal allotBd = new BigDecimal(allot_et.getText().toString());
        BigDecimal restBd = new BigDecimal(rest_money_tv.getText().toString());
        if (allotBd.compareTo(restBd) > 0) {
            ToastUtil.showLong(getString(R.string.msg54));
            return;
        }
        accountPaymentBasic.setInvoiceUnit(invoice_unit_et.getText().toString());
        accountPaymentBasic.setContractNo(contract_no_et.getText().toString());
        accountPaymentBasic.setContractArchivesNo(archives_no_et.getText().toString());
        accountPaymentBasic.setProjectName(project_name_et.getText().toString());
        accountPaymentBasic.setAllotMoney(allot_et.getText().toString());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserId", getUserId());
        jsonObject.addProperty("DeptNo", getUserDeptNo());
        jsonObject.addProperty("Guid", accountPaymentBasic.getGuid());
        jsonObject.addProperty("AllotMoney", allot_et.getText().toString());
        jsonObject.addProperty("FileName", accountPaymentBasic.getFileName());
        jsonObject.addProperty("SerialNoCopy", accountPaymentBasic.getSerialNoCopy());
        jsonObject.addProperty("ImportPlace", accountPaymentBasic.getImportPlace());
        jsonObject.addProperty("InvoiceUnit", invoice_unit_et.getText().toString());
        jsonObject.addProperty("InfoSupporter", accountPaymentBasic.getInfoSupporter());
        jsonObject.addProperty("PayDate", accountPaymentBasic.getPayDate());
        jsonObject.addProperty("RemittanceUnit", accountPaymentBasic.getRemittanceUnit());
        jsonObject.addProperty("OriginalMoney", accountPaymentBasic.getOriginalMoney());
        jsonObject.addProperty("CopyType", accountPaymentBasic.getCopyType());
        jsonObject.addProperty("ContractNo", contract_no_et.getText().toString());
        jsonObject.addProperty("ContractArchivesNo", archives_no_et.getText().toString());
        jsonObject.addProperty("ProjectName", project_name_et.getText().toString());
        jsonObject.addProperty("PaymentRemark", accountPaymentBasic.getPaymentRemark());

        baseResponseObservable = HttpFactory.getApiService().savePayOther(jsonObject.toString());
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        ToastUtil.showLong(getString(R.string.saved));
                        Intent intent = getIntent();
                        intent.putExtra("accountPayment",accountPaymentBasic);
                        setResult(10020,intent);
                        finish();
                    }
                });
    }

}
