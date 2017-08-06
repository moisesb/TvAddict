package com.moisesborges.tvaddict.notification.newepisode

import com.moisesborges.tvaddict.models.Episode
import javax.inject.Inject

/**
 * Created by moises.anjos on 04/08/2017.
 */
class JobScheduler @Inject constructor() {

    fun scheduleEpisode(showId: Int, episode: Episode, time: Long, flex: Long) {
        val delta = flex / 2
        val startTime = time - delta
        val endTime = time + delta
        EpisodeNotificationJob.schedule(showId, episode.id, startTime, endTime)
    }
}