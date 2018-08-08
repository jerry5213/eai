package com.zxtech.ecs.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.DensityUtil;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.gks.model.vo.login.LoginVO;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册
 * Created by syp523 on 2018/4/12.
 */

public class FindBackPasswordActivity extends BaseActivity {

    private MyCountDownTimer mc;
    @BindView(R.id.get_code_tv)
    TextView get_code_tv;
    @BindView(R.id.login_name_et)
    EditText login_name_et;
    @BindView(R.id.code_et)
    EditText code_et;
    @BindView(R.id.pwd_et)
    EditText pwd_et;
    @BindView(R.id.bg_iv)
    ImageView bg_iv;
    @BindView(R.id.logo_iv)
    ImageView logo_iv;
    @BindView(R.id.reg_btn)
    Button reg_btn;
    @BindView(R.id.username_iv)
    ImageView username_iv;
    @BindView(R.id.vercode_iv)
    ImageView vercode_iv;
    @BindView(R.id.psd_iv)
    ImageView psd_iv;
    @BindView(R.id.login_name_line)
    View login_name_line;
    @BindView(R.id.code_line)
    View code_line;
    @BindView(R.id.pwd_line)
    View pwd_line;
    @BindView(R.id.country_code_et)
    EditText country_code_et;
    @BindView(R.id.country_code_line)
    View country_code_line;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_findback_password;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (getString(R.string.agent).equals(Constants.AGENT_MNK)) {
            bg_iv.setImageResource(R.drawable.mnk_login_bg);
            logo_iv.setImageResource(R.drawable.mnk_login_logo);
            reg_btn.setBackgroundResource(R.drawable.button_main_radius);
            username_iv.setImageResource(R.drawable.mnk_phone);
            vercode_iv.setImageResource(R.drawable.mnk_verification_code);
            psd_iv.setImageResource(R.drawable.mnk_password);
            get_code_tv.setBackgroundResource(R.drawable.radius15_main_solid_border);
            get_code_tv.setPadding(DensityUtil.dip2px(mContext, 10), DensityUtil.dip2px(mContext, 3), DensityUtil.dip2px(mContext, 10), DensityUtil.dip2px(mContext, 3));
            login_name_line.setBackgroundColor(getResources().getColor(R.color.default_text_black_color));
            code_line.setBackgroundColor(getResources().getColor(R.color.default_text_black_color));
            pwd_line.setBackgroundColor(getResources().getColor(R.color.default_text_black_color));
            country_code_line.setBackgroundColor(getResources().getColor(R.color.default_text_black_color));
            login_name_et.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            code_et.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            pwd_et.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            country_code_et.setTextColor(getResources().getColor(R.color.default_text_grey_color));

        }
    }

    @OnClick({R.id.reg_btn, R.id.get_code_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reg_btn:
                regAction();


                break;
            case R.id.get_code_tv:
                if (TextUtils.isEmpty(login_name_et.getText())) {
                    ToastUtil.showLong(getString(R.string.msg22));
                    return;
                }
                get_code_tv.setEnabled(false);
                mc = new MyCountDownTimer(60000 + 50, 1000);
                mc.start();
                baseResponseObservable = HttpFactory.getApiService().
                        getVerificationCode(login_name_et.getText().toString());
                baseResponseObservable
                        .compose(RxHelper.<BaseResponse<LoginVO>>rxSchedulerHelper())
                        .subscribe(new DefaultObserver<BaseResponse<LoginVO>>(this, true) {

                            @Override
                            public void onSuccess(BaseResponse<LoginVO> response) {


                            }
                        });

                break;
        }
    }

    private void regAction() {

        if (TextUtils.isEmpty(login_name_et.getText())) {
            ToastUtil.showLong(getString(R.string.msg22));
            return;
        }
        if (TextUtils.isEmpty(code_et.getText())) {
            ToastUtil.showLong(getString(R.string.msg23));
            return;
        }
        if (TextUtils.isEmpty(pwd_et.getText())) {
            ToastUtil.showLong(getString(R.string.msg21));
            return;
        }
        baseResponseObservable = HttpFactory.getApiService().
                modifyPassword("", login_name_et.getText().toString(), "",pwd_et.getText().toString(),"2");
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(this, true) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        ToastUtil.showLong(getString(R.string.msg28));
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }

    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            get_code_tv.setEnabled(true);
            get_code_tv.setText(R.string.regain);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            get_code_tv.setText(millisUntilFinished / 1000 +getString(R.string.second));
        }
    }
}
