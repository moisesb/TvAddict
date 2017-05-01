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
import com.moisesborges.tvaddict.models.Season;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Moisés on 01/05/2017.
 */
public class SeasonsAdapter extends RecyclerView.Adapter<SeasonsAdapter.ViewHolder> {

    private final List<Season> mSeasons = new ArrayList<>();
    private final ItemClickListener<Season> mItemClickListener;

    public SeasonsAdapter(@Nullable ItemClickListener<Season> itemClickListener) {
        super();
        mItemClickListener = itemClickListener;
    }

    public void setSeasons(@NonNull List<Season> seasons) {
        mSeasons.clear();
        mSeasons.addAll(seasons);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_season, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Season season = mSeasons.get(position);
        holder.bind(season);
    }

    @Override
    public int getItemCount() {
        return mSeasons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.season_image_view)
        ImageView mSeasonImageView;
        @BindView(R.id.season_text_view)
        TextView mNumberTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.season_item_layout)
        public void onSeasonClick() {
            if (mItemClickListener == null) {
                return;
            }

            int position = getAdapterPosition();
            Season season = mSeasons.get(position);
            mItemClickListener.consume(season);
        }

        public void bind(@NonNull Season season) {
            if (season.getImage() != null) {
                Glide.with(itemView.getContext())
                        .load(season.getImage().getMedium())
                        .into(mSeasonImageView);
            }

            mNumberTextView.setText(season.getName());
        }
    }
}
