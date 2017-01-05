package com.wt.zhxm.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author wtt SharedPreferences工具类
 *
 */
public class PrefUtils {
	public static final String PREF_NAME = "config";

	public static boolean getBoolean(Context ctx, String key, boolean defValue) {
		// 判断之前有没有显示过新手引导
		SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		boolean userGuide = sp.getBoolean(key, defValue);
		return userGuide;
	}

	public static void setBoolean(Context ctx, String key, boolean value) {
		SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}
	
	

	public static String getString(Context ctx, String key, String defValue) {
		SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		return sp.getString(key, defValue);
	}

	public static void setString(Context ctx, String key, String value) {
		SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}
}
