package com.zxtech.ecs.ui.me.setting;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.MainActivity;
import com.zxtech.ecs.R;
import com.zxtech.ecs.common.Constants;
import com.zxtech.ecs.widget.SelectDialog;
import com.zxtech.gks.common.SPUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zxtech.ecs.common.Constants.LANGUAGE_EN;
import static com.zxtech.ecs.common.Constants.LANGUAGE_ZH;

/**
 * 设置
 * Created by syp523 on 2018/2/2.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.language_tv)
    TextView language_tv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.setting));
    }

    @OnClick(R.id.language_layout)
    public void languageAction() {

        List<String> names = new ArrayList<>();
        names.add(getString(R.string.chinese));
        names.add(getString(R.string.english));
        showDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String defaultLanguage = (String) SPUtils.get(mContext, Constants.SHARED_LANGUAGE, Locale.getDefault().toString());
                switch (position) {
                    case 0:
                        if (defaultLanguage.equals(LANGUAGE_ZH)) {
                            return;
                        } else {
                            swithchLanguage(LANGUAGE_ZH);
                        }
                    case 1:
                        if (defaultLanguage.equals(LANGUAGE_EN)) {
                            return;
                        } else {
                            swithchLanguage(LANGUAGE_EN);
                        }
                        break;
                }

            }
        }, names);

    }

    private void swithchLanguage(String language) {
        changeAppLanguage(language);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("switch_language", true);
        getActivity().startActivity(intent);
    }

    public void changeAppLanguage(String language) {
        //	String language = (String) SPUtils.get(mContext,Constants.SHARED_LANGUAGE, Locale.getDefault().toString());
        String sta = language;
        // 本地语言设置
        Locale myLocale = new Locale(sta);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        SPUtils.put(mContext, Constants.SHARED_LANGUAGE, sta);
    }
}
