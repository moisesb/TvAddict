package com.moisesborges.tvaddict.notification

import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import com.moisesborges.tvaddict.App
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by moises.anjos on 12/07/2017.
 */

class UpdateShowJob : Job() {

    @Inject lateinit var showUpdateService: ShowUpdateService

    override fun onRunJob(params: Params?): Result {
        injectDependecies()
        showUpdateService.updateShows()

        return Result.SUCCESS
    }

    private fun injectDependecies() {
        (context.applicationContext as App).appComponent.inject(this)
    }

    companion object {
        @JvmStatic
        val TAG = "${UpdateShowJob::class.java.simpleName}.job"

        @JvmStatic
        fun schedule() {
            JobRequest.Builder(TAG)
                    .setPeriodic(TimeUnit.HOURS.toMillis(24), TimeUnit.HOURS.toMillis(3))
                    .setRequiredNetworkType(JobRequest.NetworkType.UNMETERED)
                    .setUpdateCurrent(true)
                    .setRequirementsEnforced(true)
                    .build()
                    .schedule()
        }
    }

}
