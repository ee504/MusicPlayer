package com.starichenkov.musicplayer;

import android.database.Observable;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ItunesSongApi {
    @GET("search?term={name}&entity=musicTrack")
    public Single<List<Track>> fetchTracks(@Path("name") String name);

    @GET("search")
    Single<List<Track>> fetchTracks2(@Query("term") String term, @Query("entity") String entity);

    @GET("search")
    Single<List<Track>> fetchTracks3(@Query("term") String term);
}
