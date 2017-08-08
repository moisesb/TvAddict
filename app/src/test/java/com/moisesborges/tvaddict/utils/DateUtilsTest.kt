package com.moisesborges.tvaddict.utils

import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by moises.anjos on 08/08/2017.
 */
class DateUtilsTest {

    @Test
    fun airdateToUiString() {
        val uiString = DateUtils.airdateToUiString("2013-06-24")
        assertThat(uiString, `is`("Jun 24, 2013"))
    }

}