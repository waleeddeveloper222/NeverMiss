package com.waleed.nevermiss.ui.main

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.waleed.nevermiss.R
import com.waleed.nevermiss.ui.fragment.group.GroupFragment
import com.waleed.nevermiss.ui.fragment.history.HistoryFragment
import com.waleed.nevermiss.ui.fragment.message.MessageFragment
import com.waleed.nevermiss.ui.fragment.profile.ProfileFragment
import android.view.MenuInflater
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_send_message.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        sectionsPagerAdapter.addFragment(MessageFragment(), "Messages")
        sectionsPagerAdapter.addFragment(HistoryFragment(), "History")
        sectionsPagerAdapter.addFragment(GroupFragment(), "Groups")
        sectionsPagerAdapter.addFragment(ProfileFragment(), "Profile")

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_tips -> {
                // tips()
                true
            }
            R.id.menu_sync -> {
                //sync()
                true
            }
            R.id.menu_logout -> {
                //logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}