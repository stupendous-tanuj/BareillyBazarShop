package com.app.bareillybazarshop.utils;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validation {

    //    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        if (isNotNullOrEmpty(password)) {
            int length = password.length();
            if (length > 5)
                return true;
        }
        return false;
    }

    public static boolean isValidName(String name) {
        if (isNotNullOrEmpty(name) && name.matches("[a-zA-Z\\s']+")) {
            Log.i("validator", "is valid name");
            return true;
        }
        return false;
    }

    public static boolean isNotNullOrEmpty(String string) {
        return (string != null && string.length() > 0);
    }


}
