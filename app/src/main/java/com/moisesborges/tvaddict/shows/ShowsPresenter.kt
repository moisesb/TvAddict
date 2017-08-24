package com.moisesborges.tvaddict.shows

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.mvp.BasePresenter
import com.moisesborges.tvaddict.mvp.RxJavaConfig
import com.raizlabs.android.dbflow.kotlinextensions.save
import io.reactivex.functions.Consumer
import timber.log.Timber

import javax.inject.Inject

/**
 * Created by Moisés on 12/04/2017.
 */

class ShowsPresenter
@Inject constructor(private val showsRepository: ShowsRepository,
                    rxJavaConfig: RxJavaConfig) : BasePresenter<ShowsView>(rxJavaConfig) {

    fun loadShows() {
        checkView()

        view.displayProgress(true)

        val disposable = showsRepository.loadShows(0)
                .map(mapToUnfollowingListOfShows())
                .compose(applySchedulersToSingle())
                .subscribe({ shows ->
                    view.displayProgress(false)
                    view.displayTvShows(shows)
                    view.setPage(0)

                }
                ) { throwable ->
                    view.displayProgress(false)
                    view.displayError()
                }

        addDisposable(disposable)
    }

    private fun mapToUnfollowingListOfShows(): (List<Show>) -> List<Show> {
        return { showsFromWeb ->
            val savedShows = showsRepository.getSavedShows().blockingGet()
            showsFromWeb.filter { savedShows.count { savedShow -> savedShow.id == it.id } == 0 }
        }
    }


    fun openShowDetails(show: Show) {
        checkView()
        view.navigateToShowDetails(show)
    }

    fun loadMoreShows(currentPage: Int) {
        checkView()

        val nextPage = currentPage + 1
        val disposable = showsRepository.loadShows(nextPage)
                .map(mapToUnfollowingListOfShows())
                .compose(applySchedulersToSingle())
                .subscribe({ shows ->
                    view.displayMoreTvShows(shows)
                    view.setPage(nextPage)
                }, { error -> view.displayError() })

        addDisposable(disposable)
    }

    fun followShow(show: Show) {
        checkView()

        val disposable = showsRepository.saveFullShowInfo(show)
                .compose(applySchedulersToSingle())
                .subscribe({ savedShow ->
                    view.hideShow(savedShow)
                    view.displayFollowingShowMessage(savedShow)
                }, {error -> Timber.e(error)})

        addDisposable(disposable)
    }
}
