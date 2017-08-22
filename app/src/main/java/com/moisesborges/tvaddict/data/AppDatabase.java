package com.moisesborges.tvaddict.data;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.migration.BaseMigration;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

/**
 * Created by Moisés on 22/04/2017.
 */

@Database(
        name = AppDatabase.NAME,
        version = AppDatabase.VERSION
)
public class AppDatabase {
    public static final String NAME = "TvAddictDataBase";
    public static final int VERSION = 2;

    @Migration(version = 2, database = AppDatabase.class)
    public static class Migration2 extends BaseMigration {

        @Override
        public void migrate(@NonNull DatabaseWrapper database) {
            for (Class<?> klass : FlowManager.getDatabase(AppDatabase.class).getModelClasses()) {
                ModelAdapter modelAdapter = FlowManager.getModelAdapter(klass);
                database.execSQL("DROP TABLE IF EXISTS " + modelAdapter.getTableName());
                database.execSQL(modelAdapter.getCreationQuery());
            }
        }
    }
}
