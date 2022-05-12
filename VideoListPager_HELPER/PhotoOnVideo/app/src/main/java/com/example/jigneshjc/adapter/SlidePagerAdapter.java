    package com.example.jigneshjc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.jigneshjc.tabs.FirstFragment;
import com.example.jigneshjc.tabs.SecondFragment;


    public class SlidePagerAdapter extends FragmentStatePagerAdapter {
    public SlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();
            default:
                break;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}