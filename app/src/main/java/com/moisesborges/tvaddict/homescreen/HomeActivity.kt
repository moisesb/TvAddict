package com.moisesborges.tvaddict.homescreen

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.search.SearchActivity
import com.moisesborges.tvaddict.shows.ShowsFragment
import com.moisesborges.tvaddict.upcoming.UpcomingEpisodesFragment
import com.moisesborges.tvaddict.watching.WatchListFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import java.util.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupActionBar()
        setupNavigationDrawer()
        setupTabs()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setupActionBar() {
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp)
        setSupportActionBar(toolbar)
    }

    private fun setupTabs() {
        sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        container.adapter = sectionsPagerAdapter
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }

    private fun setupNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_search -> openSearchActivity()
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    private fun openSearchActivity() {
        startActivity(SearchActivity.newIntent(this))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        return false
    }


    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private var fragments: MutableList<Fragment> = ArrayList(3)

        init {
            initFragments()
        }

        private fun initFragments() {
            fragments.add(ShowsFragment.newInstance())
            fragments.add(WatchListFragment.newInstance())
            fragments.add(UpcomingEpisodesFragment.newInstance())
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }
    }
}
