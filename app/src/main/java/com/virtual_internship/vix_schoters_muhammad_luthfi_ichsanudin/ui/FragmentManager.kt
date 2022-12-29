package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.ui.fragment.*
import com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.utils.Constants.TOTAL_NEWS_TAB

class FragmentManager(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle){

    override fun getItemCount(): Int = TOTAL_NEWS_TAB

    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> { return GeneralFragment() }
            1 -> { return BusinessFragment() }
            2 -> { return EntertainmentFragment() }
            3 -> { return HealthFragment() }
            4 -> { return ScienceFragment() }
            5 -> { return SportsFragment() }
            6 -> { return TechFragment() }

            else -> return BusinessFragment()

        }
    }
}