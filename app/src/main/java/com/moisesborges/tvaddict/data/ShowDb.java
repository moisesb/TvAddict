package com.moisesborges.tvaddict.data;

import com.moisesborges.tvaddict.models.Show;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Moisés on 22/04/2017.
 */

public interface ShowDb {
    Completable save(Show show);

    Single<List<Show>> findAllShows();
}
