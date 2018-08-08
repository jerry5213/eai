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

public class BidPriceDialogFragment extends BaseDialogFragment {

    @BindView(R.id.bid_discount_et)
    EditText bid_discount_et;
    @BindView(R.id.commission_et)
    EditText commission_et;
    @BindView(R.id.bid_price_et)
    EditText bid_price_et;
    @BindView(R.id.other_info_et)
    EditText other_info_et;
    @BindView(R.id.save_tv)
    TextView save_tv;

    public BidInfoFragment bidInfoFragment;

    public static BidPriceDialogFragment newInstance() {
        return new BidPriceDialogFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_bid_price;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        bid_discount_et.setText(bidInfoFragment.projectBid.getBidDiscount());
        commission_et.setText(bidInfoFragment.projectBid.getCommission());
        bid_price_et.setText(bidInfoFragment.projectBid.getBidPrice());
        other_info_et.setText(bidInfoFragment.projectBid.getOtherInfo());

        bid_discount_et.setEnabled(bidInfoFragment.isEdit());
        commission_et.setEnabled(bidInfoFragment.isEdit());
        bid_price_et.setEnabled(bidInfoFragment.isEdit());
        other_info_et.setEnabled(bidInfoFragment.isEdit());
        save_tv.setEnabled(bidInfoFragment.isEdit());
    }

    @Override
    public boolean isBottomShow() {
        return false;
    }


    @OnClick(R.id.save_tv)
    public void save(){
        bidInfoFragment.projectBid.setBidDiscount(bid_discount_et.getText().toString());
        bidInfoFragment.projectBid.setCommission(commission_et.getText().toString());
        bidInfoFragment.projectBid.setBidPrice(bid_price_et.getText().toString());
        bidInfoFragment.projectBid.setOtherInfo(other_info_et.getText().toString());

        if (bidInfoFragment.isRequiredCheck(bidInfoFragment.BID_PRICE_CHECK)) {
            dismiss();
        }
    }
}
