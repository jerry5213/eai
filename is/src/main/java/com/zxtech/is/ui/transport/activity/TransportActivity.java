package com.zxtech.is.ui.transport.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.BaseFragment;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.ui.transport.fragment.TransportS2Fragment;
import com.zxtech.is.ui.transport.fragment.TransportS3Fragment;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by syp660 on 2018/4/19.
 */
@Route(path = "/is/transport")
public class TransportActivity extends BaseActivity {
    private BaseFragment[] mFragments = new BaseFragment[2];
    private static final int FIRST = 0;
    private static final int SECOND = 1;

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindViews({R2.id.transport_s2, R2.id.transport_s3})
    TextView[] tabTexts;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_transport;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);
        int position = getIntent().getIntExtra("position", 0);
        if (savedInstanceState == null) {
            mFragments[FIRST] = TransportS2Fragment.newInstance();
            mFragments[SECOND] = TransportS3Fragment.newInstance();
            loadMultipleRootFragment(R.id.transport_container, position,
                    mFragments[FIRST],
                    mFragments[SECOND]
            );
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()
            // 自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(TransportS2Fragment.class);
            mFragments[SECOND] = findFragment(TransportS3Fragment.class);
        }
        showTab(FIRST);

    }

    @OnClick({R2.id.transport_s2, R2.id.transport_s3})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.transport_s2) {
            showHideFragment(mFragments[FIRST]);
            showTab(FIRST);

        } else if (i == R.id.transport_s3) {
            showHideFragment(mFragments[SECOND]);
            showTab(SECOND);

        }

    }

    private void showTab(int position) {
        for (int i = 0; i < tabTexts.length; i++) {
            if (i == position) {
                tabTexts[position].setTextColor(tabColor(position));
            } else {
                tabTexts[i].setTextColor(tabColor(4));
            }
        }
    }

    private int tabColor(int position) {
        int[] titleColors = new int[]{getResources().getColor(R.color.dark_red), getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.grass_green), getResources().getColor(R.color.green),
                getResources().getColor(R.color.default_text_color)};
        return titleColors[position];
    }

}
