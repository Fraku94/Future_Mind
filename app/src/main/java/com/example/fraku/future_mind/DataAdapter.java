package com.example.fraku.future_mind;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import javax.sql.DataSource;

public class DataAdapter extends RecyclerView.Adapter<DataViewHolder>{

    private final MainActivity mParentActivity;
    private List<DataObject> DataList;
    private final boolean mTwoPane;

    //Przypisanie Obiektów do adaptera
    public DataAdapter(MainActivity mParentActivity, List<DataObject> DataList, boolean mTwoPane){
        this.DataList = DataList;
        this.mParentActivity = mParentActivity;
        this.mTwoPane = mTwoPane;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Przypisanie wygladu okna do adaptera
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, null,false);

        //Ustawienie RecycleView
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);

        //Podpienie i zwrocenie wygladu
        DataViewHolder rcv = new DataViewHolder((layoutView));
        return rcv;
    }

    @Override
    public void onBindViewHolder(final DataViewHolder holder,final int position)  {



        holder.mTwoPane = mTwoPane;
        holder.mainActivity = mParentActivity;


        holder.mWebViewUrl = DataList.get(position).getWebUrl();
        Glide.with(mParentActivity)
                .load(DataList.get(position).getImageUrl())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(1920,1080) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation){
                        Log.e("Glide", "Glide:   " + DataList.get(position).getImageUrl());
                        holder.mImageView.setImageBitmap(resource);
                        holder.mProgressBar.setVisibility(View.INVISIBLE);
                    }
                });

        holder.mTitle.setText(DataList.get(position).getTitle());
        holder.mDescription.setText(DataList.get(position).getDescription());
        holder.mModificationDate.setText(DataList.get(position).getModificationDate());

    }

    @Override
    public int getItemCount() {
        return this.DataList.size();
    }

}
