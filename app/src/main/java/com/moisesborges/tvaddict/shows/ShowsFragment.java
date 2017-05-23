package com.moisesborges.tvaddict.shows;

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
import android.widget.ProgressBar;

import com.moisesborges.tvaddict.App;
import com.moisesborges.tvaddict.R;
import com.moisesborges.tvaddict.adapters.ItemClickListener;
import com.moisesborges.tvaddict.adapters.ShowsAdapter;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.showdetails.ShowDetailsActivity;
import com.moisesborges.tvaddict.utils.ProgressBarHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by Moisés on 12/04/2017.
 */

public class ShowsFragment extends Fragment implements ShowsView {

    private Unbinder mUnbinder;

    @BindView(R.id.shows_recycler_view)
    RecyclerView mShowsRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Inject
    ShowsPresenter mShowsPresenter;

    private ItemClickListener<Show> mShowItemClickListener = show -> mShowsPresenter.openShowDetails(show);

    private final ShowsAdapter mShowsAdapter = new ShowsAdapter(mShowItemClickListener);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_shows, container, false);
        setRetainInstance(true);
        mUnbinder = ButterKnife.bind(this, layout);
        setupRecyclerView();
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        Timber.d("onStart");
        mShowsPresenter.bindView(this);
        mShowsPresenter.loadShows();
    }

    @Override
    public void onStop() {
        super.onStop();
        mShowsPresenter.unbindView();
    }

    private void setupRecyclerView() {
        mShowsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mShowsRecyclerView.setAdapter(mShowsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void displayProgress(boolean isLoading) {
        ProgressBarHelper.show(mProgressBar, isLoading);
    }

    @Override
    public void displayTvShows(@NonNull List<Show> shows) {
        mShowsAdapter.setShows(shows);
    }

    @Override
    public void displayError() {

    }

    @Override
    public void navigateToShowDetails(@NonNull Show show) {
        ShowDetailsActivity.start(getContext(), show);
    }

    public static Fragment newInstance() {
        return new ShowsFragment();
    }

}
