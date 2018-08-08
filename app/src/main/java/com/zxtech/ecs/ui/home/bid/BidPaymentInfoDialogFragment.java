package com.zxtech.ecs.ui.home.bid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zxtech.ecs.BaseDialogFragment;
import com.zxtech.ecs.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/6/19.
 */

public class BidPaymentInfoDialogFragment extends BaseDialogFragment {

    @BindView(R.id.payee_fullname_et)
    EditText payee_fullname_et;
    @BindView(R.id.bank_et)
    EditText bank_et;
    @BindView(R.id.account_et)
    EditText account_et;
    @BindView(R.id.save_tv)
    TextView save_tv;

    public BidInfoFragment bidInfoFragment;

    public static BidPaymentInfoDialogFragment newInstance() {
        return new BidPaymentInfoDialogFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_bid_payment_info;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        payee_fullname_et.setText(bidInfoFragment.projectBid.getPayeeFullName());
        bank_et.setText(bidInfoFragment.projectBid.getBank());
        account_et.setText(bidInfoFragment.projectBid.getAccount());

        payee_fullname_et.setEnabled(bidInfoFragment.isEdit());
        bank_et.setEnabled(bidInfoFragment.isEdit());
        account_et.setEnabled(bidInfoFragment.isEdit());
        save_tv.setEnabled(bidInfoFragment.isEdit());
    }

    @Override
    public boolean isBottomShow() {
        return false;
    }


    @OnClick(R.id.save_tv)
    public void save(){
        bidInfoFragment.projectBid.setPayeeFullName(payee_fullname_et.getText().toString());
        bidInfoFragment.projectBid.setBank(bank_et.getText().toString());
        bidInfoFragment.projectBid.setAccount(account_et.getText().toString());
        if (bidInfoFragment.isRequiredCheck(bidInfoFragment.BID_PAYMENT_INFO_CHECK)) {
            dismiss();
        }
    }
}
