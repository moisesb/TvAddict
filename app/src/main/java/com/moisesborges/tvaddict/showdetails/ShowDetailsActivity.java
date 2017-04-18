package com.moisesborges.tvaddict.showdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moisesborges.tvaddict.App;
import com.moisesborges.tvaddict.R;
import com.moisesborges.tvaddict.models.Show;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.show_name_text_view)
    TextView mShowNameTextView;
    @BindView(R.id.show_summary_text_view)
    TextView mShowSummaryTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        mUnbinder = ButterKnife.bind(this);
        injectDependecies();
        mShow = getIntent().getParcelableExtra(SHOW_ARG);
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

    @Override
    public void setShowName(String showName) {
        mShowNameTextView.setText(showName);
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

    private Spanned extractFromHtml(String html) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(html);
        }
    }
}
