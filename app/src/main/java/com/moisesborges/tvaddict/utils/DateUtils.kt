package com.moisesborges.tvaddict.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by moises.anjos on 04/08/2017.
 */
object DateUtils {

    private val stringDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    private val uiDateFormat = SimpleDateFormat("MMM d, yyyy")
    private val airdateFormat = SimpleDateFormat("yyyy-MM-dd")

    fun dateToAirdate(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(date)
    }


    fun stringDateToLong(date: String): Date {
        return stringDateFormat.parse(date)
    }


    fun airdateToUiString(airdate: String): String {
        return uiDateFormat.format(airdateFormat.parse(airdate)).capitalize()
    }

}