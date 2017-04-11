package com.moisesborges.tvaddict;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Moisés on 11/04/2017.
 */

public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });

        mAppComponent = createComponent();
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
