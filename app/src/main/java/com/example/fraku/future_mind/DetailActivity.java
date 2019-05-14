package com.example.fraku.future_mind;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class DetailActivity extends AppCompatActivity {

    private String TAG = DetailActivity.class.getSimpleName();

    private TextView textView;
    private ImageView imageView;
    private ProgressBar progressBar;
    String Descryption, WebViewUrl, ImgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textView = findViewById(R.id.Description);
        imageView = findViewById(R.id.Image);
        progressBar = findViewById(R.id.progress);

        WebViewUrl = getIntent().getExtras().getString("webUrl");
        Descryption = getIntent().getExtras().getString("descryption");
        ImgUrl = getIntent().getExtras().getString("imgUrl");


        progressBar.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(ImgUrl)
                .asBitmap()
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getApplication().getResources(),
                                Bitmap.createScaledBitmap(resource, 1920, 1080, false));
                        drawable.setCornerRadius(50);
                        imageView.setImageDrawable(drawable);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

        textView.setText(Descryption);

    }

    public void GoToWeb(View view) {
        Intent intent = new Intent(this , WebViewActivity.class);
        Bundle b = new Bundle();
        b.putString("webUrl", WebViewUrl);
        intent.putExtras(b);
        this.startActivity(intent);
    }
}