package com.zxtech.ecs.ui.home.contractchange;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.ui.home.quote.ProjectInfoFragment;

import butterknife.BindView;
import butterknife.BindViews;

/**
 * Created by syp523 on 2018/6/27.
 */

public class ContractChangeDetailActivity extends BaseActivity {

    private BaseFragment[] mFragments = new BaseFragment[3];

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindViews({R.id.project_tv, R.id.product_tv, R.id.summary_tv})
    TextView[] tabTexts;
    @BindView(R.id.project_line)
    View project_line;
    @BindView(R.id.product_line)
    View product_line;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contract_change_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,getString(R.string.edit_project_details));

        String projectGuidBefore = getIntent().getStringExtra("projectGuidBefore");
        String projectGuidAfter = getIntent().getStringExtra("projectGuidAfter");
        String pno = getIntent().getStringExtra("projectNo");
        String contractChangeGuid = getIntent().getStringExtra("contractChangeGuid");
        String contractType = getIntent().getStringExtra("contractType");
        String contractNo = getIntent().getStringExtra("contractNo");
        boolean isEdit = getIntent().getBooleanExtra("isEdit", true);


        if (savedInstanceState == null) {
            mFragments[0] = ProjectInfoFragment.newInstance(projectGuidAfter, pno, contractChangeGuid, contractType, ProjectInfoFragment.CONTRACT_CHANGE_ACTIVITY, isEdit);
            mFragments[1] = ProductFragment.newInstance(projectGuidBefore, projectGuidAfter, pno, contractChangeGuid, contractNo, isEdit);
            mFragments[2] = ChangeSummaryFragment.newInstance(contractChangeGuid, isEdit);

            loadMultipleRootFragment(R.id.fl_container, "2".equals(contractType) ? 1 : 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2]
            );
        } else {
            mFragments[0] = findFragment(ProjectInfoFragment.class);
            mFragments[1] = findFragment(ProductFragment.class);
            mFragments[2] = findFragment(ChangeSummaryFragment.class);
        }

        //根据合同类型判断
        if (ChangeSelectedDialogFragment.CONTRACT_CHANGE_TYPE_BUSINESS.equals(contractType)) {
            tabTexts[1].setVisibility(View.GONE);
            tabTexts[0].setTextColor(getResources().getColor(R.color.dark_red));
            product_line.setVisibility(View.GONE);
        } else if (ChangeSelectedDialogFragment.CONTRACT_CHANGE_TYPE_SPEC.equals(contractType)) {
            tabTexts[0].setVisibility(View.GONE);
            tabTexts[1].setTextColor(getResources().getColor(R.color.dark_red));
            project_line.setVisibility(View.GONE);
        } else {
            tabTexts[0].setTextColor(getResources().getColor(R.color.dark_red));
        }


        for (int i = 0; i < tabTexts.length; i++) {
            final int finalI = i;
            tabTexts[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showHideFragment(mFragments[finalI]);
                    showTab(finalI);
                }
            });

        }
    }


    private void showTab(int position) {
        for (int i = 0; i < tabTexts.length; i++) {
            if (i == position) {
                tabTexts[position].setTextColor(getResources().getColor(R.color.dark_red));
            } else {
                tabTexts[i].setTextColor(getResources().getColor(R.color.default_text_grey_color));
            }
        }
    }

}
