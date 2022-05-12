package com.example.jigneshjc.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.example.jigneshjc.Constant.Constant;
import com.example.jigneshjc.MainActivity;
import com.example.jigneshjc.lyriscvideo.R;

import java.io.File;
import java.io.IOException;

import static com.example.jigneshjc.EditActivity.progbarLayout;
import static com.example.jigneshjc.StartActivity.LyricsImage;
import static com.example.jigneshjc.StartActivity.Output;
import static com.example.jigneshjc.StartActivity.Root;


public class FirstMovieMaker extends AsyncTask<Void, Void, Boolean> {
    UtilMini helper;
    Context mContext;
    String outputPath;
    ProgressBar wv;
    String fps;
    private VideoView vv;

    public FirstMovieMaker(Context mContext,
                           String outputPath, ProgressBar wv, VideoView vv, String fps) {
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
        progbarLayout.setVisibility(View.VISIBLE);
        helper.LOG("JcPhoto  ",
                "(Image TO Video Class)Inside Pre With Input Path : " +
                        "");
    }


    @Override
    protected Boolean doInBackground(Void... voids) {

        String inputImages = LyricsImage + "/image%d.png";

        String[] comand2 =
                {
                        getFFmpeg(mContext),
                        "-y",
                        "-r",
                        "1/" + fps,
                        "-i",
                        inputImages,
                        "-vcodec",
                        "libx264",
                        "-r",
                        "2",
                        "-pix_fmt",
                        "yuv420p",
                        "-preset",
                        "ultrafast",
                        outputPath
                };

        helper.LOG("JcPhoto  ",
                "(Image TO Video Class)  In Background : " + inputImages);

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
                    "(Image TO Video Class) Process Destroy");
        }
        return Boolean.valueOf(true);
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        wv.setVisibility(View.GONE);
        progbarLayout.setVisibility(View.GONE);
        String name = Output + "/jk" + System.currentTimeMillis() + ".mp4";
        String audioPath = Root + "/" + Constant.SelectedSong + ".mp3";
        new SimpleAudioVideo(mContext, outputPath, 30, 0, audioPath
                , 0, name
                , wv, vv).execute();

    }


    public String getFFmpeg(Context context) {
        return getFilesDirectory(context).getAbsolutePath() + File.separator + "ffmpeg";
    }

    public File getFilesDirectory(Context context) {
        return context.getFilesDir();
    }


}
