package com.moisesborges.tvaddict.episodes

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewAction
import android.support.test.espresso.ViewAssertion
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.*
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.adapters.EpisodesAdapter
import com.moisesborges.tvaddict.adapters.UpcomingEpisodesAdapter
import com.moisesborges.tvaddict.models.Show
import com.moisesborges.tvaddict.utils.HtmlUtils
import com.moisesborges.tvaddict.utils.MockShow
import kotlinx.android.synthetic.main.activity_episodes.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by moises.anjos on 13/07/2017.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class EpisodesActivityTest {

    @Rule @JvmField
    val activityTestRule: ActivityTestRule<EpisodesActivity> = ActivityTestRule(EpisodesActivity::class.java, false, false)
    lateinit var intent: Intent
    lateinit var show: Show

    @Before
    fun setUp() {
        show = MockShow.newMockShowInstance()
        show.embedded = MockShow.newEmbeddedInstance()

        val seasonNumber = show.seasons.first().number
        intent = EpisodesActivity.newIntent(InstrumentationRegistry.getContext(), show, seasonNumber)
    }

    @Test
    fun shouldDisplayEpisodeDetails() {
        activityTestRule.launchActivity(intent)

        onView(withId(R.id.episodes_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<EpisodesAdapter.ViewHolder>(1, click()))

        val summary = HtmlUtils.extractFromHtml(show.episodes.first().summary).toString()
        onView(withId(R.id.episode_summary_text_view))
                .check(matches(isDisplayed()))
                .check(matches(withText(summary)))
    }
}