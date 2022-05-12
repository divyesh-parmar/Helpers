package com.example.stopmotionmaker.async;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.example.stopmotionmaker.MainActivity;
import com.example.stopmotionmaker.constant.Constant;
import com.example.stopmotionmaker.utils.UtilMini;

import java.io.File;
import java.io.IOException;


public class FirstMovieMix extends AsyncTask<Void, Void, Boolean> {
    UtilMini helper;
    Context mContext;
    String outputPath;
    ProgressBar wv;
    String fps;
    private float toatalSecond;
    private VideoView vv;
    private int userSec = 1;

    public FirstMovieMix(Context mContext,
                         String outputPath, String fps, ProgressBar wv, VideoView vv) {
        this.mContext = mContext;
        this.outputPath = outputPath;
        helper = new UtilMini(mContext);
        this.wv = wv;
        this.vv = vv;
        this.fps = fps;

    }

    public static void destroyProcess(Process process) {
        if (process != null) {
            process.destroy();
        }
    }

    public static boolean isProcessCompleted(Process process) {
        if (process == null) {
            return true;
        }
        try {
            process.exitValue();
            return true;
        } catch (IllegalThreadStateException e) {
            return false;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        wv.setVisibility(View.VISIBLE);
        helper.LOG("JcPhoto  ",
                "(Image TO Video Class)Inside Pre With Input Path : " +
                        "");
    }


    @Override
    protected Boolean doInBackground(Void... voids) {


        String[] comand2 =
                {
                        getFFmpeg(mContext),
                        "-f",
                        "concat",
                        "-i",
                        MainActivity.Root + "/Notes/SelectedSong.txt",
                        "-codec copy",
                        outputPath
                };
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(comand2);
            while (!isProcessCompleted(process)) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            destroyProcess(process);
            helper.LOG("JcPhoto  ",
                    "(Image TO Video Class) Process Destroy" + fps);
        }
        return Boolean.valueOf(true);
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        wv.setVisibility(View.GONE);
        vv.setVideoPath(outputPath);
        MediaController videoMediaController = new MediaController(mContext);
        videoMediaController.setMediaPlayer(vv);
//        vv.setMediaController(videoMediaController);
        vv.start();
        vv.requestFocus();
        vv.setKeepScreenOn(true);
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                Constant.duration = vv.getDuration() / 1000;
            }
        });
    }


    public String getFFmpeg(Context context) {
        return getFilesDirectory(context).getAbsolutePath() + File.separator + "ffmpeg";
    }

    public File getFilesDirectory(Context context) {
        return context.getFilesDir();
    }


}
