package com.moisesborges.tvaddict.data;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Season_Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Moisés on 27/04/2017.
 */

public class SeasonDbImpl implements SeasonDb {


    @Override
    public Single<Season> getSeason(int showId, int seasonNumber) {
        return RXSQLite.rx(showSeasons(showId)
                .and(Season_Table.number.eq(seasonNumber)))
                .querySingle();
    }

    @NonNull
    private Where<Season> showSeasons(int showId) {
        return SQLite.select()
                .from(Season.class)
                .where(Season_Table.showId_id.eq(showId));
    }

    @Override
    public Single<List<Season>> getSeasons(int showId) {
        return RXSQLite.rx(showSeasons(showId))
                .queryList();
    }

    @Override
    public Completable update(Season season) {
        return Completable.fromRunnable(updateSeason(season));
    }

    @NonNull
    private Runnable updateSeason(Season season) {
        return () -> FlowManager.getModelAdapter(Season.class).update(season);
    }

    @Override
    public void saveSeasons(List<Season> seasons) {
        FlowManager.getModelAdapter(Season.class)
                .saveAll(seasons);
    }
}
