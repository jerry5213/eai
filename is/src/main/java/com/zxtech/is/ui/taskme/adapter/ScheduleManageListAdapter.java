package com.zxtech.is.ui.taskme.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.util.StringUtils;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.schedule.ScheduleManager;

import java.util.List;


/**
 * Created by syp660 on 2018/4/19.
 */

public class ScheduleManageListAdapter extends BaseQuickAdapter<ScheduleManager, BaseViewHolder> {
    public ScheduleManageListAdapter(int layoutResId, @Nullable List<ScheduleManager> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScheduleManager item) {
        helper.setText(R.id.sch_manager_time, item.getScheduleTime() + mContext.getResources().getString(R.string.is_tian))
                .setText(R.id.time_list_node_title, item.getTitle())
                .setText(R.id.time_list_node_status, item.getStatus())
                .setText(R.id.time_list_node_apprname, item.getsUserName())
                .setText(R.id.time_list_planstartdate, item.getPlanStartDate())
                .setText(R.id.time_list_planfinishdate, item.getPlanFinishDate());
        if (StringUtils.isEmpty(item.getActualFinishDate())) {
            helper.setText(R.id.time_list_actualtartdate, R.string.is_no_data_detail);
        } else {
            helper.setText(R.id.time_list_actualtartdate, item.getActualFinishDate());
        }
        if (StringUtils.isEmpty(item.getsUserName())) {
            helper.setText(R.id.time_list_node_apprname, R.string.is_no_data_detail);
        } else {
            helper.setText(R.id.time_list_node_apprname, item.getsUserName());
        }
        if (StringUtils.isEmpty(item.getbUserName())) {
            helper.setText(R.id.time_list_node_tranname, R.string.is_no_data_detail);
        } else {
            helper.setText(R.id.time_list_node_tranname, item.getbUserName());
        }
        if ("0".equals(item.getFlag())) {
            helper.setGone(R.id.time_list_node, false).setGone(R.id.time_list_node_green, true);
        }
        if ("scheduleManage_gj_fe".equals(item.getTaskDefKey()) || "scheduleManage_gj_pe".equals(item.getTaskDefKey()) || "scheduleManage_cj_fe".equals(item.getTaskDefKey()) || "scheduleManage_cj_pe".equals(item.getTaskDefKey())) {
            helper.setGone(R.id.tv_qms_express, true);
        }

        //          .setBackgroundRes(R.id.item_task_me_common_d, mContext.getResources().getDrawable(R.drawable.button_dark_red_radius));
//                        tDrawable(R.drawable.button_dark_red_radius));drawable
    }

}
