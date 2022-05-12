package com.div.shaketolockunlockscreen;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class Sound_Beep {

    static MediaPlayer mPlayer;

    public static void PlaySound(Context context) {
        mPlayer = MediaPlayer.create(context, R.raw.sound);
        mPlayer.start();
        mPlayer.setLooping(false);
        mPlayer.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    public static void StopSound(Context context) {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }
}
