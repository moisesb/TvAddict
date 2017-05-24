package com.moisesborges.tvaddict.showdetails;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.data.ShowsRepository;
import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.mvp.BasePresenter;
import com.moisesborges.tvaddict.mvp.RxJavaConfig;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
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

        displayBasicShowInfo(show);

        loadShowAdditionalInfo(show);

        verifySavedShowStatus(show);
    }

    private void displayBasicShowInfo(@NonNull Show show) {
        getView().setShowImage(show.getImage().getMedium());
        getView().setShowName(show.getName());
        getView().setShowSummary(show.getSummary());
        getView().displayAdditionalInfoNotLoaded(false);
        getView().setShowRating(show.getRating() != null ? show.getRating().getAverage() : null);
        getView().setShowNetwork(show.getNetwork() != null ? show.getNetwork().getName() : null);
        getView().setShowRuntime(show.getRuntime());
        getView().setShowGenres(show.getGenres());
        getView().setShowExternalLinks(show.getExternals());
    }

    private void loadShowAdditionalInfo(@NonNull Show show) {
        getView().displayAdditionalInfoLoadingInProgress(true);
        Disposable loadShowDataDisposable = mShowsRepository.getFullShowInfo(show.getId())
                .compose(applySchedulersToSingle())
                .onErrorReturn(ignored -> Show.NOT_FOUND)
                .subscribe(showFullInfo -> {
                    getView().displayAdditionalInfoLoadingInProgress(false);
                    if (showFullInfo == Show.NOT_FOUND) {
                        getView().displayAdditionalInfoNotLoaded(true);
                        return;
                    }

                    getView().setShow(showFullInfo);
                    if (showFullInfo.getSeasons() != null) {
                        getView().displaySeasons(showFullInfo.getSeasons());
                    }
                    if (showFullInfo.getCast() != null) {
                        getView().displayCastMembers(showFullInfo.getCast());
                    }
                });

        addDisposable(loadShowDataDisposable);
    }

    private void verifySavedShowStatus(@NonNull Show show) {
        Disposable checkSavedShowDisposable = mShowsRepository.getSavedShow(show.getId())
                .compose(applySchedulersToSingle())
                .subscribe(savedShow -> getView().displaySaveShowButton(false),
                        ignored -> getView().displaySaveShowButton(true));

        addDisposable(checkSavedShowDisposable);
    }

    public void changeWatchingStatus(@NonNull Show show) {
        checkView();

        Disposable checkStatusDisposable = mShowsRepository.getSavedShow(show.getId())
                .subscribeOn(getRxJavaConfig().ioScheduler())
                .onErrorReturn(ignored -> Show.NOT_FOUND)
                .subscribe(showFromDb -> {
                    if (showFromDb == Show.NOT_FOUND) {
                        saveShow(show);
                    } else {
                        removeShow(show);
                    }
                });

        addDisposable(checkStatusDisposable);
    }

    private void removeShow(Show show) {
        Disposable removeShowDisposable = mShowsRepository.removeShow(show.getId())
                .compose(applySchedulersToCompletable())
                .subscribe(() -> {
                            getView().displayShowRemovedMessage();
                            getView().displaySaveShowButton(true);
                        },
                        Timber::e);

        addDisposable(removeShowDisposable);
    }

    private void saveShow(@NonNull Show show) {
        Disposable saveShowDisposable = mShowsRepository.saveShow(show)
                .compose(applySchedulersToCompletable())
                .subscribe(() -> {
                            getView().displaySavedShowMessage();
                            getView().displaySaveShowButton(false);
                            saveEmbeddedData(show);
                        },
                        Timber::e);

        addDisposable(saveShowDisposable);

    }

    private void saveEmbeddedData(@NonNull Show show) {
        Disposable disposable = mShowsRepository.saveShowEmbeddedData(show)
                .subscribeOn(getRxJavaConfig().ioScheduler())
                .subscribe(() -> Timber.d("saved"));
        addDisposable(disposable);
    }

    public void openEpisodes(Show show, Season season) {
        checkView();

        getView().navigateToEpisodes(show.getId(), season.getNumber(), show.getEmbedded());
    }
}
