package com.regfacu.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Facundo Mengoni.
 * @author mengonifacundo@gmail.com
 */
@SuppressWarnings("unused")
public class SharedPreferencesHelper {

    private static final Set<String> RESPONSE_NEGATIVE_STRING_SET = new HashSet<>();
    public static final Boolean RESPONSE_NEGATIVE_BOOLEAN = false;
    public static final int RESPONSE_NEGATIVE_INTEGER = -1;
    public static final float RESPONSE_NEGATIVE_FLOAT = -1f;
    public static final long RESPONSE_NEGATIVE_LONG = -1l;
    public static final String RESPONSE_NEGATIVE_STRING = null;

    private static final String NAME = "Utils";
    private static final int MODE = Context.MODE_PRIVATE;
    private static HashMap<String, SharedPreferencesHelper> mMap = new HashMap<>();
    private final String mName;

    private SharedPreferencesHelper(String name) {
        mName = name;
    }

    public static SharedPreferencesHelper get() {
        return get(NAME);
    }

    public static SharedPreferencesHelper get(String name) {
        SharedPreferencesHelper preference = mMap.get(name);
        if (preference == null) {
            preference = new SharedPreferencesHelper(name);
            mMap.put(name, preference);
        }
        return preference;
    }

    public void clearPreferences(Context aContext) {
        SharedPreferences settings = aContext.getSharedPreferences(mName, MODE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    public boolean hasKey(Context aContext, String key) {
        return aContext.getSharedPreferences(mName, MODE).contains(key);
    }

    public Boolean removeKey(Context aContext, String key) {
        SharedPreferences settings = aContext.getSharedPreferences(mName, MODE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        return editor.commit();
    }

    public boolean getBoolean(Context aContext, String key) {
        SharedPreferences settings = aContext.getSharedPreferences(mName, MODE);
        return settings.getBoolean(key, RESPONSE_NEGATIVE_BOOLEAN);
    }

    public void putBoolean(Context aContext, String key, boolean value) {
        SharedPreferences settings = aContext.getSharedPreferences(mName, MODE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public long getIntAsLong(Context aContext, String key) {
        SharedPreferences settings = aContext.getSharedPreferences(mName, MODE);
        return settings.getLong(key, RESPONSE_NEGATIVE_INTEGER);
    }

    public int getInt(Context aContext, String key) {
        SharedPreferences settings = aContext.getSharedPreferences(mName, MODE);
        return settings.getInt(key, RESPONSE_NEGATIVE_INTEGER);
    }

    public void putInt(Context aContext, String key, int value) {
        SharedPreferences settings = aContext.getSharedPreferences(mName, MODE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public float getFloat(Context aContext, String key) {
        SharedPreferences settings = aContext.getSharedPreferences(mName, MODE);
        return settings.getFloat(key, RESPONSE_NEGATIVE_FLOAT);
    }

    public void putFloat(Context aContext, String key, float value) {
        SharedPreferences settings = aContext.getSharedPreferences(mName, MODE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public long getLong(Context aContext, String key) {
        SharedPreferences settings = aContext.getSharedPreferences(mName, MODE);
        return settings.getLong(key, RESPONSE_NEGATIVE_LONG);
    }

    public void putLong(Context aContext, String key, long value) {
        SharedPreferences settings = aContext.getSharedPreferences(mName, MODE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public String getString(Context aContext, String key) {
        SharedPreferences settings = aContext.getSharedPreferences(mName, MODE);
        return settings.getString(key, RESPONSE_NEGATIVE_STRING);
    }

    public void putString(Context aContext, String key, String value) {
        SharedPreferences settings = aContext.getSharedPreferences(mName, MODE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> getStringSet(Context aContext, String key) {
        if (!Util.hasHoneycomb()) {
            return RESPONSE_NEGATIVE_STRING_SET;
        }
        SharedPreferences settings = aContext.getSharedPreferences(mName, MODE);
        return settings.getStringSet(key, RESPONSE_NEGATIVE_STRING_SET);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void putStringSet(Context aContext, String key, Set<String> value) {
        if (!Util.hasHoneycomb())
            return;
        SharedPreferences settings = aContext.getSharedPreferences(mName, MODE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(key, value);
        editor.commit();
    }
}