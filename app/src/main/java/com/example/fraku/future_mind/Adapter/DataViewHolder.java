package com.example.fraku.future_mind.Adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fraku.future_mind.DetailActivity;
import com.example.fraku.future_mind.MainActivity;
import com.example.fraku.future_mind.R;
import com.example.fraku.future_mind.WebViewFragment;

public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView mTitle, mModificationDate;
    public ImageView mImageView;
    public String mWebViewUrl, mDescription, mImgUrl;
    public boolean mTwoPane;
    public MainActivity mainActivity;
    public ProgressBar mProgressBar;


    public DataViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mProgressBar = itemView.findViewById(R.id.progress);
        mTitle = itemView.findViewById(R.id.Title);
        mModificationDate = itemView.findViewById(R.id.ModificationDate);
        mImageView = itemView.findViewById(R.id.Image);

    }

    @Override
    public void onClick(View view) {
        if (mTwoPane == true){
            Intent intent = new Intent(view.getContext() , DetailActivity.class);
            Bundle b = new Bundle();
            b.putString("descryption", mDescription);
            b.putString("webUrl", mWebViewUrl);
            b.putString("imgUrl", mImgUrl);
            intent.putExtras(b);
            view.getContext().startActivity(intent);
        }else if (mTwoPane == false){
            Bundle bundle = new Bundle();
            bundle.putString("WebUrl", mWebViewUrl);
            bundle.putString("descryption", mDescription);
            WebViewFragment fragment = new WebViewFragment();
            fragment.setArguments(bundle);

            mainActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
    }
    }
    }