package me.androidbox.mymapapp.di;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by steve on 8/27/16.
 */

public class MyMapApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
