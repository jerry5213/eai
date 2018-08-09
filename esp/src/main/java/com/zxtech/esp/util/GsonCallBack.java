package com.zxtech.esp.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.zxtech.common.view.ProgressWheel;
import com.zxtech.esp.R;
import com.zxtech.esp.data.bean.BaseVO;

import g.api.http.GRequestCallGsonBack;


public abstract class GsonCallBack<T extends BaseVO> extends GRequestCallGsonBack<T> {

    public GsonCallBack(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(T t) {
        if (t.handleSelf(context, this)) {
            onDoSuccess(t);
        }
    }

    protected abstract void onDoSuccess(T bean);

    @Override
    public void onFailure(String info) {
        dismissLoading();
        com.zxtech.common.util.T.showToast(context, info == null ? "网络繁忙" : info.equals("") ? null : info);
    }

    @Override
    public void onError(Exception e) {
        super.onError(e);
        onFailure((String) null);
    }

    private Dialog dialog;
    private ProgressWheel mProgressView;

    protected void showLoading(Activity activity) {

        View diaView = View.inflate(activity, R.layout.loading, null);
        mProgressView = (ProgressWheel) diaView.findViewById(R.id.progress);
        dialog = new Dialog(activity,R.style.MyDialogStyle);
        dialog.setContentView(diaView);
        dialog.show();
        mProgressView.spin();
    }

    final protected void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            mProgressView.stopSpinning();
            dialog.dismiss();
            mProgressView = null;
            dialog = null;
        }
    }
}
