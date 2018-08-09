package com.zxtech.is.ui.taskme.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zxtech.is.net.HttpFactory;
import com.zxtech.is.net.RxHelper;
import com.zxtech.is.BaseActivity;
import com.zxtech.is.BaseFragment;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.common.net.BaseResponse;
import com.zxtech.is.common.net.DefaultObserver;
import com.zxtech.is.service.taskme.TaskMeService;
import com.zxtech.is.ui.taskme.fragment.TaskMeCivilReviewFragment;
import com.zxtech.is.ui.taskme.fragment.TaskMeStartConfirmFragment;
import com.zxtech.is.ui.taskme.fragment.TaskMeTemporaryTaskFragment;
import com.zxtech.is.ui.taskme.fragment.TaskMeTransportFragment;
import com.zxtech.is.ui.taskme.fragment.TaskMeTransportReviewFragment;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by syp660 on 2018/4/19.
 */
@Route(path = "/is/task")
public class TaskMeListActivity extends BaseActivity {
    private BaseFragment[] mFragments = new BaseFragment[6];
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOURTH = 3;
    private static final int FIFTH = 4;
//    private static final int SIXTH  = 5;

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindViews({R2.id.task_me_list_a, R2.id.task_me_list_b, R2.id.task_me_list_c, R2.id.task_me_list_e, R2.id.task_me_list_f})
    TextView[] tabTexts;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_me_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestNet();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar);
        int position = getIntent().getIntExtra("position", 0);
        if (savedInstanceState == null) {
            mFragments[FIRST] = TaskMeStartConfirmFragment.newInstance();
            mFragments[SECOND] = TaskMeCivilReviewFragment.newInstance();
            mFragments[THIRD] = TaskMeTransportFragment.newInstance();
            mFragments[FOURTH] = TaskMeTemporaryTaskFragment.newInstance();
            mFragments[FIFTH] = TaskMeTransportReviewFragment.newInstance();
            loadMultipleRootFragment(R.id.task_me_list_container, position,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH],
                    mFragments[FIFTH]
            );
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()
            // 自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(TaskMeStartConfirmFragment.class);
            mFragments[SECOND] = findFragment(TaskMeCivilReviewFragment.class);
            mFragments[THIRD] = findFragment(TaskMeTransportFragment.class);
            mFragments[FOURTH] = findFragment(TaskMeTemporaryTaskFragment.class);
            mFragments[FIFTH] = findFragment(TaskMeTransportReviewFragment.class);
        }
        showTab(FIRST);
    }

    @OnClick({R2.id.task_me_list_a, R2.id.task_me_list_b, R2.id.task_me_list_c, R2.id.task_me_list_e, R2.id.task_me_list_f})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.task_me_list_a) {
            showHideFragment(mFragments[FIRST]);
            showTab(FIRST);

        } else if (i == R.id.task_me_list_b) {
            showHideFragment(mFragments[SECOND]);
            showTab(SECOND);

        } else if (i == R.id.task_me_list_c) {
            showHideFragment(mFragments[THIRD]);
            showTab(THIRD);

        } else if (i == R.id.task_me_list_e) {
            showHideFragment(mFragments[FOURTH]);
            showTab(FOURTH);

        } else if (i == R.id.task_me_list_f) {
            showHideFragment(mFragments[FIFTH]);
            showTab(FIFTH);

        }

    }

    private void showTab(int position) {
        for (int i = 0; i < tabTexts.length; i++) {
            if (i == position) {
                tabTexts[position].setSelected(true);
            } else {
                tabTexts[i].setSelected(false);
            }
        }
    }

    private void requestNet() {
        TaskMeService taskMeService = HttpFactory.getService(TaskMeService.class);
        taskMeService.getTaskTypeAmount()
                .compose(RxHelper.<BaseResponse<Map<String, Object>>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<Map<String, Object>>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseResponse<Map<String, Object>> response) {

                        tabTexts[0].setText(String.valueOf(response.getData().get("startConfirm")));
                        tabTexts[1].setText(String.valueOf(response.getData().get("s1")));
                        tabTexts[2].setText(String.valueOf(response.getData().get("s5")));
                        tabTexts[3].setText(String.valueOf(response.getData().get("tempTask")));
                        tabTexts[4].setText(String.valueOf(response.getData().get("scheduleManage")));
                        for (int i = 0; i < tabTexts.length; i++) {
                            String num = String.valueOf(tabTexts[i].getText());
                            if (num.equals("") || num == "null") {
                                tabTexts[i].setText("0");
                            }
                        }
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                    }
                });
    }
}
