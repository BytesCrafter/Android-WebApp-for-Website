package com.BytesCrafter.BytesCrafterUnity3D;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private String primaryUrl = "https://www.bytescrafter.net";
    public String mainUrl() {
        return primaryUrl;
    }

    private String urlDomain = "bytescrafter.net";
    public String domainName() {
        return urlDomain;
    }

    private WebView webview;
    private ProgressBar progressBar;
    SwipeRefreshLayout refreshLayout;

    @Override
    public void onStart() {
        webview.loadUrl(primaryUrl);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshScreen);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webview.loadUrl(primaryUrl);
            }
        });

        progressBar = (ProgressBar)findViewById(R.id.loadProgress);

        webview = (WebView) findViewById(R.id.mainview);
        webview.setWebViewClient( new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                refreshLayout.setRefreshing(true);
                if( !url.contains(urlDomain))
                {
                    Intent intent = new Intent(MainActivity.this, OtherActivity.class);
                    intent.putExtra("urlToLoad", url);
                    startActivity(intent);
                }

                else
                {
                    progressBar.setMax(100);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                refreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        });

        webview.setWebChromeClient( new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if(webview.canGoBack())
        {
            webview.goBack();
        }

        else
        {
            super.onBackPressed();
        }
    }
}
