package com.moisesborges.tvaddict.showdetails;

import com.moisesborges.tvaddict.exceptions.ViewNotAttachedException;
import com.moisesborges.tvaddict.models.Image;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.shows.ShowsPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by moises.anjos on 18/04/2017.
 */
public class ShowDetailsPresenterTest {

    private ShowDetailsPresenter mShowDetailsPresenter;
    private Show mShow;

    @Mock
    ShowDetailsView mShowDetailsView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mShow = new Show();
        mShowDetailsPresenter = new ShowDetailsPresenter();
    }

    @Test(expected = ViewNotAttachedException.class)
    public void shouldThrowExceptionIfViewNotAttached() throws Exception {
        mShowDetailsPresenter.loadShowDetails(mShow);
    }

    @Test
    public void shouldDisplayShowDetailsIntoView() throws Exception {
        mShow.setName("The newest show");
        Image image = new Image();
        image.setMedium("http://showimageurl.com");
        mShow.setImage(image);
        mShow.setSummary("<p>this is the newest show of this weeekend</p>");

        mShowDetailsPresenter.bindView(mShowDetailsView);

        mShowDetailsPresenter.loadShowDetails(mShow);

        verify(mShowDetailsView).setShowName(mShow.getName());
        verify(mShowDetailsView).setShowImage(mShow.getImage().getMedium());
        verify(mShowDetailsView).setShowSummary(mShow.getSummary());
    }
}