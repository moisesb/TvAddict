package com.moisesborges.tvaddict.episodes;

import com.moisesborges.tvaddict.data.ShowsRepository;
import com.moisesborges.tvaddict.models.Episode;
import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.utils.MockShow;
import com.moisesborges.tvaddict.utils.RxJavaTestConfig;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.creation.bytebuddy.MockAccess;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Moisés on 21/04/2017.
 */
public class EpisodesPresenterTest {
    
    @Mock
    EpisodesView mEpisodesView;
    @Mock
    ShowsRepository mShowsRepository;
    
    private EpisodesPresenter mEpisodesPresenter;
    private Show mShow;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mShow = MockShow.newMockShowInstance();
        mShow.setEmbedded(MockShow.newEmbeddedInstance());
        
        mEpisodesPresenter = new EpisodesPresenter(new RxJavaTestConfig(), mShowsRepository);
    }

    @Test
    public void shouldDisplayEpisodesOfRightSeasonAndLoadSavedShow() throws Exception {
        mEpisodesPresenter.bindView(mEpisodesView);

        when(mShowsRepository.getSavedShow(mShow.getId()))
                .thenReturn(Single.just(mShow));

        Season season = mShow.getSeasons().get(0);
        List<Episode> episodes = mShow.getEpisodes()
                .stream()
                .filter(episode -> episode.getSeason().equals(season.getNumber()))
                .collect(Collectors.toList());

        mEpisodesPresenter.loadEpisodes(season.getId(), mShow);
        verify(mEpisodesView).setShow(mShow);
        verify(mEpisodesView).displayEpisodes(episodes);
    }

    @Test
    public void shouldDisplayEpisodesOfRightSeasonWithouLoadShow() throws Exception {
        mEpisodesPresenter.bindView(mEpisodesView);

        when(mShowsRepository.getSavedShow(mShow.getId()))
                .thenReturn(Single.just(Show.NOT_FOUND));

        Season season = mShow.getSeasons().get(0);
        List<Episode> episodes = mShow.getEpisodes()
                .stream()
                .filter(episode -> episode.getSeason().equals(season.getNumber()))
                .collect(Collectors.toList());

        mEpisodesPresenter.loadEpisodes(season.getId(), mShow);
        verify(mEpisodesView, never()).setShow(mShow);
        verify(mEpisodesView).displayEpisodes(episodes);
    }

    @Test
    public void shouldSetEpisodeStatusAsSeen() throws Exception {
        mEpisodesPresenter.bindView(mEpisodesView);

        Mockito.when(mShowsRepository.updateShow(mShow)).thenReturn(Single.just(mShow));

        Episode episode = mShow.getEpisodes().get(0);
        episode.setWatched(false);

        mEpisodesPresenter.changeEpisodeSeenStatus(episode, mShow);

        assertThat(episode.wasWatched(), is(true));
        verify(mShowsRepository).updateShow(mShow);
        verify(mEpisodesView).refreshEpisode(episode);
    }

    @Test
    public void shouldSetEpisodeStatusAsUnseen() throws Exception {
        mEpisodesPresenter.bindView(mEpisodesView);

        Mockito.when(mShowsRepository.updateShow(mShow)).thenReturn(Single.just(mShow));

        Episode episode = mShow.getEpisodes().get(0);
        episode.setWatched(true);

        mEpisodesPresenter.changeEpisodeSeenStatus(episode, mShow);

        assertThat(episode.wasWatched(), is(false));
        verify(mShowsRepository).updateShow(mShow);
        verify(mEpisodesView).refreshEpisode(episode);
    }
}