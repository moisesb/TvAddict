package com.moisesborges.tvaddict.showdetails;

import com.moisesborges.tvaddict.mvp.BaseView;

/**
 * Created by moises.anjos on 18/04/2017.
 */

interface ShowDetailsView extends BaseView{
    void setShowName(String showName);

    void setShowImage(String imageUrl);

    void setShowSummary(String summary);
}
