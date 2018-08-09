package com.zxtech.mt.adapter;

import android.content.Context;
import android.text.TextUtils;


import com.zxtech.mt.entity.BaseElevator;
import com.zxtech.mtos.R;

import java.util.List;

/**
 * Created by Chw on 2016/8/10.
 */
public class ElevatorAdapter extends CommonAdapter<BaseElevator> {
    public ElevatorAdapter(Context context, List<BaseElevator> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, BaseElevator baseElevator, int position) {
        StringBuffer sb = new StringBuffer();
        sb.append(mContext.getString(R.string.project_name$));
        sb.append(TextUtils.isEmpty(baseElevator.getProj_name()) ? "" : baseElevator.getProj_name());
        sb.append("\n");
        sb.append(mContext.getString(R.string.elevator_brand$));
        sb.append(TextUtils.isEmpty(baseElevator.getElevator_brand()) ? "" : baseElevator.getElevator_brand());
        sb.append("\n");
        sb.append(mContext.getString(R.string.elevator_code));
        sb.append(TextUtils.isEmpty(baseElevator.getElevator_code()) ? "" : baseElevator.getElevator_code());
        sb.append("\n");
        sb.append(mContext.getString(R.string.last_maintenance_date$));
        sb.append(TextUtils.isEmpty(baseElevator.getLast_date()) ? "" : baseElevator.getLast_date());
        sb.append("\n");
        sb.append(mContext.getString(R.string.next_maintenance_date$));
        sb.append(TextUtils.isEmpty(baseElevator.getNext_date()) ? "" : baseElevator.getNext_date());
        sb.append("\n");
        sb.append(mContext.getString(R.string.annual_date$));
        sb.append(TextUtils.isEmpty(baseElevator.getCheck_date()) ? "" : baseElevator.getCheck_date());
        sb.append("\n");
        holder.setText(R.id.content_textview,sb.toString());
    }
}
