package com.moisesborges.tvaddict.upcoming

import com.moisesborges.tvaddict.models.UpcomingEpisode
import com.moisesborges.tvaddict.mvp.BaseView

/**
 * Created by moises on 28/06/2017.
 */
interface UpcomingEpisodesView : BaseView {
    fun displayProgress(loading: Boolean)
    fun displayEpisodes(episodes: List<UpcomingEpisode>)
    fun displayNoUpcomingEpisodesMessage()
    fun hideEpisode(upcomingEpisode: UpcomingEpisode)
    fun replaceEpisode(upcomingEpisode: UpcomingEpisode, nextUpcomingEpisode: UpcomingEpisode)
    fun hideNoUpcomingEpisodesMessage()
}