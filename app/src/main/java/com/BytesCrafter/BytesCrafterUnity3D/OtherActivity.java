package com.BytesCrafter.BytesCrafterUnity3D;

import android.content.Intent;
import android.graphics.Bitmap;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdListener;

public class OtherActivity extends AppCompatActivity {

    private String otherUrl;

    private String intertitialAdUnit = "ca-app-pub-9202832039465189/9199166751";
    private InterstitialAd mInterstitialAd;
    private boolean mAdLoaded = false;
    private int mAdShown = 0;

    @Override
    public void onStart() {

        Intent intent = getIntent();
        otherUrl = intent.getExtras().getString("urlToLoad");
        otherView.loadUrl(otherUrl);

        super.onStart();
    }

    private WebView otherView;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

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
                otherView.loadUrl(otherUrl);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        otherView = (WebView) findViewById(R.id.otherview);
        otherView.setWebViewClient( new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                refreshLayout.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                refreshLayout.setRefreshing(false);
                if( mAdLoaded ) {
                    //System.out.println("OtherActivity is on " + mAdShown);
                    if( mAdShown < 7 ) {
                        mAdShown += 1;
                    } else {
                        mInterstitialAd.show();
                        mAdShown = 0;
                    }
                }
            }
        });

        otherView.setWebChromeClient( new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });

        WebSettings webSettings = otherView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if( item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(OtherActivity.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(otherView.canGoBack()) {
            otherView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
