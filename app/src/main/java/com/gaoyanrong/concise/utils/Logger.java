package com.gaoyanrong.concise.utils;

import android.util.Log;

/**
 * @author 高延荣
 * @date 2018/6/12 17:45
 * 描述:  打印
 */

public class Logger {
    /**
     * 设为false关闭日志
     */
    private static final boolean LOG_ENABLE = true;

    public static void i(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.e(tag, msg);
        }
    }
}
