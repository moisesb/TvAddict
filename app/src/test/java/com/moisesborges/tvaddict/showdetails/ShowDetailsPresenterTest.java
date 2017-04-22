package com.moisesborges.tvaddict.showdetails;

import com.moisesborges.tvaddict.data.ShowsRepository;
import com.moisesborges.tvaddict.exceptions.ViewNotAttachedException;
import com.moisesborges.tvaddict.models.Embedded;
import com.moisesborges.tvaddict.models.Image;
import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.utils.MockShow;
import com.moisesborges.tvaddict.utils.RxJavaTestConfig;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;

import static org.mockito.Mockito.*;

/**
 * Created by moises.anjos on 18/04/2017.
 */
public class ShowDetailsPresenterTest {

    private ShowDetailsPresenter mShowDetailsPresenter;
    private Show mShow;
    private Show mShowFullInfo;
    @Mock
    ShowDetailsView mShowDetailsView;
    @Mock
    ShowsRepository mShowsRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mShow = MockShow.newMockShowInstance();
        mShowFullInfo = MockShow.newMockShowInstance();
        mShowFullInfo.setEmbedded(MockShow.newEmbeddedInstance());
        mShowDetailsPresenter = new ShowDetailsPresenter(mShowsRepository, new RxJavaTestConfig());
    }

    @Test(expected = ViewNotAttachedException.class)
    public void shouldThrowExceptionIfViewNotAttached() throws Exception {
        mShowDetailsPresenter.loadShowDetails(mShow);
    }

    @Test
    public void shouldDisplayShowDetailsIntoView() throws Exception {
        when(mShowsRepository.getFullShowInfo(mShow.getId()))
                .thenReturn(Single.fromCallable(() -> mShowFullInfo));

        mShowDetailsPresenter.bindView(mShowDetailsView);

        mShowDetailsPresenter.loadShowDetails(mShow);

        verify(mShowDetailsView).displaySeasonsNotLoaded(false);
        verify(mShowDetailsView).setShowName(mShow.getName());
        verify(mShowDetailsView).setShowImage(mShow.getImage().getMedium());
        verify(mShowDetailsView).setShowSummary(mShow.getSummary());
        verify(mShowDetailsView).displaySeasonsProgress(true);
        verify(mShowsRepository).getFullShowInfo(mShow.getId());
        verify(mShowDetailsView).setShow(mShowFullInfo);
        verify(mShowDetailsView).displaySeasonsProgress(false);
        verify(mShowDetailsView).displaySeasons(anyList());
    }

    @Test
    public void showOpenSeasonEpisodes() throws Exception {
        mShowDetailsPresenter.bindView(mShowDetailsView);
        Season season = mShowFullInfo.getEmbedded().getSeasons().get(0);
        mShowDetailsPresenter.openEpisodes(mShowFullInfo, season);

        verify(mShowDetailsView).navigateToEpisodes(mShowFullInfo.getId(), season.getNumber(), mShowFullInfo.getEmbedded());
    }
}