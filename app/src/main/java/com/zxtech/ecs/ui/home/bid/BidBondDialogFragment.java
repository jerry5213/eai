package com.zxtech.ecs.ui.home.bid;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.zxtech.ecs.BaseDialogFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.DropDown;
import com.zxtech.ecs.model.DropDownVo;
import com.zxtech.ecs.model.ProjectBid;
import com.zxtech.ecs.model.ProjectProductInfo;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.DateUtil;
import com.zxtech.ecs.widget.DropDownWindow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/6/19.
 */

public class BidBondDialogFragment extends BaseDialogFragment implements DatePickerDialog.OnDateSetListener{

    @BindView(R.id.account_date_tv)
    TextView account_date_tv;
    @BindView(R.id.company_afford_et)
    EditText company_afford_et;
    @BindView(R.id.agent_afford_et)
    EditText agent_afford_et;
    @BindView(R.id.total_cost_et)
    EditText total_cost_et;
    @BindView(R.id.applypay_date_tv)
    TextView applypay_date_tv;
    @BindView(R.id.return_term_et)
    EditText return_term_et;
    @BindView(R.id.return_cyc_et)
    EditText return_cyc_et;
    @BindView(R.id.bidpay_type_tv)
    TextView bidpay_type_tv;
    @BindView(R.id.pay_remark_et)
    EditText pay_remark_et;
    @BindView(R.id.save_tv)
    TextView save_tv;

    private TextView currentClickView;

    public BidInfoFragment bidInfoFragment;
    private List<DropDown> payTypeDropList = new ArrayList<>();
    private Calendar cal = Calendar.getInstance();

    public static BidBondDialogFragment newInstance() {
        return new BidBondDialogFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_bid_bond_info;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        account_date_tv.setText(bidInfoFragment.projectBid.getAccountDate());
        company_afford_et.setText(bidInfoFragment.projectBid.getCompanyAfford());
        agent_afford_et.setText(bidInfoFragment.projectBid.getAgentAfford());
        total_cost_et.setText(bidInfoFragment.projectBid.getTotalCost());
        applypay_date_tv.setText(bidInfoFragment.projectBid.getApplyPayDate());
        return_term_et.setText(bidInfoFragment.projectBid.getReturnTerm());
        return_cyc_et.setText(bidInfoFragment.projectBid.getReturnCyc());
        pay_remark_et.setText(bidInfoFragment.projectBid.getPayRemark());

        account_date_tv.setEnabled(bidInfoFragment.isEdit());
        company_afford_et.setEnabled(bidInfoFragment.isEdit());
        agent_afford_et.setEnabled(bidInfoFragment.isEdit());
        total_cost_et.setEnabled(bidInfoFragment.isEdit());
        applypay_date_tv.setEnabled(bidInfoFragment.isEdit());
        return_term_et.setEnabled(bidInfoFragment.isEdit());
        return_cyc_et.setEnabled(bidInfoFragment.isEdit());
        bidpay_type_tv.setEnabled(bidInfoFragment.isEdit());
        pay_remark_et.setEnabled(bidInfoFragment.isEdit());
        save_tv.setEnabled(bidInfoFragment.isEdit());
        initBidPayType();
    }

    @Override
    public boolean isBottomShow() {
        return false;
    }

    private void initBidPayType() {
        baseResponseObservable = HttpFactory.getApiService().getSelectList(19);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<List<DropDown>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<DropDown>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<DropDown>> response) {
                        payTypeDropList = response.getData();
                        for (DropDown dropDown : response.getData()) {
                            if (dropDown.getValue().equals(bidInfoFragment.projectBid.getBidPayType())) {
                                bidpay_type_tv.setText(dropDown.getText());
                            }
                        }
                    }
                });
    }


    @OnClick({R.id.bidpay_type_tv,R.id.account_date_tv,R.id.applypay_date_tv,R.id.save_tv})
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.bidpay_type_tv:
                new DropDownWindow(getActivity(), view, (TextView) view, payTypeDropList, view.getWidth(), -2) {
                    @Override
                    public void initEvents(int p) {
                        ((TextView) view).setText(payTypeDropList.get(p).getText());
                        //支付方式
                        bidInfoFragment.projectBid.setBidPayType(payTypeDropList.get(p).getValue());
                    }
                };
                break;
            case R.id.account_date_tv:
                currentClickView = account_date_tv;
                new DatePickerDialog(mContext, this,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                ).show();
                break;
            case R.id.applypay_date_tv:
                currentClickView = applypay_date_tv;
                new DatePickerDialog(mContext, this,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                ).show();
                break;
            case R.id.save_tv:
                save();
                break;
        }

    }


    public void save() {
        bidInfoFragment.projectBid.setAccountDate(account_date_tv.getText().toString());
        bidInfoFragment.projectBid.setCompanyAfford(company_afford_et.getText().toString());
        bidInfoFragment.projectBid.setAgentAfford(agent_afford_et.getText().toString());
        bidInfoFragment.projectBid.setTotalCost(total_cost_et.getText().toString());
        bidInfoFragment.projectBid.setApplyPayDate(applypay_date_tv.getText().toString());
        bidInfoFragment.projectBid.setReturnTerm(return_term_et.getText().toString());
        bidInfoFragment.projectBid.setReturnCyc(return_cyc_et.getText().toString());
        bidInfoFragment.projectBid.setPayRemark(pay_remark_et.getText().toString());
        if (bidInfoFragment.isRequiredCheck(bidInfoFragment.BID_BOND_CHECK)) {
            dismiss();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        currentClickView.setText(DateUtil.formatChange(year,month,dayOfMonth,DateUtil.yyyy_MM_dd));
    }
}
