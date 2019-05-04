package com.mdlicht.zb.exampleproject.opencv.viewmodel.factory

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.graphics.Bitmap
import com.mdlicht.zb.exampleproject.opencv.viewmodel.OcrViewModel

class OcrViewModelFactory (val app: Application, val bitmap: Bitmap): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OcrViewModel(app, bitmap) as T
    }
}