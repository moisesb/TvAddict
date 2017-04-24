package com.moisesborges.tvaddict.showdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.moisesborges.tvaddict.App;
import com.moisesborges.tvaddict.R;
import com.moisesborges.tvaddict.adapters.ItemClickListener;
import com.moisesborges.tvaddict.episodes.EpisodesActivity;
import com.moisesborges.tvaddict.models.Embedded;
import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.utils.ProgressBarHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Moisés on 17/04/2017.
 */

public class ShowDetailsActivity extends AppCompatActivity implements ShowDetailsView {

    private static final String SHOW_ARG = "com.moisesborges.tvaddict.showdetails.ShowDetailsActivity.show";

    @Inject
    ShowDetailsPresenter mShowDetailsPresenter;

    private Unbinder mUnbinder;
    private Show mShow;

    @BindView(R.id.show_image_view)
    ImageView mShowImageView;
    @BindView(R.id.show_summary_text_view)
    TextView mShowSummaryTextView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.seasons_recycler_view)
    RecyclerView mSeasonsRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private ItemClickListener<Season> mSeasonItemClickListener = (season) -> mShowDetailsPresenter.openEpisodes(mShow, season);

    private SeasonsAdapter mAdapter = new SeasonsAdapter(mSeasonItemClickListener);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        mUnbinder = ButterKnife.bind(this);
        setupToolbar();
        setupRecyclerView();
        injectDependecies();
        mShow = getIntent().getParcelableExtra(SHOW_ARG);
    }

    private void setupRecyclerView() {
        mSeasonsRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mSeasonsRecyclerView.setHasFixedSize(true);
        mSeasonsRecyclerView.setAdapter(mAdapter);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mShowDetailsPresenter.bindView(this);
        mShowDetailsPresenter.loadShowDetails(mShow);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mShowDetailsPresenter.unbindView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    private void injectDependecies() {
        ((App) getApplicationContext()).getAppComponent().inject(this);
    }

    public static void start(@NonNull Context context, @NonNull Show show) {
        Intent intent = new Intent(context, ShowDetailsActivity.class);
        intent.putExtra(SHOW_ARG, show);
        context.startActivity(intent);
    }

    @OnClick(R.id.save_show_float_action_button)
    public void onSaveShowClick(View view) {
        mShowDetailsPresenter.addToWatchingList(mShow);
    }

    @Override
    public void setShowName(String showName) {
        this.setTitle(showName);
    }

    @Override
    public void setShowImage(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .into(mShowImageView);
    }

    @Override
    public void setShowSummary(String summary) {
        mShowSummaryTextView.setText(extractFromHtml(summary));
    }

    @Override
    public void displaySeasons(@NonNull List<Season> seasons) {
        mAdapter.setSeasons(seasons);
    }

    @Override
    public void displaySeasonsProgress(boolean isLoading) {
        ProgressBarHelper.show(mProgressBar, isLoading);
    }

    @Override
    public void displaySeasonsNotLoaded(boolean hasError) {

    }

    @Override
    public void setShow(@NonNull Show show) {
        mShow = show;
    }

    @Override
    public void navigateToEpisodes(int showId, int seasonNumber, Embedded embeddedData) {
        EpisodesActivity.start(this, showId, seasonNumber, embeddedData);
    }

    @Override
    public void displaySavedShowMessage() {
        Toast.makeText(this, R.string.show_saved_with_success, Toast.LENGTH_SHORT).show();
    }

    private Spanned extractFromHtml(String html) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(html);
        }
    }


    public static class SeasonsAdapter extends RecyclerView.Adapter<SeasonsAdapter.ViewHolder> {

        private final List<Season> mSeasons = new ArrayList<>();
        private final ItemClickListener<Season> mItemClickListener;

        public SeasonsAdapter(@Nullable ItemClickListener<Season> itemClickListener) {
            super();
            mItemClickListener = itemClickListener;
        }

        public void setSeasons(@NonNull List<Season> seasons) {
            mSeasons.clear();
            mSeasons.addAll(seasons);
            notifyDataSetChanged();
        }

        @Override
        public SeasonsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_season, parent, false);
            return new ViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(SeasonsAdapter.ViewHolder holder, int position) {
            Season season = mSeasons.get(position);
            holder.bind(season);
        }

        @Override
        public int getItemCount() {
            return mSeasons.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.season_image_view)
            ImageView mSeasonImageView;
            @BindView(R.id.season_text_view)
            TextView mNumberTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @OnClick(R.id.season_item_layout)
            public void onSeasonClick() {
                if (mItemClickListener == null) {
                    return;
                }

                int position = getAdapterPosition();
                Season season = mSeasons.get(position);
                mItemClickListener.consume(season);
            }

            public void bind(@NonNull Season season) {
                mNumberTextView.setText(season.getName());

                if (season.getImage() != null) {
                    Glide.with(itemView.getContext())
                            .load(season.getImage().getMedium())
                            .into(mSeasonImageView);
                }
            }
        }
    }
}
