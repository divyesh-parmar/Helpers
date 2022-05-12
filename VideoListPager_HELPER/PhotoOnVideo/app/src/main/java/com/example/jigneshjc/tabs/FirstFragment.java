package com.example.jigneshjc.tabs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jigneshjc.adapter.ItemOffsetDecoration;
import com.example.jigneshjc.adapter.MoviesAdapter;
import com.example.jigneshjc.adapter.RecyclerTouchListener;
import com.example.jigneshjc.photoonvideo.R;
import com.example.jigneshjc.utils.UtilMini;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FirstFragment extends Fragment {

    static ListView video_List;
    static ArrayList<String> dummyList;
    static UtilMini helper;
    static ArrayList<String> dummyList1;
    private static RecyclerView recyclerView;
    private ProgressBar wv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gallery_tab, container, false);
        video_List = (ListView) v.findViewById(R.id.video_List);
        video_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
            }
        });


        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(),
                R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);

        helper = new UtilMini(getActivity());
        wv = v.findViewById(R.id.loadingBar);

        new BgLoaderVideoList().execute();
        return v;
    }


    private ArrayList<String> getVideoList() {
        ArrayList<String> VideoPath = new ArrayList<>();
        VideoPath.removeAll(VideoPath);
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.VideoColumns.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri,
                projection, null, null, null);
        ArrayList<String> pathArrList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                pathArrList.add(cursor.getString(0));
                helper.LOG("VideoList : ", cursor.getString(0));
                VideoPath.add(cursor.getString(0));
            }
            cursor.close();
        }
        Log.e("all path", pathArrList.toString());
        return VideoPath;
    }

    class BgLoaderVideoList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wv.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dummyList = getVideoList();
            dummyList1 = getVideoList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Collections.sort(dummyList, new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    return s1.compareToIgnoreCase(s2);
                }
            });
            //   video_List.setAdapter(new CustomGallert(getActivity(), dummyList));
            recyclerView.setAdapter(new MoviesAdapter(getActivity(), dummyList));
            wv.setVisibility(View.GONE);
        }
    }
}