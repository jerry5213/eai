package com.zxtech.mt.delegate;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.zxtech.module.common.base.IApplicationDelegate;
import com.zxtech.mt.common.UIController;
import com.zxtech.mt.entity.User;
import com.zxtech.mt.service.LocationService;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mt.utils.Util;
import com.zxtech.mtos.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by syp523 on 2018/6/15.
 */

public class ApplicationDelegate implements IApplicationDelegate {

    private static Context mContext;

    @Override
    public void onCreate(Context applicationContext) {
        mContext = applicationContext;
        login();
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

    private void login() {

        SPUtils.put(mContext, "IP", UIController.IP);
        SPUtils.put(mContext, "RESOURCE_URL", UIController.IP.substring(0, UIController.IP.lastIndexOf("/")) + "/upload/");


        Map<String, String> param = new HashMap<>();
        param.put("userCode", "002");
        param.put("userPasswd", "123");
        param.put("tenantCode", "24");
        param.put("version", String.valueOf(Util.getVersionCode(mContext)));


        HttpUtil.getInstance(mContext).request("/mtmo/mtlogin.mo", mContext.getString(R.string.msg_6), param, new HttpCallBack<User>() {
            @Override
            public void onSuccess(final User user) {
                if (true) {  //!user.is_update()
                    if (TextUtils.isEmpty(user.getEmp_id())) {
                        ToastUtil.showLong(mContext, mContext.getString(R.string.msg_66));
                        return;
                    }
                    SPUtils.put(mContext, "mt_user_id", user.getUser_id());
                    SPUtils.put(mContext, "user_realname", user.getUser_realname());
                    SPUtils.put(mContext, "mt_username", "002");
                    SPUtils.put(mContext, "mt_password", "123");
                    SPUtils.put(mContext, "grp_id", user.getGrp_id() == null ? "" : user.getGrp_id());
                    SPUtils.put(mContext, "grp_code", user.getGrp_code() == null ? "" : user.getGrp_code());
                    SPUtils.put(mContext, "comp_name", user.getComp_name() == null ? "" : user.getComp_name());
                    SPUtils.put(mContext, "tenant_code", "24");
                    SPUtils.put(mContext, "emp_name", user.getEmp_name());
                    SPUtils.put(mContext, "emp_id", user.getEmp_id());
                    SPUtils.put(mContext, "token", user.getToken());
                    SPUtils.put(mContext, "emp_photo_url", user.getEmp_photo_url() == null ? "" : user.getEmp_photo_url());
                    SPUtils.put(mContext, "emp_sign_url", user.getEmp_sign_url() == null ? "" : user.getEmp_sign_url());

                    Intent intent = new Intent(mContext, LocationService.class);
                    intent.putExtra("emp_id", SPUtils.get(mContext, "emp_id", "").toString());
                    mContext.startService(intent);




                    //  pushManager();
                    /*startActivity(new Intent(mContext, MyActivity.class));
                    finish();
                    overridePendingTransition(-1, -1);*/
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

            }
        });
    }
}
