package com.zxtech.is.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.HttpFactoryForLogin;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.util.AppUtil;
import com.zxtech.is.util.GsonUtils;
import com.zxtech.is.util.SPUtils;
import com.zxtech.is.util.ToastUtil;
import com.zxtech.is.util.Util;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.IsMainActivity;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.BaseResponseForLogin;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.common.net.DefaultObserverForLogin;
import com.zxtech.is.model.login.Login;
import com.zxtech.is.service.login.LoginService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginActivity extends BaseActivity {

    @BindView(R2.id.login_name_edittext)
    AutoCompleteTextView login_name_edittext;
    @BindView(R2.id.login_pwd_edittext)
    EditText login_pwd_edittext;
    @BindView(R2.id.version_tv)
    TextView version_tv;
    @BindView(R2.id.remember_cb)
    CheckBox remember_cb;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        login_pwd_edittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        version_tv.setText("V " + Util.getVersion(mContext));

        String userNo = (String) SPUtils.get(AppUtil.getContext(), "userNo", "");
        if (!TextUtils.isEmpty(userNo)) {
            login_name_edittext.setText(userNo);
        }
    }

    @OnClick(R2.id.login_btn)
    public void onClick() {

        String username = login_name_edittext.getText().toString().trim();
        String pwd = login_pwd_edittext.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            ToastUtil.showLong("用户名为空，请填写");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showLong("密码为空，请填写");
            return;
        }
        doLogin(username, pwd);
    }

    private void doLogin(final String username, String pwd) {
        Log.d("login_id", username);
        Log.d("login_pwd", pwd);

        Map params = new HashMap();
        params.put("username", username);
        params.put("password", pwd);
        params.put("grant_type", "password");
        params.put("scope", "select");
        params.put("client_id", "client_pwd");
        params.put("client_secret", "1");
        LoginService loginService = HttpFactoryForLogin.getService(LoginService.class);
        loginService.login(params).compose(RxHelper.<BaseResponseForLogin>rxSchedulerHelper()).subscribe(new DefaultObserverForLogin(getActivity(), false) {

            @Override
            public void onSuccess(BaseResponseForLogin response) {
                Log.d("token=", response.getAccess_token());
                Log.d("userId=", response.getUserId());
                Log.d("userNo=", response.getUserNo());
                Log.d("username=", response.getUsername());
                Log.d("roleIds=", response.getRoleIds());
                Log.d("hqFlag=", response.getHqFlag());
                Log.d("projectFlag=", response.getProjectFlag());

                SPUtils.put(AppUtil.getContext(), "token", response.getAccess_token());
                SPUtils.put(AppUtil.getContext(), "userId", response.getUserId());
                SPUtils.put(AppUtil.getContext(), "userNo", response.getUserNo());
                SPUtils.put(AppUtil.getContext(), "username", response.getUsername());
                SPUtils.put(AppUtil.getContext(), "roleIds", response.getRoleIds());
                SPUtils.put(AppUtil.getContext(), "hqFlag", response.getHqFlag());
                SPUtils.put(AppUtil.getContext(), "projectFlag", response.getProjectFlag());

                Intent intent = new Intent();
                intent.putExtra("switch_language", false);
                startActivity(IsMainActivity.class, intent);
                finish();
            }
        });

    }
}
