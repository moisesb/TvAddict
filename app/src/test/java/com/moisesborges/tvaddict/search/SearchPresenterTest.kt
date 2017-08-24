package com.moisesborges.tvaddict.search

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.utils.MockShow
import com.moisesborges.tvaddict.utils.RxJavaTestConfig
import io.reactivex.Single
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.MockitoAnnotations.*
import javax.inject.Inject

/**
 * Created by Moisés on 01/07/2017.
 */
class SearchPresenterTest {

    @Mock lateinit var mockView: SearchView
    @Mock lateinit var mockRepository: ShowsRepository
    private lateinit var presenter: SearchPresenter

    @Before
    fun setUp() {
        initMocks(this)
        presenter = SearchPresenter(RxJavaTestConfig(), mockRepository)
        presenter.bindView(mockView)
    }

    @Test
    fun shouldSearchShow() {
        val showName = "Under the Dome"
        val show = MockShow.newMockShowInstance()
        val mockResult = listOf(show)

        `when`(mockRepository.searchShows(showName)).thenReturn(Single.just(mockResult))
        `when`(mockRepository.getSavedShows()).thenReturn(Single.just(emptyList()))

        presenter.searchShow(showName)

        verify(mockView).showNotFound(false, "")
        verify(mockView).displayProgress(true)
        verify(mockRepository).searchShows(showName)
        verify(mockView).displayProgress(false)
        verify(mockView).displayResults(mockResult.map { ShowResult(it, false) })
    }

    @Test
    fun shouldAddCorrectFollowingStatus() {
        val showName = "Under the Dome"
        val firstShow = MockShow.newMockShowInstance()
        firstShow.id = 1
        val secondShow = MockShow.newMockShowInstance()
        secondShow.id = 2
        val thirdShow = MockShow.newMockShowInstance()
        thirdShow.id = 3
        val mockResult = listOf(firstShow, secondShow, thirdShow)

        `when`(mockRepository.searchShows(showName)).thenReturn(Single.just(mockResult))
        `when`(mockRepository.getSavedShows()).thenReturn(Single.just(listOf(secondShow, thirdShow)))

        presenter.searchShow(showName)

        verify(mockView).showNotFound(false, "")
        verify(mockView).displayProgress(true)
        verify(mockRepository).searchShows(showName)
        verify(mockView).displayProgress(false)

        val expectedShowResults = listOf(ShowResult(firstShow, false), ShowResult(secondShow, true), ShowResult(thirdShow, true))
        verify(mockView).displayResults(expectedShowResults)
    }

    @Test
    fun shouldShowMessageIfShowsNotFound() {
        val showName = "Some unknown show"
        val mockResult = emptyList<Show>()

        `when`(mockRepository.searchShows(showName)).thenReturn(Single.just(mockResult))
        `when`(mockRepository.getSavedShows()).thenReturn(Single.just(emptyList()))

        presenter.searchShow(showName)

        verify(mockView).showNotFound(false, "")
        verify(mockView).displayProgress(true)
        verify(mockRepository).searchShows(showName)
        verify(mockView).displayProgress(false)
        verify(mockView).showNotFound(true, showName)
    }

    @Test
    fun shouldHandleEmptyTextChanges() {
        presenter.handleTextChanges("")

        verify(mockView).clearResults()
    }

    @Test
    fun shouldNotEmptyResultsWhileWriting() {
        presenter.handleTextChanges("Some incomplete text")

        verify(mockView, never()).clearResults()
    }

    @Test
    fun shouldNavigateToShowDetails() {
        val mockShow = MockShow.newMockShowInstance()
        presenter.openShow(mockShow)

        verify(mockView).navigateToShowDetails(mockShow)
    }

    @Test
    fun shouldFollowShow() {
        val mockShow = MockShow.newMockShowInstance()
        val fullMockShow = MockShow.newFullMockShowInstance()
        val mockShowResult = ShowResult(mockShow, false)

        `when`(mockRepository.saveFullShowInfo(mockShow)).thenReturn(Single.just(fullMockShow))

        presenter.toggleFollowShowStatus(mockShowResult)

        verify(mockRepository).saveFullShowInfo(mockShow)
        verify(mockView).updateShowResult(ShowResult(fullMockShow, true))
    }

    @Test
    fun shouldUnfollowShow() {
        val mockShow = MockShow.newMockShowInstance()
        val mockShowResult = ShowResult(mockShow, true)

        `when`(mockRepository.removeShow(mockShow.id)).thenReturn(Single.just(mockShow))

        presenter.toggleFollowShowStatus(mockShowResult)
        verify(mockRepository).removeShow(mockShow.id)
        verify(mockView).updateShowResult(ShowResult(mockShow, false))
    }
}