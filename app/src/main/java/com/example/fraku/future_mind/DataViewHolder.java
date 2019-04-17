package com.example.fraku.future_mind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView mTitle, mDescription, mModificationDate;
    public ImageView mImage;
    public String mWebViewUrl;
    public boolean mTwoPane;
    public WebView webView;
    public MainActivity mainActivity;

    public DataViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mTitle = itemView.findViewById(R.id.Title);
        mDescription = itemView.findViewById(R.id.Description);
        mModificationDate = itemView.findViewById(R.id.ModificationDate);
        mImage = itemView.findViewById(R.id.Image);
        webView = itemView.findViewById(R.id.WebViewMD);

    }

    @Override
    public void onClick(View view) {
        if (mTwoPane == true){
            Intent intent = new Intent(view.getContext() , WebViewActivity.class);
            Bundle b = new Bundle();
            b.putString("webUrl", mWebViewUrl);
            intent.putExtras(b);
            view.getContext().startActivity(intent);
        }else if (mTwoPane == false){
            Bundle bundle = new Bundle();
            bundle.putString("WebUrl", mWebViewUrl);
            WebViewFragment fragment = new WebViewFragment();
            fragment.setArguments(bundle);

            mainActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
    }
    }
}