package com.zxtech.esp.ui.home;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zxtech.common.download.DownloadUtils;
import com.zxtech.common.util.F;
import com.zxtech.esp.MyApp;
import com.zxtech.esp.R;
import com.zxtech.esp.constant.C;
import com.zxtech.esp.data.URL;
import com.zxtech.esp.data.bean.BaseVO;
import com.zxtech.esp.data.bean.CourseVO;
import com.zxtech.esp.util.GsonCallBack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import g.api.http.GRequestData;
import g.api.http.GRequestParams;

/**
 * Created by SYP521 on 2017/7/5.
 */

public class CoursePlayActivity extends AppCompatActivity implements InJavaScriptLocalObj.LMSFinishListener{

    private WebView mWebView;
    private CourseVO.Data data;

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = (CourseVO.Data) getIntent().getSerializableExtra(C.DATA);
        setContentView(R.layout.activity_course_play);
        setup();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void setup() {

        mWebView = (WebView) findViewById(R.id.wv);

        try {
            if (Build.VERSION.SDK_INT >= 16) {
                Class<?> clazz = mWebView.getSettings().getClass();
                Method method = clazz.getMethod(
                        "setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(mWebView.getSettings(), true);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        /**
         *
         * 设置WebView的属性，此时可以去执行JavaScript脚本
         */
        WebSettings setting = mWebView.getSettings();
        // can read the json file on local
        // setting.setAllowUniversalAccessFromFileURLs(true);
        // setting.setDefaultTextEncodingName("utf-8");
        //setting.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

        setting.setUseWideViewPort(true);// 将图片调整到适合webview的大小
        setting.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        setting.setPluginState(WebSettings.PluginState.ON);// 支持插件
        setting.setJavaScriptEnabled(true); // 支持javascript
        setting.setAllowFileAccess(true);// 设置可以访问文件
        // setting.setBlockNetworkImage(true);// 是否显示网络图像
        // setting.setNeedInitialFocus(true);
        // 当webview调用requestFocus时为webview设置节点
        // setting.supportMultipleWindows(); // 多窗口
        setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 关闭webview中缓存
        // setting.setLayoutAlgorithm(LayoutAlgorithm.NORMAL); // 支持内容重新布局
        setting.setBuiltInZoomControls(true);// 设置支持缩放
        setting.setDisplayZoomControls(false); // 隐藏webview缩放按钮
        setting.setJavaScriptCanOpenWindowsAutomatically(false); // 支持通过JS打开新窗口
        // setting.setLightTouchEnabled(true);
        setting.setLoadsImagesAutomatically(true); // 支持自动加载图片

        mWebView.setInitialScale(500);// 对网页进行缩放试试
        // mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        // mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//滚动条在WebView外侧显示
        // mWebView.setVerticalScrollBarEnabled(false); //垂直不显示
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setBlockNetworkImage(true);

        //如果在用webview做应用的时候我们不希望新建webview进程，让程序跳来跳去那么进行如下设置
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                view.loadUrl("javascript:window.API =JavaScriptInterface.API();");
            }

            // @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                //点击超链接的时候重新在原来进程上加载URL
                //view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:window.API =JavaScriptInterface.API();");
            }
            //
            // @Override
            // public void onReceivedError(WebView view, int errorCode,
            // String description, String failingUrl) {
            // super.onReceivedError(view, errorCode, description, failingUrl);
            // }
            //
        });


        String filePath = getExternalCacheDir().getAbsolutePath();
        String fileName = DownloadUtils.getFileName(URL.getInstance().getFullUrl(data.getFile_url()));
        fileName = F.getNoExName(fileName);
        filePath = filePath + "/" + fileName + "/index_lms_html5.html";

        InJavaScriptLocalObj sobj = new InJavaScriptLocalObj();
        sobj.setLmsFinishListener(this);
        mWebView.addJavascriptInterface(sobj, "JavaScriptInterface");
        mWebView.loadUrl("file:///" + filePath);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.pauseTimers();
            mWebView.onPause();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.resumeTimers();
            mWebView.onResume();
        }
    }

    @Override
    protected void onDestroy() {

        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    @Override
    public void onLMSFinish(String score) {

        GsonCallBack<BaseVO> callBack = new GsonCallBack<BaseVO>(this){
            @Override
            protected void onDoSuccess(BaseVO bean) {
                finish();
            }

            @Override
            public void onFailure(String info) {
                super.onFailure(info);
                finish();
            }
        };
        GRequestParams params = new GRequestParams();
        params.addBodyParameter("courseid",data.getCourse_id());
        params.addBodyParameter("userid", MyApp.getUserId());
        params.addBodyParameter("credits",score);
        params.addBodyParameter("learningtime","20");
        String url = URL.getInstance().setUserLearnCourseInfoUrl();
        GRequestData requestData = new GRequestData(url,params);
        MyApp.getInstance().getHttp().send(requestData, callBack);
    }
}
