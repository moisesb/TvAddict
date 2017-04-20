package com.moisesborges.tvaddict.episodes;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moisesborges.tvaddict.R;

/**
 * Created by moises.anjos on 19/04/2017.
 */

public class EpisodesModalBottomSheet extends BottomSheetDialogFragment {

    private static final String ARG_SHOW_ID = "com.moisesborges.tvaddict.episodes.EpisodesFragment.showId";
    private static final String ARG_SEASON_ID = "com.moisesborges.tvaddict.episodes.EpisodesFragment.seasonId";

    public EpisodesModalBottomSheet() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_episodes, container, false);
    }

    public static Fragment newInstance(int showId, int seasonId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SHOW_ID, showId);
        bundle.putInt(ARG_SEASON_ID, seasonId);

        Fragment fragment = new EpisodesModalBottomSheet();
        fragment.setArguments(bundle);
        return fragment;
    }
}
