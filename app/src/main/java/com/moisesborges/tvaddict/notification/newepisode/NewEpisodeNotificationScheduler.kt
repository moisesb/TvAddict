package com.moisesborges.tvaddict.notification.newepisode

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Episode
import com.moisesborges.tvaddict.utils.DateUtils
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by moises.anjos on 04/08/2017.
 */
class NewEpisodeNotificationScheduler
@Inject constructor(private val showsRepository: ShowsRepository,
                    private val jobScheduler: JobScheduler) {

    fun scheduleJobs() {

        val savedShows = showsRepository.getSavedShows()
                .blockingGet()

        val today = Date()

        savedShows.map { it.episodeAiredAt(DateUtils.dateToAirdate(today)) }
                .filterNotNull()
                .forEach { jobScheduler.scheduleEpisode(it, notificationTime(it), TimeUnit.HOURS.toMillis(1)) }
    }

    private fun notificationTime(episode: Episode): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}