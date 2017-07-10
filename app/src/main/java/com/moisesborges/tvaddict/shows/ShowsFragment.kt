package com.moisesborges.tvaddict.shows

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moisesborges.tvaddict.App
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.adapters.ItemClickListener
import com.moisesborges.tvaddict.adapters.ShowsAdapter
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.showdetails.ShowDetailsActivity
import com.moisesborges.tvaddict.utils.ProgressBarHelper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_shows.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Moisés on 12/04/2017.
 */

class ShowsFragment : Fragment(), ShowsView {

    @Inject
    internal lateinit var showsPresenter: ShowsPresenter

    private val showItemClickListener: ItemClickListener<Show> = ItemClickListener { show -> showsPresenter.openShowDetails(show) }

    private val showsAdapter = ShowsAdapter(showItemClickListener)

    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context!!.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater!!.inflate(R.layout.fragment_shows, container, false)
        retainInstance = true
        return layout
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart")
        showsPresenter.bindView(this)
        showsPresenter.loadShows()
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable.add(listenToPageEvents())
    }

    private fun listenToPageEvents(): Disposable {
        return showsAdapter.lastItemReachedEvent()
                .subscribe({ currentPage -> showsPresenter.loadMoreShows(currentPage) },
                        { error -> Timber.e(error) })
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    override fun onStop() {
        super.onStop()
        showsPresenter.unbindView()
    }

    private fun setupRecyclerView() {
        shows_recycler_view.layoutManager = GridLayoutManager(context, 3)
        shows_recycler_view.adapter = showsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun displayProgress(isLoading: Boolean) {
        ProgressBarHelper.show(progress_bar, isLoading)
    }

    override fun displayTvShows(shows: List<Show>) {
        showsAdapter.setShows(shows)
    }

    override fun displayError() {

    }

    override fun navigateToShowDetails(show: Show) {
        ShowDetailsActivity.start(context, show)
    }

    override fun setPage(page: Int) {
        showsAdapter.setPage(page)
    }

    override fun displayMoreTvShows(shows: List<Show>) {
        showsAdapter.addShows(shows)
    }

    companion object {

        @JvmStatic
        fun newInstance(): Fragment {
            return ShowsFragment()
        }
    }

}

