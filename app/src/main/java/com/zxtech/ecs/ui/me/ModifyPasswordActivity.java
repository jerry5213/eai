package com.zxtech.ecs.ui.me;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.SPUtils;
import com.zxtech.ecs.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改密码
 * Created by syp523 on 2018/4/19.
 */

public class ModifyPasswordActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.old_password)
    TextView old_password;
    @BindView(R.id.new_password)
    TextView new_password;
    @BindView(R.id.confirm_new_password)
    TextView confirm_new_password;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar,getString(R.string.modify_password));

    }

    @OnClick(R.id.submit_btn)
    public void onClick(View view) {
        if (TextUtils.isEmpty(old_password.getText())) {
            ToastUtil.showLong(getString(R.string.msg26));
            return;
        }
        if (TextUtils.isEmpty(new_password.getText())) {
            ToastUtil.showLong(getString(R.string.msg27));
            return;
        }
        if (TextUtils.isEmpty(confirm_new_password.getText())) {
            ToastUtil.showLong(getString(R.string.msg24));
            return;
        }
        if (!new_password.getText().toString().equals(confirm_new_password.getText().toString())) {
            ToastUtil.showLong(getString(R.string.msg25));
            return;
        }
        baseResponseObservable = HttpFactory.getApiService().
                modifyPassword(getUserId(),getUserNo(), old_password.getText().toString(), new_password.getText().toString(),"1");
        baseResponseObservable
                .compose(RxHelper.<BaseResponse<String>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<String>>(this, true) {

                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        SPUtils.put(mContext, "password", new_password.getText().toString());
                        ToastUtil.showLong(getString(R.string.msg28));
                        finish();
                    }
                });
    }
}
