package com.zxtech.esp.ui.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zxtech.esp.MyApp;
import com.zxtech.esp.R;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.JsonElementVO;
import com.zxtech.esp.util.BizUtil;
import com.zxtech.esp.util.GsonCallBack;

import g.api.http.GRequestData;
import g.api.http.GRequestParams;

/**
 * Created by SYP521 on 2017/7/19.
 */

public class UserInfoNameActivity extends AppCompatActivity implements TextWatcher{

    private String new_name;
    private String username;
    private EditText et_username;
    private TextView tv_save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra(C.DATA);
        setContentView(R.layout.activity_user_info_name);
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

        et_username = (EditText) findViewById(R.id.et_username);
        et_username.setText(username);
        et_username.setSelection(et_username.getText().length());
        et_username.addTextChangedListener(this);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_name = et_username.getText().toString();
                saveName(new_name);
            }
        });
    }

    private void saveName(final String nickname){

        GRequestParams param = new GRequestParams();
        param.addBodyParameter("nickname",nickname);
        param.addBodyParameter("studentid", MyApp.getUserId());
        String url = URL.getInstance().updateUserInfoUrl();
        MyApp.getInstance().getHttp().send(new GRequestData(url, param), new GsonCallBack<JsonElementVO>(this) {
            @Override
            protected void onDoSuccess(JsonElementVO bean) {
                MyApp.setNick_name(nickname);
                Intent intent = new Intent();
                intent.putExtra("name",new_name);
                setResult(2,intent);
                finish();
            }
        });

    }

    private boolean checkText(){

        if(username.equals(new_name)){
            return false;
        }
        if(TextUtils.isEmpty(new_name)){
            return false;
        }
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        new_name = s.toString();
    }

    @Override
    public void afterTextChanged(Editable s) {

        if(checkText()){
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
