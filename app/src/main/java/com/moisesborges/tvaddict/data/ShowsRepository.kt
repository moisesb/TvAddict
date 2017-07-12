package com.moisesborges.tvaddict.data

import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.models.UpcomingEpisode
import io.reactivex.Single

/**
 * Created by Moisés on 12/04/2017.
 */

interface ShowsRepository {
    fun loadShows(page: Int): Single<List<Show>>

    fun getFullShowInfo(showId: Int): Single<Show>

    fun saveShow(show: Show): Single<Show>

    fun getSavedShows(): Single<List<Show>>

    fun getSavedShow(showId: Int): Single<Show>

    fun removeShow(showId: Int): Single<Show>

    fun updateShow(show: Show): Single<Show>

    fun fetchUpcomingEpisodes(): Single<List<UpcomingEpisode>>

    fun searchShows(showName: String): Single<List<Show>>
}
