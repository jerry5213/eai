package com.zxtech.mt.adapter;

import android.content.Context;

import com.zxtech.mt.entity.MtAccessoryOrderDetail;
import com.zxtech.mtos.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Chw on 2016/8/12.
 */
public class OrderDetailAdapter extends CommonAdapter<MtAccessoryOrderDetail> {
    private DecimalFormat fnum = null;
    public OrderDetailAdapter(Context context, List<MtAccessoryOrderDetail> datas, int layoutId) {
        super(context, datas, layoutId);
        fnum = new DecimalFormat("##0.00");
    }

    @Override
    public void convert(ViewHolder holder, MtAccessoryOrderDetail mtAccessoryOrderDetail, int position) {
        holder.setText(R.id.acccode_textview,mtAccessoryOrderDetail.getAcc_code());
        holder.setText(R.id.accname_textview,mtAccessoryOrderDetail.getAcc_name());
        holder.setText(R.id.number_textview,mtAccessoryOrderDetail.getAcc_count()+"");
        holder.setText(R.id.price_textview,fnum.format(mtAccessoryOrderDetail.getAcc_real_price()));
    }
}
