package com.zxtech.is.ui.task.adpter;

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.s1.S1Elevator;
import com.zxtech.is.ui.task.activity.S1TaskReviewActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by syp600 on 2018/5/4.
 */

public class S1ElevatorReviewAdpter extends BaseQuickAdapter<S1Elevator, BaseViewHolder> {

    private boolean checkAllFlag = false;

    private Map<String, Boolean> checkMap = new HashMap<>();

    public S1ElevatorReviewAdpter(int layoutResId, @Nullable List<S1Elevator> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final S1Elevator item) {
        helper.setChecked(R.id.cb_is_checkbox, item.isCheck()).
                setText(R.id.tv_is_elevator_no, item.getARKTX()).
                setText(R.id.tv_is_plan3CDate, item.getPlan3cdate()).
                setText(R.id.tv_is_moneyS2Date, item.getMoneys2date());
        //井道
        if ("1".equals(item.getWellholeitem())) {
            helper.setText(R.id.tv_is_wellholeItem, R.string.is_qualified);
        } else if ("2".equals(item.getWellholeitem())) {
            helper.setText(R.id.tv_is_wellholeItem, R.string.is_unqualified);
        }
        //告知工地必备条件
        if ("1".equals(item.getNotifyitem())) {
            helper.setText(R.id.tv_is_notifyItem, R.string.is_notice);
        } else if ("2".equals(item.getNotifyitem())) {
            helper.setText(R.id.tv_is_notifyItem, R.string.is_unnotice);
        }


        checkMap.put(item.getElevatorguid(), item.isCheck());

        CheckBox cb_is_checkbox = helper.getView(R.id.cb_is_checkbox);
        //行选中
        cb_is_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    item.setCheck(true);
                } else {
                    item.setCheck(false);
                }
                checkMap.put(item.getElevatorguid(), item.isCheck());
                boolean flag = true;
                for (boolean value : checkMap.values()) {
                    if (!value) {
                        flag = false;
                        break;
                    }
                }
                //多选框选中状态
                ((S1TaskReviewActivity) mContext).setCb_is_checkall(flag);
            }
        });

    }

    //获取选中条数
    public int getSelectCount() {
        int i = 0;
        for (boolean value : checkMap.values()) {
            if (value) {
                i++;
            }
        }
        return i;
    }

    public boolean isCheckAllFlag() {
        return checkAllFlag;
    }

    public void setCheckAllFlag(boolean checkAllFlag) {
        this.checkAllFlag = checkAllFlag;
    }
}
