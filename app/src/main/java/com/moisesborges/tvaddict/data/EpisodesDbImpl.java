package com.moisesborges.tvaddict.data;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.models.Episode;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Moisés on 22/04/2017.
 */

public class EpisodesDbImpl implements EpisodesDb {
    @Override
    public void save(@NonNull Episode episode) {

    }

    @Override
    public void delete(int episodeId) {

    }

    @Override
    public void update(@NonNull Episode episode) {

    }

    @Override
    public Single<List<Episode>> findEpisodes() {
        return null;
    }

    @Override
    public void saveEpisodes(@NonNull List<Episode> episodes) {
        FlowManager.getModelAdapter(Episode.class)
                .saveAll(episodes);
    }
}
