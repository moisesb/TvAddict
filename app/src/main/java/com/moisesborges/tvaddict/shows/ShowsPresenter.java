package com.moisesborges.tvaddict.shows;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.data.ShowsRepository;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.mvp.BasePresenter;
import com.moisesborges.tvaddict.mvp.RxJavaConfig;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by Moisés on 12/04/2017.
 */

public class ShowsPresenter extends BasePresenter<ShowsView> {

    private final ShowsRepository mShowsRepository;

    @Inject
    public ShowsPresenter(@NonNull ShowsRepository showsRepository,
                          @NonNull RxJavaConfig rxJavaConfig) {
        super(rxJavaConfig);
        mShowsRepository = showsRepository;
    }

    public void loadShows() {
        checkView();

        getView().displayProgress(true);

        Disposable disposable = mShowsRepository.getShows()
                .subscribeOn(getRxJavaConfig().ioScheduler())
                .observeOn(getRxJavaConfig().androidScheduler())
                .subscribe(shows -> {
                            getView().displayProgress(false);
                            getView().displayTvShows(shows);

                        }, throwable -> {
                            getView().displayProgress(false);
                            getView().displayError();
                        }
                );

        addDisposable(disposable);
    }


    public void openShowDetails(@NonNull Show show) {
        checkView();
        getView().navigateToShowDetails(show);
    }
}
