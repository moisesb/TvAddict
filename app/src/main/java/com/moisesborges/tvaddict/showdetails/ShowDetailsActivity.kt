package com.moisesborges.tvaddict.showdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.moisesborges.tvaddict.App
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.adapters.CastAdapter
import com.moisesborges.tvaddict.adapters.ItemClickListener
import com.moisesborges.tvaddict.adapters.SeasonsAdapter
import com.moisesborges.tvaddict.episodes.EpisodesActivity
import com.moisesborges.tvaddict.models.CastMember
import com.moisesborges.tvaddict.models.Externals
import com.moisesborges.tvaddict.models.Season
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.utils.HtmlUtils
import com.moisesborges.tvaddict.utils.ProgressBarHelper
import kotlinx.android.synthetic.main.activity_show_details.*
import kotlinx.android.synthetic.main.content_main_show_details.*
import javax.inject.Inject

/**
 * Created by Moisés on 17/04/2017.
 */

class ShowDetailsActivity : AppCompatActivity(), ShowDetailsView {

    @Inject
    internal lateinit var showDetailsPresenter: ShowDetailsPresenter

    private lateinit var show: Show


    private val seasonItemClickListener = ItemClickListener<Season> { season -> showDetailsPresenter.openEpisodes(show, season) }

    private val seasonsAdapter = SeasonsAdapter(seasonItemClickListener)

    private val castAdapter = CastAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_details)
        setupToolbar()
        setupRecyclerViews()
        injectDependencies()
        show = intent.getParcelableExtra(SHOW_ARG)
        save_show_float_action_button.setOnClickListener { showDetailsPresenter.changeWatchingStatus(show) }
    }

    private fun setupRecyclerViews() {
        seasons_recycler_view.layoutManager = GridLayoutManager(this, 3)
        seasons_recycler_view.setHasFixedSize(true)
        seasons_recycler_view.adapter = seasonsAdapter

        cast_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                //GridLayoutManager(this, 3)
        cast_recycler_view.setHasFixedSize(true)
        cast_recycler_view.adapter = castAdapter
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ""
    }

    override fun onStart() {
        super.onStart()
        showDetailsPresenter.bindView(this)
        showDetailsPresenter.loadShowDetails(show)
    }

    override fun onStop() {
        super.onStop()
        showDetailsPresenter.unbindView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun injectDependencies() {
        (applicationContext as App).appComponent.inject(this)
    }

    override fun setShowName(showName: String) {
        show_name_text_view.text = showName
        toolbar.title = showName
    }

    override fun setShowImage(imageUrl: String) {
        Glide.with(this)
                .load(imageUrl)
                .into(show_image_view)
    }

    override fun setShowSummary(summary: String) {
        show_summary_text_view.text = HtmlUtils.extractFromHtml(summary)
    }

    override fun displaySeasons(seasons: List<Season>) {
        seasonsAdapter.setSeasons(seasons)
    }

    override fun displayAdditionalInfoLoadingInProgress(isLoading: Boolean) {
        ProgressBarHelper.show(seasons_progress_bar, isLoading)
        ProgressBarHelper.show(cast_progress_bar, isLoading)
    }

    override fun displayAdditionalInfoNotLoaded(hasError: Boolean) {

    }

    override fun setShow(show: Show) {
        this.show = show
    }

    override fun navigateToEpisodes(show: Show, seasonNumber: Int) {
        EpisodesActivity.start(this, show, seasonNumber)
    }

    override fun displaySavedShowMessage() {
        showToast(R.string.show_saved_with_success)
    }

    private fun showToast(@StringRes message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun displayCastMembers(castMembers: List<CastMember>) {
        castAdapter.setCast(castMembers)
    }

    override fun displaySaveShowButton(shouldDisplaySavedAction: Boolean) {
        save_show_float_action_button.setImageResource(if (shouldDisplaySavedAction)
            R.drawable.ic_add_white_24px
        else
            R.drawable.ic_clear_white_24px)
        save_show_float_action_button.isEnabled = true
    }

    override fun displayShowRemovedMessage() {
        showToast(R.string.show_removed_with_success)
    }

    override fun setShowRating(rating: Double?) {
        val ratingLabel = rating?.toString() ?: resources.getString(R.string.not_available)
        show_rating_text_view.text = ratingLabel
    }

    override fun setShowRuntime(runtime: Int?) {
        val runtimeLabel = if (runtime != null)
            resources.getString(R.string.runtime_label, runtime)
        else
            resources.getString(R.string.not_available)
        show_runtime_text_view.text = runtimeLabel
    }

    override fun setShowNetwork(networkName: String) {
        show_network_text_view.text = networkName
    }

    override fun setShowGenres(genres: List<String>) {
        show_genres_text_view.text = listOfGenres(genres)
    }

    private fun listOfGenres(genres: List<String>): String {
        val builder = StringBuilder()
        for (pos in genres.indices) {
            if (pos != 0) {
                builder.append(", ")
            }
            builder.append(genres[pos])
        }
        return builder.toString()
    }

    override fun setShowExternalLinks(externals: Externals) {

    }

    companion object {

        private val SHOW_ARG = "com.moisesborges.tvaddict.showdetails.ShowDetailsActivity.show"

        @JvmStatic
        fun start(context: Context, show: Show) {
            val intent = Intent(context, ShowDetailsActivity::class.java)
            intent.putExtra(SHOW_ARG, show)
            context.startActivity(intent)
        }
    }


}
