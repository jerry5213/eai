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

public class TaskMeTemporaryTaskFragment extends BaseFragment {

    private BaseFragment[] mFragments = new BaseFragment[2];
    private static final int FIRST = 0;
    private static final int SECOND = 1;

    @BindViews({R2.id.task_me_temporary_task_list_a, R2.id.task_me_temporary_task_list_b})
    TextView[] tabTexts;

    @BindViews({R2.id.task_me_temporary_task_tab_list_a, R2.id.task_me_temporary_task_tab_list_b})
    TextView[] tabTabs;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_task_me_temporary_task_list;
    }

    public static TaskMeTemporaryTaskFragment newInstance() {
        return new TaskMeTemporaryTaskFragment();
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments[FIRST] = TaskMeTemporaryTaskProblemFragment.newInstance();
            mFragments[SECOND] = TaskMeTemporaryTaskTaskFragment.newInstance();
            loadMultipleRootFragment(R.id.task_me_temporary_task_list_container, 1,
                    mFragments[FIRST],
                    mFragments[SECOND]
            );
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()
            // 自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(TaskMeTemporaryTaskProblemFragment.class);
            mFragments[SECOND] = findFragment(TaskMeTemporaryTaskTaskFragment.class);
        }
        showTab(SECOND);
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
