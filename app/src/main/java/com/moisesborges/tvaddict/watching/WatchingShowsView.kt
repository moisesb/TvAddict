package com.moisesborges.tvaddict.watching

import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.mvp.BaseView

/**
 * Created by Moisés on 22/05/2017.
 */

interface WatchingShowsView : BaseView {
    fun displayWatchingShows(shows: List<Show>)

    fun displayEmptyListMessage()

    fun navigateToShowDetails(show: Show)
}
