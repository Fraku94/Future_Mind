package com.example.fraku.future_mind;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    private String TAG = WebViewActivity.class.getSimpleName();

    private  String WebViewUrl, Id;

    private WebView webView;


    private DatabaseHandlerData databaseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        databaseData = new DatabaseHandlerData(this);

        webView = findViewById(R.id.mWebView);


        WebViewUrl = getIntent().getExtras().getString("webUrl");
        Id = getIntent().getExtras().getString("Id");


        Cursor resSave = databaseData.getData(Id);

        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl(WebViewUrl);




    }

}