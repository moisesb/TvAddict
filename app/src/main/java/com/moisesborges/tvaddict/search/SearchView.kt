package com.moisesborges.tvaddict.search

import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.mvp.BaseView

/**
 * Created by Moisés on 01/07/2017.
 */
interface SearchView : BaseView {
    fun displayProgress(loading: Boolean)

    fun displayResults(result: List<ShowResult>)

    fun showNotFound(shouldBeDisplayed: Boolean, showName: String)

    fun clearResults()

    fun navigateToShowDetails(show: Show)

    fun updateShowResult(showResult: ShowResult)
}