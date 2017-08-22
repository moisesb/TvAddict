package com.moisesborges.tvaddict.adapters

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.search.ShowResult
import kotlinx.android.synthetic.main.item_show_result.view.*

/**
 * Created by Moisés on 04/07/2017.
 */

class SearchResultAdapter(internal val detailsClickListener: ItemClickListener<Show>,
                          internal val changeFollowingStatusListener: ItemClickListener<ShowResult>) : RecyclerView.Adapter<SearchResultAdapter.ResultViewHolder>() {

    private val results = mutableListOf<ShowResult>()

    override fun onBindViewHolder(viewHolder: ResultViewHolder, position: Int) {
        val show = results[position]
        viewHolder.bind(show)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, p1: Int): ResultViewHolder {
        val layout = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_show_result, parent, false)
        return ResultViewHolder(layout)
    }

    fun setResults(results: List<ShowResult>) {
        this.results.clear()
        this.results.addAll(results)
        notifyDataSetChanged()
    }

    fun updateResult(showResult: ShowResult) {
        val indexOf = results.indexOfFirst { it.show.id == showResult.show.id }
        if (indexOf == -1) return

        results[indexOf] = showResult
        notifyItemChanged(indexOf)
    }

    fun clear() {
        results.clear()
        notifyDataSetChanged()
    }

    inner class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener({
                val showResult = selectedShowResult()
                detailsClickListener.consume(showResult.show)
            })

            itemView.follow_show_image_button.setOnClickListener {
                val showResult = selectedShowResult()
                changeFollowingStatusListener.consume(showResult)
            }
        }

        private fun selectedShowResult(): ShowResult {
            val position = adapterPosition
            return results[position]
        }

        fun bind(showResult: ShowResult) {
            val show = showResult.show
            val following = showResult.following
            val context = itemView.context

            val imageColor = ContextCompat.getColor(context, if (following) android.R.color.white else R.color.colorAccent)
            itemView.follow_show_image_button.setColorFilter(imageColor)

            val backgroundColor = if (following) ContextCompat.getDrawable(context, R.color.colorAccent) else null
            itemView.follow_show_frame_layout.background = backgroundColor

            itemView.show_name_text_view.text = show.name
            itemView.show_network_text_view.text = show.network?.name ?: ""
            if (show.image?.medium != null) {
                Glide.with(itemView.context)
                        .load(show.image.medium)
                        .into(itemView.show_image_view)
            }

        }

    }

}


