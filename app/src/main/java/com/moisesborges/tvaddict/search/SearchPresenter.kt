package com.moisesborges.tvaddict.search

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.mvp.BasePresenter
import com.moisesborges.tvaddict.mvp.RxJavaConfig
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
                .compose(applySchedulersToSingle())
                .subscribe({ shows ->
                    view.displayProgress(false)
                    displayResults(shows, showName)
                }, { t ->
                    view.displayProgress(false)
                })

        addDisposable(disposable)
    }

    private fun displayResults(shows: List<Show>, showName: String) {
        if (shows.isNotEmpty()) {
            view.displayResults(shows)
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

}