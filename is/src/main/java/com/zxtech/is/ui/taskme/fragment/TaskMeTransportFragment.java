package com.zxtech.is.ui.taskme.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zxtech.is.BaseFragment;
import com.zxtech.is.R;
import com.zxtech.is.R2;

import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by syp660 on 2018/4/19.
 */

public class TaskMeTransportFragment extends BaseFragment {

    private BaseFragment[] mFragments = new BaseFragment[2];
    private static final int FIRST = 0;
    private static final int SECOND = 1;

    @BindViews({R2.id.fragment_task_me_transport_a, R2.id.fragment_task_me_transport_b})
    TextView[] tabTexts;

    @BindViews({R2.id.fragment_task_me_transport_tab_a, R2.id.fragment_task_me_transport_tab_b})
    TextView[] tabTabs;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_task_me_transport;
    }

    public static TaskMeTransportFragment newInstance() {
        return new TaskMeTransportFragment();
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments[FIRST] = TaskMePrdTrkFragment.newInstance();
            mFragments[SECOND] = TaskMeTrsTrkFragment.newInstance();
            loadMultipleRootFragment(R.id.fragment_task_me_transport_container, 0,
                    mFragments[FIRST],
                    mFragments[SECOND]
            );
        } else {
            mFragments[FIRST] = findFragment(TaskMePrdTrkFragment.class);
            mFragments[SECOND] = findFragment(TaskMeTrsTrkFragment.class);
        }
        showTab(FIRST);
    }

    @OnClick({R2.id.fragment_task_me_transport_a, R2.id.fragment_task_me_transport_b})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.fragment_task_me_transport_a) {
            showHideFragment(mFragments[FIRST]);
            showTab(FIRST);

        } else if (i == R.id.fragment_task_me_transport_b) {
            showHideFragment(mFragments[SECOND]);
            showTab(SECOND);

        }
    }

    private void showTab(int position) {
        for (int i = 0; i < tabTexts.length; i++) {
            if (i == position) {
                tabTexts[position].setSelected(true);
                tabTabs[position].setSelected(true);
            } else {
                tabTexts[i].setSelected(false);
                tabTabs[i].setSelected(false);
            }
        }
    }
}
