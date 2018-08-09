package com.zxtech.is.delegate;

import android.content.Context;
import android.util.Log;

import com.zxtech.is.common.net.BaseResponseForLogin;
import com.zxtech.is.common.net.DefaultObserverForLogin;
import com.zxtech.is.net.HttpFactoryForLogin;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.service.login.LoginService;
import com.zxtech.is.util.AppUtil;
import com.zxtech.is.util.SPUtils;
import com.zxtech.module.common.base.IApplicationDelegate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by syp523 on 2018/6/15.
 */

public class AppcationDelegate implements IApplicationDelegate {
    @Override
    public void onCreate(Context applicationContext) {
        AppUtil.init(applicationContext);
        // is 登陆 begin
        Map params = new HashMap();
        //安装经理 杨水
        //params.put("username", "28501133");
        //fe 黄岩
        //params.put("username", "28400009");
        //pe 白春鹏
        params.put("username", "28400005");
        params.put("password", "1");
        params.put("grant_type", "password");
        params.put("scope", "select");
        params.put("client_id", "client_pwd");
        params.put("client_secret", "1");
        LoginService loginService = HttpFactoryForLogin.getService(LoginService.class);
        loginService.login(params).compose(RxHelper.<BaseResponseForLogin>rxSchedulerHelper()).subscribe(new DefaultObserverForLogin() {

            @Override
            public void onSuccess(BaseResponseForLogin response) {
                Log.d("token=", response.getAccess_token());
                Log.d("userId=", response.getUserId());
                Log.d("userNo=", response.getUserNo());
                Log.d("username=", response.getUsername());
                Log.d("roleIds=", response.getRoleIds());
                Log.d("hqFlag=", response.getHqFlag());
                Log.d("projectFlag=", response.getProjectFlag());

                SPUtils.put(AppUtil.getContext(), "is_token", response.getAccess_token());
                SPUtils.put(AppUtil.getContext(), "is_user_Id", response.getUserId());
                SPUtils.put(AppUtil.getContext(), "userNo", response.getUserNo());
                SPUtils.put(AppUtil.getContext(), "is_username", response.getUsername());
                SPUtils.put(AppUtil.getContext(), "roleIds", response.getRoleIds());
                SPUtils.put(AppUtil.getContext(), "hqFlag", response.getHqFlag());
                SPUtils.put(AppUtil.getContext(), "projectFlag", response.getProjectFlag());
            }
        });
        // is 登陆 end
    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int level) {

    }
}
