package com.zxtech.is.ui.taskme.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.R2;

import java.util.List;
import java.util.Map;

/**
 * Created by syp660 on 2018/4/19.
 */

public class TaskMeCivilReviewAdapter extends BaseQuickAdapter<Map<String, Object>, BaseViewHolder> {
    public TaskMeCivilReviewAdapter(int layoutResId, @Nullable List<Map<String, Object>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, Object> item) {
        helper.setText(R.id.item_task_me_common_a, String.valueOf(item.get("prjName"))) //项目名称
                .setText(R.id.item_task_me_common_b, String.valueOf(item.get("eleName")))// 电梯名称
                .setText(R.id.item_task_me_common_c, String.valueOf(item.get("taskTime")))//设置日期
                .setText(R.id.item_task_me_common_e, String.valueOf(item.get("taskStep")));//设置任务步骤
        if ("2".equals(String.valueOf(item.get("taskResult")))) {
            helper.setGone(R.id.item_task_me_common_e, false).setGone(R.id.item_task_me_common_d, true);
        }
    }

}
