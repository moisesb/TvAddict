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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moisesborges.tvaddict.App;
import com.moisesborges.tvaddict.R;
import com.moisesborges.tvaddict.adapters.ItemClickListener;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.showdetails.ShowDetailsActivity;
import com.moisesborges.tvaddict.utils.ProgressBarHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    public static class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ViewHolder> {

        private final List<Show> mShows = new ArrayList<>();
        private final ItemClickListener<Show> mListener;

        public ShowsAdapter(@Nullable ItemClickListener<Show> listener) {
            mListener = listener;
        }

        public void setShows(@NonNull List<Show> shows) {
            mShows.clear();
            mShows.addAll(shows);
            notifyDataSetChanged();
        }

        @Override
        public ShowsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_show, parent, false);
            return new ViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(ShowsAdapter.ViewHolder holder, int position) {
            Show show = mShows.get(position);
            holder.bind(show);
        }

        @Override
        public int getItemCount() {
            return mShows.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.show_name_text_view)
            TextView mShowNameTextView;
            @BindView(R.id.show_image_view)
            ImageView mImageView;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @OnClick(R.id.show_item_layout)
            @SuppressWarnings("unused")
            public void onShowClick() {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    final Show show = mShows.get(position);
                    mListener.consume(show);
                }
            }

            public void bind(Show show) {
                Glide.with(itemView.getContext())
                        .load(show.getImage().getMedium())
                        .into(mImageView);
                mShowNameTextView.setText(show.getName());
            }
        }
    }

}
