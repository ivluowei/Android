package com.wt.zhxm.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/12/13 0013.
 * 状态栏改变工具类
 */
public class StatusBarColorUtils {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setChangeColor(Activity activity, int colorInd) {
        Window window = activity.getWindow();
        //需要设置这个flag才能调用setStatusBarColor来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(colorInd);
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置ContentView的FitsSystemWindows,而是设置ContentView的第一个子View使其不为系统View预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }
    }
}
