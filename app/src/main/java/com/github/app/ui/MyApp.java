package com.github.app.ui;

import android.support.multidex.MultiDexApplication;


/**
 * Created by benny
 * on 2017/10/13.
 */

public class MyApp extends MultiDexApplication {
    private static MyApp publicApp;

    @Override
    public void onCreate() {
        super.onCreate();
        publicApp = (MyApp) getApplicationContext();
    }

    /**
     * 获取Application
     *
     * @return
     */
    public static MyApp getInstance() {
        return publicApp;
    }
}
