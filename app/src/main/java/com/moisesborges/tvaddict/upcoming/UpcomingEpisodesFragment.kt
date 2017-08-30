package com.moisesborges.tvaddict.upcoming


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moisesborges.tvaddict.App
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.adapters.ItemClickListener
import com.moisesborges.tvaddict.adapters.UpcomingEpisodesAdapter
import com.moisesborges.tvaddict.models.UpcomingEpisode
import kotlinx.android.synthetic.main.fragment_upcoming_episodes.*
import javax.inject.Inject

class UpcomingEpisodesFragment : Fragment(), UpcomingEpisodesView {

    private val itemClickListener = ItemClickListener<UpcomingEpisode> {upcomingEpisode -> upcomingEpisodesPresenter.markEpisodeAsSeen(upcomingEpisode)}
    private val adapter = UpcomingEpisodesAdapter(itemClickListener)
    @Inject lateinit var upcomingEpisodesPresenter: UpcomingEpisodesPresenter
    private var noUpcomingEpisodesView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }

    private fun injectDependencies() {
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_upcoming_episodes, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        upcomingEpisodesPresenter.bindView(this)
        upcomingEpisodesPresenter.loadUpcomingEpisodes()
    }

    override fun onStop() {
        super.onStop()
        upcomingEpisodesPresenter.unbindView()
    }

    private fun setupRecyclerView() {
        upcoming_episodes_recycler_view.layoutManager = LinearLayoutManager(context)
        upcoming_episodes_recycler_view.adapter = adapter
        upcoming_episodes_recycler_view.addItemDecoration( DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun displayProgress(loading: Boolean) {
        progress_bar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    override fun displayEpisodes(episodes: List<UpcomingEpisode>) {
        adapter.setEpisodes(episodes)
    }

    override fun displayNoUpcomingEpisodesMessage() {
        if (noUpcomingEpisodesView == null) {
            noUpcomingEpisodesView = no_upcoming_episodes_view_stub.inflate()
        } else {
            noUpcomingEpisodesView?.visibility = View.VISIBLE
        }
    }

    override fun hideNoUpcomingEpisodesMessage() {
        noUpcomingEpisodesView?.visibility = View.GONE
    }

    override fun hideEpisode(upcomingEpisode: UpcomingEpisode) {
        adapter.removeEpisode(upcomingEpisode)
    }

    override fun replaceEpisode(upcomingEpisode: UpcomingEpisode, nextUpcomingEpisode: UpcomingEpisode) {
        adapter.replaceEpisode(upcomingEpisode, nextUpcomingEpisode)
    }

    companion object {

        @JvmStatic
        fun newInstance(): UpcomingEpisodesFragment {
            return UpcomingEpisodesFragment()
        }
    }

}


