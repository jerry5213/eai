package com.zxtech.is.ui.install.adapter;

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

public class InstallAdapter extends BaseQuickAdapter<Map<String, Object>, BaseViewHolder> {
    public InstallAdapter(int layoutResId, @Nullable List<Map<String, Object>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, Object> item) {
        helper.setText(R.id.item_task_me_common_a, String.valueOf(item.get("kName"))) //项目名称
                .setText(R.id.item_task_me_common_b, String.valueOf(item.get("aName")))// 电梯名称
                .setText(R.id.item_task_me_common_c, String.valueOf(item.get("schedule")) + mContext.getResources().getString(R.string.is_tian));//工期

        if ("scheduleManage_az_fe".equals(item.get("taskDefKey"))) {
            helper.setText(R.id.item_task_me_common_e, R.string.is_scheduleManage_az_fe);
        } else if ("scheduleManage_ts_fe".equals(item.get("taskDefKey"))) {
            helper.setText(R.id.item_task_me_common_e, R.string.is_scheduleManage_ts_fe);
        } else if ("scheduleManage_cj_fe".equals(item.get("taskDefKey"))) {
            helper.setText(R.id.item_task_me_common_e, R.string.is_scheduleManage_cj_fe);
        } else if ("scheduleManage_cj_pe".equals(item.get("taskDefKey"))) {
            helper.setText(R.id.item_task_me_common_e, R.string.is_scheduleManage_cj_pe);
        } else if ("scheduleManage_gj_fe".equals(item.get("taskDefKey"))) {
            helper.setText(R.id.item_task_me_common_e, R.string.is_scheduleManage_gj_fe);
        } else if ("scheduleManage_gj_pe".equals(item.get("taskDefKey"))) {
            helper.setText(R.id.item_task_me_common_e, R.string.is_scheduleManage_gj_pe);
        } else if ("scheduleManage_jf_fe".equals(item.get("taskDefKey"))) {
            helper.setText(R.id.item_task_me_common_e, R.string.is_scheduleManage_jf_fe);
        } else {
            helper.setText(R.id.item_task_me_common_e, R.string.is_scheduleManage_jf_wc);
        }
    }
}
