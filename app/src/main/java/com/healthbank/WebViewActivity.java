package com.healthbank;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends ActivityCommon {
    WebView webview;
    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setmaterialDesign();
        back();
        Bundle b = getIntent().getExtras();
        if (b != null) {
           /* String name = b.getString("name");
            setTitle(name);*/
            url = b.getString("url");
        }
        attachUI();
    }

    private void attachUI() {
        Log.e("url ","url "+url);
        webview = findViewById(R.id.webview_1);
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(url);
}}
