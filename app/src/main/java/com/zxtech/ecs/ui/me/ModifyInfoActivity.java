package com.zxtech.ecs.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.RegexUtils;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改个人信息
 * Created by syp523 on 2018/4/14.
 */

public class ModifyInfoActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content_et)
    EditText content_et;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_user;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.editing_personal));
        String data = getIntent().getStringExtra("data");
        if (data != null && data.equals("userName")) {
            content_et.setHint(R.string.hint1);
        }
        if (data != null && data.equals("userEmail")) {
            content_et.setHint(R.string.hint2);
        }
        if (data != null && data.equals("userRemark")) {
            content_et.setHint(R.string.hint3);
        }

    }

    @OnClick({R.id.submit_btn})
    public void onClick(View view) {
        final String data = getIntent().getStringExtra("data");
        if (data == null) return;
        if (TextUtils.isEmpty(content_et.getText())) {
            ToastUtil.showLong(getString(R.string.msg18));
            return;
        }
        if (data.equals("userEmail") && !RegexUtils.isEmail(content_et.getText())) {
            ToastUtil.showLong(getString(R.string.msg19));
            return;
        }
        HashMap<String, String> params = new HashMap();
        params.put(data, content_et.getText().toString());
        params.put("userId",getUserId());
        params.put("userNo",getUserNo());
        baseResponseObservable = HttpFactory.getApiService().
                updateUser(new Gson().toJson(params));
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(this, true) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        Intent intent = getIntent();
                        intent.putExtra("text", content_et.getText().toString());
                        setResult(2202,intent);
                        finish();
                    }
                });
    }

}
