package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.PriceApproval;
import com.zxtech.ecs.model.ProjectTrack;
import com.zxtech.ecs.ui.home.quote.QuotedFragment;
import com.zxtech.gks.common.SPUtils;

import java.util.List;

/**
 * Created by syp600 on 2018/7/6.
 */

public class ProjectTrackListAdapter extends BaseQuickAdapter<ProjectTrack, BaseViewHolder> {


    public ProjectTrackListAdapter(int layoutResId, @Nullable List<ProjectTrack> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, ProjectTrack item) {
        holder.setText(R.id.customer_name_tv, item.getCustomerName());
        holder.setText(R.id.visit_reason_tv, item.getVisitReason());
        holder.setText(R.id.visit_date_tv, item.getVisitDate());
        holder.setText(R.id.destination_tv, item.getDestination());
        holder.setText(R.id.sale_user_tv, item.getSalesmanUserName());
        holder.setText(R.id.company_tv, item.getDeptName());
    }

}
