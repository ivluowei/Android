package com.wt.zhxm.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

/**
 * @author wtt 清楚缓存工具类 清除内/外缓存，清除数据库，清除sharedPreference，清除files和清除自定义目录
 */
public class DataCleanUtils {
	/**
	 * @param directory
	 *            删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
	 */
	private static void deleteFilesByDirectory(File directory) {

		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				item.delete();
			}
		}
	}

	/**
	 * @param context
	 *            清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
	 */
	public static void cleanInternalCache(Context context) {

		deleteFilesByDirectory(context.getCacheDir());
	}

	/**
	 * @param context
	 *            清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
	 */
	public static void cleanDatabases(Context context) {

		deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
	}

	/**
	 * @param context
	 *            清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
	 */
	public static void cleanSharedPreference(Context context) {

		deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
	}

	/**
	 * @param context
	 * @param dbName
	 *            按名字清除本应用数据库
	 */
	public static void cleanDatabaseByName(Context context, String dbName) {

		context.deleteDatabase(dbName);
	}

	/**
	 * @param context
	 *            清除/data/data/com.xxx.xxx/files下的内容
	 */
	public static void cleanFiles(Context context) {

		deleteFilesByDirectory(context.getFilesDir());
	}

	/**
	 * @param context
	 *            清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
	 */
	public static void cleanExternalCache(Context context) {

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			deleteFilesByDirectory(context.getExternalCacheDir());
		}
	}

	/**
	 * @param filePath
	 *            清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
	 */
	public static void cleanCustomCache(String filePath) {

		deleteFilesByDirectory(new File(filePath));
	}

	/**
	 * @param context
	 * @param filepath
	 *            清除本应用所有的数据
	 */
	public static void cleanApplicationData(Context context, String... filepath) {

		cleanInternalCache(context);
		cleanExternalCache(context);
		cleanDatabases(context);
		cleanSharedPreference(context);
		cleanFiles(context);
		for (String filePath : filepath) {
			cleanCustomCache(filePath);
		}
	}
	
	public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

	
	public static long getFolderSize(File file) throws Exception{
		long size=0;
		 try {
			 File[] fileList=file.listFiles();
				for (int i = 0; i < fileList.length; i++) {
					if (fileList[i].isDirectory()) {
						size=size+getFolderSize(fileList[i]);
					}else {
						size=size+fileList[i].length();
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}
	
	 public  static String getFormatSize(double size){
		 double kiloByte=size/1024;
		 if (kiloByte<1) {
			 return size+"Byte";	
		} 
		 double megaByte=kiloByte/1024;
		 if (megaByte<1) {
			 BigDecimal result1=new BigDecimal(Double.toString(kiloByte));
			 return result1.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"KB";
		}
		 double gigaByte=megaByte/1024;
		 if (gigaByte<1) {
			 BigDecimal result2=new BigDecimal(Double.toString(megaByte));
			 return result2.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"MB";
		}
		 double teraBytes=gigaByte/1024;
		 if (teraBytes<1) {
			 BigDecimal result3=new BigDecimal(Double.toString(gigaByte));
			 return result3.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"GB";
		}
		 BigDecimal result4=new BigDecimal(teraBytes);
		 return result4.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"TB";
	 }
	 
	 public static String getCacheSize(File file)throws Exception{
		return getFormatSize(getFolderSize(file));
		 
	 }
}
