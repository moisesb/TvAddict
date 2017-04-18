package com.moisesborges.tvaddict.shows;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.data.ShowsRepository;
import com.moisesborges.tvaddict.di.DaggerConstrants;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.mvp.BasePresenter;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

/**
 * Created by Moisés on 12/04/2017.
 */

public class ShowsPresenter extends BasePresenter<ShowsView> {

    private final ShowsRepository mShowsRepository;
    private final Scheduler mIoScheduler;
    private final Scheduler mAndroidScheduler;

    @Inject
    public ShowsPresenter(@NonNull ShowsRepository showsRepository,
                          @NonNull @Named(DaggerConstrants.Names.IO_SCHEDULER) Scheduler ioScheduler,
                          @NonNull @Named(DaggerConstrants.Names.ANDROID_SCHEDULER) Scheduler androidScheduler) {
        mShowsRepository = showsRepository;
        mIoScheduler = ioScheduler;
        mAndroidScheduler = androidScheduler;
    }

    public void loadShows() {
        checkView();

        getView().displayProgress(true);

        Disposable disposable = mShowsRepository.getShows()
                .subscribeOn(mIoScheduler)
                .observeOn(mAndroidScheduler)
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
