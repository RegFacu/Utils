package com.regfacu.utils.entities;

import android.content.Context;
import android.graphics.Point;

import com.regfacu.utils.Util;
import com.regfacu.utils.tools.HardwareTools;

/**
 * @author Facundo Mengoni.
 * @author mengonifacundo@gmail.com
 */
public class DeviceWrapper {
    private static final String TAG = DeviceWrapper.class.getSimpleName();

    public String hasGPS;
    public String hasWiFi;
    public String hasBluetooth;
    public String hasBluetoothLE;
    public String imei;
    public String wifiMacAddres;
    public String heap;
    public String screenSize;
    public String screenSizeX;
    public String screenSizeY;
    public String androidVersion;
    public String ram;
    public String androidModel;
    public String androidManufacturer;
    public String pixelDensity;
    public String registrationId;

    public void getData(Context aContext) {
        this.hasGPS = HardwareTools.hasGps(aContext).toString();
        this.hasWiFi = HardwareTools.hasWifi(aContext).toString();
        this.hasBluetooth = HardwareTools.hasBluetooth(aContext).toString();
        if (Util.hasJellyBeanMR2())
            this.hasBluetoothLE = HardwareTools.hasBluetoothLE(aContext).toString();
        else
            this.hasBluetoothLE = "Not supported";
        this.imei = HardwareTools.getImei(aContext);
        this.wifiMacAddres = HardwareTools.getWifiMacAddress(aContext);
        this.heap = HardwareTools.getHeap().toString();
        this.screenSize = HardwareTools.getScreenSizeInInches(aContext).toString();
        Point size = HardwareTools.getSize(aContext);
        this.screenSizeX = String.valueOf(size.x);
        this.screenSizeY = String.valueOf(size.y);
        this.androidVersion = HardwareTools.getAndroidVersion();
        this.ram = HardwareTools.getTotalRAM();
        this.androidModel = HardwareTools.getAndroidModel();
        this.androidManufacturer = HardwareTools.getAndroidManufacturer();
        this.pixelDensity = HardwareTools.getPixelDensity(aContext).toString();
    }
}