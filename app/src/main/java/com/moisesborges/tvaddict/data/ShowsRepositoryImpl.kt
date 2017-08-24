package com.moisesborges.tvaddict.data

import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.models.UpcomingEpisode
import com.moisesborges.tvaddict.net.TvMazeApi
import io.reactivex.Single
import io.reactivex.functions.Function

/**
 * Created by Moisés on 16/04/2017.
 */

class ShowsRepositoryImpl(private val tvMazeApi: TvMazeApi,
                          private val showDb: ShowDb) : ShowsRepository {

    override fun loadShows(page: Int): Single<List<Show>> {
        return tvMazeApi.listShows(page)
    }

    override fun getFullShowInfo(showId: Int): Single<Show> {
        return tvMazeApi.fetchShowFullInfo(showId)
    }

    override fun saveFullShowInfo(show: Show): Single<Show> {
        return getFullShowInfo(showId = show.id)
                .onErrorReturn { Show.NOT_FOUND }
                .flatMap { fullShowInfo -> saveShow(if (fullShowInfo != Show.NOT_FOUND) fullShowInfo else show) }

    }

    override fun saveShow(show: Show): Single<Show> {
        return showDb.save(show)
                .toSingle { show }
    }

    override fun getSavedShows(): Single<List<Show>> = showDb.findAllShows()

    override fun getSavedShow(showId: Int): Single<Show> {
        return showDb.findShow(showId)
    }

    override fun removeShow(showId: Int): Single<Show> {
        return showDb.remove(showId)
                .toSingle { Show.NOT_FOUND }
    }

    override fun updateShow(show: Show): Single<Show> {
        return showDb.update(show)
    }

    override fun fetchUpcomingEpisodes(): Single<List<UpcomingEpisode>> {
        return showDb.findAllShows()
                .map { shows -> upcomingEpisodes(shows) }
    }

    private fun upcomingEpisodes(shows: List<Show>): List<UpcomingEpisode> {
        return shows.sortedWith(compareBy({ it.name }))
                .associate { show ->
                    val episodes = show.episodes.filter { episode -> !episode.wasWatched() }
                            .firstOrNull()
                    Pair(show, episodes)
                }
                .filter { pair -> pair.value != null }
                .map { pair -> UpcomingEpisode(pair.key, pair.value!!) }
                .requireNoNulls()
    }

    override fun searchShows(showName: String): Single<List<Show>> {
        return tvMazeApi.searchShows(showName)
                .map { showsInfo -> showsInfo.map { showInfo -> showInfo.show } }
    }
}
