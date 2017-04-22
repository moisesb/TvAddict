package com.moisesborges.tvaddict.episodes;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.models.Episode;
import com.moisesborges.tvaddict.mvp.BaseView;

import java.util.List;

/**
 * Created by Moisés on 21/04/2017.
 */

public interface EpisodesView extends BaseView {
    void displayEpisodes(@NonNull  List<Episode> episodes);
}
