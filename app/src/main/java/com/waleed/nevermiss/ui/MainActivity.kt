package com.waleed.nevermiss.ui

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.waleed.nevermiss.R
import com.waleed.nevermiss.ui.main.PlaceholderFragment
import com.waleed.nevermiss.ui.main.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        sectionsPagerAdapter.addFragment(PlaceholderFragment(), "Messages")
        sectionsPagerAdapter.addFragment(PlaceholderFragment(), "Groups")
        sectionsPagerAdapter.addFragment(PlaceholderFragment(), "History")
        sectionsPagerAdapter.addFragment(PlaceholderFragment(), "Profile")
        sectionsPagerAdapter.addFragment(PlaceholderFragment(), "Setting")


        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        // tabs.getTabAt(0)?.icon = getDrawable(R.drawable.ic_launcher_background)

        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


    }
}