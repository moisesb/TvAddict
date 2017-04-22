package com.moisesborges.tvaddict.episodes;

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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Moisés on 21/04/2017.
 */
public class EpisodesPresenterTest {
    
    @Mock
    EpisodesView mEpisodesView;
    
    private EpisodesPresenter mEpisodesPresenter;
    private Show mShow;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mShow = MockShow.newMockShowInstance();
        mShow.setEmbedded(MockShow.newEmbeddedInstance());
        
        mEpisodesPresenter = new EpisodesPresenter(new RxJavaTestConfig());
    }

    @Test
    public void shouldDisplayEpisodesOfRightSeason() throws Exception {
        mEpisodesPresenter.bindView(mEpisodesView);

        Season season = mShow.getEmbedded().getSeasons().get(0);
        List<Episode> episodes = mShow.getEmbedded().getEpisodes()
                .stream()
                .filter(episode -> episode.getSeason().equals(season.getNumber()))
                .collect(Collectors.toList());

        mEpisodesPresenter.loadEpisodes(season.getId(), mShow.getEmbedded());
        verify(mEpisodesView).displayEpisodes(episodes);
    }
}