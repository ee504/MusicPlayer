package com.starichenkov.musicplayer.retrofit;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.starichenkov.musicplayer.BR;

public class Track extends BaseObservable {

    @SerializedName("artistName")
    private String artistName;
    @SerializedName("trackName")
    private String trackName;
    @SerializedName("artworkUrl60")
    private String trackCover;
    @SerializedName("previewUrl")
    private String trackUrl;

    public void setArtistName(String artistName) {
        this.artistName = artistName;
        notifyPropertyChanged(BR.artistName);
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
        notifyPropertyChanged(BR.trackName);
    }

    public void setTrackCover(String trackCover) {
        this.trackCover = trackCover;
        notifyPropertyChanged(BR.trackCover);
    }

    public void setTrackUrl(String trackUrl) {
        this.trackUrl = trackUrl;
        notifyPropertyChanged(BR.trackUrl);
    }
    @Bindable
    public String getArtistName() {
        return artistName;
    }
    @Bindable
    public String getTrackName() {
        return trackName;
    }
    @Bindable
    public String getTrackCover() {
        return trackCover;
    }
    @Bindable
    public String getTrackUrl() {
        return trackUrl;
    }

    /*@BindingAdapter({ "artworkUrl30" })
    public void loadImage(ImageView imageView) {
        Picasso.get()
                .load(trackUrl)
                .placeholder(R.drawable.vinyl_record)
                .error(R.drawable.vinyl_record)
                .into(imageView);
    }*/


}
