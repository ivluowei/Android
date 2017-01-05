package com.wt.zhxm.fragment;


import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public interface ITabFragment {
    void onMenuItemClick(MenuItem item, Toolbar toolbar);

    BaseFragment getFragment();
}
