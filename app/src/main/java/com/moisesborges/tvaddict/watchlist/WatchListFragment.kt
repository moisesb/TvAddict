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
import com.moisesborges.tvaddict.mvp.ContentObserverView
import com.moisesborges.tvaddict.showdetails.ShowDetailsActivity
import com.moisesborges.tvaddict.ui.SpacesItemDecoration
import com.raizlabs.android.dbflow.runtime.FlowContentObserver
import kotlinx.android.synthetic.main.fragment_watching_shows.*
import javax.inject.Inject

class WatchListFragment : Fragment(), WatchListView, ContentObserverView {

    @Inject
    internal lateinit var presenter: WatchListPresenter
    private var emptyWatchListView: View? = null

    private val openShowDetailsListener = ItemClickListener<Show> { show -> presenter.openShowDetails(show) }

    private val adapter = ShowsAdapter( openShowDetailsListener, null)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
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
        injectDependencies()
    }


    private fun setupRecyclerView() {
        val rows = 3
        shows_recycler_view.layoutManager = GridLayoutManager(context, rows)
        shows_recycler_view.setHasFixedSize(true)
        shows_recycler_view.addItemDecoration(SpacesItemDecoration(16, rows))
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
        if (emptyWatchListView == null) {
            emptyWatchListView = watching_no_shows_view_stub.inflate()
        } else {
            emptyWatchListView?.visibility = View.VISIBLE
        }
    }

    override fun hideEmptyListMessage() {
        emptyWatchListView?.visibility = View.GONE
    }

    override fun navigateToShowDetails(show: Show) {
        ShowDetailsActivity.start(context, show)
    }

    override fun <T> registerContentObserver(contentObserver: FlowContentObserver, classOf: Class<T>) {
        contentObserver.registerForContentChanges(context, classOf)
    }

    override fun unregisterContentObserver(contentObserver: FlowContentObserver) {
        contentObserver.unregisterForContentChanges(context)
    }

    companion object {

        @JvmStatic
        fun newInstance(): Fragment {
            return WatchListFragment()
        }
    }
}
