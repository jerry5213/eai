package com.zxtech.ecs.ui.home.contractchange;

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

public class ContractChangeActivity extends BaseActivity implements TabLayout.OnTabSelectedListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    private BaseFragment[] mFragments = new BaseFragment[3];

    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THREE = 2;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_contract_change;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,getString(R.string.contract_change));

        tab_layout.addOnTabSelectedListener(this);


        if (savedInstanceState == null) {
            mFragments[FIRST] = ChangeFragment.newInstance(0,true);
            mFragments[SECOND] = ChangeFragment.newInstance(1,true);
            mFragments[THREE] = ChangeFragment.newInstance(2,true);

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THREE]
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
