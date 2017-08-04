package com.moisesborges.tvaddict.notification.showupdate

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Show
import javax.inject.Inject

/**
 * Created by moises.anjos on 12/07/2017.
 */
class ShowUpdateService
@Inject constructor(private val showsRepository: ShowsRepository) {

    fun updateShows(): List<Show> {
        val savedRunningShows = showsRepository.getSavedShows()
                .blockingGet()
                .filter { it.status == RUNNING_STATUS }

        val showsFromWeb = savedRunningShows.map { showsRepository.getFullShowInfo(it.id).blockingGet() }
        val updatedShows = savedRunningShows.zip(showsFromWeb)
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