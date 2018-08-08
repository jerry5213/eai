package com.zxtech.gks.ui.pa;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;
import com.zxtech.gks.IActivity;
import com.zxtech.gks.common.Constants;
import com.zxtech.gks.model.vo.ProjectInfo;

import butterknife.BindView;
import butterknife.BindViews;

/**
 * Created by SYP521 on 2017/11/4.
 */

public class ProjectInfoDetailActivity extends BaseActivity implements IActivity {

    private ProjectInfo projectInfo;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindViews({R.id.project_name,R.id.project_attribute,R.id.agent_name,R.id.product_total,R.id.project_no,
    R.id.project_type,R.id.tv_gk,R.id.salesman_name,R.id.tv_is_kq,R.id.promisesCount})
    TextView[] tvs;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_detail_info;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar);
        projectInfo = (ProjectInfo)getIntent().getSerializableExtra(Constants.DATA1);
        tvs[0].setText(projectInfo.getProject_name());
        tvs[1].setText(projectInfo.getProject_attr());
        tvs[2].setText(projectInfo.getAgent_name());
        tvs[3].setText(projectInfo.getProduct_list());
        tvs[4].setText(projectInfo.getProject_no());
        tvs[5].setText(projectInfo.getProject_type());
        tvs[6].setText(projectInfo.getGk_user());
        tvs[7].setText(projectInfo.getSale_branch());
        tvs[8].setText(projectInfo.getIs_kq());
        tvs[9].setText(projectInfo.getPromise_Count());
    }
}
