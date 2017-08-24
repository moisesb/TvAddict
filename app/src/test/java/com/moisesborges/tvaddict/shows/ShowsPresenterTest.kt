package com.moisesborges.tvaddict.shows

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.exceptions.ViewNotAttachedException
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.utils.MockShow
import com.moisesborges.tvaddict.utils.RxJavaTestConfig

import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import java.util.Arrays

import io.reactivex.Single
import org.mockito.ArgumentMatchers

import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks

/**
 * Created by Moisés on 12/04/2017.
 */
class ShowsPresenterTest {

    private lateinit var showsPresenter: ShowsPresenter
    internal lateinit var show: Show
    @Mock lateinit var mockShowsRepository: ShowsRepository
    @Mock lateinit var mockShowsView: ShowsView

    @Before
    @Throws(Exception::class)
    fun setUp() {
        initMocks(this)
        show = MockShow.newMockShowInstance()
        show.embedded = MockShow.newEmbeddedInstance()
        showsPresenter = ShowsPresenter(mockShowsRepository, RxJavaTestConfig())
    }

    @Test(expected = ViewNotAttachedException::class)
    @Throws(Exception::class)
    fun shouldThrowViewNotAttachedExceptionIfViewNotBinded() {
        showsPresenter.loadShows()
    }

    @Test
    @Throws(Exception::class)
    fun shouldDisplayShows() {
        val shows = Arrays.asList(Show(), Show())

        `when`(mockShowsRepository.getSavedShows()).thenReturn(Single.just(emptyList()))
        `when`(mockShowsRepository.loadShows(0)).thenReturn(Single.just(shows))

        showsPresenter.bindView(mockShowsView)
        showsPresenter.loadShows()

        verify(mockShowsView).displayProgress(true)
        verify(mockShowsView).displayProgress(false)
        verify(mockShowsView).displayTvShows(shows)
        verify(mockShowsView).setPage(0)
    }

    @Test
    @Throws(Exception::class)
    fun shouldDisplayErrorMessageIfNotConnected() {
        `when`(mockShowsRepository.loadShows(0)).thenReturn(Single.error<List<Show>>(Throwable()))
        showsPresenter.bindView(mockShowsView)
        showsPresenter.loadShows()

        verify<ShowsView>(mockShowsView).displayProgress(true)
        verify<ShowsView>(mockShowsView).displayProgress(false)
        verify<ShowsView>(mockShowsView).displayError()
    }

    @Test(expected = ViewNotAttachedException::class)
    @Throws(Exception::class)
    fun shouldThrowExceptionIfNavigateBeforeBindView() {
        showsPresenter.openShowDetails(show)
    }

    @Test
    @Throws(Exception::class)
    fun shouldNavigateToShowDetails() {
        showsPresenter.bindView(mockShowsView)
        showsPresenter.openShowDetails(show)
        verify<ShowsView>(mockShowsView).navigateToShowDetails(show)
    }

    @Test
    fun showLoadNextPage() {
        showsPresenter.bindView(mockShowsView)

        val currentPage = 0
        val nextPage = currentPage + 1

        val nextShows = listOf(MockShow.newMockShowInstance())

        `when`(mockShowsRepository.getSavedShows()).thenReturn(Single.just(emptyList()))
        `when`(mockShowsRepository.loadShows(nextPage))
                .thenReturn(Single.just(nextShows))

        showsPresenter.loadMoreShows(currentPage)
        verify(mockShowsRepository).loadShows(nextPage)
        verify(mockShowsView).displayMoreTvShows(nextShows)
        verify(mockShowsView).setPage(nextPage)
    }

    @Test
    fun shouldFollowShow() {
        showsPresenter.bindView(mockShowsView)

        val show = MockShow.newMockShowInstance()
        val fullShow = MockShow.newFullMockShowInstance()
        `when`(mockShowsRepository.saveFullShowInfo(show)).thenReturn(Single.just(fullShow))
        showsPresenter.followShow(show)

        verify(mockShowsRepository).saveFullShowInfo(show)
        verify(mockShowsView).hideShow(fullShow)
        verify(mockShowsView).displayFollowingShowMessage(fullShow)
    }

    @Test
    fun shouldNotDisplayFollowingShows() {
        showsPresenter.bindView(mockShowsView)

        val firstShow = MockShow.newMockShowInstance()
        firstShow.id = 1
        val secondShow = MockShow.newMockShowInstance()
        secondShow.id = 2
        val thirdShow = MockShow.newFullMockShowInstance()
        thirdShow.id = 3

        val showsFromWeb = listOf(firstShow, secondShow, thirdShow)

        `when`(mockShowsRepository.loadShows(0)).thenReturn(Single.just(showsFromWeb))
        `when`(mockShowsRepository.getSavedShows()).thenReturn(Single.just(listOf(secondShow)))

        showsPresenter.loadShows()

        val unseenTvShows = showsFromWeb.filter { it.id != secondShow.id }

        verify(mockShowsView).displayProgress(true)
        verify(mockShowsView).displayProgress(false)
        verify(mockShowsView).displayTvShows(unseenTvShows)
        verify(mockShowsView).setPage(0)
    }
}