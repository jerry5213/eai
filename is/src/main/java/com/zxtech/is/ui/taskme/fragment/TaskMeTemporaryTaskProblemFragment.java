package com.zxtech.is.ui.taskme.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zxtech.is.BaseFragment;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.model.taskquestion.TaskQuestion;
import com.zxtech.is.model.taskquestion.TaskQuestionPic;
import com.zxtech.is.model.temporary.TemporaryTask;
import com.zxtech.is.service.common.FragmentService;
import com.zxtech.is.ui.taskquestion.activity.TaskQuestionActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by syp660 on 2018/4/19.
 */

public class TaskMeTemporaryTaskProblemFragment extends BaseFragment implements FragmentService {

    private BaseFragment[] mFragments = new BaseFragment[4];
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOURTH = 3;

    @BindViews({R2.id.temporary_task_problem_all_btn, R2.id.temporary_task_problem_aqjc_btn, R2.id.temporary_task_problem_zljc_btn, R2.id.temporary_task_problem_ryyz_btn})
    TextView[] tabTexts;

    public static TaskMeTemporaryTaskProblemFragment newInstance() {
        return new TaskMeTemporaryTaskProblemFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_task_me_temporary_task_list_problem;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            mFragments[FIRST] = TaskMeTemporaryTaskProblemAllFragment.newInstance();
            mFragments[SECOND] = TaskMeTemporaryTaskProblemAqjcFragment.newInstance();
            mFragments[THIRD] = TaskMeTemporaryTaskProblemZljcFragment.newInstance();
            mFragments[FOURTH] = TaskMeTemporaryTaskProblemRyyzFragment.newInstance();
            loadMultipleRootFragment(R.id.temporary_task_problem_framelayout, 0,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]
            );
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()
            // 自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(TaskMeTemporaryTaskProblemAllFragment.class);
            mFragments[SECOND] = findFragment(TaskMeTemporaryTaskProblemAqjcFragment.class);
            mFragments[THIRD] = findFragment(TaskMeTemporaryTaskProblemZljcFragment.class);
            mFragments[FOURTH] = findFragment(TaskMeTemporaryTaskProblemRyyzFragment.class);
        }
        showTab(FIRST);
    }

    @OnClick({R2.id.temporary_task_problem_all_btn, R2.id.temporary_task_problem_aqjc_btn, R2.id.temporary_task_problem_zljc_btn, R2.id.temporary_task_problem_ryyz_btn, R2.id.temporary_task_problem_add_btn})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.temporary_task_problem_all_btn) {
            showHideFragment(mFragments[FIRST]);
            showTab(FIRST);

        } else if (i == R.id.temporary_task_problem_aqjc_btn) {
            showHideFragment(mFragments[SECOND]);
            showTab(SECOND);

        } else if (i == R.id.temporary_task_problem_zljc_btn) {
            showHideFragment(mFragments[THIRD]);
            showTab(THIRD);

        } else if (i == R.id.temporary_task_problem_ryyz_btn) {
            showHideFragment(mFragments[FOURTH]);
            showTab(FOURTH);

        } else if (i == R.id.temporary_task_problem_add_btn) {
            openProblemDetail("0", null);

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

    @Override
    public void openProblemDetail(String flag, TemporaryTask t) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        Intent intent = new Intent(mContext, TaskQuestionActivity.class);
        int openMode = 0;
        if ("0".equals(flag)) {
            openMode = 2;
            intent.putExtra("openMode", "2");
        } else {
            List<TaskQuestionPic> list = new ArrayList<>();
            if (t.getGuids() != null && !"".equals(t.getGuids())) {
                String[] guidsArray = t.getGuids().split(",");
                String[] picGuidsArray = t.getPicGuids().split(",");
                String[] typesArray = t.getTypes().split(",");

                for (int i = 0; i < guidsArray.length; i++) {
                    TaskQuestionPic taskQuestionPic = new TaskQuestionPic();
                    taskQuestionPic.setGuid(guidsArray[i]);
                    taskQuestionPic.setPicguid(picGuidsArray[i]);
                    taskQuestionPic.setType(typesArray[i]);
                    list.add(taskQuestionPic);
                }
            }

            String userFlag = "2";
            if ("questionAssign_fe".equals(t.getTaskKey())) {
                userFlag = "1";
            }
            if ("1".equals(userFlag)) {
                openMode = 3;
                intent.putExtra("openMode", "3");
                intent.putExtra("taskId", t.getTaskId());
                intent.putExtra("guid", t.getGuid());
                intent.putExtra("projectName", t.getProjectName());
                intent.putExtra("elevatorName", t.getElevatorName());
                intent.putExtra("taskType", t.getTasktype());
                intent.putExtra("taskName", t.getOptionName());
                intent.putExtra("assignProcInstName", t.getAssignUserName());
                intent.putExtra("confirmProcInstName", t.getConfirmUserName());
                intent.putExtra("endDate", f.format(t.getEnddate()));
                intent.putExtra("remark", t.getRemark());
                intent.putExtra("picList", (Serializable) list);
            } else {
                String assignstatus = t.getAssignstatus();
                if ("1".equals(assignstatus)) {
                    openMode = 4;
                    intent.putExtra("openMode", "4");
                    intent.putExtra("taskId", t.getTaskId());
                    intent.putExtra("guid", t.getGuid());
                    intent.putExtra("projectName", t.getProjectName());
                    intent.putExtra("elevatorName", t.getElevatorName());
                    intent.putExtra("taskType", t.getTasktype());
                    intent.putExtra("taskName", t.getOptionName());
                    intent.putExtra("assignProcInstName", t.getAssignUserName());
                    intent.putExtra("confirmProcInstName", t.getConfirmUserName());
                    intent.putExtra("endDate", f.format(t.getEnddate()));
                    intent.putExtra("remark", t.getRemark());
                    intent.putExtra("content", t.getContent());
                    intent.putExtra("picList", (Serializable) list);
                } else {
                    openMode = 5;
                    intent.putExtra("openMode", "5");
                    intent.putExtra("projectName", t.getProjectName());
                    intent.putExtra("elevatorName", t.getElevatorName());
                    intent.putExtra("taskName", t.getOptionName());
                    intent.putExtra("assignProcInstName", t.getAssignUserName());
                    intent.putExtra("confirmProcInstName", t.getConfirmUserName());
                    intent.putExtra("endDate", f.format(t.getEnddate()));
                    intent.putExtra("remark", t.getRemark());
                    intent.putExtra("content", t.getContent());
                    intent.putExtra("picList", (Serializable) list);
                }
            }
        }
        startActivityForResult(intent, openMode);
    }

    //Activity跳转回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 2 || requestCode == 3 || requestCode == 4) && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                refreshProblemFragment(bundle.getString("taskType"));
            }
        }
    }

    private void refreshProblemFragment(String taskType) {
        ((TaskMeTemporaryTaskProblemAllFragment) mFragments[FIRST]).mRefreshLayout.beginRefreshing();
        if ("01".equals(taskType)) {
            ((TaskMeTemporaryTaskProblemAqjcFragment) mFragments[SECOND]).mRefreshLayout.beginRefreshing();
        } else if ("02".equals(taskType)) {
            ((TaskMeTemporaryTaskProblemZljcFragment) mFragments[THIRD]).mRefreshLayout.beginRefreshing();
        } else if ("03".equals(taskType)) {
            ((TaskMeTemporaryTaskProblemRyyzFragment) mFragments[FOURTH]).mRefreshLayout.beginRefreshing();
        }
    }
}
