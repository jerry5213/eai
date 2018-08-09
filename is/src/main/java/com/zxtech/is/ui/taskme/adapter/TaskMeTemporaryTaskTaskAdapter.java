package com.zxtech.is.ui.taskme.adapter;

import android.support.annotation.Nullable;

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

public class TaskMeTemporaryTaskTaskAdapter extends BaseQuickAdapter<TemporaryTask, BaseViewHolder> {
    public TaskMeTemporaryTaskTaskAdapter(int layoutResId, @Nullable List<TemporaryTask> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final TemporaryTask item) {

        if (item.getIsVisible() == null) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
            helper.setText(R.id.item_task_me_temporary_task_type, item.getOptionName())
                    .setText(R.id.item_task_me_temporary_task_project, item.getProjectName())
                    .setText(R.id.item_task_me_temporary_task_elevator, item.getElevatorName())
                    .setText(R.id.item_task_me_temporary_task_date, "：" + f.format(item.getStartdate()));

            //添加“执行任务”按钮事件
            helper.addOnClickListener(R.id.item_task_me_temporary_task_btn);
            //站位隐藏
            helper.setVisible(R.id.item_task_me_temporary_task_linearLayout, true);
        } else if (!item.getIsVisible()) {
            //站位隐藏
            helper.setVisible(R.id.item_task_me_temporary_task_linearLayout, false);
        }
    }
}
