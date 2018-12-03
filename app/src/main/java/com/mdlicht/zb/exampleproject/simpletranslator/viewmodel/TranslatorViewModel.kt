package com.mdlicht.zb.exampleproject.simpletranslator.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.databinding.ObservableField
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.simpletranslator.api.RetrofitUtil
import com.mdlicht.zb.exampleproject.simpletranslator.model.Require
import io.reactivex.Observable
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

    /**
     * Initialize publish subject for translation
     */
    init {
        compositeDisposable.add(
            publishSubject
                .debounce(500, TimeUnit.MILLISECONDS) // If any input doesn't happen for 500 ms
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

    /**
     * @param view : Clicked view
     * If translation's result text is not empty, copy it to clip board
     */
    fun onResultLongClick(view: View): Boolean {
        destText.get()?.let {
            compositeDisposable.add(
                Observable.just(it)
                    .filter { text ->
                        !TextUtils.isEmpty(text)
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { text ->
                        run {
                            val clipBoard: ClipboardManager =
                                view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clipData: ClipData = ClipData.newPlainText(
                                view.context.getString(R.string.simple_translator_copy_clipboard),
                                text
                            )
                            clipBoard.primaryClip = clipData
                            Toast.makeText(
                                view.context,
                                view.context.getString(R.string.simple_translator_msg_copy_clipboard),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            )
        } ?: run {
            Toast.makeText(
                view.context,
                view.context.getString(R.string.simple_translator_msg_empty_text),
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }
}