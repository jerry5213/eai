package com.zxtech.ecs.net;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.zxtech.ecs.ui.home.scheme.CheckTipsDialogFragment;
import com.zxtech.ecs.ui.home.scheme.detail.SmartSearchDialogFragment;
import com.zxtech.ecs.util.ToastUtil;
import com.zxtech.module.common.widget.ProgressDialog;


import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


/**
 * Created by chw on 2017/4/18.
 */

public abstract class DefaultObserver<T extends BaseResponse> implements Observer<T> {
    private Activity activity;
    //  Activity 是否在执行onStop()时取消订阅
    private boolean isAddInStop = false;
    private ProgressDialog dialog;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();// 管理订阅者者

    public DefaultObserver(Activity activity) {
        this.activity = activity;
    }

    public DefaultObserver(Activity activity, boolean isShowLoading) {
        this.activity = activity;
        dialog = new ProgressDialog(activity);
        if (isShowLoading) {
            dialog.show();
        }
    }


    @Override
    public void onSubscribe(Disposable d) {
        mCompositeDisposable.add(d);
    }

    @Override
    public void onNext(T response) {
        dismissProgress();
        if (response.isSuccess()) {
            onSuccess(response);
        } else if (response.isMessage()) {
            dismissProgress();
            onMessage(response.getMessage());

        } else {
            onFail(response);
        }
    }

    private void dismissProgress() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(Throwable e) {
        //    Log.e("Retrofit", e.getMessage());
        dismissProgress();
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
        onFail();
    }

    @Override
    public void onComplete() {
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);

    public void onFail() {

    }

    public void onMessage(String message) {
        ToastUtil.showLong(message);
    }

    /**
     * 服务器返回fail
     *
     * @param response 服务器返回的数据
     */
    public void onFail(T response) {
        String message = response.getMessage();
        if (TextUtils.isEmpty(message)) {
            ToastUtil.showLong("服务器返回数据失败");
        } else {
            ToastUtil.showLong(message);
        }
        onFail();
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtil.showLong("网络连接失败,请检查网络");
                break;

            case CONNECT_TIMEOUT:
                ToastUtil.showLong("服务器维护中,请稍后再试");
                break;

            case BAD_NETWORK:
                ToastUtil.showLong("服务器异常");
                break;

            case PARSE_ERROR:
                ToastUtil.showLong("解析服务器响应数据失败");
                break;

            case UNKNOWN_ERROR:
            default:
                ToastUtil.showLong("未知错误");
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
