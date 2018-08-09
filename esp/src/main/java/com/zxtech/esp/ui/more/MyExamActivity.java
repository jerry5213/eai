package com.zxtech.esp.ui.more;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zxtech.esp.R;
import com.zxtech.esp.util.BizUtil;

/**
 * Created by SYP521 on 2017/7/27.
 */

public class MyExamActivity extends AppCompatActivity {

    private ViewPager viewpager;
    private boolean[] refreshItemFlag = {false,false};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exam);
        step();
    }

    private void step() {

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(new ExamViewPagerAdapter(this,this.getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewpager,true);

        TextView tv_back = (TextView) findViewById(R.id.tv_back);
        BizUtil.setIconFont(tv_back, "\ue620");
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean getRefreshItemFlag(int pos) {
        return refreshItemFlag[pos];
    }
    public boolean setRefreshItemFlag(int pos,boolean b) {
        return refreshItemFlag[pos] = b;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!= Activity.RESULT_OK)
            return;
        refreshItemFlag[0] = true;
        refreshItemFlag[1] = true;
    }
}
