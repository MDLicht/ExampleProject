package com.mdlicht.zb.exampleproject.xmlparsing.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mdlicht.zb.exampleproject.xmlparsing.api.RetrofitXmlUtil
import com.mdlicht.zb.exampleproject.xmlparsing.model.Note
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class XmlParsingViewModel : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val note: MutableLiveData<Note> = MutableLiveData()

    init {
        loadNote()
    }

    private fun loadNote() {
        compositeDisposable.add(
            RetrofitXmlUtil.create().getNote().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    note.value = it
                },
                {
                    it.printStackTrace()
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}