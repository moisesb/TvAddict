package com.moisesborges.tvaddict;

import android.app.Application;

import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobManager;
import com.moisesborges.tvaddict.notification.BackgroundJobCreator;
import com.moisesborges.tvaddict.notification.newepisode.EpisodesNotificationSchedulerJob;
import com.moisesborges.tvaddict.notification.showupdate.UpdateShowJob;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by Moisés on 11/04/2017.
 */

public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!installLeakCanary()) return;

        FlowManager.init(this);

        JobManager.create(this).addJobCreator(new BackgroundJobCreator());

        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });

        mAppComponent = createComponent();

        scheduleJobs();
    }


    private boolean installLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return false;
        }
        LeakCanary.install(this);
        return true;
    }

    protected AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .netModule(new NetModule(getApplicationContext()))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }


    private void scheduleJobs() {
        EpisodesNotificationSchedulerJob.schedule();
        UpdateShowJob.schedule();
    }
}
