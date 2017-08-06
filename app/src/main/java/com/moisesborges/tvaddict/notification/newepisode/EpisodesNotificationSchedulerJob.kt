package com.moisesborges.tvaddict.notification.newepisode

import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import com.moisesborges.tvaddict.App
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by moises.anjos on 04/08/2017.
 */
class EpisodesNotificationSchedulerJob : Job() {

    @Inject lateinit var notificationScheduler: NewEpisodeNotificationScheduler

    override fun onRunJob(params: Params?): Result {
        (context.applicationContext as App).appComponent.inject(this)

        notificationScheduler.scheduleJobs()

        return Result.SUCCESS
    }

    companion object {

        @JvmStatic
        val TAG = "${EpisodesNotificationSchedulerJob::class.java.simpleName}.job"

        @JvmStatic
        fun schedule() {
            JobRequest.Builder(TAG)
                    .setPeriodic(TimeUnit.HOURS.toMillis(24), TimeUnit.HOURS.toMillis(1))
                    .setRequiredNetworkType(JobRequest.NetworkType.ANY)
                    .setUpdateCurrent(true)
                    .build()
        }
    }
}