package com.regfacu.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Facundo Mengoni.
 * @author mengonifacundo@gmail.com
 */
public class Converters {
    private static final String TAG = Converters.class.getSimpleName();

    public final static long ONE_SECOND = 1000;
    public final static long SECONDS_IN_MINUTE = 60;
    public final static long ONE_MINUTE = ONE_SECOND * SECONDS_IN_MINUTE;
    public final static long MINUTES_IN_HOUR = 60;
    public final static long ONE_HOUR = ONE_MINUTE * MINUTES_IN_HOUR;
    public final static long HOURS_IN_DAY = 24;
    public final static long ONE_DAY = ONE_HOUR * HOURS_IN_DAY;
    public final static long DAYS_IN_WEAK = 7;
    public final static long ONE_WEEK = ONE_DAY * DAYS_IN_WEAK;
    public final static long DAYS_IN_MONTHS = 30;
    public final static long ONE_MONTH = ONE_DAY * DAYS_IN_MONTHS;
    public final static long DAYS_IN_YEARS = 365;
    public final static long ONE_YEAR = ONE_DAY * DAYS_IN_YEARS;
    public static Logger mLogger;

    static {
        mLogger = Logger.getLogger(TAG);
        mLogger.setLevel(Level.OFF);
    }

    public static String formatCounter(Integer value) {
        // ToDo Analyze if it is need
        if (value < 1000) {
            return value.toString();
        } else if (value < 1000000) {
            int a = (int) (value / 1000);
            if (a >= 100)
                return String.format("+%.1f K", ((Integer) (a / 100)).floatValue());
            return String.format("+%d K", a);
        }
        int b = (int) (value / 1000000);
        if (b >= 100)
            return String.format("+%.1f M", ((Integer) (b / 100)).floatValue());
        return String.format("+%d M", b);
    }

    public static String millisToLongYMWDHMS(long duration) {
        // ToDo Analyze if it is need
        String res = "";
        long temp = 0;
        if (duration >= ONE_SECOND) {
            temp = duration / ONE_YEAR;
            if (temp > 0) {
                duration -= temp * ONE_YEAR;
                res += temp;
                if (temp > 1) {
                    res += " a\u00F1os";
                } else {
                    res += " a\u00F1o";
                }
                return res;
            }

            temp = duration / ONE_MONTH;
            if (temp > 0) {
                duration -= temp * ONE_MONTH;
                res += temp;
                res += " mes";
                return res;
            }

            temp = duration / ONE_WEEK;
            if (temp > 0) {
                duration -= temp * ONE_WEEK;
                res += temp;
                res += " sem";
                return res;
            }

            temp = duration / ONE_DAY;
            if (temp > 0) {
                duration -= temp * ONE_DAY;
                res += temp;
                if (temp > 1) {
                    res += " dias";
                } else {
                    res += " d\u00EDa";
                }
                return res;
            }

            temp = duration / ONE_HOUR;
            if (temp > 0) {
                duration -= temp * ONE_HOUR;
                res += temp;
                if (temp > 1) {
                    res += " horas";
                } else {
                    res += " hora";
                }
                return res;
            }

            temp = duration / ONE_MINUTE;
            if (temp > 0) {
                duration -= temp * ONE_MINUTE;
                res += temp;
                res += " min";
                return res;
            }

            temp = duration / ONE_SECOND;
            if (temp > 0) {
                res += temp;
                res += " seg";
                return res;
            }
            return res.toString();
        } else {
            return "0 seg";
        }
    }

    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is, "UTF-8").useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static TimeZone getGMTTimeZone() {
        return TimeZone.getTimeZone("GMT");
    }

    /**
     * Convierte un string con un formato especifico a un objeto Date
     * Examples:
     * "EEE MMM dd HH:mm:ss zzzz yyyy" -> "Fri Jul 30 16:19:36 GMT+02:00 2021"
     * "yyyy-MM-dd HH:mm:ss" -> "2013-04-15 16:45:52"
     * "yyyy-MM-dd" -> "2013-04-15 16:45:52"
     */
    public static Date stringToDate(String date, String format) {
        return stringToDate(date, format, getGMTTimeZone());
    }

    /**
     * Convierte un string con un formato especifico a un objeto Date
     */
    public static Date stringToDate(String date, String format, TimeZone timeZone) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            formatter.setTimeZone(timeZone);
            return formatter.parse(date);
        } catch (Exception e) {
            mLogger.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    public static String dateToString(Date date, String format) {
        return dateToString(date, format, getGMTTimeZone());
    }

    public static String dateToString(Date date, String format, TimeZone timeZone) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            formatter.setTimeZone(timeZone);
            return formatter.format(date);
        } catch (Exception e) {
            mLogger.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    public static boolean isYesterday(Date aDate) {
        Calendar c1 = Calendar.getInstance(); // today
        c1.add(Calendar.DAY_OF_YEAR, -1); // yesterday

        Calendar c2 = Calendar.getInstance();
        c2.setTime(aDate); // your date

        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR));
    }

    public static boolean isToday(Date aDate) {
        Calendar c1 = Calendar.getInstance(); // today

        Calendar c2 = Calendar.getInstance();
        c2.setTime(aDate); // your date

        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR));
    }
}