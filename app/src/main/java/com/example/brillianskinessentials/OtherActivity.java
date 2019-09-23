package com.example.brillianskinessentials;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OtherActivity extends AppCompatActivity {

    @Override
    public void onStart() {

        Intent intent = getIntent();
        String urlToLoad = intent.getExtras().getString("urlToLoad");
        otherView.loadUrl(urlToLoad);

        super.onStart();
    }

    private WebView otherView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        otherView = (WebView) findViewById(R.id.otherview);
        otherView.setWebViewClient( new WebViewClient() {

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
