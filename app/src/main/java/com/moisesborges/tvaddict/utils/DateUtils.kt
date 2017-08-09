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
    private val airtimeFormat = SimpleDateFormat("HH:mm")

    @JvmStatic
    fun dateToAirdate(date: Date): String {
        return airdateFormat.format(date)
    }

    @JvmStatic
    fun dateToAirtime(date: Date): String {
        return airtimeFormat.format(date)
    }

    @JvmStatic
    fun stringToDateAndHour(date: String): Date {
        return stringDateFormat.parse(date)
    }

    @JvmStatic
    fun dateAndHourToString(date: Date): String {
        return stringDateFormat.format(date)
    }

    @JvmStatic
    fun airdateToUiString(airdate: String): String {
        return uiDateFormat.format(airdateFormat.parse(airdate)).capitalize()
    }

    @JvmStatic
    fun timeInTheFuture(time: String): Boolean {
        return stringToDateAndHour(time).after(Date())
    }



}