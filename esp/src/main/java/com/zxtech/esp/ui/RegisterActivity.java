package com.zxtech.esp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zxtech.common.util.T;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.R;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.JsonElementVO;
import com.zxtech.esp.util.GsonCallBack;

import g.api.http.GRequestData;
import g.api.http.GRequestParams;

/**
 * Created by SYP521 on 2017/8/25.
 */

public class RegisterActivity extends AppCompatActivity implements TextWatcher{

    private EditText et_login_name;
    private EditText et_real_name;
    private EditText et_company;
    private EditText et_tel;
    private EditText et_login_pwd;
    private Button btn_register;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_login_name = (EditText) findViewById(R.id.et_login_name);
        et_login_name.addTextChangedListener(this);
        et_real_name = (EditText) findViewById(R.id.et_real_name);
        et_real_name.addTextChangedListener(this);
        et_company = (EditText) findViewById(R.id.et_company);
        et_company.addTextChangedListener(this);
        et_tel = (EditText) findViewById(R.id.et_tel);
        et_tel.addTextChangedListener(this);
        et_login_pwd = (EditText) findViewById(R.id.et_login_pwd);
        et_login_pwd.addTextChangedListener(this);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });
    }

    private boolean checkRegister() {

        String loginName = et_login_name.getText().toString();
        String realName = et_real_name.getText().toString();
        String company = et_company.getText().toString();
        String tel = et_tel.getText().toString();
        String pwd = et_login_pwd.getText().toString();

        if(TextUtils.isEmpty(loginName)){
            return false;
        }
        if(TextUtils.isEmpty(realName)){
            return false;
        }
        if(TextUtils.isEmpty(company)){
            return false;
        }
        if(TextUtils.isEmpty(pwd)){
            return false;
        }
        if(TextUtils.isEmpty(tel)){
            return false;
        }
        return true;
    }

    private void doRegister(){

        String loginName = et_login_name.getText().toString();
        String realName = et_real_name.getText().toString();
        String company = et_company.getText().toString();
        String pwd = et_login_pwd.getText().toString();
        String tel = et_tel.getText().toString();

        GsonCallBack<JsonElementVO> callBack = new GsonCallBack<JsonElementVO>(this) {
            @Override
            public void onStart() {
                showLoading(RegisterActivity.this);
            }

            @Override
            protected void onDoSuccess(JsonElementVO vo) {
                dismissLoading();
                T.showToast(RegisterActivity.this,vo.getMsg());
                finish();
            }
        };
        GRequestParams params = new GRequestParams();
        params.addBodyParameter("logincode",loginName);
        params.addBodyParameter("name",realName);
        params.addBodyParameter("nickname",realName);
        params.addBodyParameter("standby1",company);
        params.addBodyParameter("tel",tel);
        params.addBodyParameter("password",pwd);
        String url = URL.getInstance().registerUser();
        GRequestData requestData = new GRequestData(url, params);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if(checkRegister()){
            btn_register.setClickable(true);
            btn_register.setEnabled(true);
        }else{
            btn_register.setClickable(false);
            btn_register.setEnabled(false);
        }
    }
}
