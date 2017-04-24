package com.moisesborges.tvaddict.data;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.models.Image;
import com.moisesborges.tvaddict.models.Show;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by moises.anjos on 24/04/2017.
 */

public class ShowDbImpl implements ShowDb {
    @Override
    public Completable save(Show show) {
        return Completable.fromCallable(saveShow(show));
    }

    @NonNull
    private Callable<Object> saveShow(Show show) {
        return () -> FlowManager.getModelAdapter(Show.class).save(show);
    }

    @Override
    public Single<List<Show>> findAllShows() {
        return RXSQLite.rx(SQLite.select()
                .from(Show.class))
                .queryList();
    }
}
