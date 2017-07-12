package com.moisesborges.tvaddict.notification

import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import java.util.concurrent.TimeUnit

/**
 * Created by moises.anjos on 12/07/2017.
 */

class UpdateShowJob : Job() {


    override fun onRunJob(params: Params?): Result {


        return Result.SUCCESS
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
