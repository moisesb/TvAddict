package com.moisesborges.tvaddict.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moisesborges.tvaddict.R;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.shows.ShowsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Moisés on 22/05/2017.
 */
public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ViewHolder> {

    private final List<Show> mShows = new ArrayList<>();
    private final ItemClickListener<Show> mListener;

    public ShowsAdapter(@Nullable ItemClickListener<Show> listener) {
        mListener = listener;
    }

    public void setShows(@NonNull List<Show> shows) {
        mShows.clear();
        mShows.addAll(shows);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_show, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Show show = mShows.get(position);
        holder.bind(show);
    }

    @Override
    public int getItemCount() {
        return mShows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.show_name_text_view)
        TextView mShowNameTextView;
        @BindView(R.id.show_image_view)
        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.show_item_layout)
        @SuppressWarnings("unused")
        public void onShowClick() {
            if (mListener != null) {
                int position = getAdapterPosition();
                final Show show = mShows.get(position);
                mListener.consume(show);
            }
        }

        public void bind(Show show) {
            Glide.with(itemView.getContext())
                    .load(show.getImage().getMedium())
                    .into(mImageView);
            mShowNameTextView.setText(show.getName());
        }
    }
}
