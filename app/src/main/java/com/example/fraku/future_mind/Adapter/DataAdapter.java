package com.example.fraku.future_mind.Adapter;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.fraku.future_mind.MainActivity;
import com.example.fraku.future_mind.R;

import java.util.List;

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

        holder.mDescription = DataList.get(position).getDescription();
        holder.mWebViewUrl = DataList.get(position).getWebUrl();
        holder.mImgUrl = DataList.get(position).getImageUrl();


        //Wczytywanie zdjęć
        Glide.with(mParentActivity)
                .load(DataList.get(position).getImageUrl())
                .asBitmap()
                .into(new BitmapImageViewTarget(holder.mImageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(mParentActivity.getResources(),
                                Bitmap.createScaledBitmap(resource, 1920, 1080, false));
                        drawable.setCircular(true);
                        holder.mImageView.setImageDrawable(drawable);
                        holder.mProgressBar.setVisibility(View.INVISIBLE);
                    }
                });

        holder.mTitle.setText(DataList.get(position).getTitle());
        holder.mModificationDate.setText(DataList.get(position).getModificationDate());

    }

    @Override
    public int getItemCount() {
        return this.DataList.size();
    }

}
