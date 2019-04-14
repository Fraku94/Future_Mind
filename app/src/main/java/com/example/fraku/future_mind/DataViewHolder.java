package com.example.fraku.future_mind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView mTitle, mDescription, mModificationDate;
    public ImageView mImage;
    public String mId, mWebViewUrl;

    public DataViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mTitle = itemView.findViewById(R.id.Title);
        mDescription = itemView.findViewById(R.id.Description);
        mModificationDate = itemView.findViewById(R.id.ModificationDate);
        mImage = itemView.findViewById(R.id.Image);
    }

    @Override
    public void onClick(View view) {


            Intent intent = new Intent(view.getContext() , WebViewActivity.class);
            Bundle b = new Bundle();
            b.putString("Id", mId);
            b.putString("webUrl", mWebViewUrl);
            intent.putExtras(b);
            view.getContext().startActivity(intent);

    }
}