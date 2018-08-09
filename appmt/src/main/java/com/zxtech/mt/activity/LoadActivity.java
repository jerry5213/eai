package com.zxtech.mt.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.zxtech.module.common.widget.ProgressDialog;
import com.zxtech.mt.common.UIController;
import com.zxtech.mt.entity.User;
import com.zxtech.mt.imagepicker.SystemBarTintManager;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mt.utils.Util;
import com.zxtech.mtos.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by syp523 on 2017/7/31.
 */

public class LoadActivity extends Activity {

    private Context mContext;
    private static final int LOAD_DISPLAY_TIME = 1300;
    private SystemBarTintManager tintManager;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        initStateBar();
        setContentView(R.layout.activity_load);
        mContext = this;
        //SPUtils.put(getApplicationContext(),"IP","https://emp.5000m.com/essos");
        if (TextUtils.isEmpty((String) SPUtils.get(getApplicationContext(), "IP", ""))) {
            SPUtils.put(getApplicationContext(), "IP", UIController.IP);
            SPUtils.put(getApplicationContext(), "RESOURCE_URL", UIController.IP.substring(0, UIController.IP.lastIndexOf("/")) + "/upload/");
        }


        new Handler().postDelayed(new Runnable() {
            public void run() {
                //Go to main activity, and finish load activity
                /*Intent mainIntent = new Intent(LoadActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();*/
                login();
                //overridePendingTransition(-1, -1);
            }
        }, LOAD_DISPLAY_TIME);
    }

    private void initStateBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.tr);  //设置上方状态栏的颜色
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    private void login() {

        final ProgressDialog dialog = new ProgressDialog(mContext, getString(R.string.msg_63));
        dialog.show();
        Map<String, String> param = new HashMap<>();
        param.put("userCode","002");
        param.put("userPasswd", "123");
        param.put("tenantCode", "24");
        param.put("version", String.valueOf(Util.getVersionCode(mContext)));


        HttpUtil.getInstance(mContext).request("/mtmo/mtlogin.mo", getString(R.string.msg_6), param, new HttpCallBack<User>() {
            @Override
            public void onSuccess(final User user) {
                if (true) {  //!user.is_update()
                    if (TextUtils.isEmpty(user.getEmp_id())) {
                        dialog.dismiss();
                        ToastUtil.showLong(mContext, getString(R.string.msg_66));
                        return;
                    }
                    SPUtils.put(mContext, "user_id", user.getUser_id());
                    SPUtils.put(mContext, "user_realname", user.getUser_realname());
                    SPUtils.put(mContext, "username", "002");
                    SPUtils.put(mContext, "password", "123");
                    SPUtils.put(mContext, "grp_id", user.getGrp_id() == null ? "" : user.getGrp_id());
                    SPUtils.put(mContext, "grp_code", user.getGrp_code() == null ? "" : user.getGrp_code());
                    SPUtils.put(mContext, "comp_name", user.getComp_name() == null ? "" : user.getComp_name());
                    SPUtils.put(mContext, "tenant_code", "24");
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
                } /*else {
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
                }*/
            }

            @Override
            public void onFail(String msg) {
                dialog.dismiss();
            }
        });
    }
}
