package com.zxtech.mt.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.zxtech.module.common.widget.ProgressDialog;
import com.zxtech.mt.common.Constants;
import com.zxtech.mt.common.VolleySingleton;
import com.zxtech.mt.imagepicker.SystemBarTintManager;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mt.widget.FontView;
import com.zxtech.mt.widget.SelectDialog;
import com.zxtech.mtos.R;

import java.util.List;
import java.util.Locale;


/**
 * Base
 * Created by chw on 2016/6/22.
 */
public abstract  class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected Context mContext;
    protected LayoutInflater mInfalter;
    protected LinearLayout main_layout,bottomLayout;
    protected TextView menu1,menu2,menu4,menu5,title_textview;
    protected TextView set_textview;
    protected ImageView back_button;
    protected RequestQueue mQueue;
    protected FontView menu1_bg,menu2_bg,menu4_bg,menu5_bg;
    private LinearLayout menu1_layout,menu2_layout,menu4_layout,menu5_layout;
    protected ProgressDialog progressbar;
    public static final String TAG = "BaseActivity";
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    //请求超时时间
    protected static final int REQUEST_TIMEOUT = 10000;

    private SystemBarTintManager tintManager;

    private long exitTime = 0;

    protected String language = "zh";

    protected Gson gson = new Gson();
    protected AlertDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        mInfalter = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setContentView(R.layout.activity_base);
        initStateBar();

        String locale = Locale.getDefault().getLanguage();
        language = (String) SPUtils.get(mContext, Constants.SHARED_LANGUAGE, locale);
        //initLanguage();

        initBaseView();
        onCreate();
        findView();
        setListener();
        initData();
        initView();
    }

    private void initLanguage() {

        String locale = Locale.getDefault().getLanguage();
        String defaultLanguage = (String) SPUtils.get(mContext, Constants.SHARED_LANGUAGE, locale);

        String sta = defaultLanguage;
        sta = "en";
        // 本地语言设置
        Locale myLocale = new Locale(sta);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    private void initBaseView() {
        main_layout = (LinearLayout) findViewById(R.id.main_layout);
        bottomLayout = (LinearLayout) findViewById(R.id.bottomLayout);
        back_button = (ImageView) findViewById(R.id.back_button);
        menu1 = (TextView) findViewById(R.id.menu1);
        menu2 = (TextView) findViewById(R.id.menu2);
        menu4 = (TextView) findViewById(R.id.menu4);
        menu5 = (TextView) findViewById(R.id.menu5);
        menu1_bg = (FontView) findViewById(R.id.menu1_bg);
        menu2_bg = (FontView) findViewById(R.id.menu2_bg);
        menu4_bg = (FontView) findViewById(R.id.menu4_bg);
        menu5_bg = (FontView) findViewById(R.id.menu5_bg);
        menu1_layout = (LinearLayout) findViewById(R.id.menu1_layout);
        menu2_layout = (LinearLayout) findViewById(R.id.menu2_layout);
        menu4_layout = (LinearLayout) findViewById(R.id.menu4_layout);
        menu5_layout = (LinearLayout) findViewById(R.id.menu5_layout);
        title_textview = (TextView) findViewById(R.id.title_textview);
        set_textview = (TextView) findViewById(R.id.set_textview);
        menu1_layout.setOnClickListener(this);
        menu2_layout.setOnClickListener(this);
        menu4_layout.setOnClickListener(this);
        menu5_layout.setOnClickListener(this);
        set_textview.setOnClickListener(this);
        back_button.setOnClickListener(this);
        progressbar =  new ProgressDialog(mContext);

        mQueue = VolleySingleton.getVolleySingleton(mContext.getApplicationContext()).getRequestQueue();
    }


    protected abstract void onCreate();
    protected abstract void findView();
    protected abstract void setListener();
    protected abstract void initData();
    protected abstract void initView();

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.menu1_layout) {
            if (((Activity) mContext) instanceof HomeActivity) {
                return;
            }
            startActivity(new Intent(mContext, HomeActivity.class));
            overridePendingTransition(-1, -1);
            finish();

        } else if (i == R.id.menu2_layout) {
            if (((Activity) mContext) instanceof MtActivity) {
                return;
            }
            startActivity(new Intent(mContext, MtActivity.class));
            overridePendingTransition(-1, -1);
            finish();

        } else if (i == R.id.menu4_layout) {
            if (((Activity) mContext) instanceof CallActivity) {
                return;
            }
            startActivity(new Intent(mContext, CallActivity.class));
            overridePendingTransition(-1, -1);
            finish();

        } else if (i == R.id.menu5_layout) {
            if (((Activity) mContext) instanceof MyActivity) {
                return;
            }
            startActivity(new Intent(mContext, MyActivity.class));
            overridePendingTransition(-1, -1);
            finish();

        } else if (i == R.id.back_button) {
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);

        }
    }


    protected void setBottomLayoutHide(){
        bottomLayout.setVisibility(View.GONE);
    }

    protected void setBackHide(){
        back_button.setVisibility(View.INVISIBLE);
    }


    private void initStateBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.main);  //设置上方状态栏的颜色
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if (((Activity) mContext) instanceof HomeActivity || ((Activity) mContext) instanceof MyActivity || ((Activity) mContext) instanceof MtActivity || ((Activity) mContext) instanceof CallActivity) {
                if((System.currentTimeMillis()-exitTime) > 2000){
                    ToastUtil.showLong(mContext,getString(R.string.msg_67));
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }
                return true;
            }else{
                finish();
                overridePendingTransition(R.anim.left_in,R.anim.right_out);
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
