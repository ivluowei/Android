package com.wt.zhxm.utils;

import android.content.Context;

/**
 * @author wtt
 *缓存工具类
 */
public class CacheUtils {
 public static void setCache(Context ctx,String key,String value ){
	 PrefUtils.setString(ctx, key, value);
 }
 public static String getCache(Context ctx,String key){
	return PrefUtils.getString(ctx, key, null);
	 
 }
}
