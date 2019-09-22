package com.example.brillianskinessentials;

import android.content.Intent;
import android.graphics.Bitmap;
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
        super.onStart();
        Log.d("Bytes Crafter", "Currently on Other!");
    }

    private WebView otherView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        //MainActivity.
        //getSupportActionBar().setTitle("Other Activity");
        //setTitle("Go Back");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        otherView = (WebView) findViewById(R.id.otherview);
        otherView.setWebViewClient( new WebViewClient() {

        });

        Intent intent = getIntent();
        String urlToLoad = intent.getExtras().getString("urlToLoad");
        otherView.loadUrl(urlToLoad);

        WebSettings webSettings = otherView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if( item.getItemId() == android.R.id.home)
        {
            //finish();
            Intent intent = new Intent(OtherActivity.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
