package com.mdlicht.zb.exampleproject.opencv.activity

import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import com.googlecode.tesseract.android.TessBaseAPI
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.common.MultiDexApp
import com.mdlicht.zb.exampleproject.common.util.BitmapUtil
import com.mdlicht.zb.exampleproject.databinding.ActivityOcrBinding
import com.mdlicht.zb.exampleproject.opencv.viewmodel.OcrViewModel
import com.mdlicht.zb.exampleproject.opencv.viewmodel.factory.OcrViewModelFactory
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream

class OcrActivity : AppCompatActivity() {
    lateinit var binding: ActivityOcrBinding

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

        binding.apply {
            val bitmap = intent.getParcelableExtra<Bitmap>(KEY_SRC)
            vm = ViewModelProviders.of(this@OcrActivity, OcrViewModelFactory(application, bitmap))[OcrViewModel::class.java]
            lifecycleOwner = this@OcrActivity
        }
    }
}
