package com.moisesborges.tvaddict.notification.newepisode

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Episode
import com.moisesborges.tvaddict.models.Show
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

        savedShows.map { mapToPairShowEpisode(it, today) }
                .filterNotNull()
                .forEach { jobScheduler.scheduleEpisode(it.first.id, it.second, fourHoursBefore(it.second), TimeUnit.HOURS.toMillis(1)) }
    }

    private fun mapToPairShowEpisode(show: Show, today: Date): Pair<Show, Episode>? {
        val episodeAiredToday = show.episodeAiredAt(DateUtils.dateToAirdate(today))
        return if (episodeAiredToday != null) Pair(show,episodeAiredToday) else null
    }

    private fun fourHoursBefore(episode: Episode): Long {
        val calendar = Calendar.getInstance()
        calendar.time = DateUtils.stringDateToLong("${episode.airdate} ${episode.airtime}")
        calendar.add(Calendar.HOUR, -4)
        return calendar.time.time
    }
}