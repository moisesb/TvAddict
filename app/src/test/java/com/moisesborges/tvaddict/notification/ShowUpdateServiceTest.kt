package com.moisesborges.tvaddict.notification

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.utils.MockShow
import io.reactivex.Single
import org.hamcrest.Matchers
import org.hamcrest.Matchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.MockitoAnnotations.*

/**
 * Created by moises.anjos on 12/07/2017.
 */
class ShowUpdateServiceTest {

    @Mock lateinit var mockRepository: ShowsRepository
    lateinit var showUpdateService: ShowUpdateService
    @Before
    fun setUp() {
        initMocks(this)
        showUpdateService = ShowUpdateService(mockRepository)
    }

    @Test
    fun shouldNotUpdateShowsIfNotWatchingAnyShows() {
        `when`(mockRepository.getSavedShows()).thenReturn(Single.just(emptyList()))

        val updatedShows = showUpdateService.updateShows()

        assertThat(updatedShows, empty())
    }

    @Test
    fun shouldTryToUpdateShowIfShowIsRunning() {
        val mockShow = MockShow.newMockShowInstance()
        val sameShow = mockShow
        mockShow.status = "Running"
        `when`(mockRepository.getSavedShows()).thenReturn(Single.just(listOf(mockShow)))
        `when`(mockRepository.getFullShowInfo(mockShow.id)).thenReturn(Single.just(sameShow))

        val updatedShows = showUpdateService.updateShows()

        verify(mockRepository).getFullShowInfo(mockShow.id)

        assertThat(updatedShows, empty())
    }

    @Test
    fun shouldNotUpdateShowsIfShowEnded() {
        val mockShow = MockShow.newMockShowInstance()
        mockShow.status = "Ended"
        `when`(mockRepository.getSavedShows()).thenReturn(Single.just(listOf(mockShow)))

        val updatedShows = showUpdateService.updateShows()

        assertThat(updatedShows, empty())
    }
}