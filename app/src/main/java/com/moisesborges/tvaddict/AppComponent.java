package com.moisesborges.tvaddict;

import dagger.Component;

/**
 * Created by Moisés on 11/04/2017.
 */
@Component(modules = {
        NetModule.class
})
public interface AppComponent {

    void inject(MainActivity activity);
}
