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
 * Created by syp661 on 2018/5/02.
 */

public class TaskMeStartConfirmFragment extends BaseFragment {

    private BaseFragment[] mFragments = new BaseFragment[2];
    private static final int FIRST = 0;
    private static final int SECOND = 1;

    @BindViews({R2.id.task_me_temporary_task_list_a, R2.id.task_me_temporary_task_list_b})
    TextView[] tabTexts;

    @BindViews({R2.id.task_me_temporary_task_tab_list_a, R2.id.task_me_temporary_task_tab_list_b})
    TextView[] tabTabs;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_task_me_item_task_list;
    }

    public static TaskMeStartConfirmFragment newInstance() {
        return new TaskMeStartConfirmFragment();
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments[FIRST] = TaskMeItemAssignedFragment.newInstance();
            mFragments[SECOND] = TaskMeInstaWayRecordsFragment.newInstance();
            loadMultipleRootFragment(R.id.task_me_temporary_task_list_container, 0,
                    mFragments[FIRST],
                    mFragments[SECOND]
            );
        } else {
            mFragments[FIRST] = findFragment(TaskMeItemAssignedFragment.class);
            mFragments[SECOND] = findFragment(TaskMeInstaWayRecordsFragment.class);
        }
        showTab(FIRST);
    }

    @OnClick({R2.id.task_me_temporary_task_list_a, R2.id.task_me_temporary_task_list_b})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.task_me_temporary_task_list_a) {
            showHideFragment(mFragments[FIRST]);
            showTab(FIRST);

        } else if (i == R.id.task_me_temporary_task_list_b) {
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