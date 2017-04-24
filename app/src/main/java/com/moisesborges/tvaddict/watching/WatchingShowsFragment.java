package com.moisesborges.tvaddict.watching;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.moisesborges.tvaddict.App;
import com.moisesborges.tvaddict.R;
import com.moisesborges.tvaddict.data.ShowsRepository;

import org.w3c.dom.Text;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WatchingShowsFragment extends Fragment {

    @Inject
    ShowsRepository mShowsRepository;

    @BindView(R.id.list)
    TextView mTextView;


    public WatchingShowsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        injectDependencies();
    }

    private void injectDependencies() {
        ((App) getContext().getApplicationContext()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watching_shows, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mShowsRepository.getSavedShows()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shows -> {
                    Gson gson = new Gson();
                    mTextView.setText(gson.toJson(shows));
                });
    }

    public static Fragment newInstance() {
        return new WatchingShowsFragment();
    }

}
