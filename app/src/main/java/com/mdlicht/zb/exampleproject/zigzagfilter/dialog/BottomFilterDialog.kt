package com.mdlicht.zb.exampleproject.zigzagfilter.dialog


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.FragmentBottomFilterDialogBinding
import com.mdlicht.zb.exampleproject.zigzagfilter.adapter.BottomFilterPagerAdapter
import com.mdlicht.zb.exampleproject.zigzagfilter.fragment.FilterBaseFragment
import com.mdlicht.zb.exampleproject.zigzagfilter.fragment.FilterColorFragment
import com.mdlicht.zb.exampleproject.zigzagfilter.fragment.FilterPriceFragment
import kotlinx.android.synthetic.main.tab_zigzag_filter.view.*

class BottomFilterDialog : BottomSheetDialogFragment(), ViewPager.OnPageChangeListener,
    FilterPriceFragment.OnPriceChangedListener, FilterColorFragment.OnColorListChangedListener {
    lateinit var binding: FragmentBottomFilterDialogBinding

    var minPrice: Int = 0
    var maxPrice: Int = 100
    var colorList: MutableList<String> = mutableListOf()

    var onFilterChangedListener: OnFilterChangedListener? = null

    public interface OnFilterChangedListener {
        public fun onFilterChanged(minPrice: Int, maxPrice: Int, colorList: List<String>)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFilterChangedListener)
            onFilterChangedListener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_filter_dialog, container, false)
        binding.dialog = this

        binding.vpContainer.apply {
            adapter = BottomFilterPagerAdapter(childFragmentManager, context)
            addOnPageChangeListener(this@BottomFilterDialog)
        }

        binding.tabLayout.apply {
            setupWithViewPager(binding.vpContainer)
            for (i in 0 until tabCount) {
                val tab = getTabAt(i)
                val view = LayoutInflater.from(context).inflate(R.layout.tab_zigzag_filter, this, false)
                view.tvTitle.text = (binding.vpContainer.adapter as FragmentPagerAdapter).getPageTitle(i)
                setTabCheck(view.ivCheck, isChecked(i))
                tab?.customView = view
            }
        }

        return binding.root
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {

    }

    fun onClearClick(v: View) {
        ((binding.vpContainer.adapter as BottomFilterPagerAdapter).getItem(binding.vpContainer.currentItem) as FilterBaseFragment).clearFilter()
    }

    fun onConfirmClick(v: View) {
        onFilterChangedListener?.onFilterChanged(minPrice, maxPrice, colorList)
        dismissAllowingStateLoss()
    }

    fun setTabCheck(ivCheck: ImageView, isChecked: Boolean) {
        ivCheck.visibility = if (isChecked) View.VISIBLE else View.INVISIBLE
    }

    override fun onPriceChanged(minPrice: Int, maxPrice: Int) {
        this.minPrice = minPrice
        this.maxPrice = maxPrice
        setTabCheck(binding.tabLayout.getTabAt(0)?.customView?.ivCheck!!, isChecked(0))
    }

    override fun onColorListChanged(colorList: List<String>) {
        this.colorList.clear()
        this.colorList.addAll(colorList)
        setTabCheck(binding.tabLayout.getTabAt(1)?.customView?.ivCheck!!, isChecked(1))
    }

    fun isChecked(position: Int): Boolean {
        if (position == 0) {
            return minPrice != 0 || maxPrice != 100
        } else if (position == 1) {
            return colorList.size > 0
        }
        return false
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BottomFilterDialog().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
