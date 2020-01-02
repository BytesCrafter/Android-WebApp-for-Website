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
//import android.webkit.WebResourceRequest;
//import android.webkit.WebResourceResponse;
import android.widget.ProgressBar;

//import android.app.AlertDialog;
//import android.content.DialogInterface;

//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdListener;

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

    private InterstitialAd mInterstitialAd;
    private boolean mAdLoaded = false;
    public int mAdShown = 0;

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
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                //mInterstitialAd.show();
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
                // Load the next interstitial.
                mAdLoaded = false;
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshScreen);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webview.loadUrl(primaryUrl);
            }
        });

        //progressBar = (ProgressBar)findViewById(R.id.loadProgress);

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
                    System.out.println("Counter: " + mAdShown);

                    //progressBar.setMax(100);
                    //progressBar.setVisibility(View.VISIBLE);
                    if( mAdLoaded ) {

                        if( mAdShown < 7 ) {
                            mAdShown += 1;
                        } else {
                            mInterstitialAd.show();
                            mAdShown = 0;
                        }
                    }
                }
            }

//            @Override
//            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
//
//
//                // Use the Builder class for convenient dialog construction
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setCancelable(true);
//                builder.setTitle("Hellow");
//                builder.setMessage("ABC123");
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                builder.show();
//
//            }

            @Override
            public void onPageFinished(WebView view, String url) {
                refreshLayout.setRefreshing(false);
                //progressBar.setVisibility(View.GONE);
            }
        });

        webview.setWebChromeClient( new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //progressBar.setProgress(newProgress);
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
