package com.BytesCrafter.BytesCrafterUnity3D;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class OtherActivity extends AppCompatActivity {

    private String otherUrl;

    @Override
    public void onStart() {

        Intent intent = getIntent();
        otherUrl = intent.getExtras().getString("urlToLoad");
        otherView.loadUrl(otherUrl);

        super.onStart();
    }

    private WebView otherView;
    private ProgressBar progressBar;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshScreen);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                otherView.loadUrl(otherUrl);
            }
        });

        progressBar = (ProgressBar)findViewById(R.id.loadProgress);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        otherView = (WebView) findViewById(R.id.otherview);
        otherView.setWebViewClient( new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setMax(100);
                progressBar.setVisibility(View.VISIBLE);
                refreshLayout.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                refreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        });

        otherView.setWebChromeClient( new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });

        WebSettings webSettings = otherView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if( item.getItemId() == android.R.id.home)
        {
            Intent intent = new Intent(OtherActivity.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(otherView.canGoBack())
        {
            otherView.goBack();
        }

        else
        {
            super.onBackPressed();
        }
    }
}
