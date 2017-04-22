package com.moisesborges.tvaddict.episodes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moisesborges.tvaddict.App;
import com.moisesborges.tvaddict.R;
import com.moisesborges.tvaddict.models.Embedded;
import com.moisesborges.tvaddict.models.Episode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EpisodesActivity extends AppCompatActivity implements EpisodesView {

    public static final String ARG_SHOW_ID = "com.moisesborges.tvaddict.episodesshow.EpisodesActivity.showId";
    public static final String ARG_SEASON_NUMBER = "com.moisesborges.tvaddict.episodesshow.EpisodesActivity.seasonNumber";
    public static final String ARG_EMBEDDED = "com.moisesborges.tvaddict.episodesshow.EpisodesActivity.embedded";

    private int mShowId;
    private int mSeasonNumber;
    private Embedded mEmbeddedData;

    @BindView(R.id.episodes_recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    EpisodesPresenter mEpisodesPresenter;

    private EpisodesAdapter mEpisodesAdapter = new EpisodesAdapter();

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);
        mUnbinder = ButterKnife.bind(this);
        ((App) getApplicationContext()).getAppComponent().inject(this);

        mShowId = getIntent().getIntExtra(ARG_SHOW_ID, 0);
        mSeasonNumber = getIntent().getIntExtra(ARG_SEASON_NUMBER, 0);
        mEmbeddedData = getIntent().getParcelableExtra(ARG_EMBEDDED);

        setupRecyclerView();
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
        mEpisodesPresenter.loadEpisodes(mSeasonNumber, mEmbeddedData);
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
                             int showId,
                             int seasonNumber,
                             @NonNull Embedded embeddedData) {
        Intent intent = new Intent(context, EpisodesActivity.class);
        intent.putExtra(ARG_SHOW_ID, showId);
        intent.putExtra(ARG_SEASON_NUMBER, seasonNumber);
        intent.putExtra(ARG_EMBEDDED, embeddedData);
        context.startActivity(intent);
    }

    @Override
    public void displayEpisodes(@NonNull List<Episode> episodes) {
        mEpisodesAdapter.setEpisodes(episodes);
    }

    public static class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.ViewHolder> {


        private final List<Episode> mEpisodes = new ArrayList<>();

        public EpisodesAdapter() {
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

        public class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.episode_name_text_view)
            TextView mEpisodeNameTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bind(@NonNull Episode episode) {
                mEpisodeNameTextView.setText(episode.getName());
            }
        }
    }
}
