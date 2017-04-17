package com.moisesborges.tvaddict;

import com.moisesborges.tvaddict.shows.ShowsFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Moisés on 11/04/2017.
 */
@Component(modules = {
        NetModule.class,
        DataModule.class
})
@Singleton
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(ShowsFragment fragment);
}
