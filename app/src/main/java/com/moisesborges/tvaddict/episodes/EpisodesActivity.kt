package com.moisesborges.tvaddict.episodes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.moisesborges.tvaddict.App
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.adapters.EpisodesAdapter
import com.moisesborges.tvaddict.adapters.ItemClickListener
import com.moisesborges.tvaddict.models.Episode
import com.moisesborges.tvaddict.models.Show
import kotlinx.android.synthetic.main.activity_episodes.*
import javax.inject.Inject

class EpisodesActivity : AppCompatActivity(), EpisodesView {

    private lateinit var show: Show
    private var seasonNumber: Int = 0

    @Inject
    lateinit var episodesPresenter: EpisodesPresenter

    private val toggleEpisodeStatusListener = ItemClickListener<Episode> { episode -> episodesPresenter.changeEpisodeSeenStatus(episode, show) }
    private val openEpisodeDetailsListener = ItemClickListener<Episode> { episode -> episodesPresenter.openEpisodeDetails(episode) }
    private val mEpisodesAdapter = EpisodesAdapter(toggleEpisodeStatusListener, openEpisodeDetailsListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episodes)
        injectDependecies()

        show = intent.getParcelableExtra<Show>(ARG_SHOW)
        seasonNumber = intent.getIntExtra(ARG_SEASON_NUMBER, 0)

        setupRecyclerView()
    }


    private fun injectDependecies() {
        (applicationContext as App).appComponent.inject(this)
    }

    private fun setupRecyclerView() {
        episodes_recycler_view.layoutManager = LinearLayoutManager(this)
        episodes_recycler_view.setHasFixedSize(true)
        episodes_recycler_view.adapter = mEpisodesAdapter
    }

    override fun onStart() {
        super.onStart()
        episodesPresenter.bindView(this)
        episodesPresenter.loadEpisodes(seasonNumber, show)
    }

    override fun onStop() {
        super.onStop()
        episodesPresenter.unbindView()
    }

    override fun displayEpisodes(episodes: List<Episode>) {
        mEpisodesAdapter.setEpisodes(episodes)
    }

    override fun refreshEpisode(episode: Episode) {
        mEpisodesAdapter.updateEpisode(episode)
    }

    override fun setShow(showFromDb: Show) {
        show = showFromDb
    }

    override fun showEpisodeDetails(episode: Episode) {
        EpisodeDetailsBottomSheet.show(episode, supportFragmentManager)
    }

    companion object {

        private val ARG_SEASON_NUMBER = "com.moisesborges.tvaddict.episodesshow.EpisodesActivity.seasonNumber"
        private val ARG_SHOW = "com.moisesborges.tvaddict.episodesshow.EpisodesActivity.show"

        @JvmStatic
        fun start(context: Context,
                  show: Show,
                  seasonNumber: Int) {
            val intent = newIntent(context, show, seasonNumber)
            context.startActivity(intent)
        }

        @JvmStatic
        fun newIntent(context: Context, show: Show, seasonNumber: Int): Intent {
            val intent = Intent(context, EpisodesActivity::class.java)
            intent.putExtra(ARG_SHOW, show)
            intent.putExtra(ARG_SEASON_NUMBER, seasonNumber)
            return intent
        }
    }
}
