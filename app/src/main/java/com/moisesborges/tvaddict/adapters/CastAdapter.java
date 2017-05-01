package com.moisesborges.tvaddict.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moisesborges.tvaddict.R;
import com.moisesborges.tvaddict.models.CastMember;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Moisés on 01/05/2017.
 */

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    private final List<CastMember> mCast = new ArrayList<>();

    public CastAdapter() {
    }

    public void setCast(@NonNull List<CastMember> cast) {
        mCast.addAll(cast);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cast_member, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CastMember castMember = mCast.get(position);
        holder.bind(castMember);
    }

    @Override
    public int getItemCount() {
        return mCast.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cast_member_image_view)
        ImageView mImageView;

        @BindView(R.id.cast_member_name_text_view)
        TextView mNameTextView;

        @BindView(R.id.character_name_text_view)
        TextView mCharacterTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(CastMember castMember) {
            if (castMember.getPerson().getImage() != null) {
                Glide.with(itemView.getContext())
                        .load(castMember.getPerson().getImage().getMedium())
                        .into(mImageView);
            }

            mNameTextView.setText(castMember.getPerson().getName());
            mCharacterTextView.setText(castMember.getCharacter().getName());
        }
    }
}
