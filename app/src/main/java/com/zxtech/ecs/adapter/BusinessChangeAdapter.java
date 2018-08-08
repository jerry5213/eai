package com.zxtech.ecs.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ContractChangeSummary;

import java.util.List;

/**
 * Created by syp523 on 2018/7/2.
 */

public class BusinessChangeAdapter extends BaseQuickAdapter<ContractChangeSummary.DicInfoListBusBean, BaseViewHolder> {
    public BusinessChangeAdapter(int layoutResId, @Nullable List<ContractChangeSummary.DicInfoListBusBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContractChangeSummary.DicInfoListBusBean item) {
        helper.setText(R.id.number_tv,String.valueOf(helper.getAdapterPosition()+1));
        helper.setText(R.id.before_tv,item.getChangBefore());
        helper.setText(R.id.after_tv,item.getChangAfter());
    }
}
