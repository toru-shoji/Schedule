package com.toro_studio.schedule.views;

import android.app.Application;

import io.realm.Realm;

public class BasicApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
