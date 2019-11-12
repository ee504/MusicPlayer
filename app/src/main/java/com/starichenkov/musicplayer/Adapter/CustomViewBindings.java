package com.starichenkov.musicplayer.Adapter;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.starichenkov.musicplayer.R;


public class CustomViewBindings {

    private final static String TAG = "myTag";

    @BindingAdapter("setAdapter")
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        //Log.d(TAG, "setAdapter");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("imageUrl")
    public static void bindRecyclerViewAdapter(ImageView imageView, String imageUrl) {
        Picasso.get().load(imageUrl).placeholder(R.drawable.vinyl_record_60x60).error(R.drawable.vinyl_record_60x60).into(imageView);
    }
    @BindingAdapter("imageUrlBig")
    public static void bindImageView(ImageView imageView, String imageUrl) {
        Picasso.get().load(imageUrl.replace("60x60bb", "375x375bb")).placeholder(R.drawable.vinyl_record_500x500).error(R.drawable.vinyl_record_500x500).into(imageView);
    }

}
