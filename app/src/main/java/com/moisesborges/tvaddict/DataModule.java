package com.moisesborges.tvaddict;

import com.moisesborges.tvaddict.data.ShowDb;
import com.moisesborges.tvaddict.data.ShowDbImpl;
import com.moisesborges.tvaddict.data.ShowsRepository;
import com.moisesborges.tvaddict.data.ShowsRepositoryImpl;
import com.moisesborges.tvaddict.mvp.RxJavaConfig;
import com.moisesborges.tvaddict.net.TvMazeApi;

import javax.inject.Singleton;

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
    @Singleton
    public ShowDb providesShowDb() {
        return new ShowDbImpl();
    }

    @Provides
    public ShowsRepository providesShowsRepository(TvMazeApi tvMazeApi, ShowDb showDb) {
        return new ShowsRepositoryImpl(tvMazeApi, showDb);
    }

    @Provides
    @Singleton
    public RxJavaConfig providesRxJavaConfig() {
        return new RxJavaConfig() {
            @Override
            public Scheduler ioScheduler() {
                return Schedulers.io();
            }

            @Override
            public Scheduler androidScheduler() {
                return AndroidSchedulers.mainThread();
            }
        };
    }

}
