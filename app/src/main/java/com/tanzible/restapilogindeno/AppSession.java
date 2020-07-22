package com.tanzible.restapilogindeno;

import android.content.Context;
import android.content.SharedPreferences;

class AppSession {

    private static final String SESSION_NAME = "com.m.comfycars";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor prefsEditor;

    public AppSession(Context context) {
        mSharedPreferences = context.getSharedPreferences(SESSION_NAME,
                Context.MODE_PRIVATE);
        prefsEditor = mSharedPreferences.edit();
    }

    public void setLogin(boolean Login) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putBoolean("Login", Login);
        prefsEditor.commit();
    }

    public boolean isLogin() {
        return mSharedPreferences.getBoolean("Login", false);
    }
}
