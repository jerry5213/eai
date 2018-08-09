package com.zxtech.is.ui.taskme.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.util.SPUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.MyItemDecoration;
import com.zxtech.is.BaseFragment;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.temporary.TemporaryTask;
import com.zxtech.is.service.check.CommonCheckService;
import com.zxtech.is.service.temporary.TemporaryTaskService;
import com.zxtech.is.ui.check.activity.QualityCheckActivity;
import com.zxtech.is.ui.check.activity.SecurityCheckActivity;
import com.zxtech.is.ui.taskme.activity.TaskMeTemporaryTaskAddActivity;
import com.zxtech.is.ui.taskme.adapter.TaskMeTemporaryTaskTaskAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by syp602 on 2018/4/19.
 */

public class TaskMeTemporaryTaskTaskFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener, CalendarView.OnDateSelectedListener, CalendarView.OnMonthChangeListener {

    private List<TemporaryTask> mBeans = new ArrayList<>();
    private TaskMeTemporaryTaskTaskAdapter mAdapter;
    private Date mDate;

    @BindView(R2.id.temporary_task_review_rv)
    RecyclerView mRecyclerView;

    @BindView(R2.id.tv_month_day)
    TextView mTextMonthDay;
    @BindView(R2.id.tv_year)
    TextView mTextYear;
    @BindView(R2.id.tv_lunar)
    TextView mTextLunar;
    @BindView(R2.id.tv_current_day)
    TextView mTextCurrentDay;
    @BindView(R2.id.calendarView)
    CalendarView mCalendarView;
    @BindView(R2.id.calendarLayout)
    CalendarLayout mCalendarLayout;

    public static TaskMeTemporaryTaskTaskFragment newInstance() {
        return new TaskMeTemporaryTaskTaskFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_task_me_temporary_task_list_task;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mDate = new Date();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new MyItemDecoration());

        mAdapter = new TaskMeTemporaryTaskTaskAdapter(R.layout.item_task_me_temporary_task, mBeans);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
    }

    //当前页面加载后执行
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mTextMonthDay.setText(mCalendarView.getCurMonth() + getResources().getString(R.string.is_temporary_task_month) + mCalendarView.getCurDay() + getResources().getString(R.string.is_temporary_task_day));
        mTextLunar.setText(getResources().getString(R.string.is_temporary_task_this_data));
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
        mCalendarView.setOnDateSelectedListener(this);
        mCalendarView.setOnMonthChangeListener(this);

        initData(mCalendarView.getCurYear(), mCalendarView.getCurMonth());
    }

    //点击日历的日期
    @Override
    public void onDateSelected(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + getResources().getString(R.string.is_temporary_task_month) + calendar.getDay() + getResources().getString(R.string.is_temporary_task_day));
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

        String selectDate = calendar.getYear() + "/" + calendar.getMonth() + "/" + calendar.getDay();
        Date d = getSelectDate(selectDate);
        if (d != null) {
            mDate = d;
            selectTemporaryTask();
        }
    }

    //月份切换时执行
    @Override
    public void onMonthChange(int year, int month) {
        initData(year, month);
    }

    //字符串转日期
    @Nullable
    private Date getSelectDate(String selectDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            return simpleDateFormat.parse(selectDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //结束临时任务
    public void completeTemporaryTask(String guid, String taskId) {
        TemporaryTask temporaryTask = new TemporaryTask();
        temporaryTask.setGuid(guid);
        temporaryTask.setTaskId(taskId);
        String param = GsonUtils.toJson(temporaryTask, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        TemporaryTaskService temporaryTaskService = HttpFactory.getService(TemporaryTaskService.class);
        temporaryTaskService.completeTemporaryTask(requestBody)
                .compose(RxHelper.<BaseResponse<Object>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Object>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<Object> response) {
                        selectTemporaryTask();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        String strdate = sdf.format(mDate);
                        String[] strdateArray = strdate.split("/");
                        int year = Integer.valueOf(strdateArray[0]);
                        int month = Integer.valueOf(strdateArray[1]);
                        initData(year, month);
                    }
                });
    }

    //检索临时任务
    private void selectTemporaryTask() {
        String userId = String.valueOf(SPUtils.get(mContext, "is_user_Id", ""));
        TemporaryTask temporaryTask = new TemporaryTask();
        temporaryTask.setUserId(userId);
        temporaryTask.setStartdate(mDate);
        String param = GsonUtils.toJson(temporaryTask, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        TemporaryTaskService temporaryTaskService = HttpFactory.getService(TemporaryTaskService.class);
        temporaryTaskService.selectTemporaryTask(requestBody)
                .compose(RxHelper.<BaseResponse<List<TemporaryTask>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<TemporaryTask>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<TemporaryTask>> response) {
                        mBeans.clear();
                        mBeans.addAll(response.getData());
                        if (mBeans.size() > 0 && mBeans.size() < 3) {
                            int emptyLength = 3 - mBeans.size();
                            for (int i = 0; i < emptyLength; i++) {
                                TemporaryTask temp = new TemporaryTask();
                                temp.setIsVisible(false);
                                mBeans.add(temp);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    //初始化日期显示标记
    private void initData(int year, int month) {
        Date d = getSelectDate(year + "/" + month + "/10");
        String userId = String.valueOf(SPUtils.get(mContext, "is_user_Id", ""));
        TemporaryTask temporaryTask = new TemporaryTask();
        temporaryTask.setUserId(userId);
        temporaryTask.setStartdate(d);
        String param = GsonUtils.toJson(temporaryTask, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        TemporaryTaskService temporaryTaskService = HttpFactory.getService(TemporaryTaskService.class);
        temporaryTaskService.selectTemporaryTaskByMonth(requestBody)
                .compose(RxHelper.<BaseResponse<List<TemporaryTask>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<TemporaryTask>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<List<TemporaryTask>> response) {
                        List<TemporaryTask> list = response.getData();
                        mCalendarView.setSchemeDate(null);
                        if (list != null && list.size() > 0) {
                            List<Calendar> schemes = new ArrayList<>();
                            SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
                            for (TemporaryTask t : list) {
                                String strtdate = sf.format(t.getStartdate());
                                String[] strtdateArray = strtdate.split("/");
                                int yearTemp = Integer.valueOf(strtdateArray[0]);
                                int monthTemp = Integer.valueOf(strtdateArray[1]);
                                int dayTemp = Integer.valueOf(strtdateArray[2]);
                                schemes.add(getSchemeCalendar(yearTemp, monthTemp, dayTemp, Color.GREEN, "事"));
                            }
                            mCalendarView.setSchemeDate(schemes);
                        }
                    }
                });
    }

    //添加日期标记
    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);
        calendar.setScheme(text);
        return calendar;
    }

    @OnClick({R2.id.add_temporary_task_img})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.add_temporary_task_img) {
            showTemporaryTask();

        }
    }

    //显示新增临时任务页面
    private void showTemporaryTask() {
        Intent intent = new Intent(mContext, TaskMeTemporaryTaskAddActivity.class);
        startActivityForResult(intent, 0);
        ;
    }

    //Activity跳转回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                String flag = bundle.getString("flag");
                if ("1".equals(flag)) {
                    selectTemporaryTask();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    String strdate = sdf.format(mDate);
                    String[] strdateArray = strdate.split("/");
                    int year = Integer.valueOf(strdateArray[0]);
                    int month = Integer.valueOf(strdateArray[1]);
                    initData(year, month);
                }
            }
        } else if ((requestCode == 1 || requestCode == 2) && resultCode == RESULT_OK) {
            selectTemporaryTask();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String strdate = sdf.format(mDate);
            String[] strdateArray = strdate.split("/");
            int year = Integer.valueOf(strdateArray[0]);
            int month = Integer.valueOf(strdateArray[1]);
            initData(year, month);
        }
    }

    //点击执行任务
    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        if (view.getId() == R.id.item_task_me_temporary_task_btn) {
            TemporaryTask t = mBeans.get(position);
            String guid = t.getGuid();
            String tasktype = t.getTasktype();
            String taskId = t.getTaskId();
            if ("aqjc".equals(tasktype)) {
                checkImplementTask(t, 1);
            } else if ("zljc".equals(tasktype)) {
                checkImplementTask(t, 2);
            } else {
                completeTemporaryTask(guid, taskId);
            }
        }
    }

    //验证执行任务是否符合条件
    private void checkImplementTask(TemporaryTask t, final int flag) {
        final String guid = t.getGuid();
        final String tasktype = t.getTasktype();
        final String taskId = t.getTaskId();
        final String elevatorGuid = t.getElevatorguid();
        final String optionName = t.getOptionName();
        final String projectName = t.getProjectName();
        final String projectguid = t.getProjectguid();
        final String elevatorName = t.getElevatorName();

        CommonCheckService commonCheckService = HttpFactory.getService(CommonCheckService.class);
        commonCheckService.judgeInstallTypeFun(elevatorGuid)
                .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        if (response.getData() == null || "0".equals(String.valueOf(response.getData()))) {
                            ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_preparation));
                        } else {
                            Intent intent = null;
                            if ("aqjc".equals(tasktype)) {
                                intent = new Intent(mContext, SecurityCheckActivity.class);
                            } else if ("zljc".equals(tasktype)) {
                                intent = new Intent(mContext, QualityCheckActivity.class);
                            }
                            intent.putExtra("taskId", taskId);
                            intent.putExtra("elevatorGuid", elevatorGuid);
                            intent.putExtra("customGuid", guid);
                            intent.putExtra("projectGuid", projectguid);
                            intent.putExtra("projectName", projectName);
                            intent.putExtra("elevatorName", elevatorName);
                            intent.putExtra("optionCode", tasktype);
                            intent.putExtra("optionName", optionName);
                            startActivityForResult(intent, flag);
                        }
                    }
                });
    }
}
