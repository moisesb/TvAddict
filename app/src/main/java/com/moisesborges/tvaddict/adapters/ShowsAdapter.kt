package com.moisesborges.tvaddict.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.models.Show

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * Created by Moisés on 22/05/2017.
 */
class ShowsAdapter(private val listener: ItemClickListener<Show>?) : RecyclerView.Adapter<ShowsAdapter.ViewHolder>() {

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

    fun lastItemReachedEvent() : Observable<Int> {
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
        @BindView(R.id.show_name_text_view) @JvmField
        var showNameTextView: TextView? = null
        @BindView(R.id.show_image_view) @JvmField
        var imageView: ImageView? = null

        init {
            ButterKnife.bind(this, itemView)
        }

        @OnClick(R.id.show_item_layout)
        fun onShowClick() {
            if (listener != null) {
                val position = adapterPosition
                val show = shows[position]
                listener.consume(show)
            }
        }

        fun bind(show: Show) {
            Glide.with(itemView.context)
                    .load(show.image.medium)
                    .into(imageView!!)
            showNameTextView!!.text = show.name
        }
    }

}
