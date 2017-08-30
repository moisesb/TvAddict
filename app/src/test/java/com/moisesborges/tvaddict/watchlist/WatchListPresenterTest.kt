package com.moisesborges.tvaddict.watching

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.utils.MockShow
import com.moisesborges.tvaddict.utils.RxJavaTestConfig

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import java.util.ArrayList

import io.reactivex.Single

import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

/**
 * Created by Moisés on 22/05/2017.
 */
class WatchListPresenterTest {

    private lateinit var presenter: WatchListPresenter

    @Mock
    internal lateinit var mockShowsRepository: ShowsRepository
    @Mock
    internal lateinit var mockView: WatchListView

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = WatchListPresenter(RxJavaTestConfig(), mockShowsRepository)
    }

    @Test
    @Throws(Exception::class)
    fun shouldDisplayEmptyWatchingShowsListMessage() {
        presenter.bindView(mockView)
        `when`(mockShowsRepository.getSavedShows()).thenReturn(Single.just<List<Show>>(ArrayList<Show>()))

        presenter.loadWatchingShows()
        verify<WatchListView>(mockView).displayEmptyListMessage()
    }

    @Test
    @Throws(Exception::class)
    fun shouldDisplayWatchingShowsList() {
        presenter.bindView(mockView)
        val shows = ArrayList<Show>()
        shows.add(Show())
        shows.add(Show())
        `when`(mockShowsRepository.getSavedShows()).thenReturn(Single.just<List<Show>>(shows))

        presenter.loadWatchingShows()
        verify(mockView).hideEmptyListMessage()
        verify(mockView).displayWatchingShows(shows)
    }

    @Test
    fun shouldNavigateToShowDetails() {
        presenter.bindView(mockView)

        val show = MockShow.newMockShowInstance()

        presenter.openShowDetails(show)
        verify(mockView).navigateToShowDetails(show)
    }
}