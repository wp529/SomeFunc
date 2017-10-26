package com.example.administrator.demo;

import android.app.Application;
/**
 * Created by WangPing on 2017/10/24.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());
    }
}
