package com.zxtech.ecs.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.MainActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.event.EventAction;
import com.zxtech.ecs.model.UserManager;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.RegexUtils;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.gks.common.SPUtils;
import com.zxtech.gks.model.vo.login.LoginVO;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 完善信息
 * Created by syp523 on 2018/4/12.
 */

public class PerfectInformationActivity extends BaseActivity {
    @BindView(R.id.real_name_et)
    EditText real_name_et;
    @BindView(R.id.email_et)
    EditText email_et;
    @BindView(R.id.company_et)
    EditText company_et;
    @BindView(R.id.username_iv)
    ImageView username_iv;
    @BindView(R.id.email_iv)
    ImageView email_iv;
    @BindView(R.id.company_iv)
    ImageView company_iv;
    @BindView(R.id.complete_btn)
    Button complete_btn;
    @BindView(R.id.next_btn)
    Button next_btn;
    @BindView(R.id.bg_iv)
    ImageView bg_iv;
    @BindView(R.id.logo_iv)
    ImageView logo_iv;
    @BindView(R.id.real_name_line)
    View real_name_line;
    @BindView(R.id.email_line)
    View email_line;
    @BindView(R.id.company_line)
    View company_line;
    @BindView(R.id.tip_tv)
    TextView tip_tv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_perfect_info;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (getString(R.string.agent).equals(Constants.AGENT_MNK)) {
            bg_iv.setImageResource(R.drawable.mnk_login_bg);
            logo_iv.setImageResource(R.drawable.mnk_login_logo);
            username_iv.setImageResource(R.drawable.mnk_username);
            email_iv.setImageResource(R.drawable.mnk_email);
            company_iv.setImageResource(R.drawable.mnk_company);
            complete_btn.setBackgroundResource(R.drawable.button_main_radius);
            real_name_et.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            email_et.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            company_et.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            tip_tv.setTextColor(getResources().getColor(R.color.default_text_grey_color));
            real_name_line.setBackgroundColor(getResources().getColor(R.color.default_text_black_color));
            email_line.setBackgroundColor(getResources().getColor(R.color.default_text_black_color));
            company_line.setBackgroundColor(getResources().getColor(R.color.default_text_black_color));
        }
    }

    @OnClick({R.id.complete_btn, R.id.next_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.complete_btn:
                updateUserAction();
                break;
            case R.id.next_btn:
                doLogin();
                break;
        }
    }

    private void updateUserAction() {
        if (TextUtils.isEmpty(real_name_et.getText()) && TextUtils.isEmpty(email_et.getText()) && TextUtils.isEmpty(company_et.getText())) {
            ToastUtil.showLong(getString(R.string.msg40));
            return;
        }
        if (!TextUtils.isEmpty(email_et.getText()) && !RegexUtils.isEmail(email_et.getText())) {
            ToastUtil.showLong(getString(R.string.msg41));
            return;
        }
        Intent intent = getIntent();
        HashMap<String, String> params = new HashMap();
        params.put("userEmail", email_et.getText().toString());
        params.put("userName", real_name_et.getText().toString());
        params.put("userRemark", company_et.getText().toString());
        params.put("userId", intent.getStringExtra("userId"));
        params.put("userNo", intent.getStringExtra("userNo"));
        baseResponseObservable = HttpFactory.getApiService().
                updateUser(new Gson().toJson(params));
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<LoginVO>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<LoginVO>>(this, true) {

                    @Override
                    public void onSuccess(BaseResponse<LoginVO> response) {
                        doLogin();
                    }
                });
    }


    private void doLogin() {
        Intent intent = getIntent();
        final String username = intent.getStringExtra("userNo");
        final String pwd = intent.getStringExtra("password");
        final String country = intent.getStringExtra("countryCode");
        Map params = new HashMap();
        params.put("userNo", username);
        params.put("password", pwd);
        params.put("platformType", "android");
        params.put("countryCode", country);
        params.put("language", Util.convertEcsLanguage(language));

        baseResponseObservable = HttpFactory.getApiService().
                getLoginInfo(params);
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<LoginVO>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<LoginVO>>(this, true) {

                    @Override
                    public void onSuccess(BaseResponse<LoginVO> response) {

                        LoginVO loginData = response.getData();
                        UserManager.saveUserInfo(mContext, loginData, true, username, pwd, country);
                        //存储登录成功用户名
                        Set<String> usernameSet = (Set<String>) SPUtils.get(mContext, "username_array", new HashSet<String>());
                        usernameSet.add(username);
                        SPUtils.put(mContext, "username_array", usernameSet);
                        SPUtils.put(mContext,"wx_login",false);


                        Intent intent = new Intent(mContext,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(-1,-1);
                    }
                });

    }
}
