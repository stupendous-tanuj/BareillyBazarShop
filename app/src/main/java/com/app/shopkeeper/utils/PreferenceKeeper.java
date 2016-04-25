package com.app.shopkeeper.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.app.shopkeeper.constant.AppConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreferenceKeeper {

    private SharedPreferences prefs;
    private static PreferenceKeeper keeper;
    private static Context context;


    public static PreferenceKeeper getInstance() {
        if (keeper == null) {
            keeper = new PreferenceKeeper(context);
        }
        return keeper;
    }

    private PreferenceKeeper(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setContext(Context context1) {
        context = context1;
    }


    public void setDeviceInfo(String info) {
        prefs.edit().putString("device_info", info).commit();
    }

    public String getDeviceInfo() {
        return prefs.getString("device_info", "");
    }

    public boolean getIsLogin() {
        return prefs.getBoolean(AppConstant.PreferenceKeeperNames.LOGIN, false);
    }

    public void setIsLogin(boolean isLogin) {
        prefs.edit().putBoolean(AppConstant.PreferenceKeeperNames.LOGIN, isLogin).commit();
    }

    public String getflatNumber() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.FLAT_NUMBER, "");
    }

    public void setflatNumber(String flatNumber) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.FLAT_NUMBER, flatNumber).commit();
    }


    public String getArea() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.AREA, "");
    }

    public void setArea(String flatNumber) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.AREA, flatNumber).commit();
    }


    public String getLocality() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.LOCALITY, "");
    }

    public void setLocality(String flatNumber) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.LOCALITY, flatNumber).commit();
    }

    public String getCity() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.CITY, "");
    }

    public void setCity(String flatNumber) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.CITY, flatNumber).commit();
    }

    public String getState() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.STATE, "");
    }

    public void setState(String flatNumber) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.STATE, flatNumber).commit();
    }

    public String getPincode() {
        return prefs.getString(AppConstant.PreferenceKeeperNames.PINCODE, "");
    }

    public void setPincode(String flatNumber) {
        prefs.edit().putString(AppConstant.PreferenceKeeperNames.PINCODE, flatNumber).commit();
    }

    public void clearData() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
