package com.zxtech.ecs.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ProductCancel;

import java.util.List;

/**
 * Created by syp523 on 2018/7/30.
 */

public class ProductCancelAdapter extends BaseQuickAdapter<ProductCancel,BaseViewHolder> {

    private Drawable edit = null;
    private Drawable down = null;
    private Drawable delete = null;
    private boolean isEdit;

    public ProductCancelAdapter(Context context, int layoutResId, @Nullable List<ProductCancel> data,boolean isEdit) {
        super(layoutResId, data);
        edit = context.getResources().getDrawable(R.drawable.edit_red);
        edit.setBounds(0, 0, edit.getMinimumWidth(), edit.getMinimumHeight());
        down = context.getResources().getDrawable(R.drawable.down);
        down.setBounds(0, 0, edit.getMinimumWidth(), edit.getMinimumHeight());
        delete = context.getResources().getDrawable(R.drawable.image_delete);
        delete.setBounds(0, 0, delete.getMinimumWidth(), delete.getMinimumHeight());
        this.isEdit = isEdit;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductCancel item) {
        final int position = helper.getAdapterPosition();
        TextView no_tv = helper.getView(R.id.no_tv);
        no_tv.setText(mContext.getString(R.string.product) + (position + 1));
        if (isEdit) {
            no_tv.setCompoundDrawables(null, null, delete, null);
        }else{
            no_tv.setCompoundDrawables(null, null, null, null);
        }


        helper.setText(R.id.quote_number_tv,item.getEqsProductNo());
        helper.setText(R.id.product_name_tv,item.getElevatorProduct());
        helper.setText(R.id.count_tv,item.getCancelCount());
        helper.setText(R.id.product_params_tv,item.getElevatorType());
        helper.setText(R.id.elevator_tv,item.getCancelElevotorNo());
        TextView project_state_tv = helper.getView(R.id.project_state_tv);
        project_state_tv.setText(item.getCancelStateValue());
        TextView payment_tv = helper.getView(R.id.payment_tv);
        payment_tv.setText(item.getPaymentValue());
        TextView reason_tv = helper.getView(R.id.reason_tv);
        reason_tv.setText(item.getCancelReason());
        TextView solution_tv = helper.getView(R.id.solution_tv);
        solution_tv.setText(item.getCancelMoneyDealRemark());

        if (isEdit) {
            project_state_tv.setCompoundDrawables(null, null, down, null);
            payment_tv.setCompoundDrawables(null, null, down, null);
            reason_tv.setCompoundDrawables(null, null, edit, null);
            solution_tv.setCompoundDrawables(null, null, edit, null);
        }else{
            project_state_tv.setCompoundDrawables(null, null, null, null);
            payment_tv.setCompoundDrawables(null, null, null, null);
            reason_tv.setCompoundDrawables(null, null, null, null);
            solution_tv.setCompoundDrawables(null, null, null, null);
        }
        helper.addOnClickListener(R.id.no_tv).addOnClickListener(R.id.project_state_tv).addOnClickListener(R.id.payment_tv).addOnClickListener(R.id.reason_tv).addOnClickListener(R.id.solution_tv);

    }
}
