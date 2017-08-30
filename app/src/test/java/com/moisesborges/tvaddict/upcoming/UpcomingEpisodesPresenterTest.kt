package com.moisesborges.tvaddict.upcoming

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.models.UpcomingEpisode
import com.moisesborges.tvaddict.utils.MockShow
import com.moisesborges.tvaddict.utils.RxJavaTestConfig
import io.reactivex.Single
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks

/**
 * Created by moises on 28/06/2017.
 */
class UpcomingEpisodesPresenterTest {

    lateinit var presenter: UpcomingEpisodesPresenter
    @Mock lateinit var mockRepository: ShowsRepository
    @Mock lateinit var mockView: UpcomingEpisodesView

    @Before
    fun setUp() {
        initMocks(this)
        presenter = UpcomingEpisodesPresenter(RxJavaTestConfig(), mockRepository)
        presenter.bindView(mockView)
    }

    @Test
    fun shouldLoadUpcomingEpisodes() {
        val show = MockShow.newMockShowInstance()
        show.embedded = MockShow.newEmbeddedInstance()

        val upcomingEpisodes = show.episodes
                .map { episode -> UpcomingEpisode(show, episode) }
                .take(3)

        `when`(mockRepository.fetchUpcomingEpisodes()).thenReturn(Single.just(upcomingEpisodes))

        presenter.loadUpcomingEpisodes()

        verify(mockView).displayProgress(true)
        verify(mockRepository).fetchUpcomingEpisodes()
        verify(mockView).displayProgress(false)
        verify(mockView).hideNoUpcomingEpisodesMessage()
        verify(mockView).displayEpisodes(upcomingEpisodes)

    }

    @Test
    fun shouldDisplayNoUpcomingEpisodes() {
        val upcomingEpisodes = ArrayList<UpcomingEpisode>()

        `when`(mockRepository.fetchUpcomingEpisodes()).thenReturn(Single.just(upcomingEpisodes))

        presenter.loadUpcomingEpisodes()

        verify(mockView).displayProgress(true)
        verify(mockRepository).fetchUpcomingEpisodes()
        verify(mockView).displayProgress(false)
        verify(mockView, never()).displayEpisodes(upcomingEpisodes)
        verify(mockView).displayNoUpcomingEpisodesMessage()
    }

    @Test
    fun shouldMarkEpisodeAsSeenAndShowNext() {
        val show = MockShow.newMockShowInstance()
        show.embedded = MockShow.newEmbeddedInstance()

        val episode = show.episodes.first()
        val upcomingEpisode = UpcomingEpisode(show, episode)

        val nextEpisode = show.episodes[1]
        val nextUpcomingEpisode = UpcomingEpisode(show, nextEpisode)
        `when`(mockRepository.updateShow(upcomingEpisode.show)).thenReturn(Single.just(upcomingEpisode.show))

        presenter.markEpisodeAsSeen(upcomingEpisode)

        assertThat(episode.wasWatched(), `is`(true))
        assertThat(show.nextEpisode(), notNullValue())
        assertThat(show.nextEpisode(), `is`(nextEpisode))
        verify(mockRepository).updateShow(show)
        verify(mockView).replaceEpisode(upcomingEpisode, nextUpcomingEpisode)
    }

    @Test
    fun shouldMarkEpisodeAsSeenAndRemove() {
        val show = MockShow.newMockShowInstance()
        show.embedded = MockShow.newEmbeddedInstance()

        val lastEpisode = show.episodes.last()
        show.episodes.filter { it != lastEpisode }
                .forEach {it.setWatched(true)}

        val upcomingEpisode = UpcomingEpisode(show, lastEpisode)

        `when`(mockRepository.updateShow(upcomingEpisode.show)).thenReturn(Single.just(upcomingEpisode.show))

        presenter.markEpisodeAsSeen(upcomingEpisode)

        assertThat(lastEpisode.wasWatched(), `is`(true))
        assertThat(show.nextEpisode(), notNullValue())
        assertThat(show.nextEpisode(), `is`(Show.EPISODE_NOT_FOUND))
        verify(mockRepository).updateShow(show)
        verify(mockView).hideEpisode(upcomingEpisode)
    }
}