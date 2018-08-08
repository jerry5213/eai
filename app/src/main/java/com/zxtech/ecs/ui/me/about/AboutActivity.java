package com.zxtech.ecs.ui.me.about;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zxtech.ecs.APPConfig;
import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.ecs.util.Util;
import com.zxtech.ecs.widget.CornersTransform;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 关于
 * Created by syp523 on 2018/4/14.
 */

public class AboutActivity extends BaseActivity {
    @BindView(R.id.version_code_tv)
    TextView version_code_tv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.scan_layout)
    LinearLayout scan_layout;
    @BindView(R.id.app_support_tv)
    TextView app_support_tv;
    @BindView(R.id.support_tv)
    TextView support_tv;
    @BindView(R.id.android_code_iv)
    ImageView android_code_iv;
    @BindView(R.id.ios_code_iv)
    ImageView ios_code_iv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.about));
        version_code_tv.setText("V " + Util.getVersion(mContext));
        Glide.with(this).load(R.drawable.icon).transform(new CornersTransform(this, 40)).into(logo);

        Glide.with(this).load(APPConfig.BASE_URL.split("mobileapi")[0] + "app/QR-code/android.png").into(android_code_iv);
        Glide.with(this).load(APPConfig.BASE_URL.split("mobileapi")[0] + "app/QR-code/ios.png").into(ios_code_iv);

        baseResponseObservable = HttpFactory.getApiService().getSupportTelephone(getString(R.string.agent));
        baseResponseObservable
                .compose(this.bindToLifecycle())
                .compose(RxHelper.<BaseResponse<Map<String, String>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Map<String, String>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BaseResponse<Map<String, String>> response) {
                        app_support_tv.setText(response.getData().get("app_support_telephone"));
                        support_tv.setText(response.getData().get("elevator_support_telephone"));
                    }
                });

    }
}
