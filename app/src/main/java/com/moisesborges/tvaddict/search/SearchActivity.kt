package com.moisesborges.tvaddict.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.moisesborges.tvaddict.App
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.adapters.SearchResultAdapter
import com.moisesborges.tvaddict.models.Show
import kotlinx.android.synthetic.main.activity_search.*
import timber.log.Timber
import javax.inject.Inject

class SearchActivity : AppCompatActivity(), SearchView {

    @Inject lateinit var searchPresenter: SearchPresenter
    private val adapter =  SearchResultAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        injectDepencies()
        setupRecyclerView()
        searchPresenter.bindView(this)

        search_view.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchShows(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    adapter.clear()
                }
                return false
            }

        })

        val intent = intent
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            handleIntent(intent)
        }
    }

    private fun handleIntent(intent: Intent) {
        if (intent.action == Intent.ACTION_SEARCH) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            searchShows(query)
        }
    }

    private fun setupRecyclerView() {
        results_recycler_view.layoutManager = LinearLayoutManager(this)
        results_recycler_view.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        searchPresenter.unbindView()
    }

    private fun injectDepencies() {
        (applicationContext as App).appComponent.inject(this)
    }

    private fun searchShows(query: String?) {
        searchPresenter.searchShow(query ?: "")
    }

    override fun displayProgress(loading: Boolean) {
        progress_bar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    override fun displayResults(result: List<Show>) {
        adapter.setResult(result)
    }

    companion object {

        @JvmStatic
        fun newIntent(context: Context): Intent = Intent(context, SearchActivity::class.java)

    }
}
