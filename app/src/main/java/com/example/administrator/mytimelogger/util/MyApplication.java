package com.example.administrator.mytimelogger.util;

import android.app.Application;
import android.content.Context;

import com.example.administrator.mytimelogger.model.Tag;

import java.util.List;

/**
 * Created by Administrator on 2016/8/25.
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }

    public static Context getContext() {
        return context;
    }
}
