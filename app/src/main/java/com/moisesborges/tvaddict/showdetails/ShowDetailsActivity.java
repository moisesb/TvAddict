package com.moisesborges.tvaddict.showdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.moisesborges.tvaddict.App;
import com.moisesborges.tvaddict.R;
import com.moisesborges.tvaddict.adapters.CastAdapter;
import com.moisesborges.tvaddict.adapters.ItemClickListener;
import com.moisesborges.tvaddict.adapters.SeasonsAdapter;
import com.moisesborges.tvaddict.episodes.EpisodesActivity;
import com.moisesborges.tvaddict.models.CastMember;
import com.moisesborges.tvaddict.models.Externals;
import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.utils.ProgressBarHelper;

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
    @BindView(R.id.seasons_progress_bar)
    ProgressBar mSeasonsProgressBar;
    @BindView(R.id.cast_recycler_view)
    RecyclerView mCastRecyclerView;
    @BindView(R.id.cast_progress_bar)
    ProgressBar mCastProgressBar;
    @BindView(R.id.save_show_float_action_button)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.show_genres_text_view)
    TextView mGenresTextView;
    @BindView(R.id.show_network_text_view)
    TextView mNetworkTextView;
    @BindView(R.id.show_runtime_text_view)
    TextView mRuntimeTextView;
    @BindView(R.id.show_ration_text_view)
    TextView mRatingTextView;

    private ItemClickListener<Season> mSeasonItemClickListener = (season) -> mShowDetailsPresenter.openEpisodes(mShow, season);

    private SeasonsAdapter mSeasonsAdapter = new SeasonsAdapter(mSeasonItemClickListener);

    private CastAdapter mCastAdapter = new CastAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        mUnbinder = ButterKnife.bind(this);
        setupToolbar();
        setupRecyclerViews();
        injectDependencies();
        mShow = getIntent().getParcelableExtra(SHOW_ARG);
    }

    private void setupRecyclerViews() {
        mSeasonsRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mSeasonsRecyclerView.setHasFixedSize(true);
        mSeasonsRecyclerView.setAdapter(mSeasonsAdapter);

        mCastRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mCastRecyclerView.setHasFixedSize(true);
        mCastRecyclerView.setAdapter(mCastAdapter);
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

    private void injectDependencies() {
        ((App) getApplicationContext()).getAppComponent().inject(this);
    }

    public static void start(@NonNull Context context, @NonNull Show show) {
        Intent intent = new Intent(context, ShowDetailsActivity.class);
        intent.putExtra(SHOW_ARG, show);
        context.startActivity(intent);
    }

    @OnClick(R.id.save_show_float_action_button)
    public void onSaveShowClick(View view) {
        mShowDetailsPresenter.changeWatchingStatus(mShow);
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
        mSeasonsAdapter.setSeasons(seasons);
    }

    @Override
    public void displayAdditionalInfoLoadingInProgress(boolean isLoading) {
        ProgressBarHelper.show(mSeasonsProgressBar, isLoading);
        ProgressBarHelper.show(mCastProgressBar, isLoading);
    }

    @Override
    public void displayAdditionalInfoNotLoaded(boolean hasError) {

    }

    @Override
    public void setShow(@NonNull Show show) {
        mShow = show;
    }

    @Override
    public void navigateToEpisodes(Show show, int seasonNumber) {
        EpisodesActivity.start(this, show, seasonNumber);
    }

    @Override
    public void displaySavedShowMessage() {
        showToast(R.string.show_saved_with_success);
    }

    private void showToast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayCastMembers(List<CastMember> castMembers) {
        mCastAdapter.setCast(castMembers);
    }

    @Override
    public void displaySaveShowButton(boolean shouldDisplaySavedAction) {
        mFloatingActionButton.setImageResource(shouldDisplaySavedAction ?
                R.drawable.ic_add_white_24px : R.drawable.ic_clear_white_24px);
        mFloatingActionButton.setEnabled(true);
    }

    @Override
    public void displayShowRemovedMessage() {
        showToast(R.string.show_removed_with_success);
    }

    @Override
    public void setShowRating(Double rating) {
        String ratingLabel = rating != null ?
                rating.toString() : getResources().getString(R.string.not_available);
        mRatingTextView.setText(ratingLabel);
    }

    @Override
    public void setShowRuntime(Integer runtime) {
        String runtimeLabel = runtime != null ?
                getResources().getString(R.string.runtime_label, runtime) : getResources().getString(R.string.not_available);
        mRuntimeTextView.setText(runtimeLabel);
    }

    @Override
    public void setShowNetwork(String networkName) {
        mNetworkTextView.setText(networkName);
    }

    @Override
    public void setShowGenres(List<String> genres) {
        mGenresTextView.setText(listOfGenres(genres));
    }

    private String listOfGenres(List<String> genres) {
        StringBuilder builder = new StringBuilder();
        for (int pos = 0; pos < genres.size(); pos++) {
            if (pos != 0) {
                builder.append(" - ");
            }
            builder.append(genres.get(pos));
        }
        return builder.toString();
    }

    @Override
    public void setShowExternalLinks(Externals externals) {

    }

    private Spanned extractFromHtml(String html) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(html);
        }
    }


}
