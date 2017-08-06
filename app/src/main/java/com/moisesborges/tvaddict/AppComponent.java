package com.moisesborges.tvaddict;

import com.moisesborges.tvaddict.episodes.EpisodesActivity;
import com.moisesborges.tvaddict.notification.newepisode.EpisodeNotificationJob;
import com.moisesborges.tvaddict.notification.newepisode.EpisodesNotificationSchedulerJob;
import com.moisesborges.tvaddict.notification.showupdate.UpdateShowJob;
import com.moisesborges.tvaddict.search.SearchActivity;
import com.moisesborges.tvaddict.showdetails.ShowDetailsActivity;
import com.moisesborges.tvaddict.shows.ShowsFragment;
import com.moisesborges.tvaddict.upcoming.UpcomingEpisodesFragment;
import com.moisesborges.tvaddict.watching.WatchingShowsFragment;

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
    void inject(ShowDetailsActivity activity);

    void inject(EpisodesActivity activity);

    void inject(SearchActivity activity);

    void inject(ShowsFragment fragment);

    void inject(WatchingShowsFragment fragment);

    void inject(UpcomingEpisodesFragment fragment);

    void inject(UpdateShowJob job);

    void inject(EpisodesNotificationSchedulerJob job);

    void inject(EpisodeNotificationJob job);
}
