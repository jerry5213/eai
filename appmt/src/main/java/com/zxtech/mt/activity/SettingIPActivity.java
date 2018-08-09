package com.zxtech.mt.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.zxtech.mt.common.UIController;
import com.zxtech.mt.utils.SPUtils;
import com.zxtech.mt.utils.ToastUtil;
import com.zxtech.mtos.R;

/**
 * Created by syp523 on 2017/9/22.
 */

public class SettingIPActivity extends BaseActivity {

    private EditText ip_edittext;

    @Override
    protected void onCreate() {
        View view = mInfalter.inflate(R.layout.activity_set_ip, null);
        main_layout.addView(view);
        title_textview.setText("");
        setBottomLayoutHide();
        set_textview.setText("保存");
    }

    @Override
    protected void findView() {
        ip_edittext = (EditText) findViewById(R.id.ip_edittext);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ip_edittext.setHint((String)SPUtils.get(getApplicationContext(),"IP",""));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.set_textview) {
            String text = ip_edittext.getText().toString().replaceAll(" ","");
            if (TextUtils.isEmpty(text)) {

                return;
            }
            if (!text.startsWith("http")) {
                ToastUtil.showLong(mContext,"格式错误");
                return;
            }
            SPUtils.put(getApplicationContext(),"IP", text);
            SPUtils.put(getApplicationContext(),"RESOURCE_URL",  text.substring(0, text.lastIndexOf("/"))+"/upload/");
            finish();

        }
    }
}
