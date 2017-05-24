package com.moisesborges.tvaddict.data;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.models.CastMember;
import com.moisesborges.tvaddict.models.Episode;
import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.net.TvMazeApi;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
    public Single<Show> saveShow(@NonNull Show show) {
        return mShowDb.save(show)
                .toSingle(() -> show);
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
    public Single<Show> removeShow(int showId) {
        return mShowDb.remove(showId)
                .toSingle(() -> Show.NOT_FOUND);
    }

    @Override
    public Single<Show> updateShow(Show show) {
        return mShowDb.update(show);
    }
}
