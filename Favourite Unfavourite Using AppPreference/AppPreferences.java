package com.div.punjabivideostatus;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private static String PREFS_NAME;
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    public AppPreferences(Context context) {
        PREFS_NAME = context.getResources().getString(R.string.app_name);
        this.preferences = context.getSharedPreferences(PREFS_NAME, 0);
        editor = preferences.edit();
    }

    public void setStranger(String KEY,boolean b) {
        editor.putBoolean(KEY, b);
        editor.commit();
    }

    public boolean getStranger(String KEY) {
        return preferences.getBoolean(KEY, false);
    }
}