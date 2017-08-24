package com.moisesborges.tvaddict.watching

import com.moisesborges.tvaddict.data.ShowsRepository
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.mvp.BasePresenter
import com.moisesborges.tvaddict.mvp.ContentObserverView
import com.moisesborges.tvaddict.mvp.RxJavaConfig
import com.moisesborges.tvaddict.mvp.asContentObserverView
import com.raizlabs.android.dbflow.runtime.FlowContentObserver
import com.raizlabs.android.dbflow.sql.language.SQLOperator
import com.raizlabs.android.dbflow.structure.BaseModel

import javax.inject.Inject

/**
 * Created by Moisés on 22/05/2017.
 */

class WatchingShowsPresenter
@Inject constructor(rxJavaConfig: RxJavaConfig,
                    private val showsRepository: ShowsRepository) : BasePresenter<WatchingShowsView>(rxJavaConfig) {

    var contentObserver: FlowContentObserver = FlowContentObserver()

    override fun bindView(view: WatchingShowsView) {
        super.bindView(view)
        view.asContentObserverView()?.registerContentObserver(contentObserver, Show::class.java)
        contentObserver.addModelChangeListener { clazz: Class<*>?, action: BaseModel.Action, arrayOfSQLOperators: Array<SQLOperator> -> loadWatchingShows() }
    }

    override fun unbindView() {
        view.asContentObserverView()?.unregisterContentObserver(contentObserver)
        super.unbindView()
    }

    fun loadWatchingShows() {
        checkView()

        val disposable = showsRepository.getSavedShows()
                .compose(applySchedulersToSingle<List<Show>>())
                .subscribe { shows ->
                    if (shows.isEmpty()) {
                        view.displayEmptyListMessage()
                    } else {
                        view.displayWatchingShows(shows)
                    }
                }

        addDisposable(disposable)
    }

    fun openShowDetails(show: Show) {
        checkView()

        view.navigateToShowDetails(show)
    }
}
