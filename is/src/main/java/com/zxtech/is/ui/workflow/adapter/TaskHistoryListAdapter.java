package com.zxtech.is.ui.workflow.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.schedule.ScheduleManager;

import java.util.List;
import java.util.Map;


/**
 * Created by syp660 on 2018/4/19.
 */

public class TaskHistoryListAdapter extends BaseQuickAdapter<Map<String, Object>, BaseViewHolder> {
    public TaskHistoryListAdapter(int layoutResId, @Nullable List<Map<String, Object>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, Object> item) {
        if (item.get("title") != null) {
            helper.setText(R.id.task_title, item.get("title").toString());
        }
        if (item.get("userName") != null) {
            helper.setText(R.id.task_tranname, item.get("userName").toString());
        } else {
            helper.setText(R.id.task_tranname, R.string.is_no_data_detail);
        }
        if (item.get("result") != null) {
            if ("1".equals(item.get("result").toString())) {
                helper.setText(R.id.task_status, R.string.is_agree);
            } else if ("2".equals(item.get("result").toString())) {
                helper.setText(R.id.task_status, R.string.is_no_agree);
            }
        }
        if (item.get("startTime") != null) {
            helper.setText(R.id.task_startdate, item.get("startTime").toString());
        } else {
            helper.setText(R.id.task_startdate, R.string.is_no_data_detail);
        }
        if (item.get("endTime") != null) {
            helper.setText(R.id.task_finishdate, item.get("endTime").toString());
        } else {
            helper.setText(R.id.task_finishdate, R.string.is_no_data_detail);
        }
        if (item.get("comments") != null) {
            helper.setText(R.id.task_comments, item.get("comments").toString());
        } else {
            helper.setText(R.id.task_comments, R.string.is_no_data_detail);
        }
        if ("0".equals(item.get("flag"))) {
            helper.setGone(R.id.task_list_node, false).setGone(R.id.task_list_node_green, true);
        }
    }

}
