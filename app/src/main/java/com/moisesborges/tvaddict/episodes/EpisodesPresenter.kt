package com.moisesborges.tvaddict.episodes

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Episode
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.mvp.BasePresenter
import com.moisesborges.tvaddict.mvp.RxJavaConfig

import java.util.ArrayList
import java.util.Timer

import javax.inject.Inject

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import timber.log.Timber


/**
 * Created by Moisés on 21/04/2017.
 */

class EpisodesPresenter
@Inject constructor(rxJavaConfig: RxJavaConfig,
                    private val mShowsRepository: ShowsRepository) : BasePresenter<EpisodesView>(rxJavaConfig) {

    fun loadEpisodes(seasonNumber: Int, show: Show) {
        checkView()

        val disposable = mShowsRepository.getSavedShow(show.id!!)
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

        episode.setWatched(!episode.wasWatched())
        val disposable = mShowsRepository.updateShow(show)
                .compose(applySchedulersToSingle<Show>())
                .subscribe({ ignored -> view.refreshEpisode(episode) }, { Timber.e(it) })

        addDisposable(disposable)
    }

    fun openEpisodeDetails(episode: Episode) {
        checkView()

        view.showEpisodeDetails(episode)
    }
}
