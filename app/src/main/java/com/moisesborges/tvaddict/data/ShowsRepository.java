package com.moisesborges.tvaddict.data;

import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Show;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Moisés on 12/04/2017.
 */

public interface ShowsRepository {
    Single<List<Show>> getShows();

    Single<List<Season>> getSeasons(int showId);
}
