package com.moisesborges.tvaddict.shows;

import com.moisesborges.tvaddict.data.ShowsRepository;
import com.moisesborges.tvaddict.exceptions.ViewNotAttachedException;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.utils.MockShow;
import com.moisesborges.tvaddict.utils.RxJavaTestConfig;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.*;

/**
 * Created by Moisés on 12/04/2017.
 */
public class ShowsPresenterTest {

    private ShowsPresenter mShowsPresenter;
    private Show mShow;
    @Mock
    ShowsRepository mMockShowsRepository;
    @Mock
    ShowsView mMockShowsView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mShow = MockShow.newMockShowInstance();
        mShow.setEmbedded(MockShow.newEmbeddedInstance());
        mShowsPresenter = new ShowsPresenter(mMockShowsRepository, new RxJavaTestConfig());
    }

    @Test(expected = ViewNotAttachedException.class)
    public void shouldThrowViewNotAttachedExceptionIfViewNotBinded() throws Exception {
        mShowsPresenter.loadShows();
    }

    @Test
    public void shouldDisplayShows() throws Exception {
        List<Show> shows = Arrays.asList(new Show(), new Show());
        when(mMockShowsRepository.getShows()).thenReturn(Single.just(shows));

        mShowsPresenter.bindView(mMockShowsView);
        mShowsPresenter.loadShows();

        verify(mMockShowsView).displayProgress(true);
        verify(mMockShowsView).displayProgress(false);
        verify(mMockShowsView).displayTvShows(shows);
    }

    @Test
    public void shouldDisplayErrorMessageIfNotConnected() throws Exception {
        when(mMockShowsRepository.getShows()).thenReturn(Single.error(new Throwable()));
        mShowsPresenter.bindView(mMockShowsView);
        mShowsPresenter.loadShows();

        verify(mMockShowsView).displayProgress(true);
        verify(mMockShowsView).displayProgress(false);
        verify(mMockShowsView).displayError();
    }

    @Test(expected = ViewNotAttachedException.class)
    public void shouldThrowExceptionIfNavigateBeforeBindView() throws Exception {
        mShowsPresenter.openShowDetails(mShow);
    }

    @Test
    public void shouldNavigateToShowDetails() throws Exception {
        mShowsPresenter.bindView(mMockShowsView);
        mShowsPresenter.openShowDetails(mShow);
        verify(mMockShowsView).navigateToShowDetails(mShow);
    }

}