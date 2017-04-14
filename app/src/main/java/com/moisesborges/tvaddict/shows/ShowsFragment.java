package com.moisesborges.tvaddict.shows;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moisesborges.tvaddict.R;
import com.moisesborges.tvaddict.models.ShowInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Moisés on 12/04/2017.
 */

public class ShowsFragment extends Fragment implements ShowsView {

    private Unbinder mUnbinder;

    @BindView(R.id.shows_recycler_view)
    RecyclerView mShowsRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private final ShowsAdapter mShowsAdapter = new ShowsAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_shows, container, false);
        mUnbinder = ButterKnife.bind(this, layout);
        setupRecyclerView();
        return layout;
    }

    private void setupRecyclerView() {
        mShowsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mShowsRecyclerView.setHasFixedSize(true);
        mShowsRecyclerView.setAdapter(mShowsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void displayProgress(boolean loading) {
        mProgressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void displayTvShows(@NonNull List<ShowInfo> showInfos) {
        mShowsAdapter.setShowInfos(showInfos);
    }

    @Override
    public void displayError() {

    }

    public static Fragment newInstance() {
        return new ShowsFragment();
    }

    public static class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ViewHolder> {

        private final List<ShowInfo> mShowInfos = new ArrayList<>();

        public ShowsAdapter() {
        }

        public void setShowInfos(@NonNull List<ShowInfo> showInfos) {
            mShowInfos.clear();
            mShowInfos.addAll(showInfos);
            notifyDataSetChanged();
        }

        @Override
        public ShowsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_show_info, parent, false);
            return new ViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(ShowsAdapter.ViewHolder holder, int position) {
            ShowInfo showInfo = mShowInfos.get(position);
            holder.bind(showInfo);
        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.show_name_text_view)
            TextView mShowNameTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bind(ShowInfo showInfo) {
                mShowNameTextView.setText(showInfo.getShow().getName());
            }
        }
    }

}
