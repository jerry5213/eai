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

public class SpecChangeAdapter extends BaseQuickAdapter<ContractChangeSummary.DicInfoListTecBean, BaseViewHolder> {
    public SpecChangeAdapter(int layoutResId, @Nullable List<ContractChangeSummary.DicInfoListTecBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContractChangeSummary.DicInfoListTecBean item) {
        helper.setText(R.id.elevator_tv, item.getElevatorNo());
        helper.setText(R.id.equipment_tv, item.getDeviceNo());
        helper.setText(R.id.type_tv, item.getElevatorType());
        helper.setText(R.id.before_tv, item.getChangBefore());
        helper.setText(R.id.after_tv, item.getChangAfter());
    }
}
