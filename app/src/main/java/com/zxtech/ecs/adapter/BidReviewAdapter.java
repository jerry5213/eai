package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.BidReview;
import com.zxtech.ecs.model.ContractChange;

import java.util.List;

/**
 * Created by syp523 on 2018/3/31.
 */

public class BidReviewAdapter extends BaseQuickAdapter<BidReview, BaseViewHolder> {
    private int selectedPosition = -1;
    private int state;
    public BidReviewAdapter(int layoutResId, @Nullable List<BidReview> data,int state) {
        super(layoutResId, data);
        this.state = state;
    }

    @Override
    protected void convert(final BaseViewHolder holder, BidReview item) {
        holder.setGone(R.id.check_iv,state == 1);
        holder.setText(R.id.project_no_tv, item.getProjectNo());
        holder.setText(R.id.customer_tv, item.getCustomerName());
        holder.setText(R.id.create_date_tv, item.getNodeCreateTime());
        holder.setText(R.id.project_tv, item.getProjectName());
        holder.setText(R.id.sale_user_tv, item.getSalesmanUserName());
        holder.setText(R.id.company_tv, item.getBranchName());
        holder.setText(R.id.process_tv, item.getProcess());
        holder.setText(R.id.is_bid_tv, item.getIsBiddingText());
        ImageView check_iv = holder.getView(R.id.check_iv);
        holder.setGone(R.id.is_bid_tv,state == 1);
        holder.setGone(R.id.check_iv,state == 1 );
        if (this.selectedPosition == holder.getAdapterPosition()) {
            check_iv.setImageResource(R.drawable.match_check);
        }else{
            check_iv.setImageResource(R.drawable.match);
        }

        check_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}
