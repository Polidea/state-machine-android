package com.polidea.statemachine.log;

import android.support.annotation.NonNull;

public class Logger {
    private static final String DEFAULT_TAG = "StateMachine";

    static LogHandler logHandler = new DefaultLogHandler();

    static boolean enabled = false;

    public static void setLogHandler(@NonNull LogHandler logHandler) {
        Logger.logHandler = logHandler;
    }

    public static LogHandler getLogHandler() {
        return logHandler;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        Logger.enabled = enabled;
    }

    public static void d(String message) {
        d(DEFAULT_TAG, message);
    }

    public static void d(String tag, String message) {
        if (enabled) {
            logHandler.d(tag, message);
        }
    }

    public static void i(String message) {
        i(DEFAULT_TAG, message);
    }

    public static void i(String tag, String message) {
        if (enabled) {
            logHandler.i(tag, message);
        }
    }

    public static void v(String message) {
        v(DEFAULT_TAG, message);
    }

    public static void v(String tag, String message) {
        if (enabled) {
            logHandler.v(tag, message);
        }
    }

    public static void w(String message) {
        w(DEFAULT_TAG, message);
    }

    public static void w(String tag, String message) {
        if (enabled) {
            logHandler.w(tag, message);
        }
    }

    public static void e(String message) {
        e(DEFAULT_TAG, message);
    }

    public static void e(String tag, String message) {
        if (enabled) {
            logHandler.e(tag, message);
        }
    }

    public static void e(Throwable e) {
        e(e.getMessage(), e);
    }

    public static void e(String message, Throwable e) {
        e(DEFAULT_TAG, message, e);
    }

    public static void e(String tag, String message, Throwable e) {
        if (enabled) {
            logHandler.e(tag, message, e);
        }
    }
}
