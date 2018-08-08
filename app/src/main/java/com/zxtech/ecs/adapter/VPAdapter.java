package com.zxtech.ecs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @auth: hexl
 * @date: 2018/1/17
 * @desc:
 */

public class VPAdapter extends FragmentStatePagerAdapter {
	private List<Fragment> mFragments;
	private String[] mTitles;

	public VPAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
		super(fm);
		this.mFragments = fragments;
		this.mTitles = titles;
	}

	public void setTitles(String[] titles) {
		mTitles = titles;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTitles[position];
	}

	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}
}
