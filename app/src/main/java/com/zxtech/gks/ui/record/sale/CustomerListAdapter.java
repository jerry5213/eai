package com.zxtech.gks.ui.record.sale;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zxtech.ecs.R;
import com.zxtech.gks.model.vo.Customer;

import java.util.List;

/**
 * Created by syp523 on 2017/11/30.
 */

public class CustomerListAdapter extends BaseQuickAdapter<Customer, BaseViewHolder> {

    private Integer selectedPosition = -1;

    public CustomerListAdapter(int layoutResId, @Nullable List<Customer> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Customer item) {
        helper.setText(R.id.contacts_name, item.getCustomerContact());
        helper.setText(R.id.customer_name, item.getCustomerName());
        helper.setText(R.id.tv_address, item.getCustomerAdd());

        ImageView selected_iv = helper.getView(R.id.selected_iv);
        if (helper.getAdapterPosition() == selectedPosition) {
            selected_iv.setImageResource(R.drawable.match_check);
        } else {
            selected_iv.setImageResource(R.drawable.match);
        }
        helper.addOnClickListener(R.id.selected_iv);
    }

    public void setSelectedPosition(Integer selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    public Integer getSelectedPosition() {
        return selectedPosition;
    }

    //    public CustomerListAdapter(Context context, int layoutId, List<Customer> datas) {
//        super(context, layoutId, datas);
//    }
//
//    @Override
//    protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, Customer customer, final int position) {
//
//        holder.setText(R.id.branch_name,customer.getCustomerProperty());
//        holder.setText(R.id.customer_name,customer.getCustomerName());
//        holder.setText(R.id.tv_address,customer.getCustomerAdd());
//
//        ImageView selected_iv = holder.getView(R.id.selected_iv);
//        if (position == selectedPosition) {
//            selected_iv.setImageResource(R.drawable.match_check);
//        }else{
//            selected_iv.setImageResource(R.drawable.match);
//        }
//
//        selected_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectedPosition = position;
//                notifyDataSetChanged();
//            }
//        });
//
//    }

}
