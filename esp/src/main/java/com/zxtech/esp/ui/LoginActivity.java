package com.zxtech.esp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zxtech.common.upgrade.DownloadApk;
import com.zxtech.common.util.SPCache;
import com.zxtech.common.util.T;
import com.zxtech.esp.AppConfig;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.R;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.JsonElementVO;
import com.zxtech.esp.data.bean.LoginVO;
import com.zxtech.esp.util.BizUtil;
import com.zxtech.esp.util.GsonCallBack;

import g.api.http.GRequestData;
import g.api.http.GRequestParams;

/**
 * Created by SYP521 on 2017/6/29.
 */

public class LoginActivity extends BaseActivity implements TextWatcher {

    private final String USER_NAME = "USER_NAME";
    private final String PASSWORD = "PASSWORD";
    private final String TOKEN = "TOKEN";
    EditText et_username;
    EditText et_pwd;
    private Button btn_login;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setup();
    }

    private void setup() {

        String versionName = T.getVersionName(this, null);
        TextView tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText("V" + versionName);
        TextView tv_ic_username = (TextView) findViewById(R.id.tv_ic_username);
        BizUtil.setIconFont(tv_ic_username, "\ue6d3");
        TextView tv_ic_pwd = (TextView) findViewById(R.id.tv_ic_pwd);
        BizUtil.setIconFont(tv_ic_pwd, "\ue6d2");
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new onLoginPageClickListener());
        et_username = (EditText) findViewById(R.id.et_username);
        et_username.addTextChangedListener(this);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_pwd.addTextChangedListener(this);
        TextView tv_register = (TextView) findViewById(R.id.tv_register);
        tv_register.setOnClickListener(new onLoginPageClickListener());

        //1.注册下载广播接收器
        DownloadApk.registerBroadcast(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        et_username.setText(SPCache.getInstance(this).getString(USER_NAME));
        et_pwd.setText(SPCache.getInstance(this).getString(PASSWORD));
    }

    private boolean checkEmpty() {

        String username = et_username.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
            return false;
        }
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (checkEmpty()) {
            btn_login.setClickable(true);
            btn_login.setEnabled(true);
        } else {
            btn_login.setClickable(false);
            btn_login.setEnabled(false);
        }
    }

    private class onLoginPageClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            int i = v.getId();
            if (i == R.id.btn_login) {
                String username = et_username.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
                    T.showToast(v.getContext(), getResources().getString(R.string.error_blank_password));
                } else {
                    login(username, pwd);
                }

            } else if (i == R.id.tv_register) {
                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(intent);

            } else {
            }
        }
    }

    private void login(final String username, final String pwd) {

        GsonCallBack<LoginVO> callBack = new GsonCallBack<LoginVO>(this) {
            @Override
            public void onStart() {
                showLoading(LoginActivity.this);
            }

            @Override
            protected void onDoSuccess(LoginVO bean) {
                SPCache.getInstance(context).setString(USER_NAME, username);
                SPCache.getInstance(context).setString(PASSWORD, pwd);
                SPCache.getInstance(context).setString(TOKEN, bean.getData().getToken());
                SPCache.getInstance(context).setString("DepartId", bean.getData().getDepartId());
                SPCache.getInstance(context).setString("userId", bean.getData().getUserId());
                getFaceInfo(bean.getData().getUserId());
                dismissLoading();
                MyApp.setUserId(bean.getData().getUserId());
                MyApp.setToken(bean.getData().getToken());
                MyApp.setPhoto_url(bean.getData().getPhoto_url());
                MyApp.setNick_name(bean.getData().getNick_name());
                checkVersion(bean.getData());
            }
        };
        GRequestParams params = new GRequestParams();
        params.addHeader("token", SPCache.getInstance(this).getString(TOKEN));
        GRequestData requestData = new GRequestData(URL.getInstance().getLoginUrl(username, pwd), null);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    private void checkVersion(final LoginVO.Data data) {

        //2.删除已存在的Apk
        DownloadApk.removeFile(this);
        int versioncode = T.getVersionCode(this, 1);
        if (data.getVersion_code() > versioncode) {

            String title = getResources().getString(R.string.tip);
            String desc = getResources().getString(R.string.upgrade_dialog_desc);
            String ok = getResources().getString(R.string.upgrade_dialog_ok);
            String cancel = getResources().getString(R.string.upgrade_dialog_cancel);

            /*AlertDialog dialog = SimpleDialog.createDialog(this, title, desc, ok, cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //3.如果手机已经启动下载程序，执行downloadApk。否则跳转到设置界面
                    try {
                        if (DownLoadUtils.getInstance(getApplicationContext()).canDownload()) {
                            String url = AppConfig.getFileHost() + "app/" + data.getFilename();
                            DownloadApk.downloadApk(getApplicationContext(), url, "ESP更新", "ESP");
                        } else {
                            DownLoadUtils.getInstance(getApplicationContext()).skipToDownloadManager();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    goMain(data, true);
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    goMain(data, false);
                }
            }, false);
            dialog.show();*/
        } else {
            goMain(null, false);
        }

    }


    private void goMain(LoginVO.Data data, boolean action) {

        if (data != null && data.getMust_update() != 0) {
            //强制更新
            String t1 = getResources().getString(R.string.toast_upgrade_t1);
            String t2 = getResources().getString(R.string.toast_upgrade_t2);
            T.showToast(this, action ? t1 : t2);
            finish();
        } else {
            Intent in = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(in);
            finish();
        }

    }

    protected void getFaceInfo(String uid){

        SPCache.getInstance(LoginActivity.this).setString(C.FACE_INFO_STATUS,"0");
        GsonCallBack<JsonElementVO> callBack = new GsonCallBack<JsonElementVO>(this) {

            @Override
            protected void onDoSuccess(JsonElementVO bean) {

                if(bean.getStatus() == 1){
                    SPCache.getInstance(LoginActivity.this).setString(C.FACE_INFO_STATUS,"1");
                }
            }

            @Override
            public void onFailure(String info) {
                super.onFailure(info);
            }
        };
        String url = AppConfig.getHOST()+"face/getUserInfo.do?uid="+uid;
        GRequestData requestData = new GRequestData(url,null);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    @Override
    protected void onDestroy() {
        //4.反注册广播接收器
        DownloadApk.unregisterBroadcast(this);
        super.onDestroy();
    }
}
