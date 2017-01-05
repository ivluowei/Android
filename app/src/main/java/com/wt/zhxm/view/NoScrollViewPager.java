package com.wt.zhxm.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author wtt
 * 不能左右滑的viewpager
 *
 */
public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context) {
		super(context);
		 
	}

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		 
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// 事件是否被拦截，false代表不拦截
		return false;
	}
	
	/* 
	 * 什么事都不做
	 */
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
	}

}
