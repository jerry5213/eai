package com.zxtech.mt.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxtech.module.common.widget.ProgressDialog;
import com.zxtech.mt.common.UIController;
import com.zxtech.mt.entity.User;
import com.zxtech.mt.imagepicker.SystemBarTintManager;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mt.utils.Util;
import com.zxtech.mt.widget.SimpleDialog;
import com.zxtech.mtos.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 登陆
 * Created by Chw on 2016/7/20.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button login_button;

    private Context mContext;

    private EditText login_name_edittext, login_pwd_edittext, tenant_edittext;


    private String userName, passWord, tenant;


    private boolean isNewVersion = true;

    private SystemBarTintManager tintManager;
    private AlertDialog confirmDialog;

    private ImageView logo_imageview;
    private TextView version_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        mContext = this;
        findView();
        setListener();
        initView();
    }

    protected void findView() {
        login_button = findViewById(R.id.login_button);
        login_name_edittext = findViewById(R.id.login_name_edittext);
        login_pwd_edittext = findViewById(R.id.login_pwd_edittext);

        tenant_edittext = findViewById(R.id.tenant_edittext);
        logo_imageview = findViewById(R.id.logo_imageview);
        version_textview = findViewById(R.id.version_textview);
    }

    protected void setListener() {
        login_button.setOnClickListener(this);
        logo_imageview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startActivity(new Intent(mContext, SettingIPActivity.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                return false;
            }
        });
    }


    protected void initView() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        UIController.SCREEN_WIDTH = displayMetrics.widthPixels;
        UIController.SCREEN_HEIGHT = displayMetrics.heightPixels;
       // UIController.IMEI = Util.getIMEI(mContext.getApplicationContext());
        version_textview.setText("V " + Util.getVersion(mContext));
        autoLogin();


    }

    private void autoLogin() {

        userName = (String) SPUtils.get(mContext, "username", "");
        passWord = (String) SPUtils.get(mContext, "password", "");
        tenant = (String) SPUtils.get(mContext, "tenant_code", "");
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(passWord) && !TextUtils.isEmpty(tenant)) {
            login_name_edittext.setText(userName);
            login_name_edittext.setSelection(userName.length());
            login_pwd_edittext.setText(passWord);
            tenant_edittext.setText(tenant);
            if ((Boolean) SPUtils.get(mContext, "auto", true)) { //是否设置自动登录
                if (!getIntent().getBooleanExtra("out", false)) {
                    login();
                }
            }
        }

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.login_button) {
            userName = login_name_edittext.getText().toString();
            passWord = login_pwd_edittext.getText().toString();
            tenant = tenant_edittext.getText().toString();
            if (TextUtils.isEmpty(userName)) {
                ToastUtil.showLong(mContext, getString(R.string.msg_1));
                login_name_edittext.setFocusable(true);
                login_name_edittext.setFocusableInTouchMode(true);
                login_name_edittext.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(passWord)) {
                ToastUtil.showLong(mContext, getString(R.string.msg_2));
                login_pwd_edittext.setFocusable(true);
                login_pwd_edittext.setFocusableInTouchMode(true);
                login_pwd_edittext.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(tenant)) {
                ToastUtil.showLong(mContext, getString(R.string.msg_65));
                tenant_edittext.setFocusable(true);
                tenant_edittext.setFocusableInTouchMode(true);
                tenant_edittext.requestFocus();
                return;
            }

            login();

        }

    }

    private void login() {
        final ProgressDialog dialog = new ProgressDialog(mContext, getString(R.string.msg_63));
        dialog.show();
        Map<String, String> param = new HashMap<>();
        param.put("userCode", login_name_edittext.getText().toString());
        param.put("userPasswd", login_pwd_edittext.getText().toString());
        param.put("tenantCode", tenant);
        param.put("version", String.valueOf(Util.getVersionCode(mContext)));


        HttpUtil.getInstance(mContext).request("/mtmo/mtlogin.mo", getString(R.string.msg_6), param, new HttpCallBack<User>() {
            @Override
            public void onSuccess(final User user) {
                if (!user.is_update()) {
                    if (TextUtils.isEmpty(user.getEmp_id())) {
                        dialog.dismiss();
                        ToastUtil.showLong(mContext, getString(R.string.msg_66));
                        return;
                    }
                    SPUtils.put(mContext, "user_id", user.getUser_id());
                    SPUtils.put(mContext, "user_realname", user.getUser_realname());
                    SPUtils.put(mContext, "username", userName);
                    SPUtils.put(mContext, "password", passWord);
                    SPUtils.put(mContext, "grp_id", user.getGrp_id() == null ? "" : user.getGrp_id());
                    SPUtils.put(mContext, "grp_code", user.getGrp_code() == null ? "" : user.getGrp_code());
                    SPUtils.put(mContext, "comp_name", user.getComp_name() == null ? "" : user.getComp_name());
                    SPUtils.put(mContext, "tenant_code", tenant);
                    SPUtils.put(mContext, "emp_name", user.getEmp_name());
                    SPUtils.put(mContext, "emp_id", user.getEmp_id());
                    SPUtils.put(mContext, "token", user.getToken());
                    SPUtils.put(mContext, "emp_photo_url", user.getEmp_photo_url() == null ? "" : user.getEmp_photo_url());
                    SPUtils.put(mContext, "emp_sign_url", user.getEmp_sign_url() == null ? "" : user.getEmp_sign_url());
                    dialog.dismiss();
                    //  pushManager();
                    startActivity(new Intent(mContext, MyActivity.class));
                    finish();
                    overridePendingTransition(-1, -1);
                } else {
                    dialog.dismiss();
                    confirmDialog = SimpleDialog.createDialog(mContext, getString(R.string.hint), getString(R.string.msg_64), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            UpdateManager manager = new UpdateManager(mContext);
                            manager.setApkUrl(SPUtils.get(mContext, "RESOURCE_URL", "") + user.getApk_file_url());
                            manager.checkUpdate();
                            confirmDialog.dismiss();
                        }
                    }, false);
                    confirmDialog.show();

                }

            }

            @Override
            public void onFail(String msg) {
                dialog.dismiss();
            }
        });


    }


}
