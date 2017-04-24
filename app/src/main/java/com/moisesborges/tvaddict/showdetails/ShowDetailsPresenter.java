package com.moisesborges.tvaddict.showdetails;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.data.ShowsRepository;
import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.mvp.BasePresenter;
import com.moisesborges.tvaddict.mvp.RxJavaConfig;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Created by moises.anjos on 18/04/2017.
 */

public class ShowDetailsPresenter extends BasePresenter<ShowDetailsView> {

    private final ShowsRepository mShowsRepository;

    @Inject
    public ShowDetailsPresenter(@NonNull ShowsRepository showsRepository,
                                @NonNull RxJavaConfig rxJavaConfig) {
        super(rxJavaConfig);
        mShowsRepository = showsRepository;
    }

    public void loadShowDetails(@NonNull Show show) {
        checkView();

        getView().setShowImage(show.getImage().getMedium());
        getView().setShowName(show.getName());
        getView().setShowSummary(show.getSummary());
        getView().displaySeasonsNotLoaded(false);

        getView().displaySeasonsProgress(true);
        mShowsRepository.getFullShowInfo(show.getId())
                .subscribeOn(getRxJavaConfig().ioScheduler())
                .observeOn(getRxJavaConfig().androidScheduler())
                .subscribe(showFullInfo -> {
                    getView().displaySeasonsProgress(false);
                    getView().setShow(showFullInfo);
                    if (showFullInfo.getEmbedded() != null) {
                        getView().displaySeasons(showFullInfo.getEmbedded().getSeasons());
                    }
                }, throwable -> {
                    getView().displaySeasonsProgress(false);
                    getView().displaySeasonsNotLoaded(true);
                });

    }

    public void addToWatchingList(@NonNull Show show) {
        checkView();

        mShowsRepository.saveShow(show)
                .subscribeOn(getRxJavaConfig().ioScheduler())
                .observeOn(getRxJavaConfig().androidScheduler())
                .subscribe(() -> getView().displaySavedShowMessage(),
                        Timber::e);
    }

    public void openEpisodes(Show show, Season season) {
        checkView();

        getView().navigateToEpisodes(show.getId(), season.getNumber(), show.getEmbedded());
    }
}
