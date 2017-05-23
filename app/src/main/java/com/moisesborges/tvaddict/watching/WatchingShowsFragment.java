package com.moisesborges.tvaddict.watching;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.moisesborges.tvaddict.App;
import com.moisesborges.tvaddict.R;
import com.moisesborges.tvaddict.adapters.ShowsAdapter;
import com.moisesborges.tvaddict.models.Show;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WatchingShowsFragment extends Fragment implements WatchingShowsView {

    @Inject
    WatchingShowsPresenter mPresenter;

    @BindView(R.id.shows_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.watching_no_shows_view_stub)
    ViewStub mEmptyListViewStub;

    private ShowsAdapter mAdapter = new ShowsAdapter(null);

    public WatchingShowsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        injectDependencies();
    }

    private void injectDependencies() {
        ((App) getContext().getApplicationContext()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_watching_shows, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupRecyclerView();
    }


    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.bindView(this);
        mPresenter.loadWatchingShows();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.unbindView();
    }

    public static Fragment newInstance() {
        return new WatchingShowsFragment();
    }

    @Override
    public void displayWatchingShows(@NonNull List<Show> shows) {
        mAdapter.setShows(shows);
    }

    @Override
    public void displayEmptyListMessage() {
        mEmptyListViewStub.inflate();
    }
}
