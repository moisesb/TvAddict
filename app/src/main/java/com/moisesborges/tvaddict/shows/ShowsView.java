package com.moisesborges.tvaddict.shows;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.models.ShowInfo;
import com.moisesborges.tvaddict.mvp.BaseView;

import java.util.List;

/**
 * Created by Moisés on 12/04/2017.
 */

public interface ShowsView extends BaseView {
    void displayProgress(boolean loading);

    void displayTvShows(@NonNull List<ShowInfo> showInfos);

    void displayError();
}
