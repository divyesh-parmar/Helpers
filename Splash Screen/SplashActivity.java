package com.div.zipfilereader;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {

    int[] imageArray = { R.drawable.p1, R.drawable.p2,
            R.drawable.p3, R.drawable.p4,R.drawable.p5
    };
    ImageView proimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
	getWindow().setFlags(1024,1024);
        proimg=findViewById(R.id.txtlogo);

        RelativeLayout.LayoutParams layoutP = new RelativeLayout.LayoutParams(
                getResources().getDisplayMetrics().widthPixels*334/1080,
                getResources().getDisplayMetrics().heightPixels*50/1920);
        layoutP.addRule(RelativeLayout.CENTER_IN_PARENT);
        proimg.setLayoutParams(layoutP);

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
                proimg.setImageResource(imageArray[i]);
                i++;
                if (i > imageArray.length - 1) {
                    i = 0;
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 1000);


        Handler handler2=new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
            }
        },5000);

    }
}
