package com.moisesborges.tvaddict.shows;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.mvp.BaseView;

import java.util.List;

/**
 * Created by Moisés on 12/04/2017.
 */

public interface ShowsView extends BaseView {
    void displayProgress(boolean isLoading);

    void displayTvShows(@NonNull List<Show> shows);

    void displayError();

    void navigateToShowDetails(@NonNull Show show);
}
