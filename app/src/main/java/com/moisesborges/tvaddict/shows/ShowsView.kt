package com.moisesborges.tvaddict.shows

import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.mvp.BaseView

/**
 * Created by Moisés on 12/04/2017.
 */

interface ShowsView : BaseView {

    fun displayProgress(isLoading: Boolean)

    fun displayTvShows(shows: List<Show>)

    fun displayError()

    fun navigateToShowDetails(show: Show)

    fun setPage(page: Int)

    fun displayMoreTvShows(shows: List<Show>)
}
