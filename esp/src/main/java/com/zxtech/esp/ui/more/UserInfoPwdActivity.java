package com.zxtech.esp.ui.more;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zxtech.common.util.Md5;
import com.zxtech.common.util.T;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.R;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.JsonElementVO;
import com.zxtech.esp.util.BizUtil;
import com.zxtech.esp.util.GsonCallBack;

import java.security.NoSuchAlgorithmException;

import g.api.http.GRequestData;
import g.api.http.GRequestParams;

/**
 * Created by SYP521 on 2017/7/19.
 */

public class UserInfoPwdActivity extends AppCompatActivity implements TextWatcher{

    private String new_pwd;
    private String sure_pwd;
    private EditText et_new_pwd;
    private EditText et_sure_pwd;
    private TextView tv_save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_password);
        step();
    }

    private void step() {

        TextView tv_back = (TextView) findViewById(R.id.tv_back);
        BizUtil.setIconFont(tv_back, "\ue620");
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_new_pwd = (EditText) findViewById(R.id.et_new_pwd);
        et_sure_pwd = (EditText) findViewById(R.id.et_sure_pwd);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new_pwd = et_new_pwd.getText().toString();
                sure_pwd = et_sure_pwd.getText().toString();
                if(new_pwd.equals(sure_pwd)){
                    savePwd(new_pwd);
                }else {
                    T.showToast(v.getContext(),getResources().getString(R.string.password_empty));
                }
            }
        });
        et_new_pwd.addTextChangedListener(this);
        et_sure_pwd.addTextChangedListener(this);
    }

    private void savePwd(final String pwd){

        try {
            String md5Pwd = Md5.md5s(pwd);
            GRequestParams param = new GRequestParams();
            param.addBodyParameter("password",md5Pwd);
            param.addBodyParameter("studentid", MyApp.getUserId());
            String url = URL.getInstance().updateUserInfoUrl();
            MyApp.getInstance().getHttp().send(new GRequestData(url, param), new GsonCallBack<JsonElementVO>(this) {
                @Override
                protected void onDoSuccess(JsonElementVO bean) {
                    finish();
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private boolean checkEmpty(){

        new_pwd = et_new_pwd.getText().toString();
        sure_pwd = et_sure_pwd.getText().toString();
        if(TextUtils.isEmpty(new_pwd) || TextUtils.isEmpty(sure_pwd)){
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

        if(checkEmpty()){
            tv_save.setClickable(true);
            tv_save.setEnabled(true);
            tv_save.setTextColor(getResources().getColor(R.color.text_color_white));
        }else{
            tv_save.setClickable(false);
            tv_save.setEnabled(false);
            tv_save.setTextColor(getResources().getColor(R.color.text_color_gray));
        }
    }
}
