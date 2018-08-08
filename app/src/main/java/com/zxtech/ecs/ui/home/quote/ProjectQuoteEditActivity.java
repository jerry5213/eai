package com.zxtech.ecs.ui.home.quote;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.BaseFragment;
import com.zxtech.ecs.R;
import com.zxtech.ecs.event.EventAction;
import com.zxtech.ecs.model.ProjectQuote;
import com.zxtech.ecs.net.BasicResponse;
import com.zxtech.ecs.net.DefaultObserver;
import com.zxtech.ecs.net.HttpFactory;
import com.zxtech.ecs.net.RxHelper;
import com.zxtech.ecs.util.DensityUtil;
import com.zxtech.gks.model.bean.SaveResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by syp521 on 2018/3/31.
 */

public class ProjectQuoteEditActivity extends BaseActivity {

    private BaseFragment[] mFragments = new BaseFragment[2];

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private String[] titles;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_offer_edit;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar,getString(R.string.quotatoin_applicatoin));
        //隐藏软键盘

        ProjectQuote projectQuote = (ProjectQuote) getIntent().getSerializableExtra("projectQuote");
        String pid = projectQuote.getProjectGuid();
        String pno = projectQuote.getProjectNo();

        //页面，数据源

        if (savedInstanceState == null) {
            mFragments[0] = ProjectInfoFragment.newInstance(pid, pno, "",null, ProjectInfoFragment.QUOTE_ACTIVITY,true);
            mFragments[1] = ProductInfoFragment.newInstance(pid, pno);

            loadMultipleRootFragment(R.id.fl_container, 0,
                    mFragments[0],
                    mFragments[1]
            );
        } else {
            mFragments[0] = findFragment(ProjectInfoFragment.class);
            mFragments[1] = findFragment(ProductInfoFragment.class);
        }

        titles = new String[]{getString(R.string.eai_project_info), getString(R.string.product_info)};

        tabLayout.addTab(tabLayout.newTab().setText(titles[0]));
        tabLayout.addTab(tabLayout.newTab().setText(titles[1]));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        });

        //设置分割线
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.tab_divider)); //设置分割线的样式
        linearLayout.setDividerPadding(DensityUtil.dip2px(mContext, 10)); //设置分割线间隔
    }




}
