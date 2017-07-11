package com.moisesborges.tvaddict.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.models.Show
import kotlinx.android.synthetic.main.item_show_result.view.*

/**
 * Created by Moisés on 04/07/2017.
 */

class SearchResultAdapter(internal val clickListener: ItemClickListener<Show>) : RecyclerView.Adapter<SearchResultAdapter.ResultViewHolder>() {

    private val result = mutableListOf<Show>()

    override fun onBindViewHolder(viewHolder: ResultViewHolder?, position: Int) {
        val show = result[position]
        viewHolder?.bind(show)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, p1: Int): ResultViewHolder {
        val layout = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_show_result, parent, false)
        return ResultViewHolder(layout)
    }

    fun setResult(result: List<Show>) {
        this.result.clear()
        this.result.addAll(result)
        notifyDataSetChanged()
    }

    fun clear() {
        result.clear()
        notifyDataSetChanged()
    }

    inner class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener({
                val position = adapterPosition
                val show = result[position]
                clickListener.consume(show)
            })
        }

        fun bind(show: Show) {
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


