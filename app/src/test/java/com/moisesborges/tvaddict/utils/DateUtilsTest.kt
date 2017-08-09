package com.moisesborges.tvaddict.utils

import org.hamcrest.Matchers.`is`
import org.junit.Test

import org.junit.Assert.*
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by moises.anjos on 08/08/2017.
 */
class DateUtilsTest {

    @Test
    fun airdateToUiString() {
        val uiString = DateUtils.airdateToUiString("2013-06-24")
        assertThat(uiString, `is`("Jun 24, 2013"))
    }

    @Test
    fun airdateGreaterThanToday() {
        val today = Date()
        assertThat(DateUtils.timeInTheFuture(DateUtils.dateAndHourToString(today)), `is`(false))
        val oneDayInMillis = TimeUnit.DAYS.toMillis(1)
        val tomorrow = Date(today.time + oneDayInMillis)
        assertThat(DateUtils.timeInTheFuture(DateUtils.dateAndHourToString(tomorrow)), `is`(true))

        val onHourInMillis = TimeUnit.HOURS.toMillis(1)
        val oneHourAdvance = Date(today.time + onHourInMillis)
        assertThat(DateUtils.timeInTheFuture(DateUtils.dateAndHourToString(oneHourAdvance)), `is`(true))

        val yesterday = Date(today.time - oneDayInMillis)
        assertThat(DateUtils.timeInTheFuture(DateUtils.dateAndHourToString(yesterday)), `is`(false))
    }
}