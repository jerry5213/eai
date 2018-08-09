package com.zxtech.mt.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.zxtech.mt.common.Constants;
import com.zxtech.mt.entity.MtWorkPlan;
import com.zxtech.mtos.R;

import java.util.List;

/**
 * Created by Chw on 2016/6/22.
 */
public class MtFinishAdapter extends CommonAdapter<MtWorkPlan>{


    public MtFinishAdapter(Context context, List<MtWorkPlan> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final MtWorkPlan mtWorkPlan, final int position) {
        holder.setText(R.id.number_textview,mtWorkPlan.getElevator_code());
        holder.setText(R.id.project_textview,mtWorkPlan.getProj_name());
        holder.setText(R.id.mtdate_textview,mtWorkPlan.getPlan_date());
        holder.setText(R.id.type_textview,mtWorkPlan.getWork_type_name());
        holder.setText(R.id.real_textview,mtWorkPlan.getReal_date());
        holder.setText(R.id.brand_textview,mtWorkPlan.getElevator_brand());
        if (Constants.MT_WORK_STATUS_FINISH.equals(mtWorkPlan.getStatus())){
            holder.setText(R.id.re_textview, mContext.getString(R.string.mt_status_finish));
        }else if (Constants.MT_WORK_STATUS_WAIT.equals(mtWorkPlan.getStatus())){ //工作 改为 待保养
            holder.setText(R.id.re_textview,mContext.getString(R.string.mt_status_wait));
        }else if (Constants.MT_WORK_STATUS_IN.equals(mtWorkPlan.getStatus())){ //处理 改为 保养中
            holder.setText(R.id.re_textview,mContext.getString(R.string.mt_status_in));
        }else if (Constants.MT_WORK_STATUS_PAUSE.equals(mtWorkPlan.getStatus())){
            holder.setText(R.id.re_textview,mContext.getString(R.string.mt_status_pause));
        } else if (Constants.MT_WORK_STATUS_SUBMIT.equals(mtWorkPlan.getStatus())){
            holder.setText(R.id.re_textview,mContext.getString(R.string.mt_status_sign));
        }else{
            holder.setText(R.id.re_textview,mContext.getString(R.string.mt_status_in));
        }


    }
}

