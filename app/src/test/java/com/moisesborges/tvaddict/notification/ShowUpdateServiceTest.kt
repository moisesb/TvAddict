package com.moisesborges.tvaddict.notification

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.notification.showupdate.ShowUpdateService
import com.moisesborges.tvaddict.utils.MockShow
import io.reactivex.Single
import org.hamcrest.Matchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
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
    fun shouldUpdateShowIfShowIsRunning() {
        val firstShow = MockShow.newMockShowInstance()
        firstShow.status = "Ended"

        val secondShow = MockShow.newMockShowInstance()
        secondShow.id = 2
        secondShow.status = "Running"

        val thirdShow = MockShow.newMockShowInstance()
        thirdShow.id = 3
        thirdShow.status = "Running"

        val mockSecondShow = mock(Show::class.java)
        val mockThirdShow = mock(Show::class.java)

        `when`(mockRepository.getSavedShows()).thenReturn(Single.just(listOf(firstShow, secondShow, thirdShow)))
        `when`(mockRepository.getFullShowInfo(secondShow.id)).thenReturn(Single.just(mockSecondShow))
        `when`(mockRepository.getFullShowInfo(thirdShow.id)).thenReturn(Single.just(mockThirdShow))
        `when`(mockSecondShow.updated).thenReturn(secondShow.updated + 10000)
        `when`(mockThirdShow.updated).thenReturn(thirdShow.updated)
        `when`(mockSecondShow.embedded).thenReturn(MockShow.newEmbeddedInstance())

        val updatedShows = showUpdateService.updateShows()

        verify(mockRepository).getFullShowInfo(secondShow.id)
        verify(mockRepository).getFullShowInfo(thirdShow.id)
        verify(mockSecondShow).embedded
        verify(mockThirdShow, never()).embedded

        assertThat(updatedShows, iterableWithSize(1))
        assertThat(updatedShows, contains(secondShow))
        assertThat(secondShow.embedded, `is`(mockSecondShow.embedded))
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