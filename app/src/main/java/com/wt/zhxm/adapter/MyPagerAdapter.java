package com.wt.zhxm.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author wtt viewpager适配器
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> mList;

	public MyPagerAdapter(FragmentManager fm, List<Fragment> mFramLayouts) {
		super(fm);
		this.mList = mFramLayouts;

	}
	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {

		return mList.get(arg0);
	}

	@Override
	public int getCount() {

		return mList.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}
}
