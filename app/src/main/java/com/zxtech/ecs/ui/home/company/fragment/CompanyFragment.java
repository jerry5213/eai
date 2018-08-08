package com.zxtech.ecs.ui.home.company.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.CompanyEntity;
import com.zxtech.ecs.net.BaseResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;

import butterknife.BindView;

/**
 * @date: 2018/2/1
 * @desc: 公司介绍
 */

public class CompanyFragment extends BaseFragment {

    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;

    public static CompanyFragment newInstance() {
        return new CompanyFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_company;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

        //支持JS
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        //支持屏幕缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        //隐藏缩放工具
        mWebView.getSettings().setDisplayZoomControls(false);


        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progress_bar != null) {
                    if (newProgress < 100) {
                        progress_bar.setProgress(newProgress);
                    } else {
                        progress_bar.setVisibility(View.GONE);
                    }
                }


            }
        });
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        requestNet();
    }

    private void requestNet() {
        HttpFactory.getApiService()
                .getCompanyInfo("2", "ComTag1")
                .compose(RxHelper.<BaseResponse<CompanyEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<CompanyEntity>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<CompanyEntity> response) {
                        if (response.getData().getResultInfo() != null && response.getData().getResultInfo().size() != 0) {
                            mWebView.loadUrl(response.getData().getResultInfo().get(0).getCoverPath());
                        }
                    }
                });
    }

}
