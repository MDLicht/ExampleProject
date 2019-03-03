package com.mdlicht.zb.exampleproject.zigzagfilter.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Build
import android.text.Html
import android.text.Spanned

class ZigZagFilterViewModel : ViewModel() {
    val minPrice: MutableLiveData<Int> = MutableLiveData<Int>()
    val maxPrice: MutableLiveData<Int> = MutableLiveData<Int>()
    val color: MutableLiveData<List<String>> = MutableLiveData<List<String>>()

    val clickDialog: MutableLiveData<Boolean> = MutableLiveData()

    fun onClickShowDialog() {
        clickDialog.value = true
    }

    fun convertPriceString(minP: Int?, maxP: Int?): String {
        return if(minP == null || maxP == null) {
            "-"
        } else {
            "$minP ~ $maxP"
        }
    }

    fun convertColorString(colorList: List<String>?): Spanned {
        val colorStr = if(colorList == null || colorList.isEmpty()) {
            "-"
        } else {
            colorList.joinToString {
                "<font color='$it'>$it</font>"
            }
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(
                colorStr,
                Html.FROM_HTML_MODE_LEGACY
            )
        } else {
            Html.fromHtml(colorStr)
        }
    }
}