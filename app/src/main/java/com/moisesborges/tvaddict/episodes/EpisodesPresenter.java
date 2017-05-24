package com.moisesborges.tvaddict.episodes;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.data.ShowsRepository;
import com.moisesborges.tvaddict.models.Episode;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.mvp.BasePresenter;
import com.moisesborges.tvaddict.mvp.RxJavaConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import timber.log.Timber;


/**
 * Created by Moisés on 21/04/2017.
 */

public class EpisodesPresenter extends BasePresenter<EpisodesView> {


    private final ShowsRepository mShowsRepository;

    @Inject
    public EpisodesPresenter(@NonNull RxJavaConfig rxJavaConfig,
                             @NonNull ShowsRepository showsRepository) {
        super(rxJavaConfig);
        mShowsRepository = showsRepository;
    }

    public void loadEpisodes(int seasonNumber,
                             @NonNull Show show) {
        checkView();

        Disposable disposable = mShowsRepository.getSavedShow(show.getId())
                .compose(applySchedulersToSingle())
                .onErrorReturn(ignored -> Show.NOT_FOUND)
                .doOnSuccess(showFromDb -> {
                    if (showFromDb != Show.NOT_FOUND) {
                        getView().setShow(showFromDb);
                    }
                })
                .map(showFromDb -> showFromDb != Show.NOT_FOUND ? showFromDb.getEpisodes() : show.getEpisodes())
                .map(episodes -> {
                    List<Episode> episodesFromSeason = new ArrayList<>();
                    for (Episode episode : episodes) {
                        if (episode.getSeason() == seasonNumber) {
                            episodesFromSeason.add(episode);
                        }
                    }
                    return episodesFromSeason;
                })
                .subscribe(episodes -> getView().displayEpisodes(episodes), Timber::e);

        addDisposable(disposable);
    }

    public void changeEpisodeSeenStatus(@NonNull Episode episode,
                                        @NonNull Show show) {
        checkView();

        episode.setWatched(!episode.wasWatched());
        Disposable disposable = mShowsRepository.updateShow(show)
                .compose(applySchedulersToSingle())
                .subscribe(ignored -> getView().refreshEpisode(episode), Timber::e);

        addDisposable(disposable);
    }
}
