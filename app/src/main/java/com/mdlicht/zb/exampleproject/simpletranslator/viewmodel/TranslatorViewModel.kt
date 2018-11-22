package com.mdlicht.zb.exampleproject.simpletranslator.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.util.Log
import com.mdlicht.zb.exampleproject.simpletranslator.api.RetrofitUtil
import com.mdlicht.zb.exampleproject.simpletranslator.model.Require
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class TranslatorViewModel : ViewModel() {
    val srcText: ObservableField<String> = ObservableField()
    val destText: ObservableField<String> = ObservableField()
    private val publishSubject = PublishSubject.create<String>()
    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(
            publishSubject
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .filter {
                    it.isNotEmpty()
                }
                .switchMap {
                    RetrofitUtil.create().translate(Require("ko", "en", it))
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    destText.set(it.message.result.translatedText)
                }, {
                    destText.set("")
                })
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun onTextChanged(text: CharSequence) {
        publishSubject.onNext(text.toString())
    }
}