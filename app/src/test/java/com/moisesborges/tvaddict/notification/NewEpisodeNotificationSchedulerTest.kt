package com.moisesborges.tvaddict.notification

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Episode
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.notification.newepisode.JobScheduler
import com.moisesborges.tvaddict.notification.newepisode.NewEpisodeNotificationScheduler
import com.moisesborges.tvaddict.utils.DateUtils
import com.moisesborges.tvaddict.utils.MockShow
import io.reactivex.Single
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by moises.anjos on 04/08/2017.
 */
class NewEpisodeNotificationSchedulerTest {

    lateinit var notificationScheduler : NewEpisodeNotificationScheduler

    @Mock lateinit var mockShowRepository : ShowsRepository
    @Mock lateinit var mockJobScheduler: JobScheduler

    @Before
    fun setUp() {
        initMocks(this)
        notificationScheduler = NewEpisodeNotificationScheduler(mockShowRepository, mockJobScheduler)
    }

    @Test
    fun shouldScheduleOneEpisode() {
        val show = MockShow.newMockShowInstance()
        val embedded = MockShow.newEmbeddedInstance()

        val newEpisode = Episode()
        newEpisode.id = 500
        val today = Date()
        val airdate = DateUtils.dateToAirdate(today)
        newEpisode.airdate = airdate
        newEpisode.airtime = "20:00"

        embedded.episodes.add(newEpisode)
        show.embedded = embedded

        `when`(mockShowRepository.getSavedShows()).thenReturn(Single.just(listOf(show)))

        notificationScheduler.scheduleJobs()

        val expectedTime = DateUtils.stringDateToLong("${airdate} 16:00").time

        verify(mockJobScheduler).scheduleEpisode(newEpisode, expectedTime, TimeUnit.HOURS.toMillis(1))
    }

    @Test @Ignore
    fun shouldNotScheduleIfThereNoNewEpisode() {
        val show = MockShow.newFullMockShowInstance()
        val mockedShow = mock(Show::class.java)

        `when`(mockShowRepository.getSavedShows()).thenReturn(Single.just(listOf(show)))

        notificationScheduler.scheduleJobs()

        //verify(mockJobScheduler, never()).schedule(anyLong(), anyLong())

    }
}