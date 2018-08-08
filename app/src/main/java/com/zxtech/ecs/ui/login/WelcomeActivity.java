package com.zxtech.ecs.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.App;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.MainActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.event.EventAction;
import com.zxtech.ecs.model.UserManager;
import com.zxtech.ecs.model.WXInfo;
import com.zxtech.ecs.net.ApiService;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.service.LocationService;
import com.zxtech.ecs.test.wxapi.WXEntryActivity;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.StatusBarUtils;
import com.zxtech.gks.model.vo.login.Depts;
import com.zxtech.gks.model.vo.login.LoginVO;
import com.zxtech.gks.model.vo.login.Roles;
import com.zxtech.gks.model.vo.login.UserInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 欢迎界面
 * Created by syp523 on 2018/4/19.
 */

public class WelcomeActivity extends BaseActivity  {
    private Call<BaseResponse<LoginVO>> callback;

    private static final int DELAY_MILLIS = 3000;
    private static final int TIMEOUT = 3500;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {



        Boolean isFirst = (Boolean) SPUtils.get(mContext, "is_first", true);//第一次登陆
        handler.sendEmptyMessageDelayed(0, DELAY_MILLIS);
        if (!isFirst) {
            Boolean wx_login = (Boolean) SPUtils.get(mContext, "wx_login", false);//微信登录

            if (wx_login) {
                wxLogin();
                return;
            }

            final String user_no = (String) SPUtils.get(mContext, "user_no", "");//用户名
            final String password = (String) SPUtils.get(mContext, "password", "");//密码
            final String country_code = (String) SPUtils.get(mContext, "country_code", "");//国家代码
            if (!TextUtils.isEmpty(user_no) && !TextUtils.isEmpty(password)) {
                Map params = new HashMap();
                params.put("userNo", user_no);
                params.put("password", password);
                params.put("platformType", "android");
                params.put("countryCode", country_code);
                callback = getRetrofit().create(ApiService.class).autoLoginInfo(params);
                callback.enqueue(new Callback<BaseResponse<LoginVO>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<LoginVO>> call, Response<BaseResponse<LoginVO>> response) {
                        BaseResponse<LoginVO> body = response.body();
                        if (response.isSuccessful() && "success".equals(body.getStatus())) {
                            LoginVO loginData = body.getData();
                            SPUtils.put(mContext, "wx_login", false);
                            UserManager.saveUserInfo(mContext, loginData, true, user_no, password, country_code);
                            EventBus.getDefault().postSticky(new EventAction(EventAction.LOGIN_REFRESH_MENU)); //刷新菜单
                        } else {
                            failCheckUser();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<LoginVO>> call, Throwable t) {
                        failCheckUser();
                    }
                });
            }
        }
    }

    private Retrofit getRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(APPConfig.BASE_URL)
                .build();
        return retrofit;
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            goHome();
            super.handleMessage(msg);
        }
    };

    public void goHome() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(-1, -1);
    }

    private void wxLogin() {
        String wx_open_id = (String) SPUtils.get(mContext, "wx_openid", "");
        String wx_access_token = (String) SPUtils.get(mContext, "wx_access_token", "");
        String wx_refresh_token = (String) SPUtils.get(mContext, "wx_refresh_token", "");

        callback = getRetrofit().create(ApiService.class).wechatAutoLogin(Constants.WECHAT_APP_ID, wx_open_id, wx_access_token, wx_refresh_token);
        callback.enqueue(new Callback<BaseResponse<LoginVO>>() {
            @Override
            public void onResponse(Call<BaseResponse<LoginVO>> call, Response<BaseResponse<LoginVO>> response) {
                BaseResponse<LoginVO> body = response.body();
                if (response.isSuccessful() && "success".equals(body.getStatus())) {
                    LoginVO loginData = body.getData();
                    SPUtils.put(mContext, "wx_login", true);
                    UserManager.saveUserInfo(mContext, loginData, false, null, null, null);
                    EventBus.getDefault().postSticky(new EventAction(EventAction.LOGIN_REFRESH_MENU)); //刷新菜单
                } else {
                    failCheckUser();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<LoginVO>> call, Throwable t) {
                failCheckUser();
            }
        });
    }


    private void failCheckUser() {
        SPUtils.put(mContext, "user_id", "");
        SPUtils.put(mContext, "user_no", "");
        SPUtils.put(mContext, "user_name", "");
        SPUtils.put(mContext, "user_dept_no", "");
        SPUtils.put(mContext, "user_dept_name", "");
        SPUtils.put(mContext, "user_remark", "");
        SPUtils.put(mContext, "user_email", "");
        SPUtils.put(mContext, "country_code", "");
        SPUtils.put(mContext, "password", "");
    }


}
