package com.moisesborges.tvaddict.watching;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.data.ShowsRepository;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.mvp.BasePresenter;
import com.moisesborges.tvaddict.mvp.RxJavaConfig;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Created by Moisés on 22/05/2017.
 */

public class WatchingShowsPresenter extends BasePresenter<WatchingShowsView> {

    private ShowsRepository mShowsRepository;

    @Inject
    public WatchingShowsPresenter(@NonNull RxJavaConfig rxJavaConfig,
                                  @NonNull ShowsRepository showsRepository) {
        super(rxJavaConfig);
        mShowsRepository = showsRepository;
    }

    public void loadWatchingShows() {
        checkView();

        Disposable disposable = mShowsRepository.getWatchingShows()
                .compose(applySchedulersToSingle())
                .subscribe(shows -> {
                    if (shows.size() == 0) {
                        getView().displayEmptyListMessage();
                        return;
                    }

                    getView().displayWatchingShows(shows);
                });

        addDisposable(disposable);
    }
}
