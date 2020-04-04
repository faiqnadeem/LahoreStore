package com.LahoreStore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.LahoreStore.Activites.R;

public class MainActivity extends AppCompatActivity {

    WebView webview;
    private String webUrl = "https://lahorestore.pk";
    ProgressBar progressBarWeb;

    RelativeLayout relativeLayout;
    Button button404;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        webview = (WebView) findViewById(R.id.web1);
        progressBarWeb = (ProgressBar) findViewById(R.id.ProgressBar);


        button404 = (Button) findViewById(R.id.retry404);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);


        checkConnection();

        webview.loadUrl(webUrl);
        webview.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);

        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.getSettings().setAppCacheEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setSavePassword(true);
        webview.getSettings().setSaveFormData(true);
        webview.getSettings().setEnableSmoothTransition(true);


        webview.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBarWeb.setVisibility(View.VISIBLE);
                progressBarWeb.setProgress(newProgress);
                setTitle("Lahore Store is Under Development");


                if(newProgress == 100){

                    progressBarWeb.setVisibility(View.GONE);

                }

                super.onProgressChanged(view, newProgress);
            }
        });


        button404.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
            }
        });

        }


    @Override
    public void onBackPressed() {
        if(webview.canGoBack()){
            webview.goBack();
        }
        else{

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("ARE YOU SURE YOU WANT TO EXIT ?")
                    .setNegativeButton("NO",null)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    }).show();

        }

    }



    public void checkConnection(){

        ConnectivityManager connectivityManager = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if(wifi.isConnected()){

            webview.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility((View.GONE));

        }

        else if (mobileNetwork.isConnected()){

            webview.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility((View.GONE));
        }

        else{

            webview.setVisibility(View.GONE);
            relativeLayout.setVisibility((View.VISIBLE));

        }
    }
}


