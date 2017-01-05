package com.wt.zhxm.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2016/12/12 0012.
 * 是否链接网络工具类
 */
public class NetUtils {
    public static boolean isNetworking(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isAvailable()) {
                // 联网
                return true;
            } else {
                // 未联网
                return false;
            }
        }
        return false;
    }
}
