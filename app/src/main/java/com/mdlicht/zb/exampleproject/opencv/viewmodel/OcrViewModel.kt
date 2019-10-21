package com.mdlicht.zb.exampleproject.opencv.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.databinding.ObservableField
import android.graphics.Bitmap
import android.os.Environment
import com.bumptech.glide.Glide
import com.googlecode.tesseract.android.TessBaseAPI
import com.mdlicht.zb.exampleproject.common.MultiDexApp
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream

class OcrViewModel(app: Application, b: Bitmap) : AndroidViewModel(app) {
    val text: ObservableField<String> = ObservableField()
    val bitmap: ObservableField<Bitmap> = ObservableField(b)

    private val compositeDisposable = CompositeDisposable()

    private val tesseract: TessBaseAPI = TessBaseAPI()

    init {
        initTesseract()
        ocr(b)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun ocr(bitmap: Bitmap) {
        compositeDisposable.add(
            Observable.just(bitmap)
                .map {
                    tesseract.setImage(it)
                    tesseract.utF8Text
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    text.set(it)
                }, {
                    it.printStackTrace()
                }, {
                    tesseract.end()
                })
        )
    }

    fun initTesseract() {
        // tesseract reads language from tesseract folder, create it if not exists.
        val f = File(Environment.getExternalStorageDirectory().absolutePath + "/tesseract/tessdata")
        if (!f.exists()) {
            f.mkdirs()
        }

        val tessLangCode = "eng"
        // copy the eng lang file from assets folder if not exists.
        val f1 =
            File(Environment.getExternalStorageDirectory().absolutePath + "/tesseract/tessdata/$tessLangCode.traineddata")
        if (!f1.exists()) {
            val ins = getApplication<MultiDexApp>().assets.open("tessdata/$tessLangCode.traineddata")
            val fout = FileOutputStream(f1)
            val buf = ByteArray(1024)
            var len: Int

            while (true) {
                val byteCount = ins.read(buf)
                if (byteCount < 0)
                    break
                fout.write(buf, 0, byteCount)
            }
            ins.close()
            fout.close()
        }
        tesseract.pageSegMode = TessBaseAPI.PageSegMode.PSM_OSD_ONLY
        tesseract.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST,"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz0123456789\"'/\\,.!?@#\$%^&*()-=_+|;:\'`~[]{}<>")
        tesseract.init(Environment.getExternalStorageDirectory().absolutePath + "/tesseract", tessLangCode)
    }
}