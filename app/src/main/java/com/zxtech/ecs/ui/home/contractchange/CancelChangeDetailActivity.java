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
import butterknife.OnClick;

/**
 * 取消合同 -详情
 * Created by syp523 on 2018/7/30.
 */

public class CancelChangeDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.before_tv)
    TextView before_tv;
    @BindView(R.id.cancel_product_tv)
    TextView cancel_product_tv;

    private BaseFragment[] mFragments = new BaseFragment[2];

    private static final int FIRST = 0;
    private static final int SECOND = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_cancel;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar,"取消合同");


        String projectGuidBefore = getIntent().getStringExtra("projectGuidBefore");
        String projectGuidAfter = getIntent().getStringExtra("projectGuidAfter");
        String pno = getIntent().getStringExtra("projectNo");
        String contractChangeGuid = getIntent().getStringExtra("contractChangeGuid");
        String contractType = getIntent().getStringExtra("contractType");
        String contractNo = getIntent().getStringExtra("contractNo");
        boolean isEdit = getIntent().getBooleanExtra("isEdit", true);

        if (savedInstanceState == null) {
            mFragments[FIRST] = ProductBeforeFragment.newInstance(projectGuidBefore,projectGuidAfter,
                    pno, contractNo,contractChangeGuid,isEdit,1);
            mFragments[SECOND] = ProductCancelFragment.newInstance(null, null,
                    null,null,contractChangeGuid,isEdit);

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[0],
                    mFragments[1]
            );
        }
    }


    @OnClick({R.id.before_tv, R.id.cancel_product_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.before_tv:
                showHideFragment(mFragments[FIRST]);
                switchTab(FIRST);
                break;
            case R.id.cancel_product_tv:
                showHideFragment(mFragments[SECOND]);
                switchTab(SECOND);
                break;
        }
    }

    private void switchTab(int index) {
        if (index == FIRST) {
            before_tv.setBackgroundResource(R.drawable.left_solid_border);
            before_tv.setTextColor(getResources().getColor(R.color.white));
            cancel_product_tv.setBackgroundResource(R.drawable.right_solid_white_border);
            cancel_product_tv.setTextColor(getResources().getColor(R.color.main));
        }else {
            before_tv.setBackgroundResource(R.drawable.left_solid_white_border);
            before_tv.setTextColor(getResources().getColor(R.color.main));
            cancel_product_tv.setBackgroundResource(R.drawable.right_solid_border);
            cancel_product_tv.setTextColor(getResources().getColor(R.color.white));
        }
    }
}
