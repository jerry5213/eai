package com.zxtech.ecs.ui.home.scheduling;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.zxtech.ecs.BaseDialogFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.event.EventDeliveryTime;
import com.zxtech.ecs.util.DateUtil;
import com.zxtech.ecs.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/6/19.
 */

public class SchedulingDialogFragment extends BaseDialogFragment implements DatePickerDialog.OnDateSetListener{

    @BindView(R.id.expect_date_tv)
    TextView expect_date_tv;
    @BindView(R.id.standard_date_tv)
    TextView standard_date_tv;
    @BindView(R.id.real_date_tv)
    TextView real_date_tv;
    @BindView(R.id.save_tv)
    TextView save_tv;

    private Calendar cal = Calendar.getInstance();

    public static SchedulingDialogFragment newInstance() {
        return new SchedulingDialogFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_scheduling;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        expect_date_tv.setText("");
        standard_date_tv.setText("");
        real_date_tv.setText("");
    }

    @Override
    public boolean isBottomShow() {
        return false;
    }

    private TextView currentClickView;

    @OnClick({R.id.expect_date_tv,R.id.standard_date_tv,R.id.real_date_tv})
    public void onClick(final View view) {

        currentClickView = (TextView) view;
        new DatePickerDialog(mContext, this,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    @OnClick(R.id.save_tv)
    public void save(){

        if(TextUtils.isEmpty(expect_date_tv.getText().toString())){
            ToastUtil.showLong("请填写期望交期！");
            return;
        }

        EventDeliveryTime deliveryTime = new EventDeliveryTime();
        deliveryTime.setExpectedTime(expect_date_tv.getText().toString());
        deliveryTime.setPlannedTime(standard_date_tv.getText().toString());
        deliveryTime.setRealTime(real_date_tv.getText().toString());
        EventBus.getDefault().post(deliveryTime);
        dismiss();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        currentClickView.setText(DateUtil.formatChange(year,month,dayOfMonth,DateUtil.yyyy_MM_dd));
    }
}
