package com.zxtech.mt.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zxtech.mt.common.Constants;
import com.zxtech.mt.entity.CalCallFix;
import com.zxtech.mtos.R;

import java.util.List;

/**
 * Created by Chw on 2016/6/26.
 */
public class CallFinishAdapter extends CommonAdapter<CalCallFix>{

    public CallFinishAdapter(Context context, List<CalCallFix> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final CalCallFix task, int position) {
        holder.setText(R.id.project_textview,task.getProj_name());
        holder.setText(R.id.address_textview,task.getProj_address());
        holder.setText(R.id.call_time_textview,task.getCall_time());
        holder.setText(R.id.recover_time_textview,task.getRecover_time());
        TextView function_textview = holder.getView(R.id.function_textview);
        if (Constants.CAL_TASK_STATUS_SUBMIT.equals(task.getStatus())) {
            function_textview.setText(mContext.getString(R.string.call_status_sign));
        }else{
            function_textview.setText(mContext.getString(R.string.call_status_finish));
        }



    }
}
