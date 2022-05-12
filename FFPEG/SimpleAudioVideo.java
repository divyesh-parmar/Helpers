package com.example.jigneshjc.utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.example.jigneshjc.Constant.Constant;
import com.example.jigneshjc.EditActivity;
import com.example.jigneshjc.LastActivity;
import com.example.jigneshjc.LyricsActivity;
import com.example.jigneshjc.MainActivity;
import com.example.jigneshjc.adapter.MoviesAdapter;

import java.io.File;
import java.io.IOException;

import static com.example.jigneshjc.StartActivity.LyricsImage;

public class SimpleAudioVideo extends AsyncTask<Void, Void, Boolean> {
    Context mContext;
    UtilMini helper;
    String inputFile;
    long duration;
    int startVideo;
    int auduiStart;
    String audioPathName;
    String outputPath;
    ProgressBar wv;
    VideoView vv;

    public SimpleAudioVideo(Context mContext, String inputFile, long duration, int auduiStart,
                            String audioPathName,
                            int startVideo,
                            String outputPath, ProgressBar wv, VideoView vv) {
        this.mContext = mContext;
        this.inputFile = inputFile;
        this.duration = duration;
        this.auduiStart = auduiStart;
        this.audioPathName = audioPathName;
        this.startVideo = startVideo;
        this.outputPath = outputPath;
        helper = new UtilMini(mContext);
        this.wv = wv;
        this.vv = vv;

    }

    public static void destroyProcess(Process process) {
        if (process != null) {
            process.destroy();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        wv.setVisibility(View.VISIBLE);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        String[] strArr = new String[]{getFFmpeg(mContext),
                "-y", "-ss", "" + startVideo, "-t",
                "" + duration, "-i", inputFile, "-ss",
                "" + auduiStart, "-i",
                "" + audioPathName, "-map", "0:0", "-map", "1:0",
                "-acodec", "copy", "-vcodec",
                "copy", "-preset", "ultrafast", "-ss", "0", "-t", ""
                + duration, outputPath};

        Process process = null;
        try {
            process = Runtime.getRuntime().exec(strArr);
            while (!isProcessCompleted(process)) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            destroyProcess(process);
        }
        return Boolean.valueOf(true);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        wv.setVisibility(View.GONE);
        Constant.FileName = outputPath;
        vv.setVideoPath(outputPath);
        MediaController videoMediaController = new MediaController(mContext);
        videoMediaController.setMediaPlayer(vv);
        vv.setMediaController(videoMediaController);
        vv.start();
        vv.requestFocus();
        vv.setKeepScreenOn(true);
        try {
            File del = new File(inputFile);
            del.delete();
            File li = new File(LyricsImage);
            File[] l1 = li.listFiles();
            for (int i = 0; i < l1.length; i++) {
                l1[i].delete();
            }

//            File ll2 = new File(MainActivity.SelectedImages);
//            File[] l2 = ll2.listFiles();
//            for (int i = 0; i < l2.length; i++) {
//                l2[i].delete();
//            }
//            LyricsActivity.Selctedlyrics.removeAll(LyricsActivity.Selctedlyrics);
            Constant.FileName = outputPath;
            mContext.startActivity(new Intent(mContext, LastActivity.class));

        } catch (Exception e) {

        }
    }

    public String getFFmpeg(Context context) {
        return getFilesDirectory(context).getAbsolutePath() + File.separator + "ffmpeg";
    }

    public boolean isProcessCompleted(Process process) {
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

    public File getFilesDirectory(Context context) {
        return context.getFilesDir();
    }
}