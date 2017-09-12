package com.moisesborges.tvaddict.showdetails

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.exceptions.ViewNotAttachedException
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.utils.MockShow
import com.moisesborges.tvaddict.utils.RxJavaTestConfig
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks

/**
 * Created by moises.anjos on 18/04/2017.
 */
class ShowDetailsPresenterTest {

    private lateinit var showDetailsPresenter: ShowDetailsPresenter
    private lateinit var show: Show
    private lateinit var showFullInfo: Show
    @Mock lateinit var showDetailsView: ShowDetailsView
    @Mock lateinit var showsRepository: ShowsRepository

    @Before
    @Throws(Exception::class)
    fun setUp() {
        initMocks(this)
        show = MockShow.newMockShowInstance()
        showFullInfo = MockShow.newMockShowInstance()
        showFullInfo.embedded = MockShow.newEmbeddedInstance()
        showDetailsPresenter = ShowDetailsPresenter(showsRepository, RxJavaTestConfig())

        `when`(showsRepository.getFullShowInfo(show.id))
                .thenReturn(Single.fromCallable<Show> { showFullInfo })


        `when`(showsRepository.getSavedShow(show.id)).thenReturn(Single.error<Show>(NullPointerException()))
    }

    @Test(expected = ViewNotAttachedException::class)
    @Throws(Exception::class)
    fun shouldThrowExceptionIfViewNotAttached() {
        showDetailsPresenter.loadShowDetails(show)
    }

    @Test
    @Throws(Exception::class)
    fun shouldDisplayShowBasicInfoIntoView() {
        loadShowDetails()

        verify<ShowDetailsView>(showDetailsView).setShowName(show.name)
        verify<ShowDetailsView>(showDetailsView).setShowImage(show.image.medium)
        verify<ShowDetailsView>(showDetailsView).setShowSummary(show.summary)
        verify<ShowDetailsView>(showDetailsView).setShowRating(show.rating.average)
        verify<ShowDetailsView>(showDetailsView).setShowRuntime(show.runtime)
        verify<ShowDetailsView>(showDetailsView).setShowNetwork(show.network.name)
        verify<ShowDetailsView>(showDetailsView).setShowGenres(show.genres)
        verify<ShowDetailsView>(showDetailsView).setShowExternalLinks(show.externals)
    }

    private fun loadShowDetails() {
        showDetailsPresenter.bindView(showDetailsView)
        showDetailsPresenter.loadShowDetails(show)
    }

    @Test
    @Throws(Exception::class)
    fun shouldDisplayShowDetailsIntoView() {
        loadShowDetails()

        verify<ShowDetailsView>(showDetailsView).displayAdditionalInfoNotLoaded(false)
        verify<ShowDetailsView>(showDetailsView).displayAdditionalInfoLoadingInProgress(true)
        verify<ShowsRepository>(showsRepository).getFullShowInfo(show.id)
        verify<ShowDetailsView>(showDetailsView).setShow(showFullInfo)
        verify<ShowDetailsView>(showDetailsView).displayAdditionalInfoLoadingInProgress(false)
        verify<ShowDetailsView>(showDetailsView).displaySeasons(ArgumentMatchers.anyList())
        verify<ShowDetailsView>(showDetailsView).displayCastMembers(ArgumentMatchers.anyList())
    }

    @Test
    @Throws(Exception::class)
    fun showDisplaySaveMainAction() {
        `when`(showsRepository.getSavedShow(show.id)).thenReturn(Single.error<Show>(NullPointerException()))

        loadShowDetails()

        verify<ShowDetailsView>(showDetailsView).displaySaveShowButton(true)
    }

    @Test
    @Throws(Exception::class)
    fun shouldDisplayRemoveMainAction() {
        `when`(showsRepository.getSavedShow(show.id)).thenReturn(Single.fromCallable<Show> { show })

        loadShowDetails()

        verify<ShowDetailsView>(showDetailsView).displaySaveShowButton(false)
    }

    @Test
    @Throws(Exception::class)
    fun shouldOpenSeasonEpisodes() {
        showDetailsPresenter.bindView(showDetailsView)
        val season = showFullInfo.embedded!!.seasons[0]
        showDetailsPresenter.openEpisodes(showFullInfo, season)

        verify<ShowDetailsView>(showDetailsView).navigateToEpisodes(showFullInfo, season.number)
    }

    @Test
    @Throws(Exception::class)
    fun shouldSaveShowInWatchingList() {
        `when`(showsRepository.saveShow(show)).thenReturn(Single.just(show))

        changeWatchingStatus()

        verify<ShowsRepository>(showsRepository).saveShow(show)
        verify<ShowDetailsView>(showDetailsView).displaySavedShowMessage()
        verify<ShowDetailsView>(showDetailsView).displaySaveShowButton(false)
    }

    private fun changeWatchingStatus() {
        showDetailsPresenter.bindView(showDetailsView)
        showDetailsPresenter.changeWatchingStatus(show)
    }

    @Test
    @Throws(Exception::class)
    fun shouldRemoveShowFromWatchingList() {
        `when`(showsRepository.getSavedShow(show.id)).thenReturn(Single.just(show))
        `when`(showsRepository.removeShow(show.id)).thenReturn(Single.just(Show.REMOVED))

        changeWatchingStatus()

        verify<ShowsRepository>(showsRepository).removeShow(show.id)
        verify<ShowDetailsView>(showDetailsView).displayShowRemovedMessage()
        verify<ShowDetailsView>(showDetailsView).displaySaveShowButton(true)
    }
}