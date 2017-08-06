package com.moisesborges.tvaddict.notification.newepisode

import android.content.Context
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import com.evernote.android.job.util.support.PersistableBundleCompat
import com.moisesborges.tvaddict.App
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Episode
import com.moisesborges.tvaddict.models.Show
import javax.inject.Inject

/**
 * Created by Moisés on 06/08/2017.
 */
class EpisodeNotificationJob : Job() {

    @Inject lateinit var showsReposity: ShowsRepository

    override fun onRunJob(params: Params?): Result {
        (context.applicationContext as App).appComponent.inject(this)

        val showId = getParams().extras.getInt(SHOW_ID_ARG, -1)
        val episodeId = getParams().extras.getInt(EPISODE_ID_ARG, -1)
        if (showId == -1 || episodeId == -1) {
            return Result.FAILURE
        }

        val show = showsReposity.getSavedShow(showId)
                .blockingGet() ?: return Result.FAILURE

        val episode = show.episodes.find { it.id == episodeId } ?: return Result.FAILURE

        notifyEpisode(show, episode)

        return Result.SUCCESS
    }

    private fun notifyEpisode(show: Show, episode: Episode) {
        val notification = NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(context.getString(R.string.episode_notification_title))
                .setContentText(context.getString(R.string.notification_text, show.name, episode.airtime))
                .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(0, notification)
    }


    companion object {
        val TAG = "${EpisodeNotificationJob::class.java}.job"
        val EPISODE_ID_ARG = "${EpisodeNotificationJob::class.java}.episodeId"
        val SHOW_ID_ARG = "${EpisodeNotificationJob::class.java}.showId"

        fun schedule(showId: Int, episodeId: Int, startTime: Long, endTime: Long) {
            val extras = PersistableBundleCompat()
            extras.putInt(SHOW_ID_ARG, showId)
            extras.putInt(EPISODE_ID_ARG, episodeId)
            JobRequest.Builder(TAG)
                    .setExtras(extras)
                    .setExecutionWindow(startTime, endTime)
                    .build()
        }

    }
}