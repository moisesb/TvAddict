package com.moisesborges.tvaddict.adapters

import android.support.v7.widget.RecyclerView

/**
 * Created by moises.anjos on 10/07/2017.
 */
class EndelessScrollListener: RecyclerView.OnScrollListener() {

    private var currentPage: Int = 0
    private var isLoading: Boolean = false

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView!!.layoutManager
        val childCount = layoutManager.childCount
    }
}