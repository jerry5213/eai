package com.zxtech.esp.ui.more;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zxtech.esp.R;

/**
 * Created by SYP521 on 2017/7/27.
 */

public class ExamViewPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public ExamViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        ExamFragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ExamFragment();
                fragment.setReviewStatus(0);
                break;
            case 1:
                fragment = new ExamFragment();
                fragment.setReviewStatus(1);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return mContext.getResources().getString(R.string.untested);
            case 1:
                return mContext.getResources().getString(R.string.tested);
            case 2:
                return "我";
            default:
                return "微博";
        }
    }
}
