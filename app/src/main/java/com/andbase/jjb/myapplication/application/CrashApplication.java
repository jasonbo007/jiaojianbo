package com.andbase.jjb.myapplication.application;


import android.app.Application;

import com.andbase.jjb.myapplication.util.storage.StorageUtil;


public class CrashApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        String dir = StorageUtil.getAppCacheDir(this.getApplicationContext()) + "/demo";
        StorageUtil.init(this, dir);
    }
}