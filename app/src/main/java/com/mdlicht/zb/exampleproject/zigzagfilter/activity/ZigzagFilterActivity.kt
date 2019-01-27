package com.mdlicht.zb.exampleproject.zigzagfilter.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
        binding.vm = ViewModelProviders.of(this).get(ZigZagFilterViewModel::class.java)
        binding.activity = this
    }

    fun onClickOpenDialog(view: View) {
        BottomFilterDialog.newInstance().show(supportFragmentManager, null)
    }

    override fun onFilterChanged(minPrice: Int, maxPrice: Int, colorList: List<String>) {
        binding.tvColor.text = colorList.joinToString()
        binding.tvPrice.text = "$minPrice ~ $maxPrice"
    }
}
