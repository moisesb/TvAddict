package com.moisesborges.tvaddict.mvp

import com.raizlabs.android.dbflow.runtime.FlowContentObserver

/**
 * Created by moises.anjos on 24/08/2017.
 */
interface ContentObserverView {
    fun <T> registerContentObserver(contentObserver: FlowContentObserver, classOf: Class<T>)

    fun unregisterContentObserver(contentObserver: FlowContentObserver)
}