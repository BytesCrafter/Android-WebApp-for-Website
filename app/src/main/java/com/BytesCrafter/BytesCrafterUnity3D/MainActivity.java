package com.BytesCrafter.BytesCrafterUnity3D;

import android.content.Intent;
import android.graphics.Bitmap;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdListener;

public class MainActivity extends AppCompatActivity {

    private String intertitialAdUnit = "ca-app-pub-9202832039465189/9199166751";

    private String primaryUrl = "https://www.bytescrafter.net";
    public String mainUrl() {
        return primaryUrl;
    }

    private String urlDomain = "bytescrafter.net";
    public String domainName() {
        return urlDomain;
    }

    private WebView webview;
    SwipeRefreshLayout refreshLayout;

    private InterstitialAd mInterstitialAd;
    private boolean mAdLoaded = false;
    private int mAdShown = 0;

    @Override
    public void onStart() {
        webview.loadUrl(primaryUrl);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId( intertitialAdUnit );
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                mAdLoaded = true;
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                mAdLoaded = false;
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshScreen);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webview.loadUrl(primaryUrl);
            }
        });

        webview = (WebView) findViewById(R.id.mainview);
        webview.setWebViewClient( new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                refreshLayout.setRefreshing(true);
                if( !url.contains(urlDomain)) {
                    Intent intent = new Intent(MainActivity.this, OtherActivity.class);
                    intent.putExtra("urlToLoad", url);
                    startActivity(intent);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                refreshLayout.setRefreshing(false);
                //System.out.println("MainActivity is on " + mAdShown);
                if( mAdLoaded ) {
                    if( mAdShown < 7 ) {
                        mAdShown += 1;
                    } else {
                        mInterstitialAd.show();
                        mAdShown = 0;
                    }
                }
            }
        });

        webview.setWebChromeClient( new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if(webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
