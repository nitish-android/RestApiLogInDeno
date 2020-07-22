package com.tanzible.restapilogindeno;

import android.content.Context;
import android.content.SharedPreferences;

class AppPreferance {

    public static final String SHARED_PREFERENCE_NAME = "com.m.comfycars";
    public static final String MOBILENO = "mobile_no";
    public static final String USER_ID = "user_id";





    public static String getPhoneNo(Context context, String Key) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(MOBILENO, "");
    }

    public static void setPhonno(Context context, String value, String Key) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MOBILENO, value);
        editor.commit();
    }

    public static String getUserId(Context context, String userId) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return preferences.getString(USER_ID, "");
    }

    public static void setUserId(Context context, String value, String userId) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_ID, value);
        editor.commit();
    }


}
