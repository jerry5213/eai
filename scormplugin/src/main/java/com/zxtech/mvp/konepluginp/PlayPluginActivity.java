package com.zxtech.mvp.konepluginp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.xwalk.core.XWalkInitializer;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkSettings;
import org.xwalk.core.XWalkUpdater;
import org.xwalk.core.XWalkView;


public class PlayPluginActivity extends AppCompatActivity implements LMSFinishListener, XWalkInitializer.XWalkInitListener,
        XWalkUpdater.XWalkBackgroundUpdateListener {

    private XWalkView xWalkWebView;
    private ProgressBar progress_bar;
    XWalkInitializer mXWalkInitializer;
    XWalkUpdater mXWalkUpdater;
    private InJavaScriptLocalObj sobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mXWalkInitializer = new XWalkInitializer(this, this);
        //mXWalkInitializer.initAsync();
        setContentView(R.layout.activity_xwebview);
        xWalkWebView = findViewById(R.id.xWalkWebView);
        progress_bar = findViewById(R.id.progress_bar);


        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();
        CookieManager.getInstance().removeSessionCookie();

        XWalkPreferences.setValue("enable-javascript", true);
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
        XWalkPreferences.setValue(XWalkPreferences.ALLOW_UNIVERSAL_ACCESS_FROM_FILE, true);
        XWalkPreferences.setValue(XWalkPreferences.JAVASCRIPT_CAN_OPEN_WINDOW, true);
        XWalkPreferences.setValue(XWalkPreferences.SUPPORT_MULTIPLE_WINDOWS, true);

        //xWalkWebView.setInitialScale;
        xWalkWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        xWalkWebView.setResourceClient(new XWalkResourceClient(xWalkWebView) {
            @Override
            public void onLoadFinished(XWalkView view, String url) {
                super.onLoadFinished(view, url);
                frameReset();
                //view.load("javascript:window.API =JavaScriptInterface.API();",null);
            }

            @Override
            public void onLoadStarted(XWalkView view, String url) {
                super.onLoadStarted(view, url);
                view.load("javascript:window.API =JavaScriptInterface.API();", null);
               // imgReset();
            }
        });

//        if (mXWalkInitializer.isXWalkReady()) {
//            initSettings();
//        }



    }

    private void initSettings() {
        XWalkSettings xWalkSettings = new XWalkSettings(xWalkWebView);
        xWalkSettings.setJavaScriptEnabled(true);
        xWalkSettings.setAllowFileAccess(true);
        xWalkSettings.setDomStorageEnabled(true);
        xWalkSettings.setUseWideViewPort(true);
        xWalkSettings.setLoadWithOverviewMode(true);
    }

    private void frameReset() {
//        xWalkWebView.loadUrl("javascript:(function(){" +
//                "var objs = document.getElementsById('slideframe'); " +
//                "for(var i=0;i<objs.length;i++)  " +
//                "{"
//                + "var img = objs[i];   " +
//                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
//                "}" +
//                "})()");

        xWalkWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsById('slideframe'); " +
                "objs.style.width='1500px';" +

                "})");
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (xWalkWebView != null) {
            xWalkWebView.pauseTimers();
            xWalkWebView.onHide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.isExistsPlugin(this)) {

            if (xWalkWebView != null && mXWalkInitializer.isXWalkReady()) {

                xWalkWebView.resumeTimers();
                xWalkWebView.onShow();
            }else{
                mXWalkInitializer.initAsync();
            }
        } else {
            dialogConfirm();
        }


    }

    private void dialogConfirm() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("您还未安装播放插件，请点击确定安装！")
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mXWalkInitializer.initAsync();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    @Override
    protected void onDestroy() {

        if (xWalkWebView != null) {
            xWalkWebView.clearCache(true);
            xWalkWebView.clearFormData();
            xWalkWebView.onDestroy();
            xWalkWebView = null;
        }

        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //屏蔽BACK键
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            this.setResult(400, null);
            android.os.Process.killProcess(android.os.Process.myPid());

        }

        return false;
    }

    @Override
    public void onLMSFinish() {

        String score = sobj.getScore();
        if (TextUtils.isEmpty(score)) {
            score = "0";
        }
        Intent intent = new Intent();
        intent.putExtra("score", score);
        setResult(1, intent);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }, 300);
    }

    @Override
    public void onXWalkInitStarted() {

    }

    @Override
    public void onXWalkInitCancelled() {
        finish();
    }

    @Override
    public void onXWalkInitFailed() {
        if (mXWalkUpdater == null) {
            mXWalkUpdater = new XWalkUpdater(this, this);
        }
        mXWalkUpdater.updateXWalkRuntime();
    }

    @Override
    public void onXWalkInitCompleted() {
        progress_bar.setVisibility(View.GONE);
        Intent intent = getIntent();
        String filePath = intent.getExtras().getString("filePath");
        sobj = new InJavaScriptLocalObj();
        sobj.setLmsFinishListener(this);

        xWalkWebView.addJavascriptInterface(sobj, "JavaScriptInterface");

        xWalkWebView.clearCache(true);
        xWalkWebView.clearFormData();
        xWalkWebView.getSettings().setJavaScriptEnabled(true);//设置能够解析JavaScript
        xWalkWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存
        xWalkWebView.getSettings().setDomStorageEnabled(true);//设置适应HTML5的一些方法
        xWalkWebView.getSettings().setAllowFileAccess(true);
        xWalkWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        //initSettings();
        xWalkWebView.loadUrl("file:///" + filePath);
    }

    @Override
    public void onXWalkUpdateStarted() {
        progress_bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onXWalkUpdateProgress(int percentage) {
        progress_bar.setProgress(percentage);
        Log.d("chw", "XWalkUpdate progress: " + percentage);
    }

    @Override
    public void onXWalkUpdateCancelled() {
        finish();
    }

    @Override
    public void onXWalkUpdateFailed() {
        finish();
    }

    @Override
    public void onXWalkUpdateCompleted() {
        mXWalkInitializer.initAsync();
    }
}
