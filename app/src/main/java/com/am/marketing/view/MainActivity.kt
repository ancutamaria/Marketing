package com.am.marketing.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.am.marketing.R
import com.am.marketing.model.TargetingSpecific
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var prevMenuItem: BottomNavigationItemView
    lateinit var nextMenuItem: BottomNavigationItemView
    lateinit var listOfFragments: MutableList<Fragment>
    var currentFragmentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listOfFragments = mutableListOf()
        listOfFragments.apply {
            add(TargetingSpecificsFragment())
            add(ChannelsFragment())
            add(CampaignsFragment())
            add(ReviewFragment())
        }
        bottomNavigationView = findViewById(R.id.bottom_navigation_menu)
        prevMenuItem = findViewById(R.id.goToPrevFragment)
        prevMenuItem.visibility = View.INVISIBLE
        nextMenuItem = findViewById(R.id.goToNextFragment)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            val fragment = TargetingSpecificsFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    @SuppressLint("RestrictedApi")
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.goToPrevFragment -> {

                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, listOfFragments[--currentFragmentIndex])
                    .commit()
                if (currentFragmentIndex == 0){
                    prevMenuItem.visibility = View.INVISIBLE
                }
                nextMenuItem.visibility = View.VISIBLE
                return@OnNavigationItemSelectedListener true

            }
            R.id.goToNextFragment -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, listOfFragments[++currentFragmentIndex])
                    .commit()
                if (currentFragmentIndex == listOfFragments.size - 1){
//                    nextMenuItem.visibility = View.INVISIBLE
                    nextMenuItem.setTitle("Done")
                }
                prevMenuItem.visibility = View.VISIBLE
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun TextView.setSelectedItem(ts: TargetingSpecific) {
        if (ts.selected)
            setTextColor(resources.getColor(R.color.lavender_color))
        else
            setTextColor(resources.getColor(R.color.white))
    }
}