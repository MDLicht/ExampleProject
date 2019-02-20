package com.mdlicht.zb.exampleproject.zigzagfilter.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class ZigZagFilterViewModel : ViewModel() {
    val minPrice: LiveData<Int> = MutableLiveData<Int>()
    val maxPrice: LiveData<Int> = MutableLiveData<Int>()
    val color: LiveData<List<String>> = MutableLiveData<List<String>>()
}