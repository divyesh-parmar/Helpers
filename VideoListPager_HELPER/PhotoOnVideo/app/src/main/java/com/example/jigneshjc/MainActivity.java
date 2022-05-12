package com.example.jigneshjc;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.jigneshjc.adapter.SlidePagerAdapter;
import com.example.jigneshjc.photoonvideo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static int MULTIPLE_PERMISSIONS = 10;
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    ViewPager viewPager;
    ImageView custom_gallery, converted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT > 22) {
            if (checkPermissions()) {
                setUp();
                setUpPager();
            }
        } else {
            setUp();
            setUpPager();
        }
    }

    private void setUpPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SlidePagerAdapter(getSupportFragmentManager()));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    converted.setImageResource(R.drawable.converted_media_hower);
                    custom_gallery.setImageResource(R.drawable.phone_media);
                }
                if (position == 0) {
                    converted.setImageResource(R.drawable.converted_media);
                    custom_gallery.setImageResource(R.drawable.phone_media_hower);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setUp() {
        custom_gallery = (ImageView) findViewById(R.id.custom_gallery);
        converted = (ImageView) findViewById(R.id.converted);
        converted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
                converted.setImageResource(R.drawable.converted_media_hower);
                custom_gallery.setImageResource(R.drawable.phone_media);
            }
        });
        custom_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
                converted.setImageResource(R.drawable.converted_media);
                custom_gallery.setImageResource(R.drawable.phone_media_hower);
            }
        });
    }

    public void openMenu(View openMenu) {

    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new
                    String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUp();
                } else {
                    finishAffinity();
                }
                return;
            }
        }
    }
}
