package com.zxtech.mt.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zxtech.mt.common.Constants;
import com.zxtech.mt.entity.CalCallFix;
import com.zxtech.mt.utils.DateUtil;
import com.zxtech.mtos.R;

import java.util.List;

/**
 * Created by Chw on 2016/6/26.
 */
public class ReCallAdapter extends CommonAdapter<CalCallFix>{

    public ReCallAdapter(Context context, List<CalCallFix> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final CalCallFix task, int position) {
        holder.setText(R.id.project_textview,task.getProj_name());
        holder.setText(R.id.address_textview,task.getProj_address());
       // holder.setText(R.id.send_textview, task.getCall_time());
        String callInfo = "";
        if (!TextUtils.isEmpty(task.getCall_person())) {
            callInfo+=task.getCall_person();
        }
        if (!TextUtils.isEmpty(task.getCall_phone())) {
            callInfo+="("+task.getCall_phone()+")";
        }
        holder.setText(R.id.phone_textview,callInfo);
        TextView is_textview = holder.getView(R.id.is_textview);
        TextView function_textview = holder.getView(R.id.function_textview);

        if (Constants.CAL_TASK_STATUS_NOTICE.equals(task.getStatus()))
        {
            function_textview.setText(mContext.getString(R.string.call_status_order));
        } else  if (Constants.CAL_TASK_STATUS_ORDER.equals(task.getStatus()))
        {
            function_textview.setText(mContext.getString(R.string.call_status_start));
        }else if (Constants.CAL_TASK_STATUS_ARRIVE.equals(task.getStatus())){
            function_textview.setText(mContext.getString(R.string.call_status_deal));
        }else if (Constants.CAL_TASK_STATUS_SUBMIT.equals(task.getStatus())){
            function_textview.setText(mContext.getString(R.string.call_status_sign));
        }else {
            function_textview.setText(mContext.getString(R.string.call_status_pause));
        }

        if ("1".equals(task.getTrap_flag())) {
            is_textview.setText(mContext.getString(R.string.trap));
            is_textview.setTextColor(mContext.getResources().getColor(R.color.red));
        }else {
            is_textview.setText(mContext.getString(R.string.not_trap));
            is_textview.setTextColor(mContext.getResources().getColor(R.color.dkgray));
        }

        holder.getView(R.id.phone_textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);

                Uri data = Uri.parse("tel:" + task.getCall_phone());

                intent.setData(data);

                mContext.startActivity(intent);
            }
         });

    }
}
