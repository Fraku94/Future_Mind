package com.example.fraku.future_mind;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewFragment extends Fragment {

    private String TAG = WebViewFragment.class.getSimpleName();

    private  String WebViewUrl;
    private WebView webView;
    private ProgressBar progressBar;

    public WebViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     Bundle bundle= getArguments();
        if(bundle !=null) {
            if(bundle.containsKey("WebUrl")) {
                WebViewUrl = bundle.getString("WebUrl");

            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_webview, container, false);
        // Show the dummy content as text in a TextView.
        webView = rootView.findViewById(R.id.WebViewMD);
        progressBar = rootView.findViewById(R.id.progressMD);
        if (webView != null) {
            progressBar.setVisibility(View.VISIBLE);
            webView.clearCache(true);
            webView.clearHistory();
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webView.loadUrl(WebViewUrl);
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView viewx, String urlx) {
                    viewx.loadUrl(urlx);
                    return false;
                }

            public void onPageFinished(WebView view, String url) {

                progressBar.setVisibility(View.GONE);

            }
            });
        }
        return rootView;
    }
}
