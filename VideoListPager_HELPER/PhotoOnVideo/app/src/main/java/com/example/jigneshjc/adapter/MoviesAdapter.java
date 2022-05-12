package com.example.jigneshjc.adapter;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.jigneshjc.photoonvideo.R;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    ArrayList<String> videoName;
    private Context mContext;

    public MoviesAdapter(Context mContext, ArrayList<String> videoName) {
        this.videoName = videoName;
        this.mContext = mContext;
        setHasStableIds(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grid, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        File fsize = new File(videoName.get(position));
        double length = fsize.length();
        length = length / 1024;

        DecimalFormat df = new DecimalFormat("#.##");
        holder.videoSize.setText(df.format((length / 1024)) + " mb");
        File fname = new File(videoName.get(position));
        if (fname.getName().contains(".mp4")) {
            String name = fname.getName().replaceAll(".mp4", "");
            holder.videoName.setText(name);
        } else if (fname.getName().contains(".3gp")) {
            String name = fname.getName().replaceAll(".3gp", "");
            holder.videoName.setText(name);
        } else if (fname.getName().contains(".mkv")) {
            String name = fname.getName().replaceAll(".mkv", "");
            holder.videoName.setText(name);
        } else if (fname.getName().contains(".avi")) {
            String name = fname.getName().replaceAll(".avi", "");
            holder.videoName.setText(name);
        } else {
            holder.videoName.setText(videoName.get(position));
        }
        try {
            Glide
                    .with(mContext)
                    .load(Uri.fromFile(new File(videoName.get(position))))
                    .into(holder.IconApp);
            holder.videoDuration.setText(getDuration(videoName.get(position)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return videoName.size();
    }

    public String getDuration(String videoFile) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(mContext, Uri.parse(videoFile));
        String time = retriever.extractMetadata(MediaMetadataRetriever.
                METADATA_KEY_DURATION);
        long timeInMillisec = Long.parseLong(time);

        long min = timeInMillisec / 10000;
        long sec = timeInMillisec / 1000;

        String format = min + "m:" + sec + "s";

        retriever.release();
        return format;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView videoName, videoSize, videoDuration, videoExtension;
        ImageView IconApp;

        public MyViewHolder(View view) {
            super(view);
            videoName = (TextView) view.findViewById(R.id.video_Name);
            videoSize = (TextView) view.findViewById(R.id.video_Size);
            videoDuration = (TextView) view.findViewById(R.id.video_Duration);
            IconApp = (ImageView) view.findViewById(R.id.video_Thumb);
        }
    }
}