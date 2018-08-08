package com.zxtech.ecs.ui.home.bid;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;

import butterknife.BindView;

/**
 * Created by syp523 on 2018/7/24.
 */

public class BidApplyActivity extends BaseActivity implements TabLayout.OnTabSelectedListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    private BaseFragment[] mFragments = new BaseFragment[3];

    private static final int FIRST = 0;
    private static final int SECOND = 1;


    @Override
    protected int getLayoutId() {
        return R.layout.acitivity_bid_apply;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar,"投标申请");
        tab_layout.addOnTabSelectedListener(this);
        if (savedInstanceState == null) {
            mFragments[FIRST] = BidApplyFragment.newInstance(0,true);
            mFragments[SECOND] = BidApplyFragment.newInstance(1,false);

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND]
            );
        }
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        showHideFragment(mFragments[tab.getPosition()]);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
