package com.moisesborges.tvaddict.net;

import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.models.ShowInfo;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Moisés on 11/04/2017.
 */

public interface TvMazeApi {
    @GET("search/shows")
    Single<List<ShowInfo>> searchShows(@Query("q") String query);

    @GET("shows")
    Single<List<Show>> listShows(@Query("page") int page);
}
