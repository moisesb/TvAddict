package com.moisesborges.tvaddict.episodes

import com.moisesborges.tvaddict.models.Episode
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.mvp.BaseView

/**
 * Created by Moisés on 21/04/2017.
 */

interface EpisodesView : BaseView {
    fun displayEpisodes(episodes: List<Episode>)

    fun refreshEpisode(episode: Episode)

    fun setShow(showFromDb: Show)

    fun showEpisodeDetails(episode: Episode)
}
