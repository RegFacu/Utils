# Utils

Personal library of common code used in general applications.

```java
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
        Util.getDeviceData(this).toString();
        Log.i(TAG, "-----  -----");
    }
}
```

**Output:**

```
Info/MainActivity﹕ ----- Converters -----
Info/MainActivity﹕ Date: Wed Aug 05 17:38:12 GMT-03:00 2015
Info/MainActivity﹕ -----  -----
Info/MainActivity﹕ ----- ConnectionTools -----
Info/ConnectionTools﹕ Is network enable: true
Info/ConnectionTools﹕ Is gps enable: true
Info/ConnectionTools﹕ Is geolocation active: true
Info/MainActivity﹕ -----  -----
Info/MainActivity﹕ ----- HardwareTools -----
Info/HardwareTools﹕ Has camera: true
Info/MainActivity﹕ -----  -----
Info/MainActivity﹕ ----- Utils -----
Info/HardwareTools﹕ Has GPS: true
Info/HardwareTools﹕ Has wifi: true
Info/HardwareTools﹕ Has Bluetooth: true
Info/HardwareTools﹕ Has BluetoothLE: true
Info/HardwareTools﹕ IMEI: XXXXXXXXXXXXXXX
Info/HardwareTools﹕ Mac address: XX:XX:XX:XX:XX:XX
Info/HardwareTools﹕ Heap: 67108864
Info/HardwareTools﹕ Inches: 4,59
Info/HardwareTools﹕ Screen size: 720x1280
Info/HardwareTools﹕ Android version: 4.3
Info/HardwareTools﹕ Total ramm: 830,93 MB
Info/HardwareTools﹕ Android model: GT-I9300
Info/HardwareTools﹕ Android manufacturer: samsung
Info/HardwareTools﹕ Density: 2,000000
Info/MainActivity﹕ -----  -----
```