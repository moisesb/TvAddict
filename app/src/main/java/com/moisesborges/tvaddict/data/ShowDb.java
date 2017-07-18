package com.moisesborges.tvaddict.data;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.models.Episode;
import com.moisesborges.tvaddict.models.Show;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Moisés on 22/04/2017.
 */

public interface ShowDb {
    @NonNull
    Completable save(Show show);

    @NonNull
    Completable remove(int showId);

    @NonNull
    Single<List<Show>> findAllShows();

    @NonNull
    Single<Show> findShow(int showId);

    @NonNull
    Single<Show> update(Show show);

    @NotNull
    Single<Episode> updateEpisode(@NotNull Episode episode);
}
