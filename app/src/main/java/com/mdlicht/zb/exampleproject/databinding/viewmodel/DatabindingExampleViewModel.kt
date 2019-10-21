package com.mdlicht.zb.exampleproject.databinding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.mdlicht.zb.exampleproject.databinding.model.Color

class DatabindingExampleViewModel: ViewModel() {
    val count: ObservableInt = ObservableInt(0)
    val text: ObservableField<String> = ObservableField()
    val color: ObservableField<Color> = ObservableField()

    fun onDecClick() {
        count.set(count.get() - 1)
    }

    fun onIncClick() {
        count.set(count.get() + 1)
    }
}