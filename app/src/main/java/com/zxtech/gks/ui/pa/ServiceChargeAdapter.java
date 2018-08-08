package com.zxtech.gks.ui.pa;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zxtech.ecs.R;
import com.zxtech.gks.model.vo.PrProductDetail.ServiceChargeVO;

import java.util.List;

/**
 * Created by SYP521 on 2017/12/7.
 */

public class ServiceChargeAdapter extends CommonAdapter<ServiceChargeVO> {

    public ServiceChargeAdapter(Context context, int layoutId, List<ServiceChargeVO> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, ServiceChargeVO serviceChargeVO, int position) {

        holder.setText(R.id.tv_name,mContext.getString(R.string.approver)+"："+serviceChargeVO.getTransactorUserName());
        holder.setText(R.id.tv_price,mContext.getString(R.string.amount2)+"："+serviceChargeVO.getAgentCommission());
        holder.setText(R.id.tv_rate,mContext.getString(R.string.service_rate)+"："+serviceChargeVO.getAgentCommissionRate()+"%");
        holder.setText(R.id.tv_float_rate,mContext.getString(R.string.ht_txt6)+"："+serviceChargeVO.getRealFloatingRate()+"%");
    }
}
