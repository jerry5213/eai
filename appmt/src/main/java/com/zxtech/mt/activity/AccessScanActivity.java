package com.zxtech.mt.activity;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.zxing.common.StringUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mt.utils.Util;
import com.zxtech.mtos.R;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by syp523 on 2017/8/15.
 */
@Route(path = "/mt/accessoryscan")
public class AccessScanActivity extends BaseActivity implements QRCodeView.Delegate {
    private QRCodeView mQRCodeView;
    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_scan, null);
        main_layout.addView(view);
        title_textview.setText(getString(R.string.menu_project_origin));
        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lps);
        setBottomLayoutHide();
    }

    @Override
    protected void findView() {
        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();

        mQRCodeView.startSpotAndShowRect();
        mQRCodeView.startSpot();

    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onScanQRCodeSuccess(String result) {
        if (TextUtils.isEmpty(result)) {
            ToastUtil.showLong(mContext,"二维码信息不正确");
            vibrate();
            mQRCodeView.startSpot();
        }else if (!Util.isNumeric(result)) {
            ToastUtil.showLong(mContext,"二维码信息不正确");
            vibrate();
            mQRCodeView.startSpot();
        }else{
            Intent intent = new Intent(mContext,AccessoryTraceActivity.class);
            intent.putExtra("id",result);
            startActivity(intent);
            overridePendingTransition(-1,-1);
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

}
