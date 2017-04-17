package com.moisesborges.tvaddict;

import com.moisesborges.tvaddict.data.ShowsRepository;
import com.moisesborges.tvaddict.data.ShowsRepositoryImpl;
import com.moisesborges.tvaddict.di.DaggerConstrants;
import com.moisesborges.tvaddict.net.TvMazeApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Moisés on 16/04/2017.
 */
@Module
public class DataModule {

    @Provides
    public ShowsRepository providesShowsRepository(TvMazeApi tvMazeApi) {
        return new ShowsRepositoryImpl(tvMazeApi);
    }

    @Provides
    @Named(DaggerConstrants.Names.ANDROID_SCHEDULER)
    public Scheduler provideAndroidScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Named(DaggerConstrants.Names.IO_SCHEDULER)
    public Scheduler provideIoScheduler() {
        return Schedulers.io();
    }
}
