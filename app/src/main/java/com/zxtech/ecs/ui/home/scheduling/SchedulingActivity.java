package com.zxtech.ecs.ui.home.scheduling;

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

public class SchedulingActivity extends BaseActivity implements TabLayout.OnTabSelectedListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    private BaseFragment[] mFragments = new BaseFragment[3];

    private static final int FIRST = 0;
    private static final int SECOND = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.acitivity_scheduling_plan;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,"排产计划");
        tab_layout.addOnTabSelectedListener(this);
        if (savedInstanceState == null) {
            mFragments[FIRST] = SchedulingPlanFragment.newInstance("NotSubmit");
            mFragments[SECOND] = SchedulingPlanFragment.newInstance("Checking");

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
