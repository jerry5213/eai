package com.zxtech.ecs.ui.home.bid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.event.EventAction;
import com.zxtech.ecs.model.BidAttachment;
import com.zxtech.ecs.model.ProjectBid;
import com.zxtech.ecs.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/7/23.
 */

public class BidInfoFragment extends BaseFragment {

    @BindView(R.id.expand_iv)
    ImageView expand_iv;
    @BindView(R.id.display_layout)
    LinearLayout display_layout;

    public ProjectBid projectBid;
    private List<BidAttachment> bidResultFiles = new ArrayList<>();
    private boolean isEdit = true;
    private boolean isExpand = false;

    public static final int ALL_CHECK = 0;
    public static final int BID_BOND_CHECK = 1;
    public static final int BID_ANALYSIS_CHECK = 2;
    public static final int BID_PRICE_CHECK = 3;
    public static final int BID_PAYMENT_INFO_CHECK = 4;
    public static final int BID_PAYMENT_METHOD_CHECK = 5;

    public static BidInfoFragment newInstance() {
        Bundle args = new Bundle();
        BidInfoFragment fragment = new BidInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bid_info;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        if (isExpand) {
            expand_iv.setVisibility(View.VISIBLE);
        } else {
            expand_iv.setVisibility(View.GONE);
        }
    }

    public void setProjectBid(ProjectBid projectBid) {
        this.projectBid = projectBid;
    }

    public List<BidAttachment> getBidResultFiles() {
        return bidResultFiles;
    }

    public void setBidResultFiles(List<BidAttachment> bidResultFiles) {
        this.bidResultFiles = bidResultFiles;
    }

    @OnClick({R.id.bid_bond_iv, R.id.bid_analysis_iv, R.id.bid_price_iv, R.id.payment_account_info_iv, R.id.project_payment_method_iv, R.id.bid_attchment_iv, R.id.expand_iv})
    public void onClick(View view) {
        if (projectBid == null) return;
        switch (view.getId()) {

            case R.id.bid_bond_iv://投标保证金
                BidBondDialogFragment bidBondDialogFragment = BidBondDialogFragment.newInstance();
                bidBondDialogFragment.bidInfoFragment = this;

                bidBondDialogFragment.show(getActivity().getFragmentManager(), "bid_bond");
                break;

            case R.id.bid_analysis_iv://投标情况分析
                BidAnalysisDialogFragment bidAnalysisDialogFragment = BidAnalysisDialogFragment.newInstance();
                bidAnalysisDialogFragment.bidInfoFragment = this;
                bidAnalysisDialogFragment.show(getActivity().getFragmentManager(), "bid_analysis");
                break;

            case R.id.bid_price_iv://投标价格
                BidPriceDialogFragment bidPriceDialogFragment = BidPriceDialogFragment.newInstance();
                bidPriceDialogFragment.bidInfoFragment = this;
                bidPriceDialogFragment.show(getActivity().getFragmentManager(), "bid_price");
                break;

            case R.id.payment_account_info_iv://付款账户信息
                BidPaymentInfoDialogFragment bidPaymentInfoDialogFragment = BidPaymentInfoDialogFragment.newInstance();
                bidPaymentInfoDialogFragment.bidInfoFragment = this;
                bidPaymentInfoDialogFragment.show(getActivity().getFragmentManager(), "bid_payment_info");
                break;

            case R.id.project_payment_method_iv://项目付款方式
                BidPaymentMethodDialogFragment bidPaymentMethodDialogFragment = BidPaymentMethodDialogFragment.newInstance();
                bidPaymentMethodDialogFragment.bidInfoFragment = this;
                bidPaymentMethodDialogFragment.show(getActivity().getFragmentManager(), "bid_payment_method");
                break;
            case R.id.bid_attchment_iv:
                BidAttachmentDialogFragment bidAttachmentDialogFragment = BidAttachmentDialogFragment.newInstance();
                bidAttachmentDialogFragment.setmDatas(bidResultFiles);
                bidAttachmentDialogFragment.bidInfoFragment = this;
                bidAttachmentDialogFragment.show(getActivity().getFragmentManager(), "bid_attachment");
                break;
            case R.id.expand_iv:
                if (isExpand) {
                    if (display_layout.getVisibility() == View.VISIBLE) {
                        display_layout.setVisibility(View.GONE);
                        expand_iv.setImageResource(R.drawable.close);
                    } else {
                        display_layout.setVisibility(View.VISIBLE);
                        expand_iv.setImageResource(R.drawable.open);
                    }
                }
                break;
        }
    }

    //必填项校验
    public boolean isRequiredCheck(int checkType) {
        if ((BID_BOND_CHECK == checkType || ALL_CHECK == checkType) && TextUtils.isEmpty(projectBid.getTotalCost())) {
            ToastUtil.showLong("请填写保证金合计");
            return false;
        }

        if (BID_ANALYSIS_CHECK == checkType || ALL_CHECK == checkType) {
            if (TextUtils.isEmpty(projectBid.getQuotationValue())) {
                ToastUtil.showLong("请填写价格分分值");
                return false;
            } else if (TextUtils.isEmpty(projectBid.getTechnologyValue())) {
                ToastUtil.showLong("请填写技术分分值");
                return false;
            } else if (TextUtils.isEmpty(projectBid.getOtherValue())) {
                ToastUtil.showLong("请填写其它分值");
                return false;
            } else if (TextUtils.isEmpty(projectBid.getQuotationStandard())) {
                ToastUtil.showLong("请填写价格分评分标准");
                return false;
            } else if (TextUtils.isEmpty(projectBid.getTechnologyRemark())) {
                ToastUtil.showLong("请填写技术分说明");
                return false;
            } else if (TextUtils.isEmpty(projectBid.getOtherRemark())) {
                ToastUtil.showLong("请填写其它分值说明");
                return false;
            } else if (TextUtils.isEmpty(projectBid.getQuotationRemark())) {
                ToastUtil.showLong("请填写价格分评分说明");
                return false;
            }
        }

        if (BID_PRICE_CHECK == checkType || ALL_CHECK == checkType) {
            if (TextUtils.isEmpty(projectBid.getBidDiscount())) {
                ToastUtil.showLong("请填写投标折扣");
                return false;
            } else if (TextUtils.isEmpty(projectBid.getBidPrice())) {
                ToastUtil.showLong("请填写投标价格");
                return false;
            }
        }

        if (BID_PAYMENT_METHOD_CHECK == checkType || ALL_CHECK == checkType) {
            if (TextUtils.isEmpty(projectBid.getPerBidCost())) {
                ToastUtil.showLong("请填写履约保证金");
                return false;
            } else if (TextUtils.isEmpty(projectBid.getProjectPayType())) {
                ToastUtil.showLong("请填写支付形式");
                return false;
            } else if (TextUtils.isEmpty(projectBid.getProjectPayee())) {
                ToastUtil.showLong("请填写承担方");
                return false;
            }
        }

        return true;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }


}
