package com.starichenkov.musicplayer.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItunesResponse {

    public String getResultCount() {
        return resultCount;
    }

    public List<Track> getListOfTracks() {
        return listOfTracks;
    }

    public void setResultCount(String resultCount) {
        this.resultCount = resultCount;
    }

    public void setListOfTracks(List<Track> listOfTracks) {
        this.listOfTracks = listOfTracks;
    }

    //счетчик выгруженных треков
    @SerializedName("resultCount")
    private String resultCount;
    //список треков с параметрами
    @SerializedName("results")
    private List<Track> listOfTracks;
}
