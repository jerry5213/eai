package com.zxtech.ecs.ui.home.contractchange;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.ui.home.quote.ProjectInfoFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by syp523 on 2018/6/27.
 */

public class ProductFragment extends BaseFragment {

    private BaseFragment[] mFragments = new BaseFragment[3];

    @BindView(R.id.before_tv)
    TextView before_tv;
    @BindView(R.id.after_tv)
    TextView after_tv;

    private static final int FIRST = 0;
    private static final int SECOND = 1;


    public static ProductFragment newInstance(String projectGuidBefore,String projectGuidAfter, String projectNo,String contractChangeGuid,String contractNo, boolean isEdit) {
        Bundle args = new Bundle();
        ProductFragment fragment = new ProductFragment();
        args.putString("projectGuidBefore", projectGuidBefore);
        args.putString("projectGuidAfter", projectGuidAfter);
        args.putString("projectNo", projectNo);
        args.putString("contractChangeGuid", contractChangeGuid);
        args.putString("contractNo", contractNo);
        args.putBoolean("isEdit", isEdit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_contract_change_product;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            mFragments[FIRST] = ProductBeforeFragment.newInstance(arguments.getString("projectGuidBefore"),arguments.getString("projectGuidAfter"),
                    arguments.getString("projectNo"), arguments.getString("contractNo"),arguments.getString("contractChangeGuid"),arguments.getBoolean("isEdit"),0);
            mFragments[SECOND] = ProductAfterFragment.newInstance(arguments.getString("projectGuidAfter"), arguments.getString("projectNo"),
                    arguments.getString("contractChangeGuid"),arguments.getString("contractChangeGuid"),arguments.getBoolean("isEdit"));

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND]
            );
        } else {
            mFragments[FIRST] = findFragment(ProductBeforeFragment.class);
            mFragments[SECOND] = findFragment(ProductAfterFragment.class);
        }
    }


    @OnClick({R.id.before_tv, R.id.after_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.before_tv:
                showHideFragment(mFragments[FIRST]);
                switchTab(FIRST);
                break;
            case R.id.after_tv:
                showHideFragment(mFragments[SECOND]);
                switchTab(SECOND);
                break;
        }
    }

    private void switchTab(int index) {
        if (index == FIRST) {
            before_tv.setBackgroundResource(R.drawable.left_solid_border);
            before_tv.setTextColor(getResources().getColor(R.color.white));
            after_tv.setBackgroundResource(R.drawable.right_solid_white_border);
            after_tv.setTextColor(getResources().getColor(R.color.main));
        }else {
            before_tv.setBackgroundResource(R.drawable.left_solid_white_border);
            before_tv.setTextColor(getResources().getColor(R.color.main));
            after_tv.setBackgroundResource(R.drawable.right_solid_border);
            after_tv.setTextColor(getResources().getColor(R.color.white));
        }
    }
}
