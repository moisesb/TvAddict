package com.moisesborges.tvaddict.notification

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Show
import io.reactivex.Observable

/**
 * Created by moises.anjos on 12/07/2017.
 */
class ShowUpdateService(private val showsRepository: ShowsRepository) {

    fun updateShows(): List<Show> {
        val savedRunningShows = showsRepository.getSavedShows()
                .blockingGet()
                .filter { it.status == RUNNING_STATUS }

        val showsFromWeb = savedRunningShows.map { showsRepository.getFullShowInfo(it.id).blockingGet() }
        val updatedShows: List<Show> = savedRunningShows.zip(showsFromWeb)
                .filter { (saved, fromWeb) -> fromWeb.updated > saved.updated }
                .map { (saved, fromWeb) ->
                    saved.embedded = fromWeb.embedded
                    saved
                }

        return updatedShows
    }

    companion object {
        internal val RUNNING_STATUS = "Running"
    }
}