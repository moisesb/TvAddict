package com.moisesborges.tvaddict.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.models.Show

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_show.view.*
import java.util.concurrent.TimeUnit

/**
 * Created by Moisés on 22/05/2017.
 */
class ShowsAdapter(private val openDetailsListener: ItemClickListener<Show>?,
                   private val followShowListener: ItemClickListener<Show>?) : RecyclerView.Adapter<ShowsAdapter.ViewHolder>() {

    private val shows = ArrayList<Show>()
    private val publish = PublishSubject.create<Int>()
    private var page = 0

    fun setShows(shows: List<Show>) {
        this.shows.clear()
        this.shows.addAll(shows)
        notifyDataSetChanged()
    }

    fun setPage(page: Int) {
        this.page = page
    }

    fun addShows(shows: List<Show>) {
        this.shows.addAll(shows)
        notifyDataSetChanged()
    }

    fun lastItemReachedEvent(): Observable<Int> {
        return publish.debounce(300, TimeUnit.MILLISECONDS)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_show, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val show = shows[position]
        holder.bind(show)

        if (lastPositionReached(position)) {
            publish.onNext(page)
        }
    }

    private fun lastPositionReached(position: Int) = position == itemCount - 1

    override fun getItemCount(): Int {
        return shows.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener({onShowClick()})
            itemView.more_actions_image_view.setOnClickListener({onMoreClick()})
        }

        @OnClick(R.id.more_actions_image_view)
        fun onMoreClick() {
            val popup = PopupMenu(itemView.context, itemView.more_actions_image_view)
            popup.menuInflater.inflate(R.menu.menu_show_item, popup.menu)
            popup.setOnMenuItemClickListener({ item ->
                when (item.itemId) {
                    R.id.action_follow_show -> {
                        followShow(selectedShow())
                    }
                }

                true
            })
            popup.show()
        }

        private fun followShow(selectedShow: Show) {
            followShowListener?.consume(selectedShow)
        }

        @OnClick(R.id.show_item_layout)
        fun onShowClick() {
            if (openDetailsListener != null) {
                val show = selectedShow()
                openDetailsListener.consume(show)
            }
        }

        private fun selectedShow(): Show {
            val position = adapterPosition
            val show = shows[position]
            return show
        }

        fun bind(show: Show) {
            Glide.with(itemView.context)
                    .load(show.image.medium)
                    .fitCenter()
                    .into(itemView.show_image_view)
            itemView.show_name_text_view.text = show.name
            itemView.more_actions_image_view.visibility = if (followShowListener != null) View.VISIBLE else View.GONE
        }
    }

}
