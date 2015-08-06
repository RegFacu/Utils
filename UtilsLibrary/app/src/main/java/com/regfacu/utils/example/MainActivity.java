package com.regfacu.utils.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.regfacu.utils.Converters;
import com.regfacu.utils.Util;
import com.regfacu.utils.tools.ConnectionTools;
import com.regfacu.utils.tools.HardwareTools;

import java.util.logging.Level;


public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    static {
        Converters.mLogger.setLevel(Level.ALL);
        ConnectionTools.mLogger.setLevel(Level.ALL);
        HardwareTools.mLogger.setLevel(Level.ALL);
        Util.mLogger.setLevel(Level.ALL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "----- Converters -----");
        Log.i(TAG, "Date: " + Converters.stringToDate("2015-08-05 20:38:12", "yyyy-MM-dd hh:mm:ss").toString());
        Log.i(TAG, "-----  -----");
        Log.i(TAG, "----- ConnectionTools -----");
        ConnectionTools.isGeolocationActive(this);
        Log.i(TAG, "-----  -----");
        Log.i(TAG, "----- HardwareTools -----");
        HardwareTools.hasCamera(this);
        Log.i(TAG, "-----  -----");
        Log.i(TAG, "----- Utils -----");
        Util.getDeviceData(this);
        Log.i(TAG, "-----  -----");
    }
}