package com.moisesborges.tvaddict.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.models.UpcomingEpisode
import kotlinx.android.synthetic.main.item_upcoming_episode.view.*

/**
 * Created by moise on 13/06/2017.
 */
class UpcomingEpisodesAdapter() : RecyclerView.Adapter<UpcomingEpisodeViewHolder>() {

    private val upcomingoEpisodes = mutableListOf<UpcomingEpisode>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UpcomingEpisodeViewHolder {
        val layout = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_upcoming_episode, viewGroup, false)
        return UpcomingEpisodeViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return upcomingoEpisodes.size
    }

    override fun onBindViewHolder(viewHolder: UpcomingEpisodeViewHolder, position: Int) {
        val episode = upcomingoEpisodes[position]
        viewHolder.bind(episode)
    }
}

class UpcomingEpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(episode: UpcomingEpisode) {
        itemView.show_name_text_view.text = episode.showName
    }

}