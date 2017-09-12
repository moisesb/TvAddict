package com.moisesborges.tvaddict.showdetails

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Season
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.mvp.BasePresenter
import com.moisesborges.tvaddict.mvp.RxJavaConfig

import javax.inject.Inject

/**
 * Created by moises.anjos on 18/04/2017.
 */

class ShowDetailsPresenter @Inject
constructor(private val showsRepository: ShowsRepository,
            rxJavaConfig: RxJavaConfig) : BasePresenter<ShowDetailsView>(rxJavaConfig) {

    fun loadShowDetails(show: Show) {
        checkView()

        displayBasicShowInfo(show)

        loadShowAdditionalInfo(show)

        verifySavedShowStatus(show)
    }

    private fun displayBasicShowInfo(show: Show) {
        view.setShowImage(show.image.medium)
        view.setShowName(show.name)
        view.setShowSummary(show.summary)
        view.displayAdditionalInfoNotLoaded(false)
        view.setShowRating( show.rating?.average)
        view.setShowNetwork( show.network?.name ?: "")
        view.setShowRuntime(show.runtime)
        view.setShowGenres(show.genres)
        view.setShowExternalLinks(show.externals)
    }

    private fun loadShowAdditionalInfo(show: Show) {
        view.displayAdditionalInfoLoadingInProgress(true)
        val loadShowDataDisposable = showsRepository.getFullShowInfo(show.id)
                .compose(applySchedulersToSingle<Show>())
                .onErrorReturn { ignored -> Show.NOT_FOUND }
                .subscribe { showFullInfo ->
                    view.displayAdditionalInfoLoadingInProgress(false)
                    if (showFullInfo === Show.NOT_FOUND) {
                        view.displayAdditionalInfoNotLoaded(true)
                        return@subscribe
                    }

                    view.setShow(showFullInfo)
                    if (showFullInfo.seasons != null) {
                        view.displaySeasons(showFullInfo.seasons)
                    }
                    if (showFullInfo.cast != null) {
                        view.displayCastMembers(showFullInfo.cast)
                    }
                }

        addDisposable(loadShowDataDisposable)
    }

    private fun verifySavedShowStatus(show: Show) {
        val checkSavedShowDisposable = showsRepository.getSavedShow(show.id)
                .compose(applySchedulersToSingle<Show>())
                .subscribe({ savedShow -> view.displaySaveShowButton(false) }
                ) { ignored -> view.displaySaveShowButton(true) }

        addDisposable(checkSavedShowDisposable)
    }

    fun changeWatchingStatus(show: Show) {
        checkView()

        val checkStatusDisposable = showsRepository.getSavedShow(show.id)
                .compose(applySchedulersToSingle<Show>())
                .onErrorReturn { ignored -> Show.NOT_FOUND }
                .flatMap<Show> { showFromDb ->
                    if (showFromDb === Show.NOT_FOUND) {
                        showsRepository.saveShow(show)
                    } else {
                        showsRepository.removeShow(show.id)
                    }
                }
                .subscribe { showAfterChange ->
                    if (showAfterChange === Show.REMOVED) {
                        view.displayShowRemovedMessage()
                        view.displaySaveShowButton(true)
                    } else {
                        view.displaySavedShowMessage()
                        view.displaySaveShowButton(false)
                    }

                }

        addDisposable(checkStatusDisposable)
    }

    fun openEpisodes(show: Show, season: Season) {
        checkView()

        view.navigateToEpisodes(show, season.number)
    }
}
