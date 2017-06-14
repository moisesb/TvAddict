package com.moisesborges.tvaddict.upcoming


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.adapters.UpcomingEpisodesAdapter
import kotlinx.android.synthetic.main.fragment_upcoming_episodes.*

class UpcomingEpisodesFragment : Fragment() {

    private val adapter = UpcomingEpisodesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_upcoming_episodes, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        upcoming_episodes_recycler_view.layoutManager = LinearLayoutManager(context)
        upcoming_episodes_recycler_view.adapter = adapter
    }


    companion object {

        @JvmStatic
        fun newInstance(): UpcomingEpisodesFragment {
            return UpcomingEpisodesFragment()
        }
    }

}
