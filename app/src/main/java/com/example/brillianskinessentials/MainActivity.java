package com.example.brillianskinessentials;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onStart() {
        super.onStart();
        webview.loadUrl("https://www.brilliantskinessentials.ph/");
    }

    public WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webview = (WebView) findViewById(R.id.mainview);
        webview.setWebViewClient( new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                if( !url.contains("www.brilliantskinessentials.ph"))
                {
                    Intent intent = new Intent(MainActivity.this, OtherActivity.class);
                    intent.putExtra("urlToLoad", url);
                    startActivity(intent);
                }
            }
        });
        webview.loadUrl("https://www.brilliantskinessentials.ph/");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
