package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.AccountPayment;

import java.util.List;

/**
 * Created by syp523 on 2018/7/9.
 */

public class AccountPaymentAdapter extends BaseQuickAdapter<AccountPayment, BaseViewHolder> {
    private int selectedPosition = -1;
    private boolean isSelected;

    public AccountPaymentAdapter(int layoutResId, @Nullable List<AccountPayment> data,boolean isSelected) {
        super(layoutResId, data);
        this.isSelected = isSelected;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AccountPayment item) {
        if (isSelected) {
            helper.setText(R.id.status_tv, item.getStatusText());
        }else{
            helper.setText(R.id.status_tv, item.getVersionNo());
        }
        helper.setText(R.id.number_tv, item.getSerialNo()+"("+item.getImportPlace()+")");
        helper.setText(R.id.remark_tv, item.getRemark());
        helper.setText(R.id.type_tv, item.getCopyType());
        helper.setText(R.id.remittance_unit_tv, item.getRemittanceUnit());
        helper.setText(R.id.original_money_tv, item.getOriginalMoney());
        helper.setText(R.id.date_tv, item.getPayDate());

        helper.setGone(R.id.detail_layout, item.isExpand());

        helper.setText(R.id.supporter_tv, item.getInfoSupporter());
        helper.setText(R.id.company_tv, item.getSaleBranchName());
        helper.setText(R.id.allot_money_tv, item.getAllotMoney());
        helper.setText(R.id.project_tv, item.getProjectName());
        helper.setText(R.id.contract_no_tv, item.getContractNo());
        helper.setText(R.id.contract_archives_tv, item.getContractArchivesNo());
        helper.setText(R.id.invoice_unit_tv, item.getInvoiceUnit());

        helper.setGone(R.id.problem_tv,!TextUtils.isEmpty(item.getPayProblem()) && !"已解决".equals(item.getPayDealStatus()));
        helper.setGone(R.id.history_tv,!"A".equals(item.getVersionNo()));//不是A版本有历史版本

        ImageView check_iv = helper.getView(R.id.check_iv);
        helper.setGone(R.id.check_iv,this.isSelected);
        if (this.selectedPosition == helper.getAdapterPosition()) {
            check_iv.setImageResource(R.drawable.match_check);
        }else{
            check_iv.setImageResource(R.drawable.match);
        }

        check_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = helper.getAdapterPosition();
                notifyDataSetChanged();
            }
        });

        ImageView expand_iv = helper.getView(R.id.expand_iv);
        if (item.isExpand()) {
            expand_iv.setImageResource(R.drawable.expand_up);
        } else {
            expand_iv.setImageResource(R.drawable.expand_down);
        }

        expand_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setExpand(!item.isExpand());
                notifyItemChanged(helper.getAdapterPosition());
            }
        });

    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}
