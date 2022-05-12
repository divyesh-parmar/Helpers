package com.div.sadvideostatus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class PolicyAct extends Activity {

    ImageView back;

    private Context mContext;

    private WebView webview;
    private ProgressBar progress_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        getWindow().setFlags(1024,1024);
        back=findViewById(R.id.back);
//        RelativeLayout.LayoutParams sdfgdfg = new RelativeLayout.LayoutParams(
//                getResources().getDisplayMetrics().widthPixels*80/1080,
//                getResources().getDisplayMetrics().heightPixels*80/1920);
//
//
//        back.setLayoutParams(sdfgdfg);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView header = (TextView) findViewById(R.id.header);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        mContext = PolicyAct.this;
        ui();
        init();
    }

    private void ui() {
        progress_bar = findViewById(R.id.progress_bar);
        webview = (WebView) findViewById(R.id.wv_privacy_policy);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        // settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webview.setScrollbarFadingEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

    }

    private void init() {

        webview.loadUrl("file:///android_asset/privacy_policy.html");
        webview.requestFocus();
        progress_bar.setVisibility(View.VISIBLE);
        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                try {
                    progress_bar.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent in=new Intent(PolicyAct.this, EntryActivity.class);
//        startActivity(in);
        finish();
    }
}
