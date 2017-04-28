package com.moisesborges.tvaddict.showdetails;

import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.models.CastMember;
import com.moisesborges.tvaddict.models.Embedded;
import com.moisesborges.tvaddict.models.Season;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.mvp.BaseView;

import java.util.List;

/**
 * Created by moises.anjos on 18/04/2017.
 */

interface ShowDetailsView extends BaseView{
    void setShowName(String showName);

    void setShowImage(String imageUrl);

    void setShowSummary(String summary);

    void displaySeasons(@NonNull List<Season> seasons);

    void displaySeasonsProgress(boolean isLoading);

    void displaySeasonsNotLoaded(boolean hasError);

    void setShow(@NonNull Show show);

    void navigateToEpisodes(int showId, int seasonNumber, Embedded embeddedData);

    void displaySavedShowMessage();

    void displayCastMembers(List<CastMember> castMembers);

    void displaySaveShowButton(boolean shouldDisplaySavedAction);

    void displayShowRemovedMessage();
}
