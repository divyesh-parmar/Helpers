package com.example.jigneshjc.screen_recorder.util;

import android.app.Application;

import com.example.jigneshjc.screen_recorder.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by vamsi on 06-05-2017 for android custom font article
 */

public class CustomFontApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/f5.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}