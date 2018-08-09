package com.zxtech.esp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zxtech.common.util.SPCache;
import com.zxtech.common.view.ProgressWheel;

import butterknife.ButterKnife;

/**
 * Created by SYP521 on 2017/7/10.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressWheel progressDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView(savedInstanceState);
    }

    protected abstract int getLayoutId();
    protected abstract void initView(Bundle savedInstanceState);

    protected void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressWheel(this,null);
        }
        if (!progressDialog.isSpinning()) {
            progressDialog.spin();
        }
    }

    protected void dismissProgress() {
        if (progressDialog != null && progressDialog.isSpinning()) {
            progressDialog.stopSpinning();
        }
    }

    public String getUserId(){
        return SPCache.getInstance(this).getString("userId","");
    }

    public String getUserLoginAccount(){
        return SPCache.getInstance(this).getString("USER_NAME","");
    }
}
