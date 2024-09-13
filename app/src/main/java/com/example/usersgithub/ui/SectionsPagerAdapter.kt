package com.example.usersgithub.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class SectionsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, data: Bundle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    private var fragmentBundle: Bundle
    init {
        fragmentBundle=data
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment()
            1 -> fragment = FollowersFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

}