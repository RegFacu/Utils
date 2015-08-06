package com.regfacu.utils.tools;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Facundo Mengoni.
 * @author mengonifacundo@gmail.com
 */
public class ConnectionTools {
    private static final String TAG = ConnectionTools.class.getSimpleName();

    public static Logger mLogger;

    static {
        mLogger = Logger.getLogger(TAG);
        mLogger.setLevel(Level.OFF);
    }

    //region Geolocation
    public static Boolean isGeolocationActive(Context aContext) {
        if (aContext == null)
            return Boolean.FALSE;
        LocationManager locationManager = (LocationManager) aContext.getSystemService(Context.LOCATION_SERVICE);
        boolean networkProvider = isGeolocationActiveByNetwork(locationManager);
        boolean gpsProvider = isGeolocationActiveByGPS(locationManager);
        boolean isGeolocationActive = networkProvider || gpsProvider;
        mLogger.info(String.format("Is geolocation active: %b", isGeolocationActive));
        return isGeolocationActive;
    }

    public static Boolean isGeolocationActiveByGPS(Context aContext) {
        if (aContext == null)
            return Boolean.FALSE;
        LocationManager service = (LocationManager) aContext.getSystemService(Context.LOCATION_SERVICE);
        return isGeolocationActiveByGPS(service);
    }

    public static Boolean isGeolocationActiveByGPS(LocationManager aLocationManager) {
        return isEnable(aLocationManager, LocationManager.GPS_PROVIDER);
    }

    public static Boolean isGeolocationActiveByNetwork(Context aContext) {
        if (aContext == null)
            return Boolean.FALSE;
        LocationManager service = (LocationManager) aContext.getSystemService(Context.LOCATION_SERVICE);
        return isGeolocationActiveByNetwork(service);
    }

    public static Boolean isGeolocationActiveByNetwork(LocationManager aLocationManager) {
        return isEnable(aLocationManager, LocationManager.NETWORK_PROVIDER);
    }

    private static Boolean isEnable(LocationManager aLocationManager, String aProvider) {
        boolean isEnable = aLocationManager.isProviderEnabled(aProvider);
        mLogger.info(String.format("Is %s enable: %b", aProvider, isEnable));
        return isEnable;
    }
    //endregion

    //region Network
    public static Boolean isNetworkActive(Context aContext) {
        if (aContext == null)
            return Boolean.FALSE;
        ConnectivityManager cm = (ConnectivityManager) aContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return Boolean.FALSE;
        }

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return isNetworkActive(networkInfo);
    }

    public static Boolean isNetworkActiveByMobile(Context aContext) {
        if (aContext == null)
            return Boolean.FALSE;
        ConnectivityManager cm = (ConnectivityManager) aContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return isNetworkActive(cm, ConnectivityManager.TYPE_MOBILE);
    }

    public static Boolean isNetworkActiveByWifi(Context aContext) {
        if (aContext == null)
            return Boolean.FALSE;
        ConnectivityManager cm = (ConnectivityManager) aContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return isNetworkActive(cm, ConnectivityManager.TYPE_WIFI);
    }

    public static Boolean isNetworkActive(ConnectivityManager cm, int networkType) {
        if (cm == null) {
            return Boolean.FALSE;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(networkType);
        return isNetworkActive(mobileNetwork);
    }

    public static Boolean isNetworkActive(NetworkInfo networkInfo) {
        if (networkInfo == null) {
            return Boolean.FALSE;
        }
        return networkInfo.isConnected();
    }
    //endregion
}