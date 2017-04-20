package com.moisesborges.tvaddict.data;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.net.TvMazeApi;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Moisés on 16/04/2017.
 */

public class ShowsRepositoryImpl implements ShowsRepository {

    private final TvMazeApi mTvMazeApi;

    public ShowsRepositoryImpl(@NonNull TvMazeApi tvMazeApi) {
        mTvMazeApi = tvMazeApi;
    }

    @Override
    public Single<List<Show>> getShows() {
        return mTvMazeApi.listShows(0);
    }

    @Override
    public Single<List<Season>> getSeasons(int showId) {
        return mTvMazeApi.listShowSeasons(showId);
    }
}
