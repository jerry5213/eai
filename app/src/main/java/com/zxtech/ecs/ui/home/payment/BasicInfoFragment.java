package com.zxtech.ecs.ui.home.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.AccountPayment;
import com.zxtech.ecs.model.AccountPaymentBasic;
import com.zxtech.ecs.model.DropDown;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.widget.DropDownWindow;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/7/10.
 */

public class BasicInfoFragment extends BaseFragment {

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
    @BindView(R.id.contract_no_tv)
    TextView contract_no_tv;
    @BindView(R.id.archives_no_tv)
    TextView archives_no_tv;
    @BindView(R.id.project_name_tv)
    TextView project_name_tv;
    @BindView(R.id.equi_money_tv)
    TextView equi_money_tv;
    @BindView(R.id.inst_money_tv)
    TextView inst_money_tv;
    @BindView(R.id.allottotal_money_tv)
    TextView allottotal_money_tv;
    @BindView(R.id.toreceive_money_tv)
    TextView toreceive_money_tv;
    @BindView(R.id.payment_remark_tv)
    TextView payment_remark_tv;
    @BindView(R.id.info_supporter_et)
    EditText info_supporter_et;
    @BindView(R.id.invoice_unit_et)
    EditText invoice_unit_et;

    private List<DropDown> paymentRemarkDownList = new ArrayList<>();
    private AccountPayment accountPaymentBasic;

    public AccountPayment getAccountPaymentBasic() {
        accountPaymentBasic.setInvoiceUnit(invoice_unit_et.getText().toString());
        accountPaymentBasic.setInfoSupporter(info_supporter_et.getText().toString());
        return accountPaymentBasic;
    }

    public static BasicInfoFragment newInstance(String accountGuid) {
        Bundle args = new Bundle();
        BasicInfoFragment fragment = new BasicInfoFragment();
        args.putString("accountGuid", accountGuid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_basic_info;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        baseResponseObservable = HttpFactory.getApiService().getAccountDetail(getArguments().getString("accountGuid"));
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<AccountPayment>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<AccountPayment>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<AccountPayment> response) {

                        accountPaymentBasic = response.getData();
                        if (TextUtils.isEmpty(accountPaymentBasic.getInfoSupporter())) {
                            accountPaymentBasic.setInfoSupporter(getUserName());
                        }
                        initView(accountPaymentBasic);
                        initPaymentRemark(response.getData());
                    }
                });
    }

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

    private void initView(AccountPayment data) {
        serial_number_tv.setText(data.getSerialNo());
        term_number_tv.setText(data.getFileName());
        original_money_tv.setText(data.getOriginalMoney());
        branch_tv.setText(data.getSaleBranchName());
        import_place_tv.setText(data.getImportPlace());
        pay_date_tv.setText(data.getPayDate());
        remittance_unit_tv.setText(data.getRemittanceUnit());
        remark_tv.setText(data.getRemark());
        contract_no_tv.setText(data.getContractNo());
        archives_no_tv.setText(data.getContractArchivesNo());
        project_name_tv.setText(data.getProjectName());
        equi_money_tv.setText(data.getEquiMoney());
        inst_money_tv.setText(data.getInstMoney());
        allottotal_money_tv.setText(data.getContractAllotTotalMoney());
        toreceive_money_tv.setText(data.getToReceiveMoney());
        payment_remark_tv.setText(data.getPaymentRemark());
        info_supporter_et.setText(data.getInfoSupporter());
        invoice_unit_et.setText(data.getInvoiceUnit());

        Bundle bundle = new Bundle();
        bundle.putString("restMoney",data.getRestMoney());
        bundle.putString("invoiceAttribution",data.getInvoiceAttribution());
        EventBus.getDefault().post(bundle);
    }


    @OnClick(R.id.payment_remark_tv)
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.payment_remark_tv:
                new DropDownWindow(mContext, view, (TextView) view, paymentRemarkDownList, view.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {

                        ((TextView) view).setText(paymentRemarkDownList.get(p).getText());
                        accountPaymentBasic.setPaymentRemark(paymentRemarkDownList.get(p).getValue());
                    }
                };
                break;
        }
    }
}
