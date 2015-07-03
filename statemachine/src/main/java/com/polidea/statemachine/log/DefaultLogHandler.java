package com.polidea.statemachine.log;

public class DefaultLogHandler implements LogHandler{

    @Override
    public void d(String tag, String message) {
        android.util.Log.d(tag, message);
    }

    @Override
    public void i(String tag, String message) {
        android.util.Log.i(tag, message);
    }

    @Override
    public void v(String tag, String message) {
        android.util.Log.v(tag, message);
    }

    @Override
    public void w(String tag, String message) {
        android.util.Log.w(tag, message);
    }

    @Override
    public void e(String tag, String message) {
        android.util.Log.e(tag, message);
    }

    @Override
    public void e(String tag, String message, Throwable e) {
        android.util.Log.e(tag, message, e);
    }
}
