package com.regfacu.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.inputmethod.InputMethodManager;

import com.regfacu.utils.entities.DeviceWrapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Facundo Mengoni.
 * @author mengonifacundo@gmail.com
 */
@SuppressWarnings("unused")
public class Util {
    private final static String TAG = Util.class.toString();

    public static Logger mLogger;

    static {
        mLogger = Logger.getLogger(TAG);
        mLogger.setLevel(Level.OFF);
    }

    private static Random randGen = new Random();
    private static final char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
    private static final char[] hexChar = ("0123456789ABCDEF").toCharArray();

    /**
     * According to this page this is the best method of performance between 3
     *
     * @param string String to analize
     * @return False if empty or not is a number, True in otherwise
     * @see <a
     * href="http://jdevelopment.nl/efficient-determine-string-number/">Page</a>
     */
    public static boolean isNumber(String string) {
        if (string == null || TextUtils.isEmpty(string)) {
            return false;
        }
        int i = 0;
        if (string.charAt(0) == '-') {
            if (string.length() > 1) {
                i++;
            } else {
                return false;
            }
        }
        for (; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String getRealPathFromURI(Context aContext, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = aContext.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            if (contentUri == null) {
                return null;
            }
            return contentUri.getPath();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public static String fileReader(String aFile) {
        File file = new File(aFile);
        return fileReader(file);
    }

    public static Boolean saveBitmap(Bitmap bitmap, File file) {
        Boolean result = Boolean.FALSE;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            result = Boolean.TRUE;
        } catch (Exception e) {
            mLogger.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Throwable ignore) {
            }
        }
        return result;
    }

    public static String fileReader(File aFile) {
        try {
            FileReader file = new FileReader(aFile);
            BufferedReader in = new BufferedReader(file);
            String json = "";
            String thisLine;
            while ((thisLine = in.readLine()) != null) {
                json += thisLine;
            }
            in.close();
            return json;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void writeInFile(String aFile, String aText) throws IOException {
        File file = new File(aFile);
        writeInFile(file, aText);
    }

    public static void writeInFile(File aFile, String aText) throws IOException {
        if (aFile.getParentFile() != null)
            aFile.getParentFile().mkdirs();
        FileWriter file = new FileWriter(aFile, true);
        BufferedWriter out = new BufferedWriter(file);
        out.write(aText);
        out.close();
    }

    public static void copy(File aSource, File aTarget) throws IOException {
        InputStream in = new FileInputStream(aSource);
        OutputStream out = new FileOutputStream(aTarget);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public static PackageInfo getPackageInfo(Context context) {
        return getPackageInfo(context, 0);
    }

    public static PackageInfo getPackageInfo(Context context, int flags) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), flags);
        } catch (NameNotFoundException e) {
            mLogger.log(Level.SEVERE, e.getMessage(), e);
        }
        return pInfo;
    }

    //region Version codes checks
    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasIceCreamSandwitch() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    public static boolean hasJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean hasLollipopMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }
    //endregion

    /**
     * Funcion que elimina acentos y caracteres especiales de una cadena de
     * texto.
     *
     * @return cadena de texto limpia de acentos y caracteres especiales.
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static String removeCharactersSpecials(String text, String pattern) {
        // Normalizamos en la forma NFD (Canonical decomposition)
        String newText = Normalizer.normalize(text, Normalizer.Form.NFKD);
        if (pattern != null) {
            /*
             * Reemplazamos algunos caracteres segun una expresion regular de
			 * Bloque Unicode
			 */
            newText = newText.replaceAll(pattern, "");
        }
        return newText;
    }

    public static void hideKeyboard(Context context, IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(windowToken, 0);
    }

    public static void waitForDebugger() {
        if (BuildConfig.DEBUG) {
            android.os.Debug.waitForDebugger();
        }
    }

    /**
     * Returns a random String of numbers and letters (lower and upper case) of
     * the specified length. The method uses the Random class that is built-in
     * to Java which is suitable for low to medium grade security uses. This
     * means that the output is only pseudo random, i.e., each number is
     * mathematically generated so is not truly random.
     * <p/>
     * <p/>
     * The specified length must be at least one. If not, the method will return
     * null.
     *
     * @param lenght the desired length of the random String to return.
     * @return a random String of numbers and letters of the specified length.
     */
    public static String randomString(int lenght, boolean allowSpaces) {
        if (lenght < 1) {
            return "";
        }
        // Create a char buffer to put random letters and numbers in.
        char[] randBuffer = new char[lenght];

        char[] chars;
        if (allowSpaces) {
            chars = (Arrays.toString(numbersAndLetters) + " ").toCharArray();
        } else {
            chars = numbersAndLetters.clone();
        }

        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = chars[randGen.nextInt(chars.length)];
        }
        return new String(randBuffer);
    }

    public static Float randomFloat(Float max) {
        return randGen.nextFloat() * max;
    }

    /**
     * If we are in debug mode, launch a exepcion to detect and control. In
     * production only ends the activity.
     *
     * @param activity The activity will be completed
     * @param msg      The message to be launched in the exepcion
     */
    public static void invalidParameterException(Activity activity, String msg) {
        if (activity != null) {
            if (!BuildConfig.DEBUG) {
                activity.finish();
            } else {
                throw new InvalidParameterException(msg);
            }
        }
    }

    public static void nullPointerException(String clazz) {
        if (BuildConfig.DEBUG) {
            throw new NullPointerException("Activity of " + clazz + " is null");
        }
    }


    public static Bitmap circleBitmap(Bitmap source, int width, int height, Boolean recycle) {
        final Bitmap output;
        if (source != null) {
            // Genero una copia modificable
            Bitmap sourceBitmap = source.copy(source.getConfig(), true);
            // Tomo las dimensiones de la imagen
            Integer imageWidth = sourceBitmap.getWidth();
            Integer imageHeight = sourceBitmap.getHeight();
            // Calculo la diferencia entre la imagen y lo requerido
            Integer imageWidthDiference = imageWidth - width;
            Integer imageHeightDiference = imageHeight - height;

            // Creo el bitmap a redibujar.
            output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(output);

            // Creo el paint.
            final int color = Color.BLACK;
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.GREEN);

            // Genero un rectangulo del tamaño de salida deseado.
            RectF rectF = new RectF(0, 0, width, height);
            canvas.drawOval(rectF, paint);

            // Dibujo el bitmap desplazado y escalado en el area definida anteriormente
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            Matrix matrix = new Matrix();
            matrix.postTranslate(-imageWidthDiference / 2, -imageHeightDiference / 2);
            float sx = width / imageWidth.floatValue();
            float sy = height / imageHeight.floatValue();
            float s = Math.max(sx, sy);
            float px = width / 2;
            float py = height / 2;
            matrix.postScale(s, s, px, py);
            canvas.drawBitmap(sourceBitmap, matrix, paint);

            // Si debo reciclar la fuente, limpio los bitmaps
            if (recycle) {
                if (output != source) {
                    source.recycle();
                }
            }
            if (output != sourceBitmap) {
                sourceBitmap.recycle();
            }
        } else {
            output = null;
        }
        return output;
    }

    public static Bitmap blurBitmap(Bitmap aBitmap, int aWidth, int aHeight, boolean isRecycleEnabled) {
        return aBitmap;
    }

    public static Bitmap roundBitmap(Bitmap source, int width, int height, int roundTopLeft, int roundTopRight, int roundBottomRight, int roundBottomLeft, Boolean recycle) {
        final Bitmap output;
        if (source != null) {
            // Genero una copia modificable
            Bitmap sourceBitmap = source.copy(source.getConfig(), true);
            // Tomo las dimensiones de la imagen
            Integer imageWidth = sourceBitmap.getWidth();
            Integer imageHeight = sourceBitmap.getHeight();
            // Calculo la diferencia entre la imagen y lo requerido
            Integer imageWidthDiference = imageWidth - width;
            Integer imageHeightDiference = imageHeight - height;

            // Creo el bitmap a redibujar.
            output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(output);

            // Creo el paint.
            final int color = Color.BLACK;
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.GREEN);

            // Hago el recorte
            Path path = new Path();
            path.setFillType(Path.FillType.EVEN_ODD);
            path.moveTo(roundTopLeft, 0);
            path.rLineTo(width - roundTopLeft - roundTopRight, 0);
            int angle = -90;
            if (roundTopRight > 0) {
                path.arcTo(new RectF(width - roundTopRight, 0, width, roundTopRight), angle, 90);
            }
            path.rLineTo(0, height - roundTopRight - roundBottomRight);
            angle += 90;
            if (roundBottomRight > 0) {
                path.arcTo(new RectF(width - roundBottomRight, height - roundBottomRight, width, height), angle, 90);
            }
            angle += 90;
            path.rLineTo(-(width - roundBottomLeft - roundBottomRight), 0);
            if (roundBottomLeft > 0) {
                path.arcTo(new RectF(0, height - roundBottomLeft, roundBottomLeft, height), angle, 90);
            }
            angle += 90;
            path.rLineTo(0, -(height - roundTopLeft - roundBottomLeft));
            if (roundTopLeft > 0) {
                path.arcTo(new RectF(0, 0, roundTopLeft, roundTopLeft), angle, 90);
            }
            path.close();
            canvas.drawPath(path, paint);

            // Dibujo el bitmap desplazado y escalado en el area definida anteriormente
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            Matrix matrix = new Matrix();
            matrix.postTranslate(-imageWidthDiference / 2, -imageHeightDiference / 2);
            float sx = width / imageWidth.floatValue();
            float sy = height / imageHeight.floatValue();
            float s = Math.max(sx, sy);
            float px = width / 2;
            float py = height / 2;
            matrix.postScale(s, s, px, py);
            canvas.drawBitmap(sourceBitmap, matrix, paint);

            // Si debo reciclar la fuente, limpio los bitmaps
            if (recycle) {
                if (output != source) {
                    source.recycle();
                }
            }
            if (output != sourceBitmap) {
                sourceBitmap.recycle();
            }
        } else {
            output = null;
        }
        return output;
    }

    public void getFacebookKeyHashes(Context aContext) {
        try {
            PackageInfo info = getPackageInfo(aContext, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                mLogger.fine("FacebookKeyHashes: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NoSuchAlgorithmException e) {
            mLogger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static DeviceWrapper getDeviceData(Context aContext) {
        DeviceWrapper deviceWrapper = new DeviceWrapper();
        deviceWrapper.getData(aContext);
        return deviceWrapper;
    }

    public static Boolean isThreadUI() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public static void notInThreadUI() {
        if (BuildConfig.DEBUG)
            if (isThreadUI()) {
                try {
                    throw new RuntimeException("NO SE DEBERIA EJECUTAR EN EL THREAD UI");
                } catch (Exception e) {
                    mLogger.log(Level.SEVERE, e.getMessage(), e);
                }
            }
    }

    public static String utf8ToUnicode(String s) {
        return s; //TODO1 ESCAPE EMOTICONS
/*        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c >> 7) > 0) {
                sb.append("\\u");
                sb.append(hexChar[(c >> 12) & 0xF]); // append the hex character for the left-most 4-bits
                sb.append(hexChar[(c >> 8) & 0xF]);  // hex for the second group of 4-bits from the left
                sb.append(hexChar[(c >> 4) & 0xF]);  // hex for the third group
                sb.append(hexChar[c & 0xF]);         // hex for the last group, e.g., the right most 4-bits
            }
            else {
                sb.append(c);
            }
        }
        return sb.toString();*/
    }

    public static String unicodeToUtf8(String text) {
        return text; //TODO1 ESCAPE EMOTICONS
        /*return StringEscapeUtils.unescapeJava(text);*/
    }

    public static void setBadge(Context aContext, Integer aValue) {
        // seteamos cant. de notificaciones en el Launcher
        // solo en HTC, SAMSUNG o SONY.
        String manufacturer = Build.MANUFACTURER;
        Intent intent = new Intent();
//        if (manufacturer.toLowerCase().contains("htc")) {
//          SIN TESTEAR, YA QUE NO DISPONEMOS DE UN HTC CON ESTA OPCION
//            try {
//                Intent localIntent1 = new Intent("com.htc.launcher.action.UPDATE_SHORTCUT");
//                localIntent1.putExtra("packagename", "com.arzion.lunula");
//                localIntent1.putExtra("count", aValue);
//                aContext.sendBroadcast(localIntent1);
//                Intent localIntent2 = new Intent("com.htc.launcher.action.SET_NOTIFICATION");
//                ComponentName localComponentName = new ComponentName(aContext, "com.arzion.lunula.Splash");
//                localIntent2.putExtra("com.htc.launcher.extra.COMPONENT", localComponentName.flattenToShortString());
//                localIntent2.putExtra("com.htc.launcher.extra.COUNT", aValue);
//                aContext.sendBroadcast(localIntent2);
//            } catch (Exception e) {
//                mLogger.log(Level.SEVERE, "HTC: " + e.getMessage(), e);
//            }
//        } else
        if (manufacturer.toLowerCase().contains("samsung")) {
            String launcherClassName = getLauncherClassName(aContext);
            if (launcherClassName == null) {
                return;
            }
            Intent intent1 = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
            intent1.putExtra("badge_count", aValue); // <- integer
            intent1.putExtra("badge_count_package_name", aContext.getPackageName());
            intent1.putExtra("badge_count_class_name", launcherClassName);
            aContext.sendBroadcast(intent1);
        } else if (manufacturer.toLowerCase().contains("sony")) {
            try {
                intent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
                intent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", getLauncherClassName(aContext));
                intent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", true);
                intent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", aValue); // <- string
                intent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", aContext.getPackageName());
                aContext.sendBroadcast(intent);
            } catch (Exception e) {
                mLogger.log(Level.SEVERE, "Sony: " + e.getMessage(), e);
            }
        }
    }

    private static String getLauncherClassName(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                return resolveInfo.activityInfo.name;
            }
        }
        return null;
    }

    public static Integer getAppVersion(Context aContext) {
        PackageInfo pInfo = getPackageInfo(aContext);
        if (pInfo == null)
            return 0;
        else
            return pInfo.versionCode;
    }
}