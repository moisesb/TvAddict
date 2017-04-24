package com.moisesborges.tvaddict.data;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Show;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Moisés on 12/04/2017.
 */

public interface ShowsRepository {
    Single<List<Show>> getShows();

    Single<Show> getFullShowInfo(int showId);

    Completable saveShow(@NonNull Show show);

    Single<List<Show>> getSavedShows();
}
