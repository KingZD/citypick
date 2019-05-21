package com.example.demo;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * 文件名:MyApplication
 * 创建者:zed
 * 创建日期:2019/3/5 13:12
 * 描述:TODO
 */
public class MyApplication extends MultiDexApplication {
    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public static MyApplication getApplication() {
        return application;
    }
}
