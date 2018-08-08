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

public class BidPaymentMethodDialogFragment extends BaseDialogFragment {

    @BindView(R.id.perbid_cost_et)
    EditText perbid_cost_et;
    @BindView(R.id.project_paytype_et)
    EditText project_paytype_et;
    @BindView(R.id.project_payee_et)
    EditText project_payee_et;
    @BindView(R.id.project_remark_et)
    EditText project_remark_et;
    @BindView(R.id.agent_info_et)
    EditText agent_info_et;
    @BindView(R.id.save_tv)
    TextView save_tv;

    public BidInfoFragment bidInfoFragment;

    public static BidPaymentMethodDialogFragment newInstance() {
        return new BidPaymentMethodDialogFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_bid_payment_method;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        perbid_cost_et.setText(bidInfoFragment.projectBid.getPerBidCost());
        project_paytype_et.setText(bidInfoFragment.projectBid.getProjectPayType());
        project_payee_et.setText(bidInfoFragment.projectBid.getProjectPayee());
        project_remark_et.setText(bidInfoFragment.projectBid.getProjectRemark());
        agent_info_et.setText(bidInfoFragment.projectBid.getAgentInfo());

        perbid_cost_et.setEnabled(bidInfoFragment.isEdit());
        project_paytype_et.setEnabled(bidInfoFragment.isEdit());
        project_payee_et.setEnabled(bidInfoFragment.isEdit());
        project_remark_et.setEnabled(bidInfoFragment.isEdit());
        agent_info_et.setEnabled(bidInfoFragment.isEdit());
        save_tv.setEnabled(bidInfoFragment.isEdit());
    }


    @Override
    public boolean isBottomShow() {
        return false;
    }

    @OnClick(R.id.save_tv)
    public void save(){
        bidInfoFragment.projectBid.setPerBidCost(perbid_cost_et.getText().toString());
        bidInfoFragment.projectBid.setProjectPayType(project_paytype_et.getText().toString());
        bidInfoFragment.projectBid.setProjectPayee(project_payee_et.getText().toString());
        bidInfoFragment.projectBid.setProjectRemark(project_remark_et.getText().toString());
        bidInfoFragment.projectBid.setAgentInfo(agent_info_et.getText().toString());
        if (bidInfoFragment.isRequiredCheck(bidInfoFragment.BID_PAYMENT_METHOD_CHECK)) {
            dismiss();
        }
    }
}
