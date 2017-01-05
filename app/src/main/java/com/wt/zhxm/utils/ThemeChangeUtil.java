package com.wt.zhxm.utils;

import android.app.Activity;

import com.wt.zhxm.R;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public class ThemeChangeUtil {
    public static boolean isChange = false;
    public static void changeTheme(Activity activity){
        if(isChange){
            activity.setTheme(R.style.NightTheme);
        }else {
            activity.setTheme(R.style.AppTheme);
        }
    }
}
