package com.mdlicht.zb.exampleproject.zigzagfilter.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ActivityZigzagFilterBinding
import com.mdlicht.zb.exampleproject.zigzagfilter.dialog.BottomFilterDialog
import com.mdlicht.zb.exampleproject.zigzagfilter.viewmodel.ZigZagFilterViewModel

class ZigzagFilterActivity : AppCompatActivity(), BottomFilterDialog.OnFilterChangedListener {
    lateinit var binding: ActivityZigzagFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_zigzag_filter)
        binding.vm = ViewModelProviders.of(this).get(ZigZagFilterViewModel::class.java).apply {
            clickDialog.observe(this@ZigzagFilterActivity, Observer {
                BottomFilterDialog.newInstance().show(supportFragmentManager, null)
            })
        }
        binding.activity = this
        binding.lifecycleOwner = this
    }

    override fun onFilterChanged(minPrice: Int, maxPrice: Int, colorList: List<String>) {
        binding.vm?.minPrice?.value = minPrice
        binding.vm?.maxPrice?.value = maxPrice
        binding.vm?.color?.value = colorList
    }
}
