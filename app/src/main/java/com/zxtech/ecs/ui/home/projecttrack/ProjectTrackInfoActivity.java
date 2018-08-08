package com.zxtech.ecs.ui.home.projecttrack;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.DropDown;
import com.zxtech.ecs.model.ProjectDetail;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.BasicResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.widget.DropDownWindow;
import com.zxtech.gks.model.bean.GetProjectDetailDropDownList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ProjectTrackInfoActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //合同状态
    @BindView(R.id.contact_state_selector)
    TextView contactState;
    //项目状态
    @BindView(R.id.project_State_selector)
    TextView projectState;
    //项目阶段
    @BindView(R.id.project_Stage_selector)
    TextView projectStage;
    //预定签约日期
    @BindView(R.id.exSign_Date_selector)
    TextView exSignDate;
    //预定付定金日期
    @BindView(R.id.exPay_Date_selector)
    TextView exPayDate;
    //预定交付日期
    @BindView(R.id.exDelivery_Dateselector)
    TextView exDeliveryDate;
    //预定报价时间
    @BindView(R.id.exEQS_Date)
    TextView exEQS_Date;

    //目的地
    @BindView(R.id.destination)
    EditText destination;
    //拜访客户名称
    @BindView(R.id.customerName_text)
    TextView customerName_text;
    //拜访时间
    @BindView(R.id.visitDate_text)
    TextView visitDate_text;
    //拜访事由
    @BindView(R.id.visitReason_text)
    TextView visitReason_text;
    @BindView(R.id.salesmanUser_text)
    TextView saleMan;
    @BindView(R.id.dept_text)
    TextView deptName;

    //项目guid
    String projectGuid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_track_info;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        projectGuid = getIntent().getStringExtra("projectGuid");
        initTitleBar(toolbar);
        initContactState();
        initSolve();
        getOther();
        saleMan.setText(getUserName());
        deptName.setText(getUserDeptName());
    }

    @OnClick({R.id.exSign_Date_selector, R.id.exPay_Date_selector,
            R.id.exDelivery_Dateselector,
            R.id.exEQS_Date,
            R.id.tv_save,
            R.id.project_State_selector,
            R.id.project_Stage_selector,
            R.id.contact_state_selector,
            R.id.visitDate_text
    })
    void onClick(View v) {

        DropDownWindow mWindow = null;
        switch (v.getId()) {
            //预定签约日期
            case R.id.exSign_Date_selector:
                openTime(exSignDate);
                break;
            //预定交付日期
            case R.id.exPay_Date_selector:
                openTime(exPayDate);
                break;
            //预定报价时间
            case R.id.exDelivery_Dateselector:
                openTime(exDeliveryDate);
                break;
            //预定报价时间
            case R.id.exEQS_Date:
                openTime(exEQS_Date);
                break;
            //拜访时间
            case R.id.visitDate_text:
                openTime(visitDate_text);
                break;
            //保存
            case R.id.tv_save:
                save();
                break;
            case R.id.project_State_selector:
                if (dropDownList == null) return;
                mWindow = new DropDownWindow(mContext, v, (TextView) v, dropDownList.getProjectStateList(), v.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        DropDown dropDown = dropDownList.getProjectStateList().get(p);
                        projectState.setText(dropDown.getText());
                        projectState.setTag(dropDown.getValue());
                        this.dismiss();
                    }
                };
                break;
            case R.id.project_Stage_selector:
                if (dropDownList == null) return;
                mWindow = new DropDownWindow(mContext, v, (TextView) v, dropDownList.getProjectStageList(), v.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        DropDown dropDown = dropDownList.getProjectStageList().get(p);
                        projectStage.setText(dropDown.getText());
                        projectStage.setTag(dropDown.getValue());
                        this.dismiss();
                    }
                };
                break;
            case R.id.contact_state_selector:
                mWindow = new DropDownWindow(mContext, v, (TextView) v, contactStateList, v.getWidth(), -2) {

                    @Override
                    public void initEvents(final int p) {
                        String txt = contactStateList.get(p);
                        contactState.setText(txt);
                        contactState.setTag(txt);
                        this.dismiss();
                    }
                };
                break;
        }
    }

    private List<String> contactStateList = new ArrayList<>();

    private void initContactState(){

        contactStateList.add(getString(R.string.please_choose));
        contactStateList.add("已签约");
        contactStateList.add("已失单");
    }

    private Calendar cal = Calendar.getInstance();
    private TextView date_tv;

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            if(date_tv!=null){
                date_tv.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
            }
        }
    };

    //时间控件
    private void openTime(final TextView textView) {
        /*Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DATE);
        @SuppressWarnings("ResourceType")
        DatePickerDialog dpd = new DatePickerDialog(mContext, DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker dp, int year, int month, int day) {
                String date = DateUtil.formatChange(year, month, day, "yyyy-MM-dd");
                textView.setText(date);
            }
        }, mYear, mMonth, mDay);
        dpd.show();*/

        date_tv = textView;

        new DatePickerDialog(mContext, listener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private String detailGuid = "";

    //初始化项目信息
    private void initSolve() {

        HttpFactory.getApiService()
                .getProjectTrackDetail(projectGuid)
                .compose(RxHelper.<BaseResponse<ProjectDetail>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<ProjectDetail>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<ProjectDetail> response) {
                        if (response.getData() != null) {

                            ProjectDetail detail = response.getData();
                            detailGuid = detail.getDetailGuid();
                            projectState.setText(detail.getProjectState());
                            projectStage.setText(detail.getProjectStage());
                            exSignDate.setText(detail.getExSignDate());
                            exPayDate.setText(detail.getExPayDate());
                            exDeliveryDate.setText(detail.getExDeliveryDate());
                            exEQS_Date.setText(detail.getExEQSDate());
                        }
                    }
                });
    }

    //校验填写的数据
    protected boolean checkData() {

        /*if ("".equals(projectState.getText().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_projectState_null_msg));
            return false;
        }
        if ("".equals(projectStage.getText().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_projectStage_null_msg));
            return false;
        }
        if ("".equals(exSignDate.getText().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_exSignDate_null_msg));
            return false;
        }
        if ("".equals(exPayDate.getText().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_exPayDate_null_msg));
            return false;
        }
        if ("".equals(exDeliveryDate.getText().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_exDeliveryDate_null_msg));
            return false;
        }
        if ("".equals(exEQS_Date.getText().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_exEQSDate_null_msg));
            return false;
        }*/

        if(!"请选择".equals(contactState.getText().toString()) && !TextUtils.isEmpty(contactState.getText().toString())){

            if(TextUtils.isEmpty(customerName_text.getText().toString())
                    && TextUtils.isEmpty(visitDate_text.getText().toString())
                    && TextUtils.isEmpty(visitReason_text.getText().toString())
                    && TextUtils.isEmpty(destination.getText().toString())
                    ){
                return true;
            }
        }

        if ("".equals(customerName_text.getText().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_customerName_null_msg));
            return false;
        }
        if ("".equals(visitDate_text.getText().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_visitDate_null_msg));
            return false;
        }
        if ("".equals(visitReason_text.getText().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_visitReason_null_msg));
            return false;
        }
        if (TextUtils.isEmpty(destination.getText().toString())) {
            ToastUtil.showLong(getResources().getString(R.string.is_check_Destination_null_msg));
            return false;
        }
        return true;
    }

    private GetProjectDetailDropDownList dropDownList;

    private void getOther() {

        baseResponseObservable = HttpFactory.getApiService().getProjectDetailDropDownList();
        baseResponseObservable
                .compose(RxHelper.<BasicResponse<GetProjectDetailDropDownList>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BasicResponse<GetProjectDetailDropDownList>>(this, false) {

                    @Override
                    public void onSuccess(BasicResponse<GetProjectDetailDropDownList> response) {
                        dropDownList = response.getData();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }

    private void save() {

        if (checkData()) {
            //合同状态
            String contact_state = contactState.getText().toString();
            //项目状态
            String project_State = projectState.getText().toString();
            //项目阶段
            String project_Stage = projectStage.getText().toString();
            //预定签约日期
            String exSign_Date = exSignDate.getText().toString();
            //预定付定金日期
            String exPay_Date = exPayDate.getText().toString();
            //预定交付日期
            String exDelivery_Date = exDeliveryDate.getText().toString();
            //预定报价时间
            String exEQSDate = exEQS_Date.getText().toString();
            //目的地
            //String exEQSDate = exEQS_Date.getText().toString();
            //拜访客户名称
            String customerName = customerName_text.getText().toString();
            //拜访时间
            String visitDate = visitDate_text.getText().toString();
            //拜访事由
            String visitReason = visitReason_text.getText().toString();
            //目的地
            String destinationTxt = destination.getText().toString();

            Map params = new HashMap();
            params.put("projectGuid", projectGuid);
            if(!"请选择".equals(contact_state) && !TextUtils.isEmpty(contact_state)){
                params.put("contactState", contact_state);
            }
            params.put("detailGuid", detailGuid);
            params.put("projectState", project_State);
            params.put("projectStage", project_Stage);
            params.put("exSignDate", exSign_Date);
            params.put("exPayDate", exPay_Date);
            params.put("exDeliveryDate", exDelivery_Date);
            params.put("exEQSDate", exEQSDate);

            params.put("trackGuid", "");
            params.put("customerName", customerName);
            params.put("destination", destinationTxt);
            params.put("visitReason", visitReason);
            params.put("visitDate", visitDate);
            params.put("deptNo", getUserDeptNo());
            params.put("deptName", getUserDeptName());
            params.put("userId", getUserId());
            params.put("userName", getUserName());

            baseResponseObservable = HttpFactory.getApiService().saveProjectTrack(new Gson().toJson(params));
            baseResponseObservable
                    .compose(RxHelper.<BaseResponse>rxSchedulerHelper())
                    .subscribe(new DefaultObserver<BaseResponse>(getActivity(), true) {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            ToastUtil.showLong("保存成功");
                            finish();
//                            EventBus.getDefault().post(new EventAction(EventAction.PRODUCT_CHANGE));
                        }
                    });
        }

    }
}
