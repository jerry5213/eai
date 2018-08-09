package com.zxtech.is.ui.task.adpter;

import android.app.DatePickerDialog;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.S1Elevator;
import com.zxtech.is.util.DateUtil;

import java.util.Calendar;
import java.util.List;

/**
 * Created by duomi on 2018/4/8.
 */

public class S1TaskElevatorAdpter extends BaseQuickAdapter<S1Elevator, BaseViewHolder> {

    public S1TaskElevatorAdpter(int layoutResId, @Nullable List<S1Elevator> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final S1Elevator item) {
//        helper.setText(R.id.tv_is_elevator_no,item.getNo());
        helper.setText(R.id.et_qms_elevator_source, item.getNo());
        final TextView tvTime = helper.getView(R.id.tv_qms_open_box_time);
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTime(tvTime, item);
            }
        });

    }

    private void openTime(final TextView textView, final S1Elevator item) {
        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DATE);
        @SuppressWarnings("ResourceType")
        DatePickerDialog dpd = new DatePickerDialog(mContext, DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker dp, int year, int month, int day) {
                String date = DateUtil.formatChange(year, month, day, "yyyy-MM-dd");
                textView.setText(date);
                item.setDate(date);
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }
}
