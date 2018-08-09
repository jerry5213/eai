package com.zxtech.is.ui.transport.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.s1.IsPlanS1;

import java.util.List;
import java.util.Map;

/**
 * Created by syp660 on 2018/4/19.
 */

public class TransportAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    public TransportAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_civilre_civilreview);
        addItemType(TYPE_LEVEL_1, R.layout.item_ele_civilreview);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        if (holder.getItemViewType() == TYPE_LEVEL_0) {
            final IsPlanS1.IsPlanPro parent = (IsPlanS1.IsPlanPro) item;
            holder.setText(R.id.item_project, parent.getPrjName());

            if (parent.isExpanded()) {
                holder.setGone(R.id.item_project_take, false).setGone(R.id.item_project_selec, true);
            } else {
                holder.setGone(R.id.item_project_selec, false).setGone(R.id.item_project_take, true);
            }


        } else if (holder.getItemViewType() == TYPE_LEVEL_1) {
            final IsPlanS1.IsPlanPro.IsProEle parent1 = (IsPlanS1.IsPlanPro.IsProEle) item;
            holder.setText(R.id.item_ele_name, parent1.getEleName());
            holder.setText(R.id.item_ele_date, parent1.getConfirmTime());
            holder.setText(R.id.item_ele_cusnamr, parent1.getUserName());
            holder.setText(R.id.item_ele_status, parent1.getStatus());

//                if (parent1.isExpanded()){
//                    holder.setGone(R.id.item_project_take, false).setGone(R.id.item_project_selec, true);
//                }else{
//                    holder.setGone(R.id.item_project_selec, false).setGone(R.id.item_project_take, true);
//                }

        }
    }

}
