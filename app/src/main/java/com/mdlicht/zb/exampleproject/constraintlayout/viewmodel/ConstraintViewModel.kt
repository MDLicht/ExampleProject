package com.mdlicht.zb.exampleproject.constraintlayout.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.view.View

class ConstraintViewModel : ViewModel() {
    val nextClick: MutableLiveData<Boolean> = MutableLiveData()
    val cancelClick: MutableLiveData<Boolean> = MutableLiveData()
    val isNextStep: ObservableBoolean = ObservableBoolean(false)

    fun onNextClick(view: View, isCancelDisplay: Boolean) {
        isNextStep.set(true)
        nextClick.postValue(isCancelDisplay)
    }

    fun onCancelClick(view: View) {
        isNextStep.set(false)
        cancelClick.postValue(true)
    }
}