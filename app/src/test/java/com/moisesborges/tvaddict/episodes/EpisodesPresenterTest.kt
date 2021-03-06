package com.moisesborges.tvaddict.episodes

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Episode
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.utils.DateUtils
import com.moisesborges.tvaddict.utils.MockShow
import com.moisesborges.tvaddict.utils.RxJavaTestConfig
import io.reactivex.Single
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Moisés on 21/04/2017.
 */
class EpisodesPresenterTest {

    @Mock
    internal lateinit var episodesView: EpisodesView
    @Mock
    internal lateinit var showsRepository: ShowsRepository

    private lateinit var episodesPresenter: EpisodesPresenter
    private lateinit var show: Show

    @Before
    fun setUp() {
        initMocks(this)

        show = MockShow.newFullMockShowInstance()
        episodesPresenter = EpisodesPresenter(RxJavaTestConfig(), showsRepository)
    }

    @Test
    fun shouldDisplayEpisodesOfRightSeasonAndLoadSavedShow() {
        episodesPresenter.bindView(episodesView)

        `when`(showsRepository.getSavedShow(show.id)).thenReturn(Single.just(show))

        val season = show.seasons.first()
        val episodes = show.episodes
                .filter { episode -> episode.season == season.number }

        episodesPresenter.loadEpisodes(season.id, show)
        verify<EpisodesView>(episodesView).setShow(show)
        verify<EpisodesView>(episodesView).displayEpisodes(episodes)
    }

    @Test
    fun shouldDisplayEpisodesOfRightSeasonWithouLoadShow() {
        episodesPresenter.bindView(episodesView)

        `when`(showsRepository.getSavedShow(show.id!!))
                .thenReturn(Single.just(Show.NOT_FOUND))

        val season = show.seasons.first()
        val episodes = show.episodes
                .filter { episode -> episode.season == season.number }

        episodesPresenter.loadEpisodes(season.id, show)
        verify<EpisodesView>(episodesView, never()).setShow(show)
        verify<EpisodesView>(episodesView).displayEpisodes(episodes)
    }

    @Test
    fun shouldSetEpisodeStatusAsSeen() {
        episodesPresenter.bindView(episodesView)

        `when`(showsRepository.updateShow(show)).thenReturn(Single.just(show))

        val episode = show.episodes.first()
        episode.setWatched(false)

        episodesPresenter.changeEpisodeSeenStatus(episode, show)

        assertThat(episode.wasWatched(), `is`(true))
        verify<ShowsRepository>(showsRepository).updateShow(show)
        verify<EpisodesView>(episodesView).refreshEpisode(episode)
    }

    @Test
    fun shouldNotSetEpisodeStatusAsSeen() {
        episodesPresenter.bindView(episodesView)

        `when`(showsRepository.updateShow(show)).thenReturn(Single.just(show))

        val episode = show.episodes.first()

        episode.setWatched(false)

        val todayPlusOneHour = Date(Date().time + TimeUnit.HOURS.toMillis(1))
        changeEpisodeAirdateAndAirtime(episode, todayPlusOneHour)

        episodesPresenter.changeEpisodeSeenStatus(episode, show)

        assertThat(episode.wasWatched(), `is`(false))
        verify(showsRepository, never()).updateShow(show)
        verify(episodesView, never()).refreshEpisode(episode)
    }

    private fun changeEpisodeAirdateAndAirtime(episode: Episode, date: Date) {
        episode.airdate = DateUtils.dateToAirdate(date)
        episode.airtime = DateUtils.dateToAirtime(date)
    }

    @Test
    fun shouldSetEpisodeStatusAsUnseen() {
        episodesPresenter.bindView(episodesView)

        `when`(showsRepository.updateShow(show)).thenReturn(Single.just(show))

        val episode = show.episodes.first()
        episode.setWatched(true)

        episodesPresenter.changeEpisodeSeenStatus(episode, show)

        assertThat(episode.wasWatched(), `is`(false))
        verify<ShowsRepository>(showsRepository).updateShow(show)
        verify<EpisodesView>(episodesView).refreshEpisode(episode)
    }

    @Test
    fun shouldSetAllSeasonEpisodesStatusAsSeen() {
        episodesPresenter.bindView(episodesView)

        `when`(showsRepository.updateShow(show)).thenReturn(Single.just(show))

        val season = show.seasons.first()
        val episodes = show.episodes
                .filter { episode -> episode.season == season.number }

        episodesPresenter.setAllEpisodesStatusAsSeen(season.number, show)

        episodes.forEach { episode -> assertThat(episode.wasWatched(), `is`(true)) }
        verify(showsRepository).updateShow(show)
        verify(episodesView).refreshAllEpisodes()
    }

    @Test
    fun shouldNotSetAllSeasonEpisodesStatusAsSeen() {
        episodesPresenter.bindView(episodesView)

        `when`(showsRepository.updateShow(show)).thenReturn(Single.just(show))

        val season = show.seasons.first()
        val episodes = show.episodes
                .filter { episode -> episode.season == season.number }

        val lastTwoEpisodes = episodes.takeLast(2)

        val todayPlusOneHour = Date(Date().time + TimeUnit.HOURS.toMillis(1))
        lastTwoEpisodes.forEach { episode -> changeEpisodeAirdateAndAirtime(episode, todayPlusOneHour) }

        episodesPresenter.setAllEpisodesStatusAsSeen(season.number, show)

        episodes.forEach { episode -> val trueIfAired = !lastTwoEpisodes.contains(episode)
            assertThat(episode.wasWatched(), `is`(trueIfAired)) }
        verify(showsRepository).updateShow(show)
        verify(episodesView).refreshAllEpisodes()
    }

    @Test
    fun shouldSetAllSeasonEpisodesStatusAsUnseen() {
        episodesPresenter.bindView(episodesView)

        `when`(showsRepository.updateShow(show)).thenReturn(Single.just(show))

        val season = show.seasons.first()
        val episodes = show.episodes
                .filter { episode -> episode.season == season.number }

        episodesPresenter.setAllEpisodesStatusAsUnseen(season.number, show)

        episodes.forEach { episode -> assertThat(episode.wasWatched(), `is`(false)) }
        verify(showsRepository).updateShow(show)
        verify(episodesView).refreshAllEpisodes()
    }
}