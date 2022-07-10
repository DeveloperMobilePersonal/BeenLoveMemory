package com.teamdev.demngayyeu2020.ui.adapter

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

@SuppressLint("NotifyDataSetChanged")
class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    var fragmets = listOf<Fragment>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return if (fragmets.isNullOrEmpty()) 0 else fragmets.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmets[position]
    }
}