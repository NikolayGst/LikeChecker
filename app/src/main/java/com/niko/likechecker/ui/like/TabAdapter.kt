package com.niko.likechecker.ui.like

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.niko.likechecker.R
import com.niko.likechecker.ui.like.fragments.scanner.FriendsScannerFragment
import com.niko.likechecker.ui.like.fragments.setting.FriendsSettingsFragment

class TabAdapter(fm: FragmentManager, private val id: String, private val context: Context) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int) = when (position) {
        0 -> FriendsSettingsFragment.newInstance(id)
        1 -> FriendsScannerFragment()
        else -> null
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int) = when (position) {
        0 -> context.getString(R.string.tab_setting)
        1 -> context.getString(R.string.tab_result)
        else -> null
    }
}
