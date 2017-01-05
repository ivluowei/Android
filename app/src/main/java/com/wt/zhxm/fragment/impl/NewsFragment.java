package com.wt.zhxm.fragment.impl;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wt.zhxm.R;
import com.wt.zhxm.bean.SmartCategory;
import com.wt.zhxm.fragment.BaseFragment;
import com.wt.zhxm.fragment.ITabFragment;
import com.wt.zhxm.utils.CacheUtils;
import com.wt.zhxm.utils.GlobalContants;
import com.wt.zhxm.utils.NetUtils;
import com.wt.zhxm.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class NewsFragment extends BaseFragment implements ITabFragment {
    private TextView mTitle;
    private ViewPager mViewpager;
    private TabLayout mTablayout;
    private List<String> mTitleList = new ArrayList<>();
    private String TAG = "NewsFragment";
    private List<SmartCategory.Tngou> mCategoryList = new ArrayList<SmartCategory.Tngou>();
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    public int getLayoutId() {

        return R.layout.fragment_news;
    }

    @Override
    public void setUpView(View view, Bundle savedInstanceState) {
        mViewpager = (ViewPager) view.findViewById(R.id.viewPager);
        mTablayout = (TabLayout) view.findViewById(R.id.tab_Layout);
        mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        String cache = CacheUtils.getCache(getActivity(), GlobalContants.SMART_CLASSIFY_URL);
        getDataFromServer();
    }

    private void getDataFromServer() {
        OkHttpUtils.get().url(GlobalContants.SMART_CLASSIFY_URL).addHeader("apikey", "e2543410a1c5ac820d431c9787819814").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                if (NetUtils.isNetworking(getActivity())) {
                    //   ToastUtil.show(getActivity(), getResources().getString(R.string.connection_failed));
                } else {
                    ToastUtil.show(getActivity(), getResources().getString(R.string.isNetWork));
                }
            }

            @Override
            public void onResponse(Call call, String s) {
                Log.d(TAG, "onResponse: " + s);
                paseData(s);
            }
        });
    }

    private void paseData(String s) {
        mCategoryList.clear();
        Gson gson = new Gson();
        SmartCategory mCategory = gson.fromJson(s, SmartCategory.class);
        mCategoryList = mCategory.getTngou();
        if (mCategoryList != null) {
            for (int i = 0; i < mCategoryList.size(); i++) {
                mTablayout.addTab(mTablayout.newTab().setText(mCategoryList.get(i).getTitle()));
                mFragments.add(new NewsListFragment());
                mViewpager.setOffscreenPageLimit(mCategoryList.size());
                mViewpager.setAdapter(new MyFragmentAdapter(getChildFragmentManager()));
                //TabLayout加载viewpager
                mTablayout.setupWithViewPager(mViewpager);
            }
        }

    }


    @Override
    public void onMenuItemClick(MenuItem item, Toolbar toolbar) {

    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

    class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return NewsListFragment.newInstance(mCategoryList.get(position));
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCategoryList.get(position).getTitle();
        }
    }
}
