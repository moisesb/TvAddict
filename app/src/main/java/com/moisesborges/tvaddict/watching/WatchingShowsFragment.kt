package com.moisesborges.tvaddict.watching


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.moisesborges.tvaddict.App
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.adapters.ItemClickListener
import com.moisesborges.tvaddict.adapters.ShowsAdapter
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.showdetails.ShowDetailsActivity
import kotlinx.android.synthetic.main.fragment_watching_shows.*
import javax.inject.Inject

class WatchingShowsFragment : Fragment(), WatchingShowsView {

    @Inject
    internal lateinit var presenter: WatchingShowsPresenter

    private val adapter = ShowsAdapter(ItemClickListener<Show> { show -> presenter.openShowDetails(show) })

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        injectDependencies()
    }

    private fun injectDependencies() {
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_watching_shows, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view!!)
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        shows_recycler_view.layoutManager = GridLayoutManager(context, 3)
        shows_recycler_view.setHasFixedSize(true)
        shows_recycler_view.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        presenter.bindView(this)
        presenter.loadWatchingShows()
    }

    override fun onStop() {
        super.onStop()
        presenter.unbindView()
    }

    override fun displayWatchingShows(shows: List<Show>) {
        adapter.setShows(shows)
    }

    override fun displayEmptyListMessage() {
        watching_no_shows_view_stub.inflate()
    }

    override fun navigateToShowDetails(show: Show) {
        ShowDetailsActivity.start(context, show)
    }

    companion object {

        @JvmStatic
        fun newInstance(): Fragment {
            return WatchingShowsFragment()
        }
    }
}