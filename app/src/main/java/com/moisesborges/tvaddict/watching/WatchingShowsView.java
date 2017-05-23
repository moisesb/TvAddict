package com.moisesborges.tvaddict.watching;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.mvp.BaseView;

import java.util.List;

/**
 * Created by Moisés on 22/05/2017.
 */

public interface WatchingShowsView extends BaseView {
    void displayWatchingShows(@NonNull List<Show> shows);

    void displayEmptyListMessage();
}
