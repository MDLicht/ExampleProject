package com.mdlicht.zb.exampleproject.opencv.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import com.googlecode.tesseract.android.TessBaseAPI
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.common.util.BitmapUtil
import com.mdlicht.zb.exampleproject.databinding.ActivityOcrBinding
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream

class OcrActivity : AppCompatActivity() {
    lateinit var binding: ActivityOcrBinding

    private val compositeDisposable = CompositeDisposable()

    private val tesseract: TessBaseAPI = TessBaseAPI()

    init {
        System.loadLibrary("opencv_java3")
    }

    companion object {
        const val KEY_SRC = "src"

        fun startActivity(context: Context, src: Bitmap) {
            context.startActivity(Intent(context, OcrActivity::class.java).apply {
                putExtra(KEY_SRC, src)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ocr)

        initTesseract()

        val bitmap = intent.getParcelableExtra<Bitmap>(KEY_SRC)

        Glide.with(binding.ivSrc)
            .asBitmap()
            .load(bitmap)
            .into(binding.ivSrc)

        compositeDisposable.add(
            Observable.just(bitmap)
                .map {
                    tesseract.setImage(it)
                    tesseract.utF8Text
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    binding.tvOcrResult.text = it
                }, {
                    it.printStackTrace()
                }, {
                    tesseract.end()
                })
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
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
            val ins = assets.open("tessdata/$tessLangCode.traineddata")
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
        tesseract.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST,"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz0123456789\"'/\\,.!?@#\$%^&*()-=_+|")
        tesseract.init(Environment.getExternalStorageDirectory().absolutePath + "/tesseract", tessLangCode)
    }
}
