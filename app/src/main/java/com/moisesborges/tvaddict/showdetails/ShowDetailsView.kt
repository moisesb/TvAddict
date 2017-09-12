package com.moisesborges.tvaddict.showdetails

import com.moisesborges.tvaddict.models.CastMember
import com.moisesborges.tvaddict.models.Externals
import com.moisesborges.tvaddict.models.Season
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.mvp.BaseView

/**
 * Created by moises.anjos on 18/04/2017.
 */

interface ShowDetailsView : BaseView {
    fun setShowName(showName: String)

    fun setShowImage(imageUrl: String)

    fun setShowSummary(summary: String)

    fun displaySeasons(seasons: List<Season>)

    fun displayAdditionalInfoLoadingInProgress(isLoading: Boolean)

    fun displayAdditionalInfoNotLoaded(hasError: Boolean)

    fun setShow(show: Show)

    fun navigateToEpisodes(show: Show, seasonNumber: Int)

    fun displaySavedShowMessage()

    fun displayCastMembers(castMembers: List<CastMember>)

    fun displaySaveShowButton(shouldDisplaySavedAction: Boolean)

    fun displayShowRemovedMessage()

    fun setShowRating(rating: Double?)

    fun setShowRuntime(runtime: Int?)

    fun setShowNetwork(networkName: String)

    fun setShowGenres(genres: List<String>)

    fun setShowExternalLinks(externals: Externals)
}
