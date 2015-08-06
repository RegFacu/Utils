package com.regfacu.utils;

import android.app.Application;

/**
 * @author Facundo Mengoni.
 * @author mengonifacundo@gmail.com
 */
public class GenericApp extends Application {
    private static final String TAG = GenericApp.class.toString();

    protected static GenericApp singleton;

    public static GenericApp getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}