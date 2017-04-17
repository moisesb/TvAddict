package com.moisesborges.tvaddict.homescreen;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.moisesborges.tvaddict.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Created by moises.anjos on 13/04/2017.
 */
@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public final ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldDisplayThreeTabs() throws Exception {
        onView(withText(R.string.tab_shows))
                .check(matches(isDisplayed()));

        onView(withText(R.string.tab_watching))
                .check(matches(isDisplayed()));

        onView(withText(R.string.tab_upcoming))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayShowsFragment() throws Exception {
        onView(withText(R.string.tab_shows))
                .perform(click());

        onView(withId(R.id.shows_layout))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayWatchingShows() throws Exception {
        onView(withText(R.string.tab_watching))
                .perform(click());

        onView(withId(R.id.watching_shows_layout))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayUpcomingEpisodes() throws Exception {
        onView(withText(R.string.tab_upcoming))
                .perform(click());

        onView(withId(R.id.upcoming_episodes_layout))
                .check(matches(isDisplayed()));
    }
}