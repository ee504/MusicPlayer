package com.starichenkov.musicplayer;

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

    @SerializedName("resultCount")
    private String resultCount;
    @SerializedName("results")
    private List<Track> listOfTracks;
}
