package com.zxtech.mt.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zxtech.mt.entity.CalCallFix;
import com.zxtech.mtos.R;

import java.util.List;

/**
 * Created by Chw on 2016/6/26.
 */
public class CallStopAdapter extends CommonAdapter<CalCallFix>{

    public CallStopAdapter(Context context, List<CalCallFix> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final CalCallFix task, int position) {
        holder.setText(R.id.project_textview,task.getProj_name());
        holder.setText(R.id.address_textview,task.getProj_address());
        holder.setText(R.id.phone_textview,task.getCall_phone());
        holder.setText(R.id.stop_time_textview,task.getStop_time());
        TextView function_textview = holder.getView(R.id.function_textview);
        function_textview.setText(mContext.getString(R.string.stop_elevator));



    }
}
