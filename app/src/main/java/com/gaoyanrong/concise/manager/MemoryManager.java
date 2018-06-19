package com.gaoyanrong.concise.manager;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;

/**
 *
 */
public class MemoryManager {
	private static final String TAG = "MemoryManager";
	//程序运行的最低内存
	private static final int MINMEMORY=30*1024*1024;
	/**
	 * 判断系统是否在低内存下运行
	 *
	 * @return
	 */
	public static boolean hasAcailMemory() {
		// 获取手机内部空间大小
		long memory = getAvailableInternalMemorySize();
		Log.i(TAG, memory+"");
		if (memory < MINMEMORY) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获取手机内部可用空间大小
	 * 
	 * @return
	 */
	public static long getAvailableInternalMemorySize() {
		// 获取 Android 数据目录
		File path = Environment.getDataDirectory();
		// 一个模拟linux的df命令的一个类,获得SD卡和手机内存的使用情况
		StatFs stat = new StatFs(path.getPath());
		// 返回 Int ，大小，以字节为单位，一个文件系统
		long blockSize = stat.getBlockSize();
		// 返回 Int ，获取当前可用的存储空间
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * 获取手机内部空间大小
	 * 
	 * @return
	 */
	public static long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		// 获取该区域可用的文件系统数
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}

	/**
	 * 获取手机外部可用空间大小
	 * 
	 * @return
	 */
	public static long getAvailableExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		} else {
			throw new RuntimeException("Don't have sdcard.");
		}
	}

	/**
	 * 获取手机外部空间大小
	 * 
	 * @return
	 */
	public static long getTotalExternalMemorySize() {
		if (externalMemoryAvailable()) {
			// 获取外部存储目录即 SDCard
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		} else {
			throw new RuntimeException("Don't have sdcard.");
		}
	}

	/**
	 * 外部存储是否可用
	 * 
	 * @return
	 */
	public static boolean externalMemoryAvailable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
}
