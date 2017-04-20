package com.moisesborges.tvaddict.showdetails;

import com.moisesborges.tvaddict.data.ShowsRepository;
import com.moisesborges.tvaddict.exceptions.ViewNotAttachedException;
import com.moisesborges.tvaddict.models.Image;
import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Show;
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

    @Mock
    ShowDetailsView mShowDetailsView;
    @Mock
    ShowsRepository mShowsRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mShow = new Show();
        mShowDetailsPresenter = new ShowDetailsPresenter(mShowsRepository, new RxJavaTestConfig());
    }

    @Test(expected = ViewNotAttachedException.class)
    public void shouldThrowExceptionIfViewNotAttached() throws Exception {
        mShowDetailsPresenter.loadShowDetails(mShow);
    }

    @Test
    public void shouldDisplayShowDetailsIntoView() throws Exception {
        mShow.setId(1);
        mShow.setName("The newest show");
        Image image = new Image();
        image.setMedium("http://showimageurl.com");
        mShow.setImage(image);
        mShow.setSummary("<p>this is the newest show of this weeekend</p>");

        when(mShowsRepository.getSeasons(mShow.getId()))
                .thenReturn(Single.fromCallable(ArrayList::new));

        mShowDetailsPresenter.bindView(mShowDetailsView);

        mShowDetailsPresenter.loadShowDetails(mShow);

        verify(mShowDetailsView).displaySeasonsNotLoaded(false);
        verify(mShowDetailsView).setShowName(mShow.getName());
        verify(mShowDetailsView).setShowImage(mShow.getImage().getMedium());
        verify(mShowDetailsView).setShowSummary(mShow.getSummary());
        verify(mShowDetailsView).displaySeasonsProgress(true);
        verify(mShowsRepository).getSeasons(mShow.getId());
        verify(mShowDetailsView).displaySeasonsProgress(false);
        verify(mShowDetailsView).displaySeasons(anyList());
    }
}