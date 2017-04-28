package com.moisesborges.tvaddict.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.moisesborges.tvaddict.models.CastMember;
import com.moisesborges.tvaddict.models.Embedded;
import com.moisesborges.tvaddict.models.Episode;
import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.models.Show_Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import java.util.List;

import hugo.weaving.DebugLog;
import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by moises.anjos on 24/04/2017.
 */

public class ShowDbImpl implements ShowDb {

    @Override
    public Completable save(Show show) {
        return Completable.fromRunnable(saveShow(show));
    }

    @NonNull
    @DebugLog
    private Runnable saveShow(Show show) {
        return () -> FlowManager.getModelAdapter(Show.class).save(show);
    }

    @Override
    public Completable remove(int showId) {
        return Completable.fromRunnable(removeShow(showId));
    }

    @NonNull
    @DebugLog
    private Runnable removeShow(int showid) {
        return () -> SQLite.delete()
                .from(Show.class)
                .where(Show_Table.id.is(showid))
                .execute();
    }

    @Override
    public Single<List<Show>> findAllShows() {
        return RXSQLite.rx(SQLite.select()
                .from(Show.class))
                .queryList();
    }

    @Override
    public Single<Show> findShow(int showId) {
        return RXSQLite.rx(SQLite.select()
                .from(Show.class)
                .where(Show_Table.id.eq(showId)))
                .querySingle();
    }

}
