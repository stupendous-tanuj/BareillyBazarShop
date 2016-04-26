package com.app.bareillybazarshop.utils;

import android.util.Log;

/**
 * Created by tsingh on 27/1/15.
 */
public class Logger {


    private static boolean isProd = false;

    public static void ERROR(String tag, String msg, Throwable th) {
        if(isProd){
            return;
        }
        Log.e(tag, msg, th);
    }

    public static void WARN(String tag, String msg, Throwable th) {
        if(isProd){
            return;
        }
        Log.w(tag, msg, th);
    }

    public static void INFO(String tag, String msg, Throwable th) {
        if(isProd){
            return;
        }
        Log.i(tag, msg, th);
    }

    public static void INFO(String tag, String msg) {
        if(isProd){
            return;
        }
        Log.i(tag, msg, null);
    }

    public static void DEBUG(String tag, String msg, Throwable th) {
        if(isProd){
            return;
        }
        Log.d(tag, msg, th);
    }
}
