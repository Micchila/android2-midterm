package com.example.wealthtracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_VERTICAL
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val root = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.nm_li_cl_main)
        viewPager = findViewById(R.id.nm_li_vp_main)
        tabLayout = findViewById(R.id.nm_li_tl_main)

        ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                view.paddingLeft,
                bars.top + 8,
                view.paddingRight,
                bars.bottom
            )
            insets
        }

        val pagerAdapter = WealthPagerAdapter(this)
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = 3
        viewPager.orientation = if (StudentIdentity.isSurnameStartsWithVowel()) {
            ORIENTATION_HORIZONTAL
        } else {
            ORIENTATION_VERTICAL
        }

        val tabTitles = listOf(
            getString(R.string.tab_input),
            getString(R.string.tab_analytics),
            getString(R.string.tab_profile)
        )
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    fun openAnalyticsTab() {
        if (::viewPager.isInitialized) {
            viewPager.setCurrentItem(1, true)
        }
    }
}
