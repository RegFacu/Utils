package com.regfacu.utils.tools;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.regfacu.utils.Util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Facundo Mengoni.
 * @author mengonifacundo@gmail.com
 */
public class HardwareTools {
    public static final String TAG = HardwareTools.class.getSimpleName();

    public static Logger mLogger;

    static {
        mLogger = Logger.getLogger(TAG);
        mLogger.setLevel(Level.OFF);
    }

    // ToDo Check for more commons features

    public static Boolean hasCamera(Context aContext) {
        PackageManager pm = aContext.getPackageManager();
        Boolean hasCamera = pm != null && pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
        mLogger.info(String.format("Has camera: %b", hasCamera));
        return hasCamera;
    }

    public static Boolean hasWifi(Context aContext) {
        PackageManager packageManager = aContext.getPackageManager();
        Boolean hasWifi = packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI);
        mLogger.info(String.format("Has wifi: %b", hasWifi));
        return hasWifi;
    }

    public static Boolean hasGps(Context aContext) {
        PackageManager packageManager = aContext.getPackageManager();
        Boolean hasGps = packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
        mLogger.info(String.format("Has GPS: %b", hasGps));
        return hasGps;
    }

    public static Boolean hasBluetooth(Context aContext) {
        PackageManager packageManager = aContext.getPackageManager();
        Boolean hasBluetooth = packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH);
        mLogger.info(String.format("Has Bluetooth: %b", hasBluetooth));
        return hasBluetooth;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static Boolean hasBluetoothLE(Context aContext) {
        Boolean hasBluetoothLE = false;
        if (Util.hasJellyBeanMR2()) {
            PackageManager packageManager = aContext.getPackageManager();
            hasBluetoothLE = packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
            mLogger.info(String.format("Has BluetoothLE: %b", hasBluetoothLE));
        } else {
            mLogger.info("BluetoothLE: not supported");
        }
        return hasBluetoothLE;
    }

    public static String getImei(Context aContext) {
        TelephonyManager telephonyManager = (TelephonyManager) aContext.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        mLogger.info(String.format("IMEI: %s", imei));
        return imei;
    }

    public static String getWifiMacAddress(Context aContext) {
        WifiManager wifiMan = (WifiManager) aContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        String macAddress = wifiInf.getMacAddress();
        mLogger.info(String.format("Mac address: %s", macAddress));
        return macAddress;
    }

    public static Long getHeap() {
        Runtime rt = Runtime.getRuntime();
        Long heap = rt.maxMemory();
        mLogger.info(String.format("Heap: %d", heap));
        return heap;
    }

    public static Float getDisplayDensity(Context aContext) {
        DisplayMetrics metrics = aContext.getResources().getDisplayMetrics();
        if (metrics == null)
            return 0f;
        return metrics.density;
    }

    public static Float getDisplayDpiY(Context aContext) {
        DisplayMetrics metrics = aContext.getResources().getDisplayMetrics();
        if (metrics == null)
            return 0f;
        return metrics.xdpi;
    }

    public static Float getDisplayDpiX(Context aContext) {
        DisplayMetrics metrics = aContext.getResources().getDisplayMetrics();
        if (metrics == null)
            return 0f;
        return metrics.ydpi;
    }

    public static Integer getDisplayWidth(Context aContext) {
        DisplayMetrics metrics = aContext.getResources().getDisplayMetrics();
        if (metrics == null)
            return 0;
        return metrics.widthPixels;
    }

    public static Integer getDisplayHeight(Context aContext) {
        DisplayMetrics metrics = aContext.getResources().getDisplayMetrics();
        if (metrics == null)
            return 0;
        return metrics.heightPixels;
    }

    public static Double getScreenSizeInInches(Context aContext) {
        Double inches = 0d;
        try {
            DisplayMetrics metrics = aContext.getResources().getDisplayMetrics();
            if (metrics == null)
                return inches;
            Double density = metrics.density * 160d;
            Double x = Math.pow(metrics.widthPixels / density, 2);
            Double y = Math.pow(metrics.heightPixels / density, 2);
            inches = Math.sqrt(x + y);
        } catch (Exception e) {
            mLogger.log(Level.SEVERE, e.getMessage(), e);
        }
        mLogger.info(String.format("Inches: %.2f", inches));
        return inches;
    }

    @SuppressWarnings("deprecation")
    public static Point getSize(Context aContext) {
        WindowManager wm = (WindowManager) aContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        int width = display.getWidth();
        int height = display.getHeight();
        size.set(width, height);
        mLogger.info(String.format("Screen size: %dx%d", width, height));
        return size;
    }

    public static String getAndroidVersion() {
        String androidVersion = Build.VERSION.RELEASE;
        mLogger.info(String.format("Android version: %s", androidVersion));
        return androidVersion;
    }

    public static String getTotalRAM() {
        RandomAccessFile reader = null;
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        String lastValue = "";
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            String load = reader.readLine();

            // Get the Number value from the string
            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(load);
            String value = "";
            while (m.find()) {
                value = m.group(1);
                // System.out.println("Ram : " + value);
            }

            Double totRam = Double.parseDouble(value);
            // totRam = totRam / 1024;

            Double mb = totRam / 1024.0;
            Double gb = totRam / 1048576.0;
            Double tb = totRam / 1073741824.0;

            if (tb > 1) {
                lastValue = twoDecimalForm.format(tb).concat(" TB");
            } else if (gb > 1) {
                lastValue = twoDecimalForm.format(gb).concat(" GB");
            } else if (mb > 1) {
                lastValue = twoDecimalForm.format(mb).concat(" MB");
            } else {
                lastValue = twoDecimalForm.format(totRam).concat(" KB");
            }
        } catch (Exception e) {
            mLogger.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    mLogger.log(Level.SEVERE, e.getMessage(), e);
                }
        }
        mLogger.info(String.format("Total ramm: %s", lastValue));
        return lastValue;
    }

    public static String getAndroidModel() {
        String androidModel = Build.MODEL;
        mLogger.info(String.format("Android model: %s", androidModel));
        return androidModel;
    }

    public static String getAndroidManufacturer() {
        String androidManufacturer = Build.MANUFACTURER;
        mLogger.info(String.format("Android manufacturer: %s", androidManufacturer));
        return androidManufacturer;
    }

    public static Float getPixelDensity(Context aContext) {
        Float density = aContext.getResources().getDisplayMetrics().density;
        mLogger.info(String.format("Density: %f", density));
        return density;
    }
}