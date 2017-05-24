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
    private final EpisodesDb mEpisodesDb;
    private final SeasonDb mSeasonDb;

    public ShowsRepositoryImpl(@NonNull TvMazeApi tvMazeApi,
                               @NonNull ShowDb showDb,
                               @NonNull EpisodesDb episodesDb,
                               @NonNull SeasonDb seasonDb) {
        mTvMazeApi = tvMazeApi;
        mShowDb = showDb;
        mEpisodesDb = episodesDb;
        mSeasonDb = seasonDb;
    }

    @Override
    public Single<List<Show>> getShows() {
        return mTvMazeApi.listShows(0);
    }

    @Override
    public Single<Show> getFullShowInfo(int showId) {
        return mTvMazeApi.fetchShowFullInfo(showId)
                .doOnSuccess(addShowIdToEmbeddedData());
    }

    @NonNull
    private Consumer<Show> addShowIdToEmbeddedData() {
        return show -> {
            for (Season season : show.getSeasons()) {
                season.setShowId(show.getId());
            }
            for (Episode episode : show.getEpisodes()) {
                episode.setShowId(show.getId());
            }
            for (CastMember castMember : show.getCast()) {
                castMember.setShowId(show.getId());
            }
        };
    }

    @Override
    public Completable saveShow(@NonNull Show show) {
        return mShowDb.save(show);
    }

    @Override
    public Completable saveShowEmbeddedData(@NonNull Show show) {
        return Completable.fromRunnable(() -> {
            mSeasonDb.saveSeasons(show.getSeasons());
            mEpisodesDb.saveEpisodes(show.getEpisodes());
        });
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
}
