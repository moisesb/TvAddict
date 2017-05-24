package com.moisesborges.tvaddict.data;

import com.moisesborges.tvaddict.models.Season;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Moisés on 27/04/2017.
 */

public interface SeasonDb {

    Single<Season> getSeason(int showId, int seasonNumber);

    Single<List<Season>> getSeasons(int showId);

    Completable update(Season season);

    void saveSeasons(List<Season> seasons);

}
