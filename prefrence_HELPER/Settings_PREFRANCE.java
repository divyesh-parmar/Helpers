package com.div.mysolution.xyz;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Settings_PREFRANCE {
    public  String[] quality_TYPES = {"HD", "Medium", "Low"};
    Context mContext;

    //Todo:HELPER INITIALIZATION
    public Settings_PREFRANCE(Context mContext) {
        this.mContext = mContext;
    }

    //Todo:SAVE FLOAT IN SP
    public boolean save_FLOAT(String key, float value) {
        SharedPreferences settings = mContext.getSharedPreferences("sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    //Todo:GET FLOAT FROM SP
    public float get_FLOAT(String key, float defaultval) {
        SharedPreferences settings = mContext.getSharedPreferences("sp", Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultval);
    }

    //Todo:SAVE STRING IN SP
    public boolean save_STRING(String key, String value) {
        SharedPreferences settings = mContext.getSharedPreferences("sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    //Todo:GET STRING FROM SP
    public String get_STRING(String key, String defaultval) {
        SharedPreferences settings = mContext.getSharedPreferences("sp", Context.MODE_PRIVATE);
        return settings.getString(key, defaultval);
    }

    //Todo:SAVE INT IN SP
    public boolean save_INT(String key, int value) {
        SharedPreferences settings = mContext.getSharedPreferences("sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    //Todo:GET INT FROM SP
    public int get_INT(String key, int defaultval) {
        SharedPreferences settings = mContext.getSharedPreferences("sp", Context.MODE_PRIVATE);
        return settings.getInt(key, defaultval);
    }

    //Todo:SAVE INT IN SP
    public boolean save_BOOLEAN(String key, boolean value) {
        SharedPreferences settings = mContext.getSharedPreferences("sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    //Todo:GET INT FROM SP
    public boolean get_BOOLEAN(String key, boolean defaultval) {
        SharedPreferences settings = mContext.getSharedPreferences("sp", Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultval);
    }

    public void fill_SPINNER(Spinner sp) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_dropdown_item,
                quality_TYPES);
        sp.setAdapter(spinnerArrayAdapter);
    }

}
