package com.example.fraku.future_mind;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewActivity extends AppCompatActivity {

    private String TAG = WebViewActivity.class.getSimpleName();

    private  String WebViewUrl;

    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebViewUrl = getIntent().getExtras().getString("webUrl");
        progressBar = findViewById(R.id.progress);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.BLUE,
                android.graphics.PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.VISIBLE);
        webView = findViewById(R.id.mWebView);
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl(WebViewUrl);
        webView.setWebViewClient(new WebViewClient()
                                 {
                                     public boolean shouldOverrideUrlLoading(WebView viewx, String urlx) {
                                         viewx.loadUrl(urlx);
                                         return false;
                                     }

                                     public void onPageFinished(WebView view, String url) {
                                             progressBar.setVisibility(View.GONE);
                                     }
                                 }
        );
    }
}