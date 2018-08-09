package com.zxtech.is.ui.taskme.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.DateUtil;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.util.SPUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.DropDownWindow;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.common.IsMstOptions;
import com.zxtech.is.model.project.PeFeDropDown;
import com.zxtech.is.model.temporary.TemporaryTask;
import com.zxtech.is.service.common.IsMstOptionService;
import com.zxtech.is.service.temporary.TemporaryTaskService;
import com.zxtech.is.ui.project.activity.ProjectManageDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by syp660 on 2018/4/19.
 */

public class TaskMeTemporaryTaskAddActivity extends BaseActivity implements ProjectManageDialog.PROJECT {

    TemporaryTask temporaryTask;
    String dateFlag;
    boolean selectUserFlag;
    String peGuid = "DDDB58F1-A563-4674-B6BD-81CF26CD0D4A";

    ProjectManageDialog updateDialog = ProjectManageDialog.newInstance();
    List<TemporaryTask> elevators;
    List<IsMstOptions> types;
    List<PeFeDropDown> users;

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    @BindView(R2.id.temporary_task_add_project)
    TextView mProject;

    @BindView(R2.id.temporary_task_add_elevator)
    TextView mElevator;

    @BindView(R2.id.temporary_task_add_type)
    TextView mType;

    @BindView(R2.id.temporary_task_add_user)
    TextView mUser;

    @BindView(R2.id.temporary_task_add_startdate)
    TextView mStartdate;

    @BindView(R2.id.temporary_task_add_enddate)
    TextView mEnddate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_temporary_task_add;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);
        temporaryTask = new TemporaryTask();
        updateDialog.setProject(this);

        String projectFlag = String.valueOf(SPUtils.get(mContext, "projectFlag", ""));
        String roleIds = String.valueOf(SPUtils.get(mContext, "roleIds", ""));
        String namea = String.valueOf(SPUtils.get(mContext, "is_username", ""));
        if (!"1".equals(projectFlag) || ("1".equals(projectFlag) && roleIds.indexOf(peGuid) > -1)) {
            selectUserFlag = true;
        } else {
            selectUserFlag = false;
        }

        initElevator("");
        initOption("GK0007", "");
        initUser("");
    }

    //初始化电梯列表信息
    private void initElevator(String projectGuid) {
        elevators = new ArrayList<>();
        if (!"".equals(projectGuid)) {
            TemporaryTaskService temporaryTaskService = HttpFactory.getService(TemporaryTaskService.class);
            temporaryTaskService.selectElevatorByProjectGuid(projectGuid)
                    .compose(RxHelper.<BaseResponse<List<TemporaryTask>>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<List<TemporaryTask>>>(getActivity(), false) {
                        @Override
                        public void onSuccess(BaseResponse<List<TemporaryTask>> response) {
                            elevators.addAll(response.getData());
                        }
                    });
        }
    }

    //初始化团队人员信息
    private void initUser(String elevatorGuid) {
        users = new ArrayList<>();
        if (!"".equals(elevatorGuid) && selectUserFlag) {
            TemporaryTaskService temporaryTaskService = HttpFactory.getService(TemporaryTaskService.class);
            temporaryTaskService.selectUserByElevatorGuid(elevatorGuid)
                    .compose(RxHelper.<BaseResponse<List<PeFeDropDown>>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<List<PeFeDropDown>>>(getActivity(), false) {
                        @Override
                        public void onSuccess(BaseResponse<List<PeFeDropDown>> response) {
                            if (response.getData() != null) {
                                users.addAll(response.getData());
                            }
                        }
                    });
        } else {
            PeFeDropDown peFeDropDown = new PeFeDropDown();
            peFeDropDown.setId(String.valueOf(SPUtils.get(mContext, "is_user_Id", "")));
            peFeDropDown.setText(String.valueOf(SPUtils.get(mContext, "is_username", "")));
            users.add(peFeDropDown);
        }
    }

    //初始化类别信息
    private void initOption(String kind, String parentCode) {
        types = new ArrayList<>();
        IsMstOptionService isMstOptionService = HttpFactory.getService(IsMstOptionService.class);
        isMstOptionService.selectParents(kind, parentCode)
                .compose(RxHelper.<BaseResponse<List<IsMstOptions>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<IsMstOptions>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<IsMstOptions>> response) {
                        types.addAll(response.getData());
                    }
                });
    }

    //选中项目回调
    @Override
    public void Projectinfo(String guid, String name) {
        temporaryTask.setProjectguid(guid);
        mProject.setText(name);

        //初始化电梯
        initElevator(guid);
    }

    @OnClick({R2.id.temporary_task_add_save, R2.id.temporary_task_add_return,
            R2.id.temporary_task_add_project, R2.id.temporary_task_add_elevator,
            R2.id.temporary_task_add_type, R2.id.temporary_task_add_user,
            R2.id.temporary_task_add_startdate, R2.id.temporary_task_add_enddate})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.temporary_task_add_save) {
            saveTemporaryTaskInfo();

        } else if (i == R.id.temporary_task_add_return) {
            backTemporaryTaskAdd("0");

        } else if (i == R.id.temporary_task_add_project) {
            updateDialog.show(((Activity) mContext).getFragmentManager(), "");

        } else if (i == R.id.temporary_task_add_elevator) {
            dropDownElevator(view);

        } else if (i == R.id.temporary_task_add_type) {
            dropDownType(view);

        } else if (i == R.id.temporary_task_add_user) {
            dropDownUser(view);

        } else if (i == R.id.temporary_task_add_startdate) {
            dateFlag = "start";
            dropDownDate();

        } else if (i == R.id.temporary_task_add_enddate) {
            dateFlag = "end";
            dropDownDate();

        }
    }

    //选中电梯
    private void dropDownElevator(View view) {

        if (elevators == null || elevators.size() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_elevator));
            return;
        }

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        mElevator.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, elevators, view.getWidth(), -2) {
            @Override
            public void initEvents(final int p) {
                mElevator.setText(elevators.get(p).getArktx());
                temporaryTask.setElevatorguid(elevators.get(p).getElevatorGuid());
                initUser(elevators.get(p).getElevatorGuid());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                mElevator.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    //选中类别
    private void dropDownType(View view) {

        if (types == null || types.size() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_data));
            return;
        }

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        mType.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, types, view.getWidth(), -2) {
            @Override
            public void initEvents(final int p) {
                mType.setText(types.get(p).getText());
                temporaryTask.setTasktype(types.get(p).getCode());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                mType.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    //选中团队人员
    private void dropDownUser(View view) {

        if (users == null || users.size() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_item));
            return;
        }

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        mUser.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, users, view.getWidth(), -2) {
            @Override
            public void initEvents(final int p) {
                mUser.setText(users.get(p).getText());
                temporaryTask.setAssignee(users.get(p).getId());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                mUser.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    //选中日期
    private void dropDownDate() {

        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DATE);
        @SuppressWarnings("ResourceType")
        DatePickerDialog dpd = new DatePickerDialog(mContext, DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker dp, int year, int month, int day) {
                String date = DateUtil.formatChange(year, month, day, "yyyy/MM/dd");
                if ("start".equals(dateFlag)) {
                    mStartdate.setText(date);
                    temporaryTask.setStartdate(getSelectDate(date));
                } else if ("end".equals(dateFlag)) {
                    mEnddate.setText(date);
                    temporaryTask.setEnddate(getSelectDate(date));
                }
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }

    //保存临时任务信息
    private void saveTemporaryTaskInfo() {
        String errorMst = checkparams();
        if (!"".equals(errorMst)) {
            ToastUtil.showLong(errorMst);
            return;
        }
        String userId = String.valueOf(SPUtils.get(mContext, "is_user_Id", ""));
        temporaryTask.setProcDefKey("temporaryTask");
        temporaryTask.setTitle(getResources().getString(R.string.is_temporary_assignment));
        temporaryTask.setUserId(userId);
        String param = GsonUtils.toJson(temporaryTask, false);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        TemporaryTaskService temporaryTaskService = HttpFactory.getService(TemporaryTaskService.class);
        temporaryTaskService.saveTemporaryTaskInfo(requestBody)
                .compose(RxHelper.<BaseResponse<Object>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Object>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<Object> response) {
                        backTemporaryTaskAdd("1");
                    }
                });
    }

    //验证临时任务保存参数
    private String checkparams() {
        String errorMst = "";
        if (temporaryTask.getProjectguid() == null || "".equals(temporaryTask.getProjectguid())) {
            errorMst = getResources().getString(R.string.is_temporary_task_check_project);
        } else if (temporaryTask.getElevatorguid() == null || "".equals(temporaryTask.getElevatorguid())) {
            errorMst = getResources().getString(R.string.is_temporary_task_check_elevator);
        } else if (temporaryTask.getTasktype() == null || "".equals(temporaryTask.getTasktype())) {
            errorMst = getResources().getString(R.string.is_temporary_task_check_type);
        } else if (temporaryTask.getAssignee() == null || "".equals(temporaryTask.getAssignee())) {
            errorMst = getResources().getString(R.string.is_temporary_task_check_user);
        } else if (temporaryTask.getStartdate() == null) {
            errorMst = getResources().getString(R.string.is_temporary_task_check_start_data);
        } else if (temporaryTask.getEnddate() == null) {
            errorMst = getResources().getString(R.string.is_temporary_task_check_end_data);
        } else if (((Date) temporaryTask.getEnddate()).before((Date) temporaryTask.getStartdate())) {
            errorMst = getResources().getString(R.string.is_temporary_task_check_data_range);
        }
        return errorMst;
    }

    //关闭新增页面
    private void backTemporaryTaskAdd(String flag) {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString("flag", flag);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
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
}
