package com.moisesborges.tvaddict.notification

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator
import com.moisesborges.tvaddict.notification.newepisode.EpisodeNotificationJob
import com.moisesborges.tvaddict.notification.newepisode.EpisodesNotificationSchedulerJob
import com.moisesborges.tvaddict.notification.showupdate.UpdateShowJob

/**
 * Created by Moisés on 12/07/2017.
 */
class BackgroundJobCreator : JobCreator {

    override fun create(tag: String?): Job {
        when (tag) {
            UpdateShowJob.TAG -> return UpdateShowJob()
            EpisodeNotificationJob.TAG -> return EpisodeNotificationJob()
            EpisodesNotificationSchedulerJob.TAG -> return EpisodesNotificationSchedulerJob()
            else -> throw IllegalArgumentException("No Job created for Tag $tag")
        }
    }
}