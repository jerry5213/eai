package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ContractChange;
import com.zxtech.ecs.model.ProjectQuote;
import com.zxtech.ecs.util.DateUtil;

import java.util.List;

/**
 * Created by syp523 on 2018/3/31.
 */

public class ContractChangeAdapter extends BaseQuickAdapter<ContractChange, BaseViewHolder> {
    public ContractChangeAdapter(int layoutResId, @Nullable List<ContractChange> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, ContractChange item) {
        holder.setText(R.id.project_no_tv, item.getProjectNo());
        holder.setText(R.id.customer_tv, item.getCustomerName());
        holder.setText(R.id.create_date_tv, item.getStartTime());
        holder.setText(R.id.project_tv, item.getProjectName());
        holder.setText(R.id.sale_user_tv, item.getSalesmanUserName());
        holder.setText(R.id.company_tv, item.getBranchName());
        holder.setText(R.id.process_tv, item.getProcess());
    }
}
