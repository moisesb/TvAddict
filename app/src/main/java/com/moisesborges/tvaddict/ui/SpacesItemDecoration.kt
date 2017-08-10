package com.moisesborges.tvaddict.ui

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by moises.anjos on 09/08/2017.
 */
class SpacesItemDecoration(private val space: Int, private val rows: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, recyclerView: RecyclerView, state: RecyclerView.State) {
        outRect.right = space
        outRect.bottom = space
        outRect.left = space
        val adapterPosition = recyclerView.getChildAdapterPosition(view)
        val lineNumber = adapterPosition / rows

        val firstLine = lineNumber == 0
        if (!firstLine) {
            outRect.top = space
        }

        val rowNumber = adapterPosition % rows
        val halfSpace = space / 2
        if (rowNumber == 0) {
            outRect.right = halfSpace
        } else if (rowNumber == rows - 1) {
            outRect.left = halfSpace
        } else {
            outRect.right = space * 3 / 4
            outRect.left = space * 3 / 4
        }

    }
}