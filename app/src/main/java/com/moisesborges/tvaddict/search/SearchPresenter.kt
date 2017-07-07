package com.moisesborges.tvaddict.search

import com.moisesborges.tvaddict.data.ShowsRepository
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

        view.displayProgress(true)
        val disposable = showsRepository.searchShows(showName)
                .compose(applySchedulersToSingle())
                .subscribe({ shows ->
                    view.displayProgress(false)
                    view.displayResults(shows)
                }, { t ->
                    view.displayProgress(false)
                })

        addDisposable(disposable)
    }

}