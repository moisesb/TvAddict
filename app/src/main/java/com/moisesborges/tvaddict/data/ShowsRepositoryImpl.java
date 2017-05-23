package com.moisesborges.tvaddict.data;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.net.TvMazeApi;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Moisés on 16/04/2017.
 */

public class ShowsRepositoryImpl implements ShowsRepository {

    private final TvMazeApi mTvMazeApi;
    private final ShowDb mShowDb;

    public ShowsRepositoryImpl(@NonNull TvMazeApi tvMazeApi,
                               @NonNull ShowDb showDb) {
        mTvMazeApi = tvMazeApi;
        mShowDb = showDb;
    }

    @Override
    public Single<List<Show>> getShows() {
        return mTvMazeApi.listShows(0);
    }

    @Override
    public Single<Show> getFullShowInfo(int showId) {
        return mTvMazeApi.fetchShowFullInfo(showId);
    }

    @Override
    public Completable saveShow(@NonNull Show show) {
        return mShowDb.save(show);
    }

    @Override
    public Single<List<Show>> getSavedShows() {
        return mShowDb.findAllShows();
    }

    @Override
    public Single<Show> getSavedShow(int showId) {
        return mShowDb.findShow(showId);
    }

    @Override
    public Completable removeShow(int showId) {
        return mShowDb.remove(showId);
    }

    @Override
    public Single<List<Show>> getWatchingShows() {
        return mShowDb.findAllShows();
    }

}
