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
    private Runnable saveShow(Show show) {
        return () -> {
            FlowManager.getModelAdapter(Show.class).save(show);

            saveShowEmbeddedInfo(show);
        };
    }

    private void saveShowEmbeddedInfo(Show show) {
        final Embedded embedded = show.getEmbedded();
        if (embedded == null) {
            return;
        }

        final Integer showId = show.getId();
        saveShowEmbeddedData(embedded.getEpisodes(), Episode.class, episode -> episode.setShowId(showId));
        saveShowEmbeddedData(embedded.getSeasons(), Season.class, season -> season.setShowId(showId));
        saveShowEmbeddedData(embedded.getCast(), CastMember.class, castMember -> castMember.setShowId(showId));
    }

    @Override
    public Completable remove(Show show) {
        return Completable.fromRunnable(removeShow(show));
    }

    @NonNull
    private Runnable removeShow(Show show) {
        return () -> SQLite.delete()
                .from(Show.class)
                .where(Show_Table.id.is(show.getId()))
                .execute();
    }


    private <T> void saveShowEmbeddedData(@Nullable List<T> data,
                                          @NonNull Class<T> type,
                                          @NonNull Consumer<T> consumer) {
        boolean containsData = data != null;
        if (!containsData) {
            return;
        }

        ModelAdapter<T> modelAdapter = FlowManager.getModelAdapter(type);
        for (T object : data) {
            consumer.consume(object);
            modelAdapter.save(object);
        }
    }


    @Override
    public Single<List<Show>> findAllShows() {
        return RXSQLite.rx(SQLite.select()
                .from(Show.class))
                .queryList();
    }

    @FunctionalInterface
    private interface Consumer<T> {
        void consume(T t);
    }
}
