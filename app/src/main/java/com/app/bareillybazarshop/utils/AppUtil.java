package com.app.bareillybazarshop.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

import com.app.bareillybazarshop.constant.AppConstant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;


public class AppUtil {


    public static <T> T parseJson(String json, Class<T> tClass) {
        return new Gson().fromJson(json, tClass);
    }

    public static <T> T parseJson(JsonElement result, Class<T> tClass) {
        return new Gson().fromJson(result, tClass);
    }

    public static String getJson(Object profile) {
        return new Gson().toJson(profile);
    }

    public static JsonObject parseJson(String response) {
        JsonObject jo = null;
        JsonElement e = null;
        return new JsonParser().parse(response).getAsJsonObject();
    }

    public static String getDeviceId(Context activity) {
        String android_id = null;
        if (activity != null) {
            android_id = Settings.Secure.getString(activity.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
        return android_id;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean checkPlayServices(Context context) {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode,
                        (Activity) context, AppConstant.PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                ((Activity) context).finish();
            }
            return false;
        }
        return true;
    }

    public static long getMillisFromDate(String dateTimeString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        long startDatemillis = 0;
        try {
            date = formatter.parse(dateTimeString);
            startDatemillis = date.getTime();
        } catch (ParseException e) {
            Log.i("APPUTILS", "Date not formate");
        }
        return startDatemillis;
    }

    public static int getTotlTime(String time) {
        StringTokenizer tokenizer = new StringTokenizer(time);
        int h = Integer.parseInt(tokenizer.nextToken(":"));
        int m = Integer.parseInt(tokenizer.nextToken(":"));
        return h + m;
    }

}