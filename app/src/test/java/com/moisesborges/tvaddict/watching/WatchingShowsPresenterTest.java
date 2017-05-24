package com.moisesborges.tvaddict.watching;

import com.moisesborges.tvaddict.data.ShowsRepository;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.utils.RxJavaTestConfig;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Moisés on 22/05/2017.
 */
public class WatchingShowsPresenterTest {

    private WatchingShowsPresenter mPresenter;

    @Mock
    ShowsRepository mMockShowsRepository;
    @Mock
    WatchingShowsView mMockView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new WatchingShowsPresenter(new RxJavaTestConfig(), mMockShowsRepository);
    }

    @Test
    public void shouldDisplayEmptyWatchingShowsListMessage() throws Exception {
        mPresenter.bindView(mMockView);
        when(mMockShowsRepository.getSavedShows()).thenReturn(Single.just(new ArrayList<>()));

        mPresenter.loadWatchingShows();
        verify(mMockView).displayEmptyListMessage();
    }

    @Test
    public void shouldDisplayWatchingShowsList() throws Exception {
        mPresenter.bindView(mMockView);
        List<Show> shows = new ArrayList<>();
        shows.add(new Show());
        shows.add(new Show());
        when(mMockShowsRepository.getSavedShows()).thenReturn(Single.just(shows));

        mPresenter.loadWatchingShows();
        verify(mMockView).displayWatchingShows(shows);
    }
}