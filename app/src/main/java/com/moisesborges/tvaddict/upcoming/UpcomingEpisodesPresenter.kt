package com.moisesborges.tvaddict.upcoming

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.models.UpcomingEpisode
import com.moisesborges.tvaddict.mvp.BasePresenter
import com.moisesborges.tvaddict.mvp.RxJavaConfig
import timber.log.Timber
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
        val disposable = showsRepository.fetchUpcomingEpisodes()
                .compose(applySchedulersToSingle())
                .subscribe({ episodes ->
                    view.displayProgress(false)
                    if (episodes.isNotEmpty()) {
                        view.displayEpisodes(episodes)
                    } else {
                        view.displayNoUpcomingEpisodes()
                    }
                })

        addDisposable(disposable)
    }


    fun markEpisodeAsSeen(upcomingEpisode: UpcomingEpisode) {
        checkView()

        val episode = upcomingEpisode.episode
        episode.setWatched(true)
        val disposable = showsRepository.updateShow(upcomingEpisode.show)
                .compose(applySchedulersToSingle())
                .map { show -> UpcomingEpisode(show, show.nextEpisode()) }
                .subscribe({ nextUpcomingEpisode ->
                    if (nextUpcomingEpisode.episode != Show.EPISODE_NOT_FOUND) {
                        view.replaceEpisode(upcomingEpisode, nextUpcomingEpisode)
                    } else {
                        view.hideEpisode(upcomingEpisode)
                    }
                }, { t -> Timber.e(t) })

        addDisposable(disposable)
    }
}