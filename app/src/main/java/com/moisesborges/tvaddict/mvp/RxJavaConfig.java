package com.moisesborges.tvaddict.mvp;

import io.reactivex.Scheduler;

/**
 * Created by moises.anjos on 19/04/2017.
 */

public interface RxJavaConfig {
    Scheduler ioScheduler();
    Scheduler androidScheduler();
}
