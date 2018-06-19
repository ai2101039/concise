package com.gaoyanrong.concise;

import android.app.Application;
import android.content.Context;

/**
 * @author 高延荣
 * @date 2018/6/13 16:50
 * 描述:
 */
public class ConciseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();


    }


    public static Context getApplicationInstance() {
        return context;
    }
}
