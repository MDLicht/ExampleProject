package com.mdlicht.zb.exampleproject.zigzagfilter.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.mdlicht.zb.exampleproject.zigzagfilter.fragment.FilterColorFragment
import com.mdlicht.zb.exampleproject.zigzagfilter.fragment.FilterPriceFragment

class BottomFilterPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val pageList: List<Fragment> = listOf(FilterPriceFragment.newInstance(), FilterColorFragment.newInstance())
    private val pageTitleList: List<String> = listOf("가격", "색상")

    override fun getItem(position: Int): Fragment {
        return pageList[position]
    }

    override fun getCount(): Int {
        return pageList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pageTitleList[position]
    }
}