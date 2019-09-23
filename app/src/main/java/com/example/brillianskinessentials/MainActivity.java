package com.example.brillianskinessentials;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    private String primaryUrl = "https://www.brilliantskinessentials.ph/";
    public String mainUrl() {
        return primaryUrl;
    }

    private String urlDomain = "brilliantskinessentials.ph";
    public String domainName() {
        return urlDomain;
    }

    private WebView webview;

    @Override
    public void onStart() {
        webview.loadUrl(primaryUrl);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webview = (WebView) findViewById(R.id.mainview);
        webview.setWebViewClient( new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                if( !url.contains(urlDomain))
                {
                    Intent intent = new Intent(MainActivity.this, OtherActivity.class);
                    intent.putExtra("urlToLoad", url);
                    startActivity(intent);
                }
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
