package cz.muni.fi.pv256.movio.uco325253;


import android.util.Log;

/**
 * This static class serves as the logging wrapper over default Android logger.
 * <p/>
 * Created by xosvald on 07.11.2015.
 */
public class L {

    /**
     * Default constructor. Private in order to ensure the static usage.
     */
    private L() {
    }

    /**
     * Logs a debug message.
     *
     * @param tag       log tag
     * @param message   log message
     * @param throwable log throwable
     */
    @SuppressWarnings("unused")
    public static void d(String tag, String message, Throwable throwable) {
        if (BuildConfig.logging) {
            Log.d(tag, message, throwable);
        }
    }

    /**
     * Logs a debug message.
     *
     * @param tag     log tag
     * @param message log message
     */
    @SuppressWarnings("unused")
    public static void d(String tag, String message) {
        if (BuildConfig.logging) {
            Log.d(tag, message);
        }
    }

    /**
     * Logs an info message.
     *
     * @param tag       log tag
     * @param message   log message
     * @param throwable log throwable
     */
    @SuppressWarnings("unused")
    public static void i(String tag, String message, Throwable throwable) {
        if (BuildConfig.logging) {
            Log.i(tag, message, throwable);
        }
    }

    /**
     * Logs an info message.
     *
     * @param tag     log tag
     * @param message log message
     */
    @SuppressWarnings("unused")
    public static void i(String tag, String message) {
        if (BuildConfig.logging) {
            Log.i(tag, message);
        }
    }

    /**
     * Logs an error message.
     *
     * @param tag       log tag
     * @param message   log message
     * @param throwable log throwable
     */
    @SuppressWarnings("unused")
    public static void e(String tag, String message, Throwable throwable) {
        if (BuildConfig.logging) {
            Log.e(tag, message, throwable);
        }
    }

    /**
     * Logs an error message.
     *
     * @param tag     log tag
     * @param message log message
     */
    @SuppressWarnings("unused")
    public static void e(String tag, String message) {
        if (BuildConfig.logging) {
            Log.e(tag, message);
        }
    }

    /**
     * Logs a verbose message.
     *
     * @param tag       log tag
     * @param message   log message
     * @param throwable log throwable
     */
    @SuppressWarnings("unused")
    public static void v(String tag, String message, Throwable throwable) {
        if (BuildConfig.logging) {
            Log.v(tag, message, throwable);
        }
    }

    /**
     * Logs a verbose message.
     *
     * @param tag     log tag
     * @param message log message
     */
    @SuppressWarnings("unused")
    public static void v(String tag, String message) {
        if (BuildConfig.logging) {
            Log.v(tag, message);
        }
    }

    /**
     * Logs a warning message.
     *
     * @param tag       log tag
     * @param message   log message
     * @param throwable log throwable
     */
    @SuppressWarnings("unused")
    public static void w(String tag, String message, Throwable throwable) {
        if (BuildConfig.logging) {
            Log.w(tag, message, throwable);
        }
    }

    /**
     * Logs a warning message.
     *
     * @param tag     log tag
     * @param message log message
     */
    @SuppressWarnings("unused")
    public static void w(String tag, String message) {
        if (BuildConfig.logging) {
            Log.w(tag, message);
        }
    }

    /**
     * Logs a WTF message.
     *
     * @param tag       log tag
     * @param message   log message
     * @param throwable log throwable
     */
    @SuppressWarnings("unused")
    public static void wtf(String tag, String message, Throwable throwable) {
        if (BuildConfig.logging) {
            Log.wtf(tag, message, throwable);
        }
    }

    /**
     * Logs a WTF message.
     *
     * @param tag     log tag
     * @param message log message
     */
    @SuppressWarnings("unused")
    public static void wtf(String tag, String message) {
        if (BuildConfig.logging) {
            Log.wtf(tag, message);
        }
    }

}
