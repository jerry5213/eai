package com.zxtech.ecs.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @auth: chw
 * @date: 2018/2/2
 * @desc: 自定义viewpager是否滑动
 */

public class ViewPagerSlide extends ViewPager {
	private boolean isCanScroll;

	public ViewPagerSlide(Context context) {
		super(context);
	}

	public ViewPagerSlide(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return isCanScroll && super.onTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return isCanScroll && super.onInterceptTouchEvent(ev);
	}

	/**
	 * 设置其是否能滑动换页
	 *
	 * @param isCanScroll false 不能滑动， true 可以滑动换页
	 */
	public void setScanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

}
