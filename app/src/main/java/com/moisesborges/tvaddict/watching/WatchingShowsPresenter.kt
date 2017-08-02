package com.moisesborges.tvaddict.watching

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.mvp.BasePresenter
import com.moisesborges.tvaddict.mvp.RxJavaConfig

import javax.inject.Inject

/**
 * Created by Moisés on 22/05/2017.
 */

class WatchingShowsPresenter
@Inject constructor(rxJavaConfig: RxJavaConfig,
            private val showsRepository: ShowsRepository) : BasePresenter<WatchingShowsView>(rxJavaConfig) {

    fun loadWatchingShows() {
        checkView()

        val disposable = showsRepository.getSavedShows()
                .compose(applySchedulersToSingle<List<Show>>())
                .subscribe { shows ->
                    if (shows.isEmpty()) {
                        view.displayEmptyListMessage()
                    } else {
                        view.displayWatchingShows(shows)
                    }
                }

        addDisposable(disposable)
    }

    fun openShowDetails(show: Show) {
        checkView()

        view.navigateToShowDetails(show)
    }
}
