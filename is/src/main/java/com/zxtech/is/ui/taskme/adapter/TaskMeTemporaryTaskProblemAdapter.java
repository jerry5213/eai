package com.zxtech.is.ui.taskme.adapter;

import android.support.annotation.Nullable;
import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.temporary.TemporaryTask;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by syp602 on 2018/4/19.
 */

public class TaskMeTemporaryTaskProblemAdapter extends BaseQuickAdapter<TemporaryTask, BaseViewHolder> {
    public TaskMeTemporaryTaskProblemAdapter(int layoutResId, @Nullable List<TemporaryTask> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final TemporaryTask item) {
        String flagName = "";
        if ("questionAssign_fe".equals(item.getTaskKey())) {
            flagName = mContext.getString(R.string.is_temporary_task_assign);
        } else {
            flagName = mContext.getString(R.string.is_temporary_task_confirm);
        }

        String contentHtml = "<strong>" + item.getOptionName() + "：</strong>" + item.getRemark();
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        helper.setText(R.id.item_task_me_temporary_task_temporary_content, Html.fromHtml(contentHtml))
                .setText(R.id.item_task_me_temporary_task_temporary_name, item.getCreateUserName())
                .setText(R.id.item_task_me_temporary_task_temporary_date, "：" + f.format(item.getCreateTime()))
                .setText(R.id.item_task_me_temporary_task_temporary_flag, flagName);
    }
}
