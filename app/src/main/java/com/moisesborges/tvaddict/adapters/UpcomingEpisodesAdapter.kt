package com.moisesborges.tvaddict.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.models.UpcomingEpisode
import kotlinx.android.synthetic.main.item_upcoming_episode.view.*

/**
 * Created by Moisés on 13/06/2017.
 */
class UpcomingEpisodesAdapter(val itemClickListener: ItemClickListener<UpcomingEpisode>) : RecyclerView.Adapter<UpcomingEpisodesAdapter.UpcomingEpisodeViewHolder>() {

    private val upcomingEpisodes = mutableListOf<UpcomingEpisode>()

    fun setEpisodes(episodes: List<UpcomingEpisode>) {
        upcomingEpisodes.clear()
        episodes.forEach { episode -> upcomingEpisodes.add(episode)}
        notifyDataSetChanged()
    }

    fun replaceEpisode(upcomingEpisode: UpcomingEpisode, nextUpcomingEpisode: UpcomingEpisode) {
        val indexOf = upcomingEpisodes.indexOf(upcomingEpisode)
        if (indexOf == - 1) {
            return
        }
        upcomingEpisodes.removeAt(indexOf)
        upcomingEpisodes.add(indexOf, nextUpcomingEpisode)
        notifyItemChanged(indexOf)
    }

    fun removeEpisode(upcomingEpisode: UpcomingEpisode) {
        val indexOf = upcomingEpisodes.indexOf(upcomingEpisode)
        if (indexOf == - 1) {
            return
        }
        upcomingEpisodes.removeAt(indexOf)
        notifyItemRemoved(indexOf)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UpcomingEpisodeViewHolder {
        val layout = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_upcoming_episode, viewGroup, false)
        return UpcomingEpisodeViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return upcomingEpisodes.size
    }

    override fun onBindViewHolder(viewHolder: UpcomingEpisodeViewHolder, position: Int) {
        val episode = upcomingEpisodes[position]
        viewHolder.bind(episode)
    }

    inner class UpcomingEpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(upcomingEpisode: UpcomingEpisode) {
            Glide.with(itemView.context)
                    .load(upcomingEpisode.show.image?.medium)
                    .into(itemView.episode_image_view)

            itemView.show_name_text_view.text = upcomingEpisode.show.name
            itemView.episode_name_text_view.text = upcomingEpisode.episode.name
            itemView.episode_seen_button.setOnClickListener({
                val upcomingEpisode = upcomingEpisodes[adapterPosition]
                itemClickListener.consume(upcomingEpisode)
            })
        }

    }

}

