package com.mdlicht.zb.exampleproject.zigzagfilter.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.zigzagfilter.fragment.FilterColorFragment
import com.mdlicht.zb.exampleproject.zigzagfilter.fragment.FilterPriceFragment

class BottomFilterPagerAdapter(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm) {
    private val pageList: List<Fragment> = listOf(FilterPriceFragment.newInstance(), FilterColorFragment.newInstance())
    private val pageTitleList: List<String> = listOf(context.getString(R.string.zigzag_filter_price_title), context.getString(R.string.zigzag_filter_color_title))

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