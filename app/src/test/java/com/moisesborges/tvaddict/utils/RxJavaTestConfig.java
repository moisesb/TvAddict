package com.moisesborges.tvaddict.utils;

import com.moisesborges.tvaddict.mvp.RxJavaConfig;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by moises.anjos on 19/04/2017.
 */

public class RxJavaTestConfig implements RxJavaConfig {
    @Override
    public Scheduler ioScheduler() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler androidScheduler() {
        return Schedulers.trampoline();
    }
}
