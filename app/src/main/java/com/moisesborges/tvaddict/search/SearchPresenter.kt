package com.moisesborges.tvaddict.search

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.mvp.BasePresenter
import com.moisesborges.tvaddict.mvp.RxJavaConfig
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Moisés on 01/07/2017.
 */
class SearchPresenter @Inject constructor(rxJavaConfig: RxJavaConfig,
                                          private val showsRepository: ShowsRepository) : BasePresenter<SearchView>(rxJavaConfig) {
    fun searchShow(showName: String) {
        checkView()

        view.showNotFound(false, "")
        view.displayProgress(true)
        val disposable = showsRepository.searchShows(showName)
                .map { shows ->
                    val savedShows = showsRepository.getSavedShows().blockingGet()
                    shows.map { show -> ShowResult(show, savedShows.count { it.id == show.id } > 0) }
                }
                .compose(applySchedulersToSingle())
                .subscribe({ showsResult ->
                    view.displayProgress(false)
                    displayResults(showsResult, showName)
                }, { t ->
                    view.displayProgress(false)
                })

        addDisposable(disposable)
    }

    private fun displayResults(showsResult: List<ShowResult>, showName: String) {
        if (showsResult.isNotEmpty()) {
            view.displayResults(showsResult)
        } else {
            view.showNotFound(true, showName)
        }
    }

    fun handleTextChanges(query: String?) {
        checkView()

        if (query.isNullOrEmpty()) {
            view.clearResults()
        }
    }

    fun openShow(show: Show) {
        checkView()

        view.navigateToShowDetails(show)
    }

    fun toggleFollowShowStatus(showResult: ShowResult) {
        checkView()

        if (showResult.following) {
            val disposable = showsRepository.removeShow(showResult.show.id)
                    .compose(applySchedulersToSingle())
                    .subscribe({ show ->
                        view.updateShowResult(ShowResult(showResult.show, false))
                    }, { error -> Timber.e(error) })

            addDisposable(disposable)
        } else {
            val disposable = showsRepository.saveShow(showResult.show)
                    .compose(applySchedulersToSingle())
                    .subscribe({ show ->
                        view.updateShowResult(ShowResult(show, true))
                    }, { error -> Timber.e(error) })

            addDisposable(disposable)
        }
    }

}