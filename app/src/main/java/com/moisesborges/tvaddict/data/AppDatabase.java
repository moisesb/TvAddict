package com.moisesborges.tvaddict.data;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Moisés on 22/04/2017.
 */

@Database(
        name = AppDatabase.NAME,
        version = AppDatabase.VERSION
)
public class AppDatabase {
    public static final String NAME = "TvAddictDataBase";
    public static final int VERSION = 1;
}
