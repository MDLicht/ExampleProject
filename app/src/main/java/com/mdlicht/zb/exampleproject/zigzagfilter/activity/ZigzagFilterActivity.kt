package com.mdlicht.zb.exampleproject.zigzagfilter.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
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
        binding.lifecycleOwner = this
    }

    fun onClickOpenDialog(view: View) {
        BottomFilterDialog.newInstance().show(supportFragmentManager, null)
    }

    override fun onFilterChanged(minPrice: Int, maxPrice: Int, colorList: List<String>) {
        val colorStr = colorList.joinToString {
            "<font color='$it'>$it</font>"
        }
        binding.tvColor.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(
                colorStr,
                Html.FROM_HTML_MODE_LEGACY
            )
        } else {
            Html.fromHtml(colorStr)
        }
        binding.tvPrice.text = "$minPrice ~ $maxPrice"
    }
}
