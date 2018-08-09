package com.zxtech.is.ui.task.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.S1Elevator;
import com.zxtech.is.model.project.Project;
import com.zxtech.is.ui.task.adpter.S1TaskElevatorAdpter;
import com.zxtech.is.util.DateUtil;
import com.zxtech.is.widget.MyItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class S1TaskDetailActivity extends BaseActivity {

    private S1TaskElevatorAdpter s1TaskElevatorAdpter;
    private List<S1Elevator> elevatorList = new ArrayList<>();

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.tb_project_info)
    LinearLayout tb_project_info;
    @BindView(R2.id.tv_open_project_info)
    TextView tv_open_project_info;
    @BindView(R2.id.tv_qms_submit)
    TextView tv_qms_submit;


    @BindView(R2.id.rv_elevator)
    RecyclerView mRvElevator;

    @BindString(R2.string.icon_zhe_die_open)
    String icon_open;
    @BindString(R2.string.icon_zhe_die_close)
    String icon_close;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_s1_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvElevator.setLayoutManager(linearLayoutManager);
        mRvElevator.addItemDecoration(new MyItemDecoration());

        s1TaskElevatorAdpter = new S1TaskElevatorAdpter(R.layout.item_task_s1_elevator, elevatorList);

        s1TaskElevatorAdpter.bindToRecyclerView(mRvElevator);
        mRvElevator.setAdapter(s1TaskElevatorAdpter);

        initData();

    }

    @OnClick({R2.id.tv_open_project_info, R2.id.tv_qms_submit})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_open_project_info) {
            if (tb_project_info.isShown()) {
                tb_project_info.setVisibility(View.GONE);
                tv_open_project_info.setText(icon_close);
            } else {
                tb_project_info.setVisibility(View.VISIBLE);
                tv_open_project_info.setText(icon_open);
            }

        } else if (i == R.id.tv_qms_submit) {//                S1Elevator item = s1TaskElevatorAdpter.getItem(0);
//                Log.d("elevatorList",item.getNo());

            int itemCount = s1TaskElevatorAdpter.getItemCount();
            Log.d("elevatorList", itemCount + "");
            EditText et_qms_elevator_source = (EditText) s1TaskElevatorAdpter.getViewByPosition(0, R.id.et_qms_elevator_source);
            Log.d("elevatorList", et_qms_elevator_source.getText().toString());
            for (S1Elevator s1 : elevatorList) {
                Log.d("elevatorList", s1.getDate() + ":" + s1.getNo());
            }

        }
    }

    private void openTime() {
//        Calendar cal = Calendar.getInstance();
//        int mYear = cal.get(Calendar.YEAR);
//        int mMonth = cal.get(Calendar.MONTH);
//        int mDay = cal.get(Calendar.DATE);
//        @SuppressWarnings("ResourceType")
//        DatePickerDialog dpd = new DatePickerDialog(mContext, DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker dp, int year, int month, int day) {
//                String date = DateUtil.formatChange(year, month, day, "yyyy-MM-dd");
//                mTvQmsOpenBoxTime.setText(date);
//            }
//        }, mYear, mMonth, mDay);
//        dpd.show();
    }

    private void initData() {
        S1Elevator e1 = new S1Elevator();
        e1.setNo("123");
        elevatorList.add(e1);

        S1Elevator e2 = new S1Elevator();
        e2.setNo("456");
        elevatorList.add(e2);

        s1TaskElevatorAdpter.notifyDataSetChanged();

    }
}
