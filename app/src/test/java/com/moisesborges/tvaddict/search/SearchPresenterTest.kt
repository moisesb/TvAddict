package com.moisesborges.tvaddict.search

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.utils.MockShow
import com.moisesborges.tvaddict.utils.RxJavaTestConfig
import io.reactivex.Single
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
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

        presenter.searchShow(showName)

        verify(mockView).showNotFound(false, "")
        verify(mockView).displayProgress(true)
        verify(mockRepository).searchShows(showName)
        verify(mockView).displayProgress(false)
        verify(mockView).displayResults(mockResult)
    }

    @Test
    fun shouldShowMessageIfShowsNotFound() {
        val showName = "Some unknown show"
        val mockResult = emptyList<Show>()

        `when`(mockRepository.searchShows(showName)).thenReturn(Single.just(mockResult))

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
}