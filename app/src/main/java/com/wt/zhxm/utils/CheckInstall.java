package com.wt.zhxm.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * @author wtt
 * 检查微信、QQ是否安装
 */
public class CheckInstall {
 
	public static boolean isisQQClientAvailable(Context context) {
		//获取packageManager
		final PackageManager packageManager=context.getPackageManager();
		//获取所有已安装程序
		List<PackageInfo> infos=packageManager.getInstalledPackages(0);
		if (infos!=null) {
			for (int i = 0; i <infos.size(); i++) {
				String pn=infos.get(i).packageName;
				if (pn.equals("com.tencent.mobileqq")) {
					return true;
				}
			}
		}
		return false;
		
	}
	
	public static boolean isWeixinAvilibl(Context context){
		final PackageManager packageManager=context.getPackageManager();
		List<PackageInfo> infos=packageManager.getInstalledPackages(0);
		if (infos!=null) {
			for (int i = 0; i <infos.size(); i++) {
			String pn=infos.get(i).packageName;
			if (pn.equals("com.tencent.mm")) {
				return true;
			}
			}
		}
		return false;
		
	}
}
