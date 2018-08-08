package com.zxtech.ecs.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.UserManager;
import com.zxtech.ecs.ui.login.LoginActivity;
import com.zxtech.ecs.util.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人信息
 * Created by syp523 on 2018/4/17.
 */

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.email_tv)
    TextView email_tv;
    @BindView(R.id.company_tv)
    TextView company_tv;
    @BindView(R.id.real_name_tv)
    TextView real_name_tv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.editing_personal));
        String user_email = getUserEmail(); //邮箱
        String user_remark = (String) SPUtils.get(mContext, "user_remark", ""); //公司名称
        String user_name = (String) SPUtils.get(mContext, "user_name", ""); //公司名称

        real_name_tv.setText(user_name);
        email_tv.setText(user_email);
        company_tv.setText(user_remark);
    }

    @OnClick({R.id.real_name_layout,R.id.email_layout, R.id.company_layout, R.id.modify_password_layout})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.real_name_layout:
                intent = new Intent(mContext, ModifyInfoActivity.class);
                intent.putExtra("data", "userName");
                startActivityForResult(intent, 2000);
                break;
            case R.id.email_layout:
                intent = new Intent(mContext, ModifyInfoActivity.class);
                intent.putExtra("data", "userEmail");
                startActivityForResult(intent, 2001);
                break;
            case R.id.company_layout:
                intent = new Intent(mContext, ModifyInfoActivity.class);
                intent.putExtra("data", "userRemark");
                startActivityForResult(intent, 2002);
                break;
            case R.id.modify_password_layout:
                intent = new Intent(mContext, ModifyPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null && data.getStringExtra("data") != null) {
            String text = data.getStringExtra("text");
            if (requestCode == 2000) {
                real_name_tv.setText(text);
                SPUtils.put(mContext, "user_name", text);
                UserManager.getInstance().setUserName(text);
            } else if (requestCode == 2001) {
                email_tv.setText(text);
                UserManager.getInstance().setUserEmail(text);
                SPUtils.put(mContext, "user_email", text);
            } else if (requestCode == 2002) {
                company_tv.setText(text);
                SPUtils.put(mContext, "user_remark", text);
            }
        }
    }
}
