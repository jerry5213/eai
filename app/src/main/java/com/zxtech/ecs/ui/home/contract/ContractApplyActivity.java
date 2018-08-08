package com.zxtech.ecs.ui.home.contract;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;

import butterknife.BindView;

/**
 * Created by syp523 on 2018/3/31.
 */

public class ContractApplyActivity extends BaseActivity implements TabLayout.OnTabSelectedListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    private BaseFragment[] mFragments = new BaseFragment[2];

    private static final int FIRST = 0;
    private static final int SECOND = 1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_contract_apply;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar,getString(R.string.contract_apply));

        tab_layout.addOnTabSelectedListener(this);


        if (savedInstanceState == null) {
            mFragments[FIRST] = ApplyingFragment.newInstance();
            mFragments[SECOND] = ApplyFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND]
            );
        } else {
            mFragments[FIRST] = findFragment(ApplyingFragment.class);
            mFragments[SECOND] = findFragment(ApplyFragment.class);
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
