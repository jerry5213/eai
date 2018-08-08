package com.zxtech.ecs.ui.home.qmsmanager;

import android.os.Bundle;
import android.webkit.WebView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp521 on 2018/4/12.
 */

public class HtmlActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qms_html;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        String url = getIntent().getStringExtra("url");
        mWebView.loadUrl(url);
    }

    @OnClick(R.id.iv_close)
    public void click(){
        finish();
    }
}
