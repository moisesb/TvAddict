package com.moisesborges.tvaddict.data;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.models.Episode;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Moisés on 22/04/2017.
 */

public interface EpisodesDb {
    void save(@NonNull Episode episode);

    void delete(int episodeId);

    void update(@NonNull Episode episode);

    Single<List<Episode>> findEpisodes();

    void saveEpisodes(@NonNull List<Episode> episodes);
}
