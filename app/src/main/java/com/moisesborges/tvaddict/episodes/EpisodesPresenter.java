package com.moisesborges.tvaddict.episodes;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.models.Embedded;
import com.moisesborges.tvaddict.models.Episode;
import com.moisesborges.tvaddict.mvp.BasePresenter;
import com.moisesborges.tvaddict.mvp.RxJavaConfig;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;


/**
 * Created by Moisés on 21/04/2017.
 */

public class EpisodesPresenter extends BasePresenter<EpisodesView> {

    @Inject
    public EpisodesPresenter(@NonNull RxJavaConfig rxJavaConfig) {
        super(rxJavaConfig);
    }

    public void loadEpisodes(int seasonNumber,
                             @NonNull Embedded embeddedData) {
        checkView();

        List<Episode> episodes = Observable.fromIterable(embeddedData.getEpisodes())
                .filter(episode -> episode.getSeason() == seasonNumber)
                .reduce(new ArrayList<>(), (BiFunction<ArrayList<Episode>, Episode, ArrayList<Episode>>) (episodeList, episode) -> {
                    episodeList.add(episode);
                    return episodeList;
                })
                .blockingGet();

        getView().displayEpisodes(episodes);
    }

        public void markEpisodeAsSeen(int seasonId,
                                  @NonNull Episode episode,
                                  @NonNull Embedded embeddedData) {

    }
}
