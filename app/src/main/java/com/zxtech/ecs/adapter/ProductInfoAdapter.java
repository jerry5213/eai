package com.zxtech.ecs.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ProductInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syp523 on 2018/6/28.
 */

public class ProductInfoAdapter extends BaseQuickAdapter<ProductInfo, BaseViewHolder> {

    private List<Integer> selectList = new ArrayList<>();

    private Drawable match = null;
    private Drawable match_check = null;
    private Drawable edit = null;
    private boolean isSelected = true;
    private boolean isEditColumn = true;

    /**
     * 报价产品和合同变更前，后界面复用
     * 报价 可选择 无梯号变更功能
     * 合同变更前 不可选择 有梯号变更功能
     * 合同变更后 可选择 无梯号变更功能
     * @param context
     * @param layoutResId
     * @param data
     * @param isSelected 选择功能是否启用
     * @param isEditColumn 字段功能是否启用
     */
    public ProductInfoAdapter(Context context, int layoutResId, @Nullable List<ProductInfo> data, boolean isSelected, boolean isEditColumn) {
        super(layoutResId, data);
        this.isSelected = isSelected;
        this.isEditColumn = isEditColumn;
        match = context.getResources().getDrawable(R.drawable.match);
        match_check = context.getResources().getDrawable(R.drawable.match_check);
        edit = context.getResources().getDrawable(R.drawable.edit_red);
        match.setBounds(0, 0, match.getMinimumWidth(), match.getMinimumHeight());
        match_check.setBounds(0, 0, match_check.getMinimumWidth(), match_check.getMinimumHeight());
        edit.setBounds(0, 0, edit.getMinimumWidth(), edit.getMinimumHeight());
    }


    public List<Integer> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<Integer> selectList) {
        this.selectList = selectList;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductInfo product) {
        final int position = helper.getAdapterPosition();
        helper.setText(R.id.column0, mContext.getString(R.string.product) + (position + 1));
        helper.setText(R.id.column1, product.getEQS_ProductNo());
        helper.setText(R.id.column2, product.getElevatorProduct());
        helper.setText(R.id.column3, String.valueOf(product.getElevatorCount()));
        helper.setText(R.id.column4, product.getAngle());
        helper.setText(R.id.column5, product.getDeliveryDate());
        helper.setText(R.id.column6, product.getRealPrice_Equi());
        TextView column7 = helper.getView(R.id.column7);
        if (isEditColumn && !TextUtils.isEmpty(product.getCutStringElevatorNo())) {
            column7.setCompoundDrawables(null, null, edit, null);
        } else {
            column7.setCompoundDrawables(null, null, null, null);
        }
        helper.setText(R.id.column7, product.getCutStringElevatorNo()).addOnClickListener(R.id.column7);
        helper.setText(R.id.column8, product.getVersionNum());
        helper.setText(R.id.column9, product.getIsConfirmVersion() ? mContext.getString(R.string.confirmed) : mContext.getString(R.string.to_be_confirmed)).addOnClickListener(R.id.column9);
        helper.setText(R.id.column10, product.getNonState());
        helper.setText(R.id.column11, product.getCreateDateStr());
        helper.setText(R.id.column12, product.getInstanceNodeName());
        //后加字段
        helper.setText(R.id.column13, product.getGuaranteeDate());
        helper.setText(R.id.column14, product.getFreeInsuranceDate());

        final TextView column0 = helper.getView(R.id.column0);
        if (isSelected) {
            column0.setEnabled(true);
            if (selectList.contains(position)) {
                column0.setCompoundDrawables(null, null, match_check, null);
            } else {
                column0.setCompoundDrawables(null, null, match, null);
            }
        } else {
            column0.setEnabled(false);
        }


        column0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectList.contains(position)) {
                    selectList.remove(Integer.valueOf(position));
                    column0.setCompoundDrawables(null, null, match, null);
                } else {
                    selectList.add(position);
                    column0.setCompoundDrawables(null, null, match_check, null);
                }
            }
        });
    }
}