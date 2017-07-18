package com.moisesborges.tvaddict.upcoming

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.UpcomingEpisode
import com.moisesborges.tvaddict.utils.MockShow
import com.moisesborges.tvaddict.utils.RxJavaTestConfig
import io.reactivex.Single
import org.hamcrest.Matchers.`is`
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
                .map { episode -> UpcomingEpisode(show.id, show.name, episode) }
                .take(3)

        `when`(mockRepository.fetchUpcomingEpisodes()).thenReturn(Single.just(upcomingEpisodes))

        presenter.loadUpcomingEpisodes()

        verify(mockView).displayProgress(true)
        verify(mockRepository).fetchUpcomingEpisodes()
        verify(mockView).displayProgress(false)
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
        verify(mockView).displayNoUpcomingEpisodes()
    }

    @Test
    fun shouldMarkEpisodeAsSeen() {
        val show = MockShow.newMockShowInstance()
        show.embedded = MockShow.newEmbeddedInstance()

        val episode = show.episodes.first()
        val upcomingEpisode = UpcomingEpisode(show.id, show.name, episode)

        val nextEpisode = show.episodes[1]
        val nextUpcomingEpisode = UpcomingEpisode(show.id, show.name, nextEpisode)
        `when`(mockRepository.fetchUpcomingEpisode(upcomingEpisode.showId)).thenReturn(Single.just(nextUpcomingEpisode))

        presenter.markEpisodeAsSeen(upcomingEpisode)

        assertThat(episode.wasWatched(), `is`(true))
        verify(mockRepository).updateEpisode(episode)
        verify(mockRepository).fetchUpcomingEpisode(upcomingEpisode.showId)
        verify(mockView).hideEpisode(upcomingEpisode)
        verify(mockView).displayEpisode(nextUpcomingEpisode)

    }
}