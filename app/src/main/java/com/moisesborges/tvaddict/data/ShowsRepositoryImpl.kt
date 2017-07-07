package com.moisesborges.tvaddict.data

import com.moisesborges.tvaddict.models.Episode
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.models.UpcomingEpisode
import com.moisesborges.tvaddict.net.TvMazeApi
import io.reactivex.Observable
import io.reactivex.ObservableSource

import io.reactivex.Single

/**
 * Created by Moisés on 16/04/2017.
 */

class ShowsRepositoryImpl(private val tvMazeApi: TvMazeApi,
                          private val showDb: ShowDb) : ShowsRepository {

    override val shows: Single<List<Show>>
        get() = tvMazeApi.listShows(0)

    override fun getFullShowInfo(showId: Int): Single<Show> {
        return tvMazeApi.fetchShowFullInfo(showId)
    }

    override fun saveShow(show: Show): Single<Show> {
        return showDb.save(show)
                .toSingle { show }
    }

    override val savedShows: Single<List<Show>>
        get() = showDb.findAllShows()

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
        return shows.associate { show ->
                    val name = show.name
                    val episodes = show.episodes.filter { episode -> !episode.wasWatched() }
                            .firstOrNull()
                    Pair(name, episodes)
                }
                .filter { pair -> pair.value != null }
                .map { pair -> UpcomingEpisode(pair.key, pair.value!!) }
                .requireNoNulls()
    }

    override fun searchShows(showName: String): Single<List<Show>> {
        return tvMazeApi.searchShows(showName)
                .map { showsInfo -> showsInfo.map { showInfo -> showInfo.show }}
    }
}
