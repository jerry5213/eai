package com.zxtech.ecs.ui.home.projecttrack;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.model.ProjectInfo;
import com.zxtech.ecs.ui.home.quote.QuotationFragment;
import com.zxtech.ecs.ui.home.quote.QuotedFragment;

import butterknife.BindView;

public class ProjectTrackTabActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    private BaseFragment[] mFragments = new BaseFragment[2];

    private static final int FIRST = 0;
    private static final int SECOND = 1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_track_tab;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar);
        tab_layout.addOnTabSelectedListener(this);

        String projectGuid = getIntent().getStringExtra("projectGuid");
        String projectStateName = getIntent().getStringExtra("projectState");
        String projectNo = getIntent().getStringExtra("projectNo");
        String workFlowNodeName = getIntent().getStringExtra("workFlowNodeName");
        String contactState = getIntent().getStringExtra("contactState");
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setProjectGuid(projectGuid);
        projectInfo.setProjectStateName(projectStateName);
        projectInfo.setProjectNo(projectNo);
        projectInfo.setWorkFlowNodeName(workFlowNodeName);
        projectInfo.setContactState(contactState);
        if (savedInstanceState == null) {
            mFragments[FIRST] = ProjectProcessFragment.newInstance(projectInfo);
            mFragments[SECOND] = ProjectTrackFragment.newInstance(projectInfo);

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND]
            );
        } else {
            mFragments[FIRST] = findFragment(QuotationFragment.class);
            mFragments[SECOND] = findFragment(QuotedFragment.class);
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
