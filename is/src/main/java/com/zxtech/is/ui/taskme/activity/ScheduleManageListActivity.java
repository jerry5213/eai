package com.zxtech.is.ui.taskme.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.DateUtil;
import com.zxtech.is.util.StringUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.schedule.ScheduleManager;
import com.zxtech.is.service.taskme.TaskMeService;
import com.zxtech.is.ui.taskme.adapter.ScheduleManageListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp688 on 4/23/2018.
 */

public class ScheduleManageListActivity extends BaseActivity {
    private List<ScheduleManager> mBeans = new ArrayList<>();
    private ScheduleManageListAdapter adapter;
    private String scheduleKey;
    private String scheduleGuid;
    private String procInstId;
    private String taskId;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.time_list_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R2.id.check_date)
    TextView check_date;
    @BindView(R2.id.time_list_title)
    TextView time_list_title;
    @BindView(R2.id.time_list_save)
    Button time_list_save;
    @BindView(R2.id.schedule_check_time)
    LinearLayout schedule_check_time;
    @BindView(R2.id.task_audit)
    LinearLayout task_audit;
    @BindView(R2.id.schedule_task_top)
    LinearLayout schedule_task_top;

    @Override
    protected int getLayoutId() {
        return R.layout.schedule_management_list;
    }

    @OnClick(R2.id.datepickerIcon)
    protected void openTime() {
        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DATE);
        @SuppressWarnings("ResourceType")
        DatePickerDialog dpd = new DatePickerDialog(mContext, DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker dp, int year, int month, int day) {
                String date = DateUtil.formatChange(year, month, day, "yyyy-MM-dd");
                check_date.setText(date);
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }

    @OnClick(R2.id.time_list_save)
    public void passSaveTime() {
        String date = check_date.getText().toString();
        if (StringUtils.isEmpty(date)) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_time));
            return;
        }
        TaskMeService taskMeService = HttpFactory.getService(TaskMeService.class);
        taskMeService.saveTime(date, scheduleKey, scheduleGuid, procInstId, taskId)
                .compose(RxHelper.<BaseResponse<List<ScheduleManager>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ScheduleManager>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<ScheduleManager>> response) {
                        ToastUtil.showLong(getResources().getString(R.string.is_check_success));
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }

    @OnClick(R2.id.time_list_pass)
    public void rejectSaveTime() {
        String date = "1";
        TaskMeService taskMeService = HttpFactory.getService(TaskMeService.class);
        taskMeService.saveTime(date, scheduleKey, scheduleGuid, procInstId, taskId)
                .compose(RxHelper.<BaseResponse<List<ScheduleManager>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ScheduleManager>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<ScheduleManager>> response) {
                        ToastUtil.showLong(getResources().getString(R.string.is_audit_success));
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }

    @OnClick(R2.id.time_list_reject)
    public void saveTime() {
        String date = "2";
        TaskMeService taskMeService = HttpFactory.getService(TaskMeService.class);
        taskMeService.saveTime(date, scheduleKey, scheduleGuid, procInstId, taskId)
                .compose(RxHelper.<BaseResponse<List<ScheduleManager>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ScheduleManager>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<ScheduleManager>> response) {
                        ToastUtil.showLong(getResources().getString(R.string.is_dismiss_success));
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        // mRecyclerView.addItemDecoration(new MyItemDecoration());

        adapter = new ScheduleManageListAdapter(R.layout.item_sch_manager, mBeans);
        adapter.bindToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(adapter);
        //mRecyclerView.addItemDecoration(new MyItemDecoration());

        Intent intent = getIntent();
        String guid = intent.getStringExtra("guid");
        String taskDefKey = intent.getStringExtra("taskDefKey");
        Boolean iden = intent.getBooleanExtra("iden", false);
        if (iden) {
            schedule_task_top.setVisibility(View.GONE);
        }
        if ("scheduleManage_cj_pe".equals(taskDefKey)) {
            task_audit.setVisibility(View.VISIBLE);
            time_list_save.setVisibility(View.GONE);
            schedule_check_time.setVisibility(View.GONE);
        }
        if ("scheduleManage_gj_pe".equals(taskDefKey)) {
            task_audit.setVisibility(View.VISIBLE);
            time_list_save.setVisibility(View.GONE);
            schedule_check_time.setVisibility(View.GONE);
        }
        if ("scheduleManage_az_fe".equals(taskDefKey)) {
            time_list_title.setText(getResources().getString(R.string.is_scheduleManage_az_fe));
        } else if ("scheduleManage_ts_fe".equals(taskDefKey)) {
            time_list_title.setText(getResources().getString(R.string.is_scheduleManage_ts_fe));
        } else if ("scheduleManage_cj_fe".equals(taskDefKey)) {
            time_list_title.setText(getResources().getString(R.string.is_scheduleManage_cj_fe));
        } else if ("scheduleManage_cj_pe".equals(taskDefKey)) {
            time_list_title.setText(getResources().getString(R.string.is_scheduleManage_cj_pe));
        } else if ("scheduleManage_gj_fe".equals(taskDefKey)) {
            time_list_title.setText(getResources().getString(R.string.is_scheduleManage_gj_fe));
        } else if ("scheduleManage_gj_pe".equals(taskDefKey)) {
            time_list_title.setText(getResources().getString(R.string.is_scheduleManage_gj_pe));
        } else if ("scheduleManage_jf_fe".equals(taskDefKey)) {
            time_list_title.setText(getResources().getString(R.string.is_scheduleManage_jf_fe));
        } else {
            time_list_title.setText(getResources().getString(R.string.is_scheduleManage_jf_wc));
        }
        requestNet(guid, iden, taskDefKey);
    }

    private void requestNet(String guid, final Boolean iden, String taskDefKey) {
        TaskMeService taskMeService = HttpFactory.getService(TaskMeService.class);
        taskMeService.getScheduleManageList(guid, iden, taskDefKey)
                .compose(RxHelper.<BaseResponse<List<ScheduleManager>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<ScheduleManager>>>(getActivity(), true) {

                    @Override
                    public void onSuccess(BaseResponse<List<ScheduleManager>> response) {
                        if (response.getData().size() == 0) {
                            ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_data));
                        } else {
                            mBeans.clear();
                            mBeans.addAll(response.getData());
                            for (int i = 0; i < mBeans.size(); i++) {
                                scheduleGuid = mBeans.get(0).getScheduleGuid();
                                procInstId = mBeans.get(0).getProcInstId();
                                scheduleKey = mBeans.get(0).getTaskDefKey();
                                taskId = mBeans.get(0).getTaskId();
//                                    task_audit.setVisibility(View.GONE);
//                                    time_list_save.setVisibility(View.VISIBLE);
//                                    schedule_check_time.setVisibility(View.VISIBLE);
                            }
                            adapter.notifyDataSetChanged();

                        }
                    }
                });
    }
}
