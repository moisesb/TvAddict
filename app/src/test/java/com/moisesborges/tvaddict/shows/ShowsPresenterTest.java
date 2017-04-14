package com.moisesborges.tvaddict.shows;

import com.moisesborges.tvaddict.data.ShowsRepository;
import com.moisesborges.tvaddict.exceptions.ViewNotAttachedException;
import com.moisesborges.tvaddict.models.ShowInfo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.*;

/**
 * Created by Moisés on 12/04/2017.
 */
public class ShowsPresenterTest {

    private ShowsPresenter mShowsPresenter;
    @Mock
    ShowsRepository mMockShowsRepository;
    @Mock
    ShowsView mMockShowsView;

    @Before
    public void setUp() throws Exception {
        Scheduler scheduler = Schedulers.trampoline();
        MockitoAnnotations.initMocks(this);
        mShowsPresenter = new ShowsPresenter(mMockShowsRepository, scheduler, scheduler);
    }

    @Test(expected = ViewNotAttachedException.class)
    public void shouldThrowViewNotAttachedExceptionIfViewNotBinded() throws Exception {
        mShowsPresenter.loadShows();
    }

    @Test
    public void shouldDisplayShows() throws Exception {
        List<ShowInfo> showInfos = Arrays.asList(new ShowInfo(), new ShowInfo());
        when(mMockShowsRepository.getShows()).thenReturn(Single.just(showInfos));

        mShowsPresenter.bindView(mMockShowsView);
        mShowsPresenter.loadShows();

        verify(mMockShowsView).displayProgress(true);
        verify(mMockShowsView).displayProgress(false);
        verify(mMockShowsView).displayTvShows(showInfos);
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
}