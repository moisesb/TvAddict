package com.moisesborges.tvaddict.showdetails;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.data.ShowsRepository;
import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.mvp.BasePresenter;
import com.moisesborges.tvaddict.mvp.RxJavaConfig;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
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
        Disposable loadShowDataDisposable = mShowsRepository.getFullShowInfo(show.getId())
                .subscribeOn(getRxJavaConfig().ioScheduler())
                .observeOn(getRxJavaConfig().androidScheduler())
                .subscribe(showFullInfo -> {
                    getView().displaySeasonsProgress(false);
                    getView().setShow(showFullInfo);
                    if (showFullInfo.getEmbedded() != null) {
                        getView().displaySeasons(showFullInfo.getEmbedded().getSeasons());
                        getView().displayCastMembers(showFullInfo.getEmbedded().getCast());
                    }
                }, throwable -> {
                    getView().displaySeasonsProgress(false);
                    getView().displaySeasonsNotLoaded(true);
                });

        addDisposable(loadShowDataDisposable);

        verifySavedShowStatus(show);
    }

    private void verifySavedShowStatus(@NonNull Show show) {
        Disposable checkSavedShowDisposable = mShowsRepository.getSavedShow(show.getId())
                .subscribeOn(getRxJavaConfig().ioScheduler())
                .observeOn(getRxJavaConfig().androidScheduler())
                .subscribe(savedShow -> getView().displaySaveShowButton(false),
                        ignored -> getView().displaySaveShowButton(true));

        addDisposable(checkSavedShowDisposable);
    }

    public void changeWatchingStatus(@NonNull Show show) {
        checkView();

        Disposable checkStatusDisposable = mShowsRepository.getSavedShow(show.getId())
                .subscribeOn(getRxJavaConfig().ioScheduler())
                .subscribe(this::removeShow,
                        ignored -> saveShow(show));

        addDisposable(checkStatusDisposable);
    }

    private void removeShow(Show show) {
        Disposable removeShowDisposable = mShowsRepository.removeShow(show.getId())
                .subscribeOn(getRxJavaConfig().ioScheduler())
                .observeOn(getRxJavaConfig().androidScheduler())
                .subscribe(() -> {
                            getView().displayShowRemovedMessage();
                            getView().displaySaveShowButton(true);
                        },
                        Timber::e);

        addDisposable(removeShowDisposable);
    }

    private void saveShow(@NonNull Show show) {
        Disposable saveShowDisposable = mShowsRepository.saveShow(show)
                .subscribeOn(getRxJavaConfig().ioScheduler())
                .observeOn(getRxJavaConfig().androidScheduler())
                .subscribe(() -> {
                            getView().displaySavedShowMessage();
                            getView().displaySaveShowButton(false);
                        },
                        Timber::e);

        addDisposable(saveShowDisposable);

    }

    public void openEpisodes(Show show, Season season) {
        checkView();

        getView().navigateToEpisodes(show.getId(), season.getNumber(), show.getEmbedded());
    }
}
