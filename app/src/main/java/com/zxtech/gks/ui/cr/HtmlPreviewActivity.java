package com.zxtech.gks.ui.cr;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.gks.IActivity;
import com.zxtech.gks.common.Constants;

import butterknife.BindView;

/**
 * Created by SYP521 on 2017/12/14.
 */

public class HtmlPreviewActivity extends BaseActivity implements IActivity {

    @BindView(R.id.web)
    WebView mWebView;
    @BindView(R.id.pro)
    ProgressBar pro;

    public static final int LOAD_JAVASCRIPT = 0X01;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == LOAD_JAVASCRIPT){
                pro.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_html_preview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        //mWebView.addJavascriptInterface(new JsObject(this, fileUrl), "client");

        String url = getIntent().getStringExtra(Constants.DATA1);
        mWebView.loadUrl(url);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                handler.sendEmptyMessage(LOAD_JAVASCRIPT);
            }

        });
    }
}
