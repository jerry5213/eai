package com.zxtech.ecs.ui.home.qmsmanager;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.JsonEntity;
import com.zxtech.ecs.model.JsonEvaluateEntity;
import com.zxtech.ecs.model.SaveAPPFeedbackInfoEntity;
import com.zxtech.ecs.model.SystemCodeListBean;
import com.zxtech.ecs.model.SystemCodeListEntity;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.DateUtil;
import com.zxtech.ecs.util.GsonUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.DropDownWindow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zxtech.ecs.common.Constants.ELEVATOR;

/**
 * @auth:hexl
 * @date:2018/2/28,
 * @desc:评价
 */

public class QmsEvaluateActivity extends BaseActivity implements RatingBar.OnRatingBarChangeListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_qms_materiel_arrival_time)
    TextView mTvQmsMaterielArrivalTime;
    @BindView(R.id.tv_qms_rectify_finish_time)
    TextView mTvQmsRectifyFinishTime;
    @BindView(R.id.tv_qms_solve)
    TextView mTvQmsSolve;
    @BindView(R.id.rating_bar_qms)
    RatingBar mRatingBarQms;
    @BindView(R.id.et_qms_detail_evaluate)
    EditText mEtQmsDetailEvaluate;
    private String mRatingStart;
    private String AutoGuid;
    private List<SystemCodeListBean> mCodeLists;
    private String qmsLan;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qms_evaluate;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);
        mRatingBarQms.setOnRatingBarChangeListener(this);
        AutoGuid = getIntent().getStringExtra("AutoGuid");
        qmsLan = Util.convertQmsLanguage(language);
        initSolve();
    }

    @OnClick({R.id.btn_qms_submit, R.id.rating_bar_qms, R.id.tv_qms_materiel_arrival_time,
            R.id.tv_qms_rectify_finish_time,
            R.id.tv_qms_solve})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_qms_submit://提交
                checkNull();
                break;
            case R.id.tv_qms_materiel_arrival_time:
                openTime(mTvQmsMaterielArrivalTime);
                break;
            case R.id.tv_qms_rectify_finish_time:
                openTime(mTvQmsRectifyFinishTime);
                break;
            case R.id.tv_qms_solve:
                dropDownSolve(v);
                break;
        }
    }

    private void initSolve() {

        mCodeLists = new ArrayList<>();
        //将参数转为json格式
        JsonEntity jsonEntity = new JsonEntity("JJQKCode", qmsLan, "", "", ELEVATOR);
        String params = GsonUtils.toJson(jsonEntity, false);

        HttpFactory.getApiService()
                .getSystemCode(params)
                .compose(RxHelper.<BaseResponse<SystemCodeListEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<SystemCodeListEntity>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<SystemCodeListEntity> response) {
                        if (response.getData().getSystemCodeList().size() != 0) {
                            mCodeLists = response.getData().getSystemCodeList();
                        }
                    }
                });
    }

    protected void dropDownSolve(View view) {

        if (mCodeLists == null || mCodeLists.size() == 0) {
            ToastUtil.showLong("无数据");
            return;
        }

        Drawable image = getResources().getDrawable(R.drawable.up);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        mTvQmsSolve.setCompoundDrawables(null, null, image, null);

        DropDownWindow mWindow = new DropDownWindow(mContext, view, mCodeLists, view.getWidth(), -2) {

            @Override
            public void initEvents(final int p) {

                mTvQmsSolve.setText(mCodeLists.get(p).getValue());
                mTvQmsSolve.setTag(mCodeLists.get(p).getCode());
                this.dismiss();
            }

            @Override
            public void dismissEvents() {
                Drawable image = getResources().getDrawable(R.drawable.down);
                image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
                mTvQmsSolve.setCompoundDrawables(null, null, image, null);
            }
        };
    }

    private void openTime(final TextView textView) {
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
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }

    private void checkNull() {
        String e_wldhsj = mTvQmsMaterielArrivalTime.getText().toString();
        String g_zgwcsj = mTvQmsRectifyFinishTime.getText().toString();
        String g_jjqk = mTvQmsSolve.getText().toString();
        String g_myddf = mRatingStart;
        String g_xxpj = mEtQmsDetailEvaluate.getText().toString();

        if (TextUtils.isEmpty(AutoGuid)) {
            ToastUtil.showLong("AutoGuid不能为空");
            return;
        }
        String params = GsonUtils.toJson(new JsonEvaluateEntity(AutoGuid, e_wldhsj, g_zgwcsj, g_jjqk, g_myddf, g_xxpj), false);
        submit(params);
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        Log.d("QmsEvaluateActivity", "rating:" + rating);
        mRatingStart = String.valueOf(rating);
    }

    private void submit(String params) {
        HttpFactory.getApiService()
                .saveFeedbackInfoEvaluate(params)
                .compose(RxHelper.<BaseResponse<SaveAPPFeedbackInfoEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<SaveAPPFeedbackInfoEntity>>(this, true) {
                    @Override
                    public void onSuccess(BaseResponse<SaveAPPFeedbackInfoEntity> response) {
                        if (response.getData().getResult().equals("1")) {
                            ToastUtil.showLong("评价成功");
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                });


    }
}
