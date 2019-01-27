package com.mdlicht.zb.exampleproject.zigzagfilter.fragment


import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.FragmentFilterColorBinding
import com.mdlicht.zb.exampleproject.zigzagfilter.adapter.FilterColorRvAdapter
import com.mdlicht.zb.exampleproject.zigzagfilter.dialog.BottomFilterDialog

class FilterColorFragment : FilterBaseFragment(), FilterColorRvAdapter.OnSelctedColorChangedListener {
    lateinit var binding: FragmentFilterColorBinding

    val colorList = ObservableArrayList<String>()

    var onColorListChangedListener: OnColorListChangedListener? = null

    public interface OnColorListChangedListener {
        public fun onColorListChanged(colorList: List<String>)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        if(parentFragment is BottomFilterDialog)
            onColorListChangedListener = parentFragment as OnColorListChangedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter_color, container, false)

        binding.rvColor.apply {
            layoutManager = GridLayoutManager(context, 5)
            adapter = FilterColorRvAdapter(this@FilterColorFragment)
        }

        return binding.root
    }

    override fun onSelectedColorChanged(colorList: List<String>) {
        this.colorList.clear()
        this.colorList.addAll(colorList)
        onColorListChangedListener?.onColorListChanged(this.colorList)
    }

    override fun clearFilter() {
        (binding.rvColor.adapter as FilterColorRvAdapter).clearColorFilter()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FilterColorFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
