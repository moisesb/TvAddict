package com.moisesborges.tvaddict.episodes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.moisesborges.tvaddict.App;
import com.moisesborges.tvaddict.R;
import com.moisesborges.tvaddict.adapters.ItemClickListener;
import com.moisesborges.tvaddict.models.Embedded;
import com.moisesborges.tvaddict.models.Episode;
import com.moisesborges.tvaddict.models.Show;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EpisodesActivity extends AppCompatActivity implements EpisodesView {

    public static final String ARG_SEASON_NUMBER = "com.moisesborges.tvaddict.episodesshow.EpisodesActivity.seasonNumber";
    private static final String ARG_SHOW = "com.moisesborges.tvaddict.episodesshow.EpisodesActivity.show";

    private Show mShow;
    private int mSeasonNumber;

    @BindView(R.id.episodes_recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    EpisodesPresenter mEpisodesPresenter;

    private ItemClickListener<Episode> mEpisodeClickListener =
            episode -> mEpisodesPresenter.changeEpisodeSeenStatus(episode, mShow);

    private EpisodesAdapter mEpisodesAdapter = new EpisodesAdapter(mEpisodeClickListener);

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);
        mUnbinder = ButterKnife.bind(this);
        injectDependecies();

        mShow = getIntent().getParcelableExtra(ARG_SHOW);
        mSeasonNumber = getIntent().getIntExtra(ARG_SEASON_NUMBER, 0);

        setupRecyclerView();
    }

    private void injectDependecies() {
        ((App) getApplicationContext()).getAppComponent().inject(this);
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mEpisodesAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mEpisodesPresenter.bindView(this);
        mEpisodesPresenter.loadEpisodes(mSeasonNumber, mShow);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mEpisodesPresenter.unbindView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    public static void start(@NonNull Context context,
                             @NonNull Show show,
                             int seasonNumber) {
        Intent intent = new Intent(context, EpisodesActivity.class);
        intent.putExtra(ARG_SHOW, show);
        intent.putExtra(ARG_SEASON_NUMBER, seasonNumber);
        context.startActivity(intent);
    }

    @Override
    public void displayEpisodes(@NonNull List<Episode> episodes) {
        mEpisodesAdapter.setEpisodes(episodes);
    }

    @Override
    public void refreshEpisode(@NonNull Episode episode) {
        mEpisodesAdapter.updateEpisode(episode);
    }

    @Override
    public void setShow(@NonNull Show showFromDb) {
        mShow = showFromDb;
    }

    public static class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.ViewHolder> {

        private final List<Episode> mEpisodes = new ArrayList<>();
        private final ItemClickListener<Episode> mClickListener;

        public EpisodesAdapter(@Nullable ItemClickListener<Episode> clickListener) {
            mClickListener = clickListener;
        }

        @Override
        public EpisodesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_episode, parent, false);
            return new ViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(EpisodesAdapter.ViewHolder holder, int position) {
            Episode episode = mEpisodes.get(position);
            holder.bind(episode);
        }

        @Override
        public int getItemCount() {
            return mEpisodes.size();
        }

        public void setEpisodes(@NonNull List<Episode> episodes) {
            mEpisodes.clear();
            mEpisodes.addAll(episodes);
            notifyDataSetChanged();
        }

        public void updateEpisode(@NonNull Episode episode) {
            int indexOf = mEpisodes.indexOf(episode);
            notifyItemChanged(indexOf);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.episode_name_text_view)
            TextView mEpisodeNameTextView;
            @BindView(R.id.watched_check_box)
            CheckBox mWatchedCheckBox;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @OnClick(R.id.watched_check_box)
            @SuppressWarnings("unused")
            public void onWatchedCheckBoxClick() {
                int position = getAdapterPosition();
                Episode episode = mEpisodes.get(position);
                mClickListener.consume(episode);
            }

            public void bind(@NonNull Episode episode) {
                mEpisodeNameTextView.setText(episode.getName());
                mWatchedCheckBox.setChecked(episode.wasWatched());
            }
        }
    }
}
