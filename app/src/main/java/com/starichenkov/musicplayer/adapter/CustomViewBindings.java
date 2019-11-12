package com.starichenkov.musicplayer.adapter;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.starichenkov.musicplayer.R;


public class CustomViewBindings {

    private final static String TAG = "myTag";

    //привязка адаптера
    @BindingAdapter("setAdapter")
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        //Log.d(TAG, "setAdapter");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    //скачивает и устанавливает превью в списке треков
    @BindingAdapter("imageUrl")
    public static void bindRecyclerViewAdapter(ImageView imageView, String imageUrl) {
        Picasso.get().load(imageUrl).placeholder(R.drawable.vinyl_record_60x60).error(R.drawable.vinyl_record_60x60).into(imageView);
    }

    //скачивает и устанавливает обложку в плееере
    @BindingAdapter("imageUrlBig")
    public static void bindImageView(ImageView imageView, String imageUrl) {
        Picasso.get().load(imageUrl.replace("60x60bb", "375x375bb")).placeholder(R.drawable.vinyl_record_500x500).error(R.drawable.vinyl_record_500x500).into(imageView);
    }

}
