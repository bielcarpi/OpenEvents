package com.grup8.OpenEvents.model;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class OpenEventsApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public void onCreate() {
        super.onCreate();
        OpenEventsApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return OpenEventsApplication.context;
    }

}
