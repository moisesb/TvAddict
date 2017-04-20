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
        mShowsRepository.getSeasons(show.getId())
                .subscribeOn(getRxJavaConfig().ioScheduler())
                .observeOn(getRxJavaConfig().androidScheduler())
                .subscribe(seasons -> {
                    getView().displaySeasonsProgress(false);
                    getView().displaySeasons(seasons);
                }, throwable -> {
                    getView().displaySeasonsProgress(false);
                    getView().displaySeasonsNotLoaded(true);
                });
    }
}
