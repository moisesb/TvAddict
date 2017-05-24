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
    public void shouldDisplayShowBasicInfoIntoView() throws Exception {
        loadShowDetails();

        verify(mShowDetailsView).setShowName(mShow.getName());
        verify(mShowDetailsView).setShowImage(mShow.getImage().getMedium());
        verify(mShowDetailsView).setShowSummary(mShow.getSummary());
        verify(mShowDetailsView).setShowRating(mShow.getRating().getAverage());
        verify(mShowDetailsView).setShowRuntime(mShow.getRuntime());
        verify(mShowDetailsView).setShowNetwork(mShow.getNetwork().getName());
        verify(mShowDetailsView).setShowGenres(mShow.getGenres());
        verify(mShowDetailsView).setShowExternalLinks(mShow.getExternals());
    }

    private void loadShowDetails() {
        mShowDetailsPresenter.bindView(mShowDetailsView);
        mShowDetailsPresenter.loadShowDetails(mShow);
    }

    @Test
    public void shouldDisplayShowDetailsIntoView() throws Exception {
        loadShowDetails();

        verify(mShowDetailsView).displayAdditionalInfoNotLoaded(false);
        verify(mShowDetailsView).displayAdditionalInfoLoadingInProgress(true);
        verify(mShowsRepository).getFullShowInfo(mShow.getId());
        verify(mShowDetailsView).setShow(mShowFullInfo);
        verify(mShowDetailsView).displayAdditionalInfoLoadingInProgress(false);
        verify(mShowDetailsView).displaySeasons(anyList());
        verify(mShowDetailsView).displayCastMembers(anyList());
    }

    @Test
    public void showDisplaySaveMainAction() throws Exception {
        when(mShowsRepository.getSavedShow(mShow.getId())).thenReturn(Single.error(new NullPointerException()));

        loadShowDetails();

        verify(mShowDetailsView).displaySaveShowButton(true);
    }

    @Test
    public void shouldDisplayRemoveMainAction() throws Exception {
        when(mShowsRepository.getSavedShow(mShow.getId())).thenReturn(Single.fromCallable(() -> mShow));

        loadShowDetails();

        verify(mShowDetailsView).displaySaveShowButton(false);
    }

    @Test
    public void shouldOpenSeasonEpisodes() throws Exception {
        mShowDetailsPresenter.bindView(mShowDetailsView);
        Season season = mShowFullInfo.getEmbedded().getSeasons().get(0);
        mShowDetailsPresenter.openEpisodes(mShowFullInfo, season);

        verify(mShowDetailsView).navigateToEpisodes(mShowFullInfo, season.getNumber());
    }

    @Test
    public void shouldSaveShowInWatchingList() throws Exception {
        when(mShowsRepository.saveShow(any())).thenReturn(Single.just(mShow));

        changeWatchingStatus();

        verify(mShowsRepository).saveShow(mShow);
        verify(mShowDetailsView).displaySavedShowMessage();
        verify(mShowDetailsView).displaySaveShowButton(false);
    }

    private void changeWatchingStatus() {
        mShowDetailsPresenter.bindView(mShowDetailsView);
        mShowDetailsPresenter.changeWatchingStatus(mShow);
    }

    @Test
    public void shouldRemoveShowFromWatchingList() throws Exception {
        when(mShowsRepository.getSavedShow(mShow.getId())).thenReturn(Single.just(mShow));
        when(mShowsRepository.removeShow(mShow.getId())).thenReturn(Single.just(Show.REMOVED));

        changeWatchingStatus();

        verify(mShowsRepository).removeShow(mShow.getId());
        verify(mShowDetailsView).displayShowRemovedMessage();
        verify(mShowDetailsView).displaySaveShowButton(true);
    }
}