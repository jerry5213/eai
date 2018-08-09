package com.zxtech.mt.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.zxtech.mt.adapter.MtCheckPhotoAdapter;
import com.zxtech.mt.common.Constants;
import com.zxtech.mt.common.UIController;
import com.zxtech.mt.common.VolleySingleton;
import com.zxtech.mt.entity.JsonData;
import com.zxtech.mt.entity.MtWorkPlanAddtion;
import com.zxtech.mt.utils.HttpCallBack;
import com.zxtech.mt.utils.HttpUtil;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mt.utils.Util;
import com.zxtech.mtos.R;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chw on 2016/10/13.
 */
public class ModifyPasswordActivity extends BaseActivity {

    private EditText password_edittext,confirm_password_edittext;

    private String username;

    private TextView username_textview;

    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_modify_password, null);
        main_layout.addView(view);
        title_textview.setText(getString(R.string.modify_password));
        setBottomLayoutHide();
        set_textview.setText(getString(R.string.submit));
    }

    @Override
    protected void findView() {
        password_edittext = (EditText) findViewById(R.id.password_edittext);
        confirm_password_edittext = (EditText) findViewById(R.id.confirm_password_edittext);
        username_textview = (TextView) findViewById(R.id.username_textview);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        username = (String) SPUtils.get(mContext, "username", "");

    }

    @Override
    protected void initView() {
        username_textview.setText(username);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId()==set_textview.getId()) {
            String password = password_edittext.getText().toString();
            String confirmPassword = confirm_password_edittext.getText().toString();
            if (TextUtils.isEmpty(password)) {
                ToastUtil.showLong(mContext, getString(R.string.msg_30));
                password_edittext.setFocusable(true);
                password_edittext.setFocusableInTouchMode(true);
                password_edittext.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(confirmPassword)) {
                ToastUtil.showLong(mContext,getString(R.string.msg_31));
                confirm_password_edittext.setFocusable(true);
                confirm_password_edittext.setFocusableInTouchMode(true);
                confirm_password_edittext.requestFocus();
                return;
            }

            if (!password.trim().equals(confirmPassword.trim())) {
                ToastUtil.showLong(mContext,getString(R.string.msg_32));
                confirm_password_edittext.setFocusable(true);
                confirm_password_edittext.setFocusableInTouchMode(true);
                confirm_password_edittext.requestFocus();
                return;
            }

            savePassword(password.trim());
        }
    }



    private void savePassword(final String password){

        progressbar.show();
        Map<String, String> param = new HashMap<>();
        param.put("id", SPUtils.get(mContext,"user_id","").toString());
        param.put("user_passwd", Util.getMD5(password));

        HttpUtil.getInstance(mContext).request("/mtmo/updateuserinfo.mo", param, new HttpCallBack<List<MtWorkPlanAddtion>>() {
            @Override
            public void onSuccess(List<MtWorkPlanAddtion> list) {
                SPUtils.put(mContext, "password", password);
                ToastUtil.showLong(mContext, getString(R.string.modify_password_success));
                progressbar.dismiss();
                finish();
            }

            @Override
            public void onFail(String msg) {
                progressbar.dismiss();
            }
        });

    }
}
