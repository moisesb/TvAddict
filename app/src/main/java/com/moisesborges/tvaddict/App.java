package com.moisesborges.tvaddict;

import android.app.Application;

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

        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });

        mAppComponent = createComponent();
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
                .netModule(new NetModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
