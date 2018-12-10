package com.gaoyanrong.concise;

import android.app.Application;

/**
 * @author 高延荣
 * @date 2018/11/30 14:03
 * 描述:
 */
public class JtApplication extends Application {
    private static JtApplication jtApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        jtApplication = this;
    }

    public static JtApplication getInstance() {
        return jtApplication;
    }
}
