package com.zxtech.is.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxtech.is.R;
import com.zxtech.is.util.DensityUtil;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;


/**
 * Created by chw on 2017/7/25.
 * loading
 */
public class ProgressDialog extends Dialog {
    private Context mContext;
    private MaterialProgressBar customProgress;
    private TextView hint_textview;
    private TextView progress_textview;
    private String tint = "请稍等";
    private MyCountDownTimer mc;
    private int textCount = 1;
    private int backgroundColor = 0xa1FFFFFF;

    public ProgressDialog(Context context) {
        super(context, R.style.dialog_progress);
        this.mContext = context;
    }

    public ProgressDialog(Context context, String tint) {
        super(context, R.style.dialog_progress);
        this.mContext = context;
        this.tint = tint;
    }

    public ProgressDialog(Context context, int style) {
        super(context, style);
        this.mContext = context;
        this.tint = tint;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        customProgress = (MaterialProgressBar) findViewById(R.id.progressBar);
        LinearLayout main_layout = (LinearLayout) findViewById(R.id.main_layout);
        hint_textview = (TextView) findViewById(R.id.hint_textview);
        progress_textview = findViewById(R.id.progress_textview);
        hint_textview.setText(tint);
        GradientDrawable gradientDrawable = new GradientDrawable();
        // gradientDrawable.setColor(0x91000000);
        gradientDrawable.setColor(backgroundColor);
        gradientDrawable.setCornerRadius(DensityUtil.dip2px(mContext, 5f));
        if (Build.VERSION.SDK_INT >= 16) {
            main_layout.setBackground(gradientDrawable);
        } else {
            main_layout.setBackgroundDrawable(gradientDrawable);
        }
        ColorStateList csl = mContext.getResources().getColorStateList(R.color.progress);
        customProgress.setProgressTintList(csl);
        setCancelable(false);
        setCanceledOnTouchOutside(false);


    }

    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
        }

        @Override
        public void onTick(long millisUntilFinished) {
            switch (textCount) {
                case 1:
                    textCount = 2;
                    hint_textview.setText(tint + ".");
                    break;
                case 2:
                    textCount = 3;
                    hint_textview.setText(tint + "..");
                    break;
                case 3:
                    textCount = 1;
                    hint_textview.setText(tint + "...");
                    break;
            }
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mc != null) {
            mc.cancel();
            mc = null;
        }

    }

    @Override
    public void show() {
        super.show();
        progress_textview.setText(null);
        mc = new MyCountDownTimer(60000, 1000);
        mc.start();
    }

    public void setTint(String tint) {
        this.tint = tint;
        hint_textview.setText(tint);
    }

    public void setProgress(String progress) {
        progress_textview.setText(progress);
    }
}
