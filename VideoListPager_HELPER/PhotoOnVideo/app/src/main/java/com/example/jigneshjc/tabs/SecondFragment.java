package com.example.jigneshjc.tabs;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.jigneshjc.adapter.ItemOffsetDecoration;
import com.example.jigneshjc.adapter.MoviesAdapter;
import com.example.jigneshjc.adapter.RecyclerTouchListener;
import com.example.jigneshjc.photoonvideo.R;
import com.example.jigneshjc.utils.UtilMini;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SecondFragment extends Fragment {

    static ArrayList<String> FullPath = new ArrayList<>();
    private static RecyclerView recyclerView;
    ArrayList<String> FileName = new ArrayList<>();

    public static SecondFragment newInstance(String text) {

        SecondFragment f = new SecondFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.creation_tab, container, false);

        new BgLoaderVideoList().execute();

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view1);
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
        return v;
    }

    public void loadVideo() {
        FileName.removeAll(FileName);
        FullPath.removeAll(FullPath);
        String fname = Environment.getExternalStorageDirectory()
                + "/_" + new UtilMini(getActivity()).getApplicationName() + "/";
        File f = new File(fname);
        if (f.exists()) {
            File fl[] = f.listFiles();
            for (int i = 0; i < fl.length; i++) {
                FileName.add(fl[i].getName());
                FullPath.add(fl[i].getAbsolutePath());
            }
        }
    }

    class BgLoaderVideoList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            loadVideo();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Collections.sort(FileName, new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    return s1.compareToIgnoreCase(s2);
                }
            });
            recyclerView.setAdapter(new MoviesAdapter(getActivity(), FullPath));
        }
    }
}