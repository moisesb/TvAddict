package com.moisesborges.tvaddict.upcoming;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moisesborges.tvaddict.R;

public class UpcomingEpisodesFragment extends Fragment {

    public UpcomingEpisodesFragment() {
    }

    public static UpcomingEpisodesFragment newInstance() {
        return new UpcomingEpisodesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upcoming_episodes, container, false);
    }

}
