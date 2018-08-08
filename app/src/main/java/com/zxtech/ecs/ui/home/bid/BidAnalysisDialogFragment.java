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

public class BidAnalysisDialogFragment extends BaseDialogFragment {

    @BindView(R.id.quotation_value_et)
    EditText quotation_value_et;
    @BindView(R.id.technology_value_et)
    EditText technology_value_et;
    @BindView(R.id.other_value_et)
    EditText other_value_et;
    @BindView(R.id.quotation_standard_et)
    EditText quotation_standard_et;
    @BindView(R.id.technology_remark_et)
    EditText technology_remark_et;
    @BindView(R.id.other_remark_et)
    EditText other_remark_et;
    @BindView(R.id.quotation_remark_et)
    EditText quotation_remark_et;
    @BindView(R.id.save_tv)
    TextView save_tv;

    public BidInfoFragment bidInfoFragment;

    public static BidAnalysisDialogFragment newInstance() {
        return new BidAnalysisDialogFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_bid_analysis;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        quotation_value_et.setText(bidInfoFragment.projectBid.getQuotationValue());
        technology_value_et.setText(bidInfoFragment.projectBid.getTechnologyValue());
        other_value_et.setText(bidInfoFragment.projectBid.getOtherValue());
        quotation_standard_et.setText(bidInfoFragment.projectBid.getQuotationStandard());
        technology_remark_et.setText(bidInfoFragment.projectBid.getTechnologyRemark());
        other_remark_et.setText(bidInfoFragment.projectBid.getOtherRemark());
        quotation_remark_et.setText(bidInfoFragment.projectBid.getQuotationRemark());

        quotation_value_et.setEnabled(bidInfoFragment.isEdit());
        technology_value_et.setEnabled(bidInfoFragment.isEdit());
        other_value_et.setEnabled(bidInfoFragment.isEdit());
        quotation_standard_et.setEnabled(bidInfoFragment.isEdit());
        technology_remark_et.setEnabled(bidInfoFragment.isEdit());
        other_remark_et.setEnabled(bidInfoFragment.isEdit());
        quotation_remark_et.setEnabled(bidInfoFragment.isEdit());
        save_tv.setEnabled(bidInfoFragment.isEdit());
    }

    @Override
    public boolean isBottomShow() {
        return false;
    }


    @OnClick(R.id.save_tv)
    public void save(){
        bidInfoFragment.projectBid.setQuotationValue(quotation_value_et.getText().toString());
        bidInfoFragment.projectBid.setTechnologyValue(technology_value_et.getText().toString());
        bidInfoFragment.projectBid.setOtherValue(other_value_et.getText().toString());
        bidInfoFragment.projectBid.setQuotationStandard(quotation_standard_et.getText().toString());
        bidInfoFragment.projectBid.setTechnologyRemark(technology_remark_et.getText().toString());
        bidInfoFragment.projectBid.setOtherRemark(other_remark_et.getText().toString());
        bidInfoFragment.projectBid.setQuotationRemark(quotation_remark_et.getText().toString());

        if (bidInfoFragment.isRequiredCheck(bidInfoFragment.BID_ANALYSIS_CHECK)) {
            dismiss();
        }
    }
}
