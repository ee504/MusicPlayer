package com.starichenkov.musicplayer;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

public class Track {

    @SerializedName("artistName")
    private String artistName;
    @SerializedName("trackName")
    private String trackName;
    @SerializedName("artworkUrl30")
    private String trackCover;
    @SerializedName("previewUrl")
    private String trackUrl;

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public void setTrackCover(String trackCover) {
        this.trackCover = trackCover;
    }

    public void setTrackUrl(String trackUrl) {
        this.trackUrl = trackUrl;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getTrackCover() {
        return trackCover;
    }

    public String getTrackUrl() {
        return trackUrl;
    }

    @BindingAdapter({ "artworkUrl30" })
    public void loadImage(ImageView imageView) {
        /*Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions()
                        .circleCrop())
                .load(imageURL)
                .placeholder(R.drawable.loading)
                .into(imageView);*/

        Picasso.get()
                .load(trackUrl)
                .placeholder(R.drawable.vinyl_record)
                .error(R.drawable.vinyl_record)
                .into(imageView);
    }



}
