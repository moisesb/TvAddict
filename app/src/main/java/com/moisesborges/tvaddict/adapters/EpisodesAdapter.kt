package com.moisesborges.tvaddict.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.models.Episode
import com.moisesborges.tvaddict.utils.DateUtils
import kotlinx.android.synthetic.main.item_episode.view.*
import java.util.*

/**
 * Created by moises.anjos on 13/07/2017.
 */
class EpisodesAdapter(private val toggleEpisodeStatusListener: ItemClickListener<Episode>,
                      private val openEpisodeDetailsListener: ItemClickListener<Episode>) : RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {

    private val episodes = ArrayList<Episode>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesAdapter.ViewHolder {
        val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_episode, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: EpisodesAdapter.ViewHolder, position: Int) {
        val episode = episodes[position]
        holder.bind(episode)
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    fun setEpisodes(episodes: List<Episode>) {
        this.episodes.clear()
        this.episodes.addAll(episodes)
        notifyDataSetChanged()
    }

    fun updateEpisode(episode: Episode) {
        val indexOf = episodes.indexOf(episode)
        notifyItemChanged(indexOf)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.watched_check_box.setOnClickListener({ onWatchedCheckBoxClick() })
            itemView.setOnClickListener({ onEpisodeClick()})
        }

        private fun onEpisodeClick() {
            val episode = selectedEpisode()
            openEpisodeDetailsListener.consume(episode)
        }

        private fun onWatchedCheckBoxClick() {
            val episode = selectedEpisode()
            toggleEpisodeStatusListener.consume(episode)
        }

        private fun selectedEpisode(): Episode {
            val position = adapterPosition
            val episode = episodes[position]
            return episode
        }

        fun bind(episode: Episode) {
            itemView.episode_name_text_view.text = "${episode.number} - ${episode.name}"
            itemView.episode_airdate_text_view.text = DateUtils.airdateToUiString(episode.airdate)
            itemView.watched_check_box.isChecked = episode.wasWatched()
        }
    }
}