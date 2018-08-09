package com.zxtech.is.ui.mt.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by syp660 on 2018/4/19.
 */

public class MtProjectProcessAdapter extends BaseQuickAdapter<Map<String, String>, BaseViewHolder> {
    private static final String DONE = "1";
    private static final String DOING = "2";
    private static final String UNDO = "3";
    public MtProjectProcessAdapter(int layoutResId, @Nullable List<Map<String, String>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, String> item) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        helper.setText(R.id.item_task_me_common_b, String.valueOf(item.get("elevatorNo")))
                .setText(R.id.item_task_me_common_c, sdf.format(new Date()))
                .setVisible(R.id.item_task_me_enter,false);
        String stateFlag = item.get("stateFlag");
        if (DONE.equals(stateFlag)) {
            helper.setText(R.id.item_task_me_common_e, "已完成");
        }
        if (DOING.equals(stateFlag)) {
            helper.setText(R.id.item_task_me_common_e, "正在处理");
        }
        if (UNDO.equals(stateFlag)) {
            helper.setText(R.id.item_task_me_common_e, "未处理");
        }


    }

}
