package com.zxtech.ecs.ui.home.company.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.ui.home.company.fragment.CompanyFragment;
import com.zxtech.ecs.ui.home.company.fragment.ProductInfoFragment;
import com.zxtech.ecs.ui.home.company.fragment.ProductIntroductionFragment;
import com.zxtech.ecs.ui.home.company.fragment.ProjectCaseFragment;
import com.zxtech.ecs.ui.home.company.fragment.PropagandistVideoFragment;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;


/**
 * @date: 2018/2/1
 * @desc: 公司介绍
 */

public class CompanyActivity extends BaseActivity {
    private BaseFragment[] mFragments = new BaseFragment[4];
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOURTH = 3;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_layout)
    LinearLayout mCommonLayout;
    @BindViews({R.id.company_tv, R.id.product_tv, R.id.video_tv, R.id.case_tv})
    TextView[] tabTexts;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar,getString(R.string.about_company));
        int position = getIntent().getIntExtra("position", 0);
        if (savedInstanceState == null) {
            mFragments[FIRST] = CompanyFragment.newInstance();
            mFragments[SECOND] = ProductInfoFragment.newInstance();
            mFragments[THIRD] = PropagandistVideoFragment.newInstance();
            mFragments[FOURTH] = ProjectCaseFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, position,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]
            );
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()
            // 自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(CompanyFragment.class);
            mFragments[SECOND] = findFragment(ProductInfoFragment.class);
            mFragments[THIRD] = findFragment(PropagandistVideoFragment.class);
            mFragments[FOURTH] = findFragment(ProjectCaseFragment.class);
        }

        // showHideFragment(mFragments[FIRST]);
        showTab(position);

//         mViewpager.setAdapter(new VPAdapter(getSupportFragmentManager(), mFragments, mTitles));
//           mTabLayout.setViewPager(mViewpager, mTitles);
//         mViewpager.setCurrentItem(position);
    }


    private void showTab(int position) {
        for (int i = 0; i < tabTexts.length; i++) {
            if (i == position) {
                tabTexts[position].setTextColor(tabColor(position));
            } else {
                tabTexts[i].setTextColor(tabColor(4));
            }
        }
    }

    private int tabColor(int position) {
        int[] titleColors = new int[]{getResources().getColor(R.color.dark_red), getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.grass_green), getResources().getColor(R.color.green),
                getResources().getColor(R.color.default_text_color)};
        return titleColors[position];
    }

    @OnClick({R.id.company_tv, R.id.product_tv, R.id.video_tv, R.id.case_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.company_tv:
                showHideFragment(mFragments[FIRST]);
                showTab(FIRST);
                break;
            case R.id.product_tv:
                showHideFragment(mFragments[SECOND]);
                showTab(SECOND);
                break;
            case R.id.video_tv:
                showHideFragment(mFragments[THIRD]);
                showTab(THIRD);
                break;
            case R.id.case_tv:
                showHideFragment(mFragments[FOURTH]);
                showTab(FOURTH);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

}
