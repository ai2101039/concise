package com.gaoyanrong.concise.manager;

import android.os.Environment;
import android.os.StatFs;


import com.gaoyanrong.concise.utils.Loger;

import java.io.File;

/**
 * @author 高延荣
 * @date 2018/12/3 11:04
 * 描述: 内存管理器
 */
public class MemoryManager {
    private static final int MIN_MEMORY = 30 * 1024 * 1024;

    /**
     * @return 返回是否有30M内存可用
     * 30M 属于低端机器
     * 如果低于30M，我们使用软引用的HashMap保存BaseView
     */
    public static boolean hasAmpleMemory() {
        long memorySize = getAvailableInternalMemorySize();
        return memorySize < MIN_MEMORY;
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
}
