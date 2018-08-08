package com.zxtech.ecs.ui.home.contractchange;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;

import butterknife.BindView;

/**
 * Created by syp523 on 2018/3/31.
 */

public class ContractChangeApprovalActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    private BaseFragment[] mFragments = new BaseFragment[3];

    private static final int FIRST = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_contract_change;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getString(R.string.change_approval));
        tab_layout.setVisibility(View.GONE);

        if (savedInstanceState == null) {
            mFragments[FIRST] = ChangeFragment.newInstance(1,false);

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST]
            );
        } else {
            mFragments[FIRST] = findFragment(ChangeFragment.class);
        }
    }


}
