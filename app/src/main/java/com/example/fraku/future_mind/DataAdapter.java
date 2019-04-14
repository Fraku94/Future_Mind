package com.example.fraku.future_mind;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataViewHolder>{

    private List<DataObject> DataList;
    private Context context;


    //Przypisanie Obiektów do adaptera
    public DataAdapter(List<DataObject> DataList, Context context){
        this.DataList = DataList;
        this.context = context;
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
    public void onBindViewHolder(final DataViewHolder holder, final int position) {


        holder.mId = DataList.get(position).getOrderId();
        holder.mWebViewUrl = DataList.get(position).getWebUrl();
        holder.mTitle.setText(DataList.get(position).getTitle());
        holder.mDescription.setText(DataList.get(position).getDescription());
        holder.mModificationDate.setText(DataList.get(position).getModificationDate());



//        Glide.with(context).load(DataList.get(position).getImageUrl()).
//                placeholder(R.mipmap.default_profile).fitCenter().into(holder.mImage);

        Glide.with(context)
                .load(DataList.get(position).getImageUrl())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(100,100) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation){

                        holder.mImage.setImageBitmap(resource);
                        //saveImage(resource, DataList.get(position).getOrderId());
                    }
                });

    }

    @Override
    public int getItemCount() {
        return this.DataList.size();
    }


//    private String saveImage(Bitmap image, String IdQue) {
//        String savedImagePath = null;
//
//        String imageFileName = "JPEG_" + IdQue + ".jpg";
//        File storageDir = new File(            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//                + "/Quiz");
//        boolean success = true;
//        if (!storageDir.exists()) {
//            success = storageDir.mkdirs();
//        }
//        if (success) {
//            File imageFile = new File(storageDir, imageFileName);
//            savedImagePath = imageFile.getAbsolutePath();
//            try {
//                OutputStream fOut = new FileOutputStream(imageFile);
//                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
//                fOut.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            // Add the image to the system gallery
//            galleryAddPic(savedImagePath);
//            Toast.makeText(context, "IMAGE SAVED", Toast.LENGTH_LONG).show();
//        }
//        return savedImagePath;
//    }
//
//    private void galleryAddPic(String imagePath) {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(imagePath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        context.sendBroadcast(mediaScanIntent);
//    }
}
