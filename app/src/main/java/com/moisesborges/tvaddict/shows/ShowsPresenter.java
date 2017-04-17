package com.moisesborges.tvaddict.shows;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.data.ShowsRepository;
import com.moisesborges.tvaddict.mvp.BasePresenter;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

/**
 * Created by Moisés on 12/04/2017.
 */

public class ShowsPresenter extends BasePresenter<ShowsView> {

    private final ShowsRepository mShowsRepository;
    private final Scheduler mIoScheduler;
    private final Scheduler mAndroidScheduler;

    public ShowsPresenter(@NonNull ShowsRepository showsRepository,
                          @NonNull Scheduler ioScheduler,
                          @NonNull Scheduler androidScheduler) {
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
                .subscribe(showInfos -> {
                            getView().displayProgress(false);
                            getView().displayTvShows(showInfos);

                        }, throwable -> {
                            getView().displayProgress(false);
                            getView().displayError();
                        }
                );

        addDisposable(disposable);
    }
}