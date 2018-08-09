package com.zxtech.mt.adapter;

import android.content.Context;

import com.zxtech.mt.entity.MtAccessoryOrder;
import com.zxtech.mtos.R;

import java.util.List;

/**
 * Created by Chw on 2016/7/3.
 */
public class PartAskAdapter extends CommonAdapter<MtAccessoryOrder>{

    public PartAskAdapter(Context context, List<MtAccessoryOrder> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, MtAccessoryOrder order, int position) {
        holder.setText(R.id.proj_textview,order.getProj_name());
        holder.setText(R.id.address_textview,order.getRec_address());
        holder.setText(R.id.person_textview,order.getRec_person());
        holder.setText(R.id.phone_textview,order.getRec_phone());
        holder.setText(R.id.date_textview, order.getHope_rec_date());
        holder.setText(R.id.price_textview,order.getTotal_sum());


    }
}
