package com.moisesborges.tvaddict.episodes

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Episode
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.mvp.BasePresenter
import com.moisesborges.tvaddict.mvp.RxJavaConfig

import javax.inject.Inject

import timber.log.Timber


/**
 * Created by Moisés on 21/04/2017.
 */

class EpisodesPresenter
@Inject constructor(rxJavaConfig: RxJavaConfig,
                    private val showsRepository: ShowsRepository) : BasePresenter<EpisodesView>(rxJavaConfig) {

    fun loadEpisodes(seasonNumber: Int, show: Show) {
        checkView()

        val disposable = showsRepository.getSavedShow(show.id!!)
                .compose(applySchedulersToSingle<Show>())
                .onErrorReturn { ignored -> Show.NOT_FOUND }
                .doOnSuccess { showFromDb ->
                    if (showFromDb !== Show.NOT_FOUND) {
                        view.setShow(showFromDb)
                    }
                }
                .map { showFromDb -> if (showFromDb !== Show.NOT_FOUND) showFromDb.episodes else show.episodes }
                .map<List<Episode>> { episodes -> episodes.filter { episode -> episode.season == seasonNumber } }
                .subscribe({ episodes -> view.displayEpisodes(episodes) }, { Timber.e(it) })

        addDisposable(disposable)
    }

    fun changeEpisodeSeenStatus(episode: Episode, show: Show) {
        checkView()

        if (!episode.wasAired()) {
            return
        }

        episode.setWatched(!episode.wasWatched())
        val disposable = showsRepository.updateShow(show)
                .compose(applySchedulersToSingle<Show>())
                .subscribe({ ignored -> view.refreshEpisode(episode) }, { Timber.e(it) })

        addDisposable(disposable)
    }

    fun openEpisodeDetails(episode: Episode) {
        checkView()

        view.showEpisodeDetails(episode)
    }

    fun setAllEpisodesStatusAsSeen(seasonNumber: Int, show: Show) {
        setAllEpisodesSeenStatus(show, seasonNumber, true)

    }

    private fun setAllEpisodesSeenStatus(show: Show, seasonNumber: Int, watched: Boolean) {
        checkView()

        show.episodes
                .filter { episode -> episode.season == seasonNumber }
                .filter { episode -> episode.wasAired() }
                .forEach { episode -> episode.setWatched(watched) }

        val disposable = showsRepository.updateShow(show)
                .compose(applySchedulersToSingle<Show>())
                .subscribe({ ignored -> view.refreshAllEpisodes() }, { Timber.e(it) })

        addDisposable(disposable)
    }

    fun setAllEpisodesStatusAsUnseen(seasonNumber: Int, show: Show) {
        setAllEpisodesSeenStatus(show, seasonNumber, false)
    }


}
