package com.moisesborges.tvaddict.data;

import com.moisesborges.tvaddict.models.ShowInfo;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Moisés on 12/04/2017.
 */

public interface ShowsRepository {
    Single<List<ShowInfo>> getShows();
}
