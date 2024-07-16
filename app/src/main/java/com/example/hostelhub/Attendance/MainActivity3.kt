package com.example.hostelhub.Attendance

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.hostelhub.Attendance.Adapter.MainPagerAdapter
import com.example.hostelhub.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity3:AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setViewPagerAdapter()
        setBottomViewListener()
        setViewPagerListener()

    }


    private fun setViewPagerAdapter() {
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter =
            MainPagerAdapter(supportFragmentManager)//viewpager connected with main pager
        viewPager.offscreenPageLimit = 1
    }

    private fun setBottomViewListener() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
//                R.id.qrScanMenuId -> {
//                    viewPager.currentItem = 0
//                }

                R.id.scannedResultMenuId -> {
                    viewPager.currentItem = 1

                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun setViewPagerListener() {
        viewPager = findViewById(R.id.viewPager)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        viewPager.setOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                when (position) {
//                    0 -> {
//                        bottomNavigationView.selectedItemId = R.id.qrScanMenuId
//                    }

                    1 -> {
                        bottomNavigationView.selectedItemId = R.id.scannedResultMenuId
                    }

                }
            }
        })

    }
}