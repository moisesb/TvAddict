package com.moisesborges.tvaddict.net;

import com.moisesborges.tvaddict.models.Episode;
import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.models.ShowInfo;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Moisés on 11/04/2017.
 */

public interface TvMazeApi {
    @GET("search/shows")
    Single<List<ShowInfo>> searchShows(@Query("q") String query);

    @GET("shows")
    Single<List<Show>> listShows(@Query("page") int page);

    @GET("shows/{id}?embed[]=episodes&embed[]=cast&embed[]=seasons")
    Single<Show> fetchShowFullInfo(@Path("id") int showId);

    @GET("shows/{id}/episodes")
    Single<List<Episode>> listShowEpisodes(@Path("id") int showId);

    @GET("shows/{id}/seasons")
    Single<List<Season>> listShowSeasons(@Path("id") int showId);
}
