package com.starichenkov.musicplayer.retrofit;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ItunesSongApi {

    @GET("search")
    Single<ItunesResponse> fetchTracks(@Query("term") String term, @Query("media") String media, @Query("limit") String limit);
}
