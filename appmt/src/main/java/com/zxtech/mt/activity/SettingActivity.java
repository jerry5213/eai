package com.zxtech.mt.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxtech.mt.common.UIController;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.Util;
import com.zxtech.mt.widget.FontTextView;
import com.zxtech.mt.widget.SelectDialog;
import com.zxtech.mtos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置
 * Created by Chw on 2016/10/13.
 */
public class SettingActivity extends BaseActivity {
    private LinearLayout setting_exit_layout;
    private LinearLayout setting_password_layout;
    private RelativeLayout setting_version_layout,auto_login_layout;
    private TextView version_name_textview;
    private FontTextView auto_login_textview;


    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_setting, null);
        main_layout.addView(view);
        title_textview.setText(getString(R.string.set));
        setBottomLayoutHide();
    }

    @Override
    protected void findView() {
        setting_exit_layout = (LinearLayout) findViewById(R.id.setting_exit_layout);
        setting_password_layout = (LinearLayout) findViewById(R.id.setting_password_layout);
        setting_version_layout = (RelativeLayout) findViewById(R.id.setting_version_layout);
        version_name_textview = (TextView) findViewById(R.id.version_name_textview);
        auto_login_layout = (RelativeLayout) findViewById(R.id.auto_login_layout);
        auto_login_textview = (FontTextView) findViewById(R.id.auto_login_textview);
    }

    @Override
    protected void setListener() {
        setting_exit_layout.setOnClickListener(this);
        setting_password_layout.setOnClickListener(this);
        setting_version_layout.setOnClickListener(this);
        auto_login_layout.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        version_name_textview.setText("V "+Util.getVersion(mContext));
        if ((Boolean) SPUtils.get(mContext,"auto",true)) {
            auto_login_textview.setText(getString(R.string.selected));
        }else{
            auto_login_textview.setText("");

        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.setting_version_layout) {
        } else if (i == R.id.setting_password_layout) {
            startActivity(new Intent(mContext, ModifyPasswordActivity.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);

        } else if (i == R.id.setting_exit_layout) {
            List<String> names = new ArrayList<>();
            names.add(getString(R.string.account_cancellation));
            showDialog(new SelectDialog.SelectDialogListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            intent.putExtra("out", true); // 是否是直接打开相机
                            startActivity(intent);
                            overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
                            finish();
                            break;

                    }

                }
            }, names);
            //   startActivityForResult(new Intent(mContext, SetBottomMenuActivity.class), 101);

        } else if (i == R.id.auto_login_layout) {
            if ((Boolean) SPUtils.get(mContext, "auto", true)) {
                SPUtils.put(mContext, "auto", false);
                auto_login_textview.setText("");
            } else {
                SPUtils.put(mContext, "auto", true);
                auto_login_textview.setText(getString(R.string.selected));
            }

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==101&&resultCode==103) {
            UIController.OUT = true;
            Intent intent = new Intent(this,LoginActivity.class);
         //   intent.putExtra(LoginActivity.EXIST, true);
            startActivity(intent);
            finish();
        }
    }
}
