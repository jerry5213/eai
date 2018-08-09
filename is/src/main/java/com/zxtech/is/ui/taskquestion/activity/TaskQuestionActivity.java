package com.zxtech.is.ui.taskquestion.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.DateUtil;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.util.ImageUtil;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.widget.DropDownWindow;
import com.zxtech.is.widget.SelectDialog;
import com.zxtech.is.BuildConfig;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.activity.BasePhotoActivity;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.model.common.IsMstOptions;
import com.zxtech.is.model.project.PeFeDropDown;
import com.zxtech.is.model.taskquestion.TaskQuestion;
import com.zxtech.is.model.taskquestion.TaskQuestionPic;
import com.zxtech.is.model.temporary.TemporaryTask;
import com.zxtech.is.service.common.IsMstOptionService;
import com.zxtech.is.service.taskquestion.TaskQuestionService;
import com.zxtech.is.service.temporary.TemporaryTaskService;
import com.zxtech.is.ui.project.activity.ProjectManageDialog;
import com.zxtech.is.ui.task.activity.ShowBigImageSimpleActivity;
import com.zxtech.is.ui.taskquestion.adapter.TaskQuestionAssignAdapter;
import com.zxtech.is.ui.taskquestion.adapter.TaskQuestionConfirmAdapter;
import com.zxtech.is.widget.MyItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by hsz on 2018/4/28.
 */

public class TaskQuestionActivity extends BasePhotoActivity implements ProjectManageDialog.PROJECT, BaseQuickAdapter.OnItemChildClickListener {
    TaskQuestion taskQuestion = new TaskQuestion();

    Map<String, RequestBody> filesMap = new HashMap<>();

    ProjectManageDialog projectManageDialog = ProjectManageDialog.newInstance();
    List<TemporaryTask> list_textview2_1;
    List<IsMstOptions> list_textview3_1;
    List<PeFeDropDown> list_textview4_1;
    List<PeFeDropDown> list_textview5_1;

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    @BindView(R2.id.textview1_1)
    TextView textview1_1;
    @BindView(R2.id.textview1_2)
    TextView textview1_2;
    @BindView(R2.id.textview2_1)
    TextView textview2_1;
    @BindView(R2.id.textview2_2)
    TextView textview2_2;
    @BindView(R2.id.textview3_1)
    TextView textview3_1;
    @BindView(R2.id.textview3_2)
    TextView textview3_2;
    @BindView(R2.id.textview4_1)
    TextView textview4_1;
    @BindView(R2.id.textview4_2)
    TextView textview4_2;
    @BindView(R2.id.textview5_1)
    TextView textview5_1;
    @BindView(R2.id.textview5_2)
    TextView textview5_2;
    @BindView(R2.id.textview6_1)
    TextView textview6_1;
    @BindView(R2.id.textview6_2)
    TextView textview6_2;
    @BindView(R2.id.textview7_1)
    EditText textview7_1;
    @BindView(R2.id.textview7_2)
    TextView textview7_2;
    @BindView(R2.id.textview8_1)
    EditText textview8_1;
    @BindView(R2.id.textview8_2)
    TextView textview8_2;
    @BindView(R2.id.show_imgs_1)
    RecyclerView show_imgs_1;
    @BindView(R2.id.show_imgs_2)
    RecyclerView show_imgs_2;
    @BindView(R2.id.add_img_button_1)
    ImageView add_img_button_1;
    @BindView(R2.id.add_img_button_2)
    ImageView add_img_button_2;
    @BindView(R2.id.upload_photos_id_2)
    LinearLayout upload_photos_id_2;
    @BindView(R2.id.buttons_id)
    LinearLayout buttons_id;

    private String add_img_button_click_flag = "0";

    private TaskQuestionAssignAdapter mAdapter1;

    private TaskQuestionConfirmAdapter mAdapter2;

    List<TaskQuestionPic> taskQuestionPicList1 = new ArrayList<>();

    List<TaskQuestionPic> taskQuestionPicList2 = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_question;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);

        Intent intentGet = getIntent();
        List<TaskQuestionPic> picList = (List<TaskQuestionPic>) intentGet.getSerializableExtra("picList");
        if (picList != null && picList.size() > 0) {
            for (TaskQuestionPic i : picList) {
                if (i.getPicguid() != null
                        && !i.getPicguid().equals("")
                        && i.getType() != null
                        && !i.getType().equals("")) {
                    i.setOpenMode(getIntent().getStringExtra("openMode"));
                    if (i.getType().equals("1")) {
                        taskQuestionPicList1.add(i);
                    } else if (i.getType().equals("2")) {
                        taskQuestionPicList2.add(i);
                    }
                }
            }
        }

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        show_imgs_1.setLayoutManager(linearLayoutManager1);
        show_imgs_1.addItemDecoration(new MyItemDecoration());
        mAdapter1 = new TaskQuestionAssignAdapter(R.layout.item_task_question_img, taskQuestionPicList1);
        show_imgs_1.setAdapter(mAdapter1);
        mAdapter1.setOnItemChildClickListener(this);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        show_imgs_2.setLayoutManager(linearLayoutManager2);
        show_imgs_2.addItemDecoration(new MyItemDecoration());
        mAdapter2 = new TaskQuestionConfirmAdapter(R.layout.item_task_question_img, taskQuestionPicList2);
        show_imgs_2.setAdapter(mAdapter2);
        mAdapter2.setOnItemChildClickListener(this);

        projectManageDialog.setProject(this);

        init_textview2_1("");
        init_textview3_1("GK0009", "");
        init_textview4_1("");
        init_textview5_1("");

        drawViewFun();
    }

    // 处理画面
    private void drawViewFun() {
        String openMode = getIntent().getStringExtra("openMode");
        // 答题时创建即时问题
        if (openMode.equals("1")) {
            textview1_1.setVisibility(View.GONE);
            textview2_1.setVisibility(View.GONE);
            textview3_1.setVisibility(View.GONE);
            textview4_2.setVisibility(View.GONE);
            textview5_2.setVisibility(View.GONE);
            textview6_2.setVisibility(View.GONE);
            textview7_1.setVisibility(View.GONE);
            textview8_1.setVisibility(View.GONE);
            upload_photos_id_2.setVisibility(View.GONE);
            // 任务时创建即时问题
        } else if (openMode.equals("2")) {
            textview1_2.setVisibility(View.GONE);
            textview2_2.setVisibility(View.GONE);
            textview3_2.setVisibility(View.GONE);
            textview4_2.setVisibility(View.GONE);
            textview5_2.setVisibility(View.GONE);
            textview6_2.setVisibility(View.GONE);
            textview7_2.setVisibility(View.GONE);
            textview8_1.setVisibility(View.GONE);
            upload_photos_id_2.setVisibility(View.GONE);
            // 整改人打开即时问题
        } else if (openMode.equals("3")) {
            textview1_1.setVisibility(View.GONE);
            textview2_1.setVisibility(View.GONE);
            textview3_1.setVisibility(View.GONE);
            textview4_1.setVisibility(View.GONE);
            textview5_1.setVisibility(View.GONE);
            textview6_1.setVisibility(View.GONE);
            textview7_1.setVisibility(View.GONE);
            textview8_2.setVisibility(View.GONE);
            add_img_button_1.setVisibility(View.GONE);
            // 确认人打开已整改的即时问题
        } else if (openMode.equals("4")) {
            textview1_1.setVisibility(View.GONE);
            textview2_1.setVisibility(View.GONE);
            textview3_1.setVisibility(View.GONE);
            textview4_1.setVisibility(View.GONE);
            textview5_1.setVisibility(View.GONE);
            textview6_1.setVisibility(View.GONE);
            textview7_1.setVisibility(View.GONE);
            textview8_1.setVisibility(View.GONE);
            add_img_button_1.setVisibility(View.GONE);
            add_img_button_2.setVisibility(View.GONE);
            // 确认人打开未整改的即时问题
        } else if (openMode.equals("5")) {
            textview1_1.setVisibility(View.GONE);
            textview2_1.setVisibility(View.GONE);
            textview3_1.setVisibility(View.GONE);
            textview4_1.setVisibility(View.GONE);
            textview5_1.setVisibility(View.GONE);
            textview6_1.setVisibility(View.GONE);
            textview7_1.setVisibility(View.GONE);
            textview8_1.setVisibility(View.GONE);
            add_img_button_1.setVisibility(View.GONE);
            upload_photos_id_2.setVisibility(View.GONE);
            buttons_id.setVisibility(View.GONE);
        }

        assignmentFun();
    }

    // 画面赋值
    private void assignmentFun() {
        String openMode = getIntent().getStringExtra("openMode");
        if (openMode.equals("1")) {
            if (getIntent().getStringExtra("projectName") != null) {
                textview1_2.setText(getIntent().getStringExtra("projectName"));
            }
            if (getIntent().getStringExtra("elevatorName") != null) {
                textview2_2.setText(getIntent().getStringExtra("elevatorName"));
            }
            if (getIntent().getStringExtra("taskName") != null) {
                textview3_2.setText(getIntent().getStringExtra("taskName"));
            }
            if (getIntent().getStringExtra("remark") != null) {
                textview7_2.setText(getIntent().getStringExtra("remark"));
            }
            // 激活整改人和确认人下拉框
            if (getIntent().getStringExtra("elevatorGuid") != null) {
                init_textview4_1(getIntent().getStringExtra("elevatorGuid"));
                init_textview5_1(getIntent().getStringExtra("elevatorGuid"));
            }
        } else if (openMode.equals("3")) {
            if (getIntent().getStringExtra("projectName") != null) {
                textview1_2.setText(getIntent().getStringExtra("projectName"));
            }
            if (getIntent().getStringExtra("elevatorName") != null) {
                textview2_2.setText(getIntent().getStringExtra("elevatorName"));
            }
            if (getIntent().getStringExtra("taskName") != null) {
                textview3_2.setText(getIntent().getStringExtra("taskName"));
            }
            if (getIntent().getStringExtra("assignProcInstName") != null) {
                textview4_2.setText(getIntent().getStringExtra("assignProcInstName"));
            }
            if (getIntent().getStringExtra("confirmProcInstName") != null) {
                textview5_2.setText(getIntent().getStringExtra("confirmProcInstName"));
            }
            if (getIntent().getStringExtra("endDate") != null) {
                textview6_2.setText(getIntent().getStringExtra("endDate"));
            }
            if (getIntent().getStringExtra("remark") != null) {
                textview7_2.setText(getIntent().getStringExtra("remark"));
            }
        } else if (openMode.equals("4")) {
            if (getIntent().getStringExtra("projectName") != null) {
                textview1_2.setText(getIntent().getStringExtra("projectName"));
            }
            if (getIntent().getStringExtra("elevatorName") != null) {
                textview2_2.setText(getIntent().getStringExtra("elevatorName"));
            }
            if (getIntent().getStringExtra("taskName") != null) {
                textview3_2.setText(getIntent().getStringExtra("taskName"));
            }
            if (getIntent().getStringExtra("assignProcInstName") != null) {
                textview4_2.setText(getIntent().getStringExtra("assignProcInstName"));
            }
            if (getIntent().getStringExtra("confirmProcInstName") != null) {
                textview5_2.setText(getIntent().getStringExtra("confirmProcInstName"));
            }
            if (getIntent().getStringExtra("endDate") != null) {
                textview6_2.setText(getIntent().getStringExtra("endDate"));
            }
            if (getIntent().getStringExtra("remark") != null) {
                textview7_2.setText(getIntent().getStringExtra("remark"));
            }
            if (getIntent().getStringExtra("content") != null) {
                textview8_2.setText(getIntent().getStringExtra("content"));
            }
        } else if (openMode.equals("5")) {
            if (getIntent().getStringExtra("projectName") != null) {
                textview1_2.setText(getIntent().getStringExtra("projectName"));
            }
            if (getIntent().getStringExtra("elevatorName") != null) {
                textview2_2.setText(getIntent().getStringExtra("elevatorName"));
            }
            if (getIntent().getStringExtra("taskName") != null) {
                textview3_2.setText(getIntent().getStringExtra("taskName"));
            }
            if (getIntent().getStringExtra("assignProcInstName") != null) {
                textview4_2.setText(getIntent().getStringExtra("assignProcInstName"));
            }
            if (getIntent().getStringExtra("confirmProcInstName") != null) {
                textview5_2.setText(getIntent().getStringExtra("confirmProcInstName"));
            }
            if (getIntent().getStringExtra("endDate") != null) {
                textview6_2.setText(getIntent().getStringExtra("endDate"));
            }
            if (getIntent().getStringExtra("remark") != null) {
                textview7_2.setText(getIntent().getStringExtra("remark"));
            }
            if (getIntent().getStringExtra("content") != null) {
                textview8_2.setText(getIntent().getStringExtra("content"));
            }
        }
    }

    @OnClick({
            R2.id.textview1_1,
            R2.id.textview2_1,
            R2.id.textview3_1,
            R2.id.textview4_1,
            R2.id.textview5_1,
            R2.id.textview6_1,
            R2.id.add_img_button_1,
            R2.id.add_img_button_2,
            R2.id.button_save_id,
            R2.id.button_return_id
    })
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.textview1_1) {
            projectManageDialog.show(((Activity) mContext).getFragmentManager(), "");

        } else if (i == R.id.textview2_1) {
            dropDown_textview2_1(view);

        } else if (i == R.id.textview3_1) {
            dropDown_textview3_1(view);

        } else if (i == R.id.textview4_1) {
            dropDown_textview4_1(view);

        } else if (i == R.id.textview5_1) {
            dropDown_textview5_1(view);

        } else if (i == R.id.textview6_1) {
            dropDown_textview6_1();

        } else if (i == R.id.add_img_button_1) {
            add_img_button_click_flag = "1";
            addAttach();

        } else if (i == R.id.add_img_button_2) {
            add_img_button_click_flag = "2";
            addAttach();

        } else if (i == R.id.button_save_id) {
            saveFun();

        } else if (i == R.id.button_return_id) {
            finish();

        }
    }

    //选中项目回调
    @Override
    public void Projectinfo(String guid, String name) {
        taskQuestion.setProjectGuid(guid);
        textview1_1.setText(name);

        //初始化电梯
        init_textview2_1(guid);
    }

    //初始化梯号
    private void init_textview2_1(String textview1) {
        list_textview2_1 = new ArrayList<>();
        if (!"".equals(textview1)) {
            TemporaryTaskService temporaryTaskService = HttpFactory.getService(TemporaryTaskService.class);
            temporaryTaskService.selectElevatorByProjectGuid(textview1)
                    .compose(RxHelper.<BaseResponse<List<TemporaryTask>>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<List<TemporaryTask>>>(getActivity(), false) {

                        @Override
                        public void onSuccess(BaseResponse<List<TemporaryTask>> response) {
                            list_textview2_1.addAll(response.getData());
                        }
                    });
        }
    }

    //初始化问题类型
    private void init_textview3_1(String kind, String parentCode) {
        list_textview3_1 = new ArrayList<>();
        IsMstOptionService isMstOptionService = HttpFactory.getService(IsMstOptionService.class);
        isMstOptionService.selectParents(kind, parentCode)
                .compose(RxHelper.<BaseResponse<List<IsMstOptions>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<List<IsMstOptions>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<List<IsMstOptions>> response) {
                        list_textview3_1.addAll(response.getData());
                    }
                });
    }

    //初始化整改人
    private void init_textview4_1(String textview2) {
        list_textview4_1 = new ArrayList<>();
        if (!"".equals(textview2)) {
            TemporaryTaskService temporaryTaskService = HttpFactory.getService(TemporaryTaskService.class);
            temporaryTaskService.selectUserByElevatorGuid(textview2)
                    .compose(RxHelper.<BaseResponse<List<PeFeDropDown>>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<List<PeFeDropDown>>>(getActivity(), false) {
                        @Override
                        public void onSuccess(BaseResponse<List<PeFeDropDown>> response) {
                            if (response.getData() != null) {
                                list_textview4_1.addAll(response.getData());
                            }
                        }
                    });
        }
    }

    //初始化确认人
    private void init_textview5_1(String textview2) {
        list_textview5_1 = new ArrayList<>();
        if (!"".equals(textview2)) {
            TemporaryTaskService temporaryTaskService = HttpFactory.getService(TemporaryTaskService.class);
            temporaryTaskService.selectUserByElevatorGuid(textview2)
                    .compose(RxHelper.<BaseResponse<List<PeFeDropDown>>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<List<PeFeDropDown>>>(getActivity(), false) {
                        @Override
                        public void onSuccess(BaseResponse<List<PeFeDropDown>> response) {
                            if (response.getData() != null) {
                                list_textview5_1.addAll(response.getData());
                            }
                        }
                    });
        }
    }

    //选中电梯
    private void dropDown_textview2_1(View view) {
        if (list_textview2_1 == null || list_textview2_1.size() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_elevator));
            return;
        }

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        textview2_1.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, list_textview2_1, view.getWidth(), -2) {
            @Override
            public void initEvents(final int p) {
                textview2_1.setText(list_textview2_1.get(p).getArktx());
                taskQuestion.setElevatorGuid(list_textview2_1.get(p).getElevatorGuid());
                init_textview4_1(list_textview2_1.get(p).getElevatorGuid());
                init_textview5_1(list_textview2_1.get(p).getElevatorGuid());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                textview2_1.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    //选中类别
    private void dropDown_textview3_1(View view) {
        if (list_textview3_1 == null || list_textview3_1.size() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_data));
            return;
        }

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        textview3_1.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, list_textview3_1, view.getWidth(), -2) {
            @Override
            public void initEvents(final int p) {
                textview3_1.setText(list_textview3_1.get(p).getText());
                taskQuestion.setTaskType(list_textview3_1.get(p).getCode());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                textview3_1.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    //选中整改人
    private void dropDown_textview4_1(View view) {
        if (list_textview4_1 == null || list_textview4_1.size() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_item));
            return;
        }

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        textview4_1.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, list_textview4_1, view.getWidth(), -2) {
            @Override
            public void initEvents(final int p) {
                textview4_1.setText(list_textview4_1.get(p).getText());
                taskQuestion.setAssignUser(list_textview4_1.get(p).getId());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                textview4_1.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    //选中确认人
    private void dropDown_textview5_1(View view) {
        if (list_textview5_1 == null || list_textview5_1.size() == 0) {
            ToastUtil.showLong(getResources().getString(R.string.is_temporary_task_no_item));
            return;
        }

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        textview5_1.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, list_textview5_1, view.getWidth(), -2) {
            @Override
            public void initEvents(final int p) {
                textview5_1.setText(list_textview5_1.get(p).getText());
                taskQuestion.setConfirmUser(list_textview5_1.get(p).getId());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                textview5_1.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    //选中计划完成日期
    protected void dropDown_textview6_1() {
        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DATE);
        @SuppressWarnings("ResourceType")
        DatePickerDialog dpd = new DatePickerDialog(mContext, DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker dp, int year, int month, int day) {
                String date = DateUtil.formatChange(year, month, day, "yyyy-MM-dd");
                textview6_1.setText(date);
                taskQuestion.setEndDate(date);
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }

    private void addAttach() {
        List<String> names = new ArrayList<>();
        names.add("拍照");
        names.add("相册");
        showSelectDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        RxPermissions rxPermissions = new RxPermissions(getActivity());
                        rxPermissions.requestEach(Manifest.permission.CAMERA)
                                .subscribe(new Consumer<Permission>() {
                                    @Override
                                    public void accept(Permission permission) throws Exception {
                                        if (permission.granted) {
                                            // 用户已经同意该权限
                                            takePhoto();
                                        } else if (permission.shouldShowRequestPermissionRationale) {
                                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                            ToastUtil.showLong("用户拒绝了该权限");
                                        } else {
                                            // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                                            ToastUtil.showLong("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                                        }
                                    }
                                });
                        break;
                    case 1:
                        takeImage();
                        break;
                }
            }
        }, names);
    }

    @Override
    protected void picSuccess(Bitmap bitmap) {
        TaskQuestionPic taskQuestionPic = new TaskQuestionPic();
        taskQuestionPic.setBitmap(bitmap);
        //本地图片路径
        String mPhotoPath = ImageUtil.saveImageToGallery(TaskQuestionActivity.this, bitmap);
        String fileType = mPhotoPath.substring(mPhotoPath.lastIndexOf(".") + 1);
        String fileName = mPhotoPath.substring(mPhotoPath.lastIndexOf("/") + 1);
        taskQuestionPic.setFileType(fileType);
        taskQuestionPic.setFileName(fileName);
        taskQuestionPic.setFilePath(mPhotoPath);
        taskQuestionPic.setOpenMode(getIntent().getStringExtra("openMode"));
        if (add_img_button_click_flag.equals("1")) {
            taskQuestionPicList1.add(taskQuestionPic);
            mAdapter1.notifyDataSetChanged();
        } else if (add_img_button_click_flag.equals("2")) {
            taskQuestionPicList2.add(taskQuestionPic);
            mAdapter2.notifyDataSetChanged();
        }
    }

    // 确认按钮的保存事件
    private void saveFun() {
        passParameterFun();

        if (judgeParameterFun()) {
            String filePath = "";
            String fileName = "";
            Map<String, RequestBody> files = new HashMap<>();
            String sortFiles = "";
            List<TaskQuestionPic> taskQuestionPicList = null;
            String openMode = getIntent().getStringExtra("openMode");
            if (openMode.equals("1") || openMode.equals("2")) {
                taskQuestionPicList = taskQuestionPicList1;
            } else if (openMode.equals("3")) {
                taskQuestionPicList = taskQuestionPicList2;
            }
            if (taskQuestionPicList != null) {
                for (int i = 0; i < taskQuestionPicList.size(); i++) {
                    filePath = taskQuestionPicList.get(i).getFilePath();
                    fileName = taskQuestionPicList.get(i).getFileName();
                    //转换
                    RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"), new File(filePath));
                    files.put("file\";filename=\"" + fileName, file);
                    sortFiles += fileName + ",";
                }
            }
            if (sortFiles.endsWith(",")) {
                sortFiles = sortFiles.substring(0, sortFiles.length() - 1);
            }
            String param = GsonUtils.toJson(taskQuestion, false);
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
            TaskQuestionService taskQuestionService = HttpFactory.getService(TaskQuestionService.class);
            taskQuestionService.saveTaskQuestion(requestBody, files, sortFiles)
                    .compose(RxHelper.<BaseResponse<Object>>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse<Object>>(getActivity(), true) {

                        @Override
                        public void onSuccess(BaseResponse<Object> response) {
                            ToastUtil.showLong(response.getMessage());
                            Intent intent = getIntent();
                            Bundle bundle = new Bundle();
                            String taskType = "";
                            if (getIntent().getStringExtra("taskType") != null) {
                                taskType = getIntent().getStringExtra("taskType");
                            } else if (taskQuestion.getTaskType() != null) {
                                taskType = taskQuestion.getTaskType();
                            }
                            bundle.putString("taskType", taskType);
                            intent.putExtras(bundle);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
        }
    }

    // 传递参数
    private void passParameterFun() {
        String openMode = getIntent().getStringExtra("openMode");
        taskQuestion.setOpenMode(openMode);
        if (openMode.equals("1")) {
            if (getIntent().getStringExtra("projectGuid") != null) {
                taskQuestion.setProjectGuid(getIntent().getStringExtra("projectGuid"));
            }
            if (getIntent().getStringExtra("elevatorGuid") != null) {
                taskQuestion.setElevatorGuid(getIntent().getStringExtra("elevatorGuid"));
            }
            if (getIntent().getStringExtra("taskType") != null) {
                taskQuestion.setTaskType(getIntent().getStringExtra("taskType"));
            }
            if (getIntent().getStringExtra("remark") != null) {
                taskQuestion.setRemark(getIntent().getStringExtra("remark"));
            }
        } else if (openMode.equals("2")) {
            if (textview7_1.getText() != null) {
                taskQuestion.setRemark(textview7_1.getText().toString());
            }
        } else if (openMode.equals("3")) {
            if (getIntent().getStringExtra("taskId") != null) {
                taskQuestion.setTaskId(getIntent().getStringExtra("taskId"));
            }
            if (getIntent().getStringExtra("guid") != null) {
                taskQuestion.setGuid(getIntent().getStringExtra("guid"));
            }
            if (textview8_1.getText() != null) {
                taskQuestion.setContent(textview8_1.getText().toString());
            }
        } else if (openMode.equals("4")) {
            if (getIntent().getStringExtra("taskId") != null) {
                taskQuestion.setTaskId(getIntent().getStringExtra("taskId"));
            }
            if (getIntent().getStringExtra("guid") != null) {
                taskQuestion.setGuid(getIntent().getStringExtra("guid"));
            }
        }
    }

    // 校验参数
    private boolean judgeParameterFun() {
        boolean returnFlag = true;
        String openMode = getIntent().getStringExtra("openMode");
        if (openMode.equals("1") || openMode.equals("2")) {
            if (taskQuestion.getProjectGuid() == null || taskQuestion.getProjectGuid().equals("")) {
                ToastUtil.showLong(getResources().getString(R.string.is_check_project_name));
                returnFlag = false;
            } else if (taskQuestion.getElevatorGuid() == null || taskQuestion.getElevatorGuid().equals("")) {
                ToastUtil.showLong(getResources().getString(R.string.is_check_elevator_name));
                returnFlag = false;
            } else if (taskQuestion.getTaskType() == null || taskQuestion.getTaskType().equals("")) {
                ToastUtil.showLong(getResources().getString(R.string.is_check_task_name));
                returnFlag = false;
            } else if (taskQuestion.getAssignUser() == null || taskQuestion.getAssignUser().equals("")) {
                ToastUtil.showLong(getResources().getString(R.string.is_check_assign_user));
                returnFlag = false;
            } else if (taskQuestion.getConfirmUser() == null || taskQuestion.getConfirmUser().equals("")) {
                ToastUtil.showLong(getResources().getString(R.string.is_check_confirm_user));
                returnFlag = false;
            } else if (taskQuestion.getEndDate() == null || taskQuestion.getEndDate().equals("")) {
                ToastUtil.showLong(getResources().getString(R.string.is_check_end_date));
                returnFlag = false;
            }
        } else if (openMode.equals("3") || openMode.equals("4")) {
            if (taskQuestion.getTaskId() == null || taskQuestion.getTaskId().equals("")) {
                ToastUtil.showLong(getResources().getString(R.string.is_check_task_id));
                returnFlag = false;
            }
            if (taskQuestion.getGuid() == null || taskQuestion.getGuid().equals("")) {
                ToastUtil.showLong(getResources().getString(R.string.is_check_is_task_question_guid));
                returnFlag = false;
            }
        }
        return returnFlag;
    }

    private Dialog dialog;

    private ImageView mImageView;

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        String adapterFlag = "";
        if (adapter instanceof TaskQuestionAssignAdapter) {
            adapterFlag = "assign";
        } else if (adapter instanceof TaskQuestionConfirmAdapter) {
            adapterFlag = "confirm";
        }
        if (!adapterFlag.equals("")) {
            int i = view.getId();
            if (i == R.id.close_img_id) {
                if (adapterFlag.equals("assign")) {
                    taskQuestionPicList1.remove(position);
                    mAdapter1.notifyItemRemoved(position);
                    mAdapter1.notifyDataSetChanged();
                } else if (adapterFlag.equals("confirm")) {
                    taskQuestionPicList2.remove(position);
                    mAdapter2.notifyItemRemoved(position);
                    mAdapter2.notifyDataSetChanged();
                }

            } else if (i == R.id.bg_img_id) {
                getImageView(position, adapterFlag);

            } else {
            }
        }
    }

    //动态的ImageView
    private void getImageView(int position, String adapterFlag) {
        List<TaskQuestionPic> taskQuestionPicList = null;
        if (adapterFlag.equals("assign")) {
            taskQuestionPicList = taskQuestionPicList1;
        } else if (adapterFlag.equals("confirm")) {
            taskQuestionPicList = taskQuestionPicList2;
        }
        String filePath = "";
        if (taskQuestionPicList.get(position) != null) {
            if (taskQuestionPicList.get(position).getPicguid() != null && !taskQuestionPicList.get(position).getPicguid().equals("")) {
                filePath = BuildConfig.BASE_URL + "s1/downloadAttach?guid=" + taskQuestionPicList.get(position).getPicguid();
            } else if (taskQuestionPicList.get(position).getFilePath() != null && !taskQuestionPicList.get(position).getFilePath().equals("")) {
                filePath = taskQuestionPicList.get(position).getFilePath();
            }
        }
        Intent intent = new Intent();
        intent.putExtra("url", filePath);
        intent.setClass(mContext, ShowBigImageSimpleActivity.class);
        startActivity(intent);
    }
}
