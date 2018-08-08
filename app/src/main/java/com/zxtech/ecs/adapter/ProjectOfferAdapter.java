package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ProjectQuote;

import java.util.List;

/**
 * Created by syp523 on 2018/3/31.
 */

public class ProjectOfferAdapter extends BaseQuickAdapter<ProjectQuote,BaseViewHolder> {
    public ProjectOfferAdapter(int layoutResId, @Nullable List<ProjectQuote> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, ProjectQuote po) {
        holder.setText(R.id.project_no_tv, po.getProjectNo());
        holder.setText(R.id.customer_tv, po.getCustomerName());
        holder.setText(R.id.create_date_tv, po.getCreateTime());
        holder.setText(R.id.project_tv, po.getProjectName());
        holder.setText(R.id.sale_user_tv, po.getSalesmanUserName());
        holder.setText(R.id.company_tv, po.getBranchName());
        holder.setText(R.id.process_tv, po.getProcess());
    }
}
