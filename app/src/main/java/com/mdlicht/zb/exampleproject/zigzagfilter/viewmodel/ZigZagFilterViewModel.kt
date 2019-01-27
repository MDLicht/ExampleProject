package com.mdlicht.zb.exampleproject.zigzagfilter.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableInt
import android.databinding.ObservableList

class ZigZagFilterViewModel : ViewModel() {
    val minPrice: ObservableInt = ObservableInt(0)
    val maxPrice: ObservableInt = ObservableInt(0)
    val color: ObservableList<String> = ObservableArrayList<String>()
}