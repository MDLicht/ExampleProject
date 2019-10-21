package com.mdlicht.zb.exampleproject.baserecyclerview.viewmodel

import androidx.lifecycle.ViewModel
import android.view.View
import com.mdlicht.zb.exampleproject.common.showToast

class ItemViewModel: ViewModel() {
    fun onItemClick(view: View, item: String) {
        view.context.showToast(item)
    }
}