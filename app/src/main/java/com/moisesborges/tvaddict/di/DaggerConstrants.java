package com.moisesborges.tvaddict.di;

/**
 * Created by Moisés on 16/04/2017.
 */

public abstract class DaggerConstrants {

    private DaggerConstrants() {

    }

    public abstract static class Names {

        public static final String ANDROID_SCHEDULER = "AndroidScheduler";
        public static final String IO_SCHEDULER = "IOScheduler";

        private Names() {

        }

    }
}
