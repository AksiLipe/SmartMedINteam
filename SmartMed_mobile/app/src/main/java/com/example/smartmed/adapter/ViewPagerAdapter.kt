package com.example.smartmed.adapter

import ProfileFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.smartmed.fragments.AppointmentFragment
import com.example.smartmed.fragments.IndicatorFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AppointmentFragment()
            1 -> IndicatorFragment()
            2 -> ProfileFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
}