package com.zxtech.is.ui.task.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zxtech.is.BaseActivity;
import com.zxtech.is.BaseFragment;
import com.zxtech.is.R;
import com.zxtech.is.R2;
import com.zxtech.is.ui.task.fragment.S1TaskFragment;
import com.zxtech.is.ui.task.fragment.S2TaskFragment;
import com.zxtech.is.ui.task.fragment.S3TaskFragment;
import com.zxtech.is.ui.task.fragment.TeamTaskFragment;
import com.zxtech.is.util.ToastUtil;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;

public class ElevatorTaskListActivity extends BaseActivity {
    private BaseFragment[] mFragments = new BaseFragment[4];
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOURTH = 3;

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.tv_is_elevator_code)
    TextView tv_is_elevator_code;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_task) {
            Log.d("test", "add");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_elevator_task;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        int position = getIntent().getIntExtra("position", 0);
        String projectNo = getIntent().getStringExtra("projectNo");
        String category = getIntent().getStringExtra("category");

        Log.d("test", projectNo + ":" + category);

        initTitleBar(mToolbar);

        initTabLayout(position);

        initFragment(savedInstanceState, position);
    }

    @OnClick({R2.id.tv_is_elevator_code})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_is_elevator_code) {
            ToastUtil.showLong("click");

        }
    }

    private void initTabLayout(final int position) {
        if (position == 0) {
            tv_is_elevator_code.setTextColor(getResources().getColor(R.color.color_background_white));
            tv_is_elevator_code.setBackground(getResources().getDrawable(R.drawable.circle_fill_blue));
        }
    }

    private void initFragment(Bundle savedInstanceState, int position) {
        if (savedInstanceState == null) {
            mFragments[FIRST] = TeamTaskFragment.newInstance();
            mFragments[SECOND] = S1TaskFragment.newInstance();
            mFragments[THIRD] = S2TaskFragment.newInstance();
            mFragments[FOURTH] = S3TaskFragment.newInstance();

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
            mFragments[FIRST] = findFragment(TeamTaskFragment.class);
            mFragments[SECOND] = findFragment(S1TaskFragment.class);
            mFragments[THIRD] = findFragment(S2TaskFragment.class);
            mFragments[FOURTH] = findFragment(S3TaskFragment.class);
        }
    }


}
