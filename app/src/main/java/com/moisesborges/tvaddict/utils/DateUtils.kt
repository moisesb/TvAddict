package com.moisesborges.tvaddict.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by moises.anjos on 04/08/2017.
 */
object DateUtils {


    fun dateToAirdate(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(date)
    }

}