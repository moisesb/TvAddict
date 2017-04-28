package com.moisesborges.tvaddict.showdetails;

import com.moisesborges.tvaddict.data.ShowsRepository;
import com.moisesborges.tvaddict.exceptions.ViewNotAttachedException;
import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.utils.MockShow;
import com.moisesborges.tvaddict.utils.RxJavaTestConfig;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.Maybe;
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

        when(mShowsRepository.getFullShowInfo(mShow.getId()))
                .thenReturn(Single.fromCallable(() -> mShowFullInfo));


        when(mShowsRepository.getSavedShow(mShow.getId())).thenReturn(Single.error(new NullPointerException()));
    }

    @Test(expected = ViewNotAttachedException.class)
    public void shouldThrowExceptionIfViewNotAttached() throws Exception {
        mShowDetailsPresenter.loadShowDetails(mShow);
    }

    @Test
    public void shouldDisplayShowDetailsIntoView() throws Exception {

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
        verify(mShowDetailsView).displayCastMembers(anyList());
    }

    @Test
    public void showDisplaySaveMainAction() throws Exception {
        when(mShowsRepository.getSavedShow(mShow.getId())).thenReturn(Single.error(new NullPointerException()));

        mShowDetailsPresenter.bindView(mShowDetailsView);
        mShowDetailsPresenter.loadShowDetails(mShow);

        verify(mShowDetailsView).displaySaveShowButton(true);
    }

    @Test
    public void shouldDisplayRemoveMainAction() throws Exception {
        when(mShowsRepository.getSavedShow(mShow.getId())).thenReturn(Single.fromCallable(() -> mShow));

        mShowDetailsPresenter.bindView(mShowDetailsView);
        mShowDetailsPresenter.loadShowDetails(mShow);

        verify(mShowDetailsView).displaySaveShowButton(false);
    }

    @Test
    public void shouldOpenSeasonEpisodes() throws Exception {
        mShowDetailsPresenter.bindView(mShowDetailsView);
        Season season = mShowFullInfo.getEmbedded().getSeasons().get(0);
        mShowDetailsPresenter.openEpisodes(mShowFullInfo, season);

        verify(mShowDetailsView).navigateToEpisodes(mShowFullInfo.getId(), season.getNumber(), mShowFullInfo.getEmbedded());
    }

    @Test
    public void shouldSaveShowInWatchingList() throws Exception {
        when(mShowsRepository.saveShow(any())).thenReturn(Completable.complete());

        mShowDetailsPresenter.bindView(mShowDetailsView);
        mShowDetailsPresenter.changeWatchingStatus(mShow);

        verify(mShowsRepository).saveShow(mShow);
        verify(mShowDetailsView).displaySavedShowMessage();
        verify(mShowDetailsView).displaySaveShowButton(false);
    }

    @Test
    public void shouldRemoveShowFromWatchingList() throws Exception {
        when(mShowsRepository.getSavedShow(mShow.getId())).thenReturn(Single.just(mShow));
        when(mShowsRepository.removeShow(mShow.getId())).thenReturn(Completable.complete());

        mShowDetailsPresenter.bindView(mShowDetailsView);
        mShowDetailsPresenter.changeWatchingStatus(mShow);

        verify(mShowsRepository).removeShow(mShow.getId());
        verify(mShowDetailsView).displayShowRemovedMessage();
        verify(mShowDetailsView).displaySaveShowButton(true);
    }
}