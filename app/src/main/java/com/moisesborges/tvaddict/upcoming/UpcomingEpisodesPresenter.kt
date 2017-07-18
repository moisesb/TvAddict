package com.moisesborges.tvaddict.upcoming

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.UpcomingEpisode
import com.moisesborges.tvaddict.mvp.BasePresenter
import com.moisesborges.tvaddict.mvp.RxJavaConfig
import javax.inject.Inject

/**
 * Created by moises on 28/06/2017.
 */
class UpcomingEpisodesPresenter
@Inject constructor(rxJavaConfig: RxJavaConfig,
                    private val showsRepository: ShowsRepository) : BasePresenter<UpcomingEpisodesView>(rxJavaConfig) {

    fun loadUpcomingEpisodes() {
        checkView()

        view.displayProgress(true)
        val diposable = showsRepository.fetchUpcomingEpisodes()
                .compose(applySchedulersToSingle())
                .subscribe({ episodes ->
                    view.displayProgress(false)
                    if (episodes.isNotEmpty()) {
                        view.displayEpisodes(episodes)
                    } else {
                        view.displayNoUpcomingEpisodes()
                    }
                })

        addDisposable(diposable)
    }


    fun markEpisodeAsSeen(upcomingEpisode: UpcomingEpisode) {
        checkView()
    }
}