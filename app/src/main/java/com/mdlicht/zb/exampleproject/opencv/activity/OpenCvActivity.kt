package com.mdlicht.zb.exampleproject.opencv.activity

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.annotation.NonNull
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.WindowManager
import com.googlecode.tesseract.android.TessBaseAPI
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ActivityOpenCvBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.opencv.android.*
import org.opencv.core.Mat
import java.io.File
import java.io.FileOutputStream


class OpenCvActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {
    lateinit var binding: ActivityOpenCvBinding

    private var matInput: Mat? = null
    private var matResult: Mat? = null
    private var matResultRoi: Mat? = null

    private var cameraIndex: Int = 0

    private val compositeDisposable = CompositeDisposable()

    private val tesseract: TessBaseAPI = TessBaseAPI()

    init {
        System.loadLibrary("opencv_java3")
        System.loadLibrary("native-lib")
    }

    external fun processImage(matAddrInput: Long, matAddrResult: Long, matAddrResultRoi: Long)

    private val loaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                LoaderCallbackInterface.SUCCESS -> {
                    binding.cameraView.enableView()
                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

        binding = DataBindingUtil.setContentView(this, R.layout.activity_open_cv)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //퍼미션 상태 확인
            if (!hasPermissions(PERMISSIONS)) {

                //퍼미션 허가 안되어있다면 사용자에게 요청
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE)
            }
        }

        binding.apply {
            cameraView.apply {
                setCvCameraViewListener(this@OpenCvActivity)
                setCameraIndex(cameraIndex)
                loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
            }

            btnRotation.apply {
                setOnClickListener {
                    with(cameraView) {
                        disableView()
                        cameraIndex = if (cameraIndex == 0) 1 else 0
                        setCameraIndex(cameraIndex)
                        enableView()
                    }
                }
            }
        }

        initTesseract()
    }

    override fun onResume() {
        super.onResume()
        if (!OpenCVLoader.initDebug()) {
            Log.d(OpenCvActivity::class.java.name, "onResume :: Internal OpenCV library not found.")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, loaderCallback)
        } else {
            Log.d(OpenCvActivity::class.java.name, "onResum :: OpenCV library found inside package. Using it!")
            loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
        binding.cameraView.disableView()
    }

    override fun onCameraViewStarted(width: Int, height: Int) {

    }

    override fun onCameraViewStopped() {

    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat {
        matInput = inputFrame.rgba()

        if (matResult == null)
            matResult = Mat(matInput!!.rows(), matInput!!.cols(), matInput!!.type())

        if (matResultRoi == null)
            matResultRoi = Mat(matInput!!.rows(), matInput!!.cols(), matInput!!.type())

        processImage(matInput!!.nativeObjAddr, matResult!!.nativeObjAddr, matResultRoi!!.nativeObjAddr)

        return matResult!!
    }

    fun initTesseract() {
        // tesseract reads language from tesseract folder, create it if not exists.
        val f = File(Environment.getExternalStorageDirectory().absolutePath + "/tesseract/tessdata")
        if (!f.exists()) {
            f.mkdirs()
        }

        val tessLangCode = "kor"
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
        tesseract.init(Environment.getExternalStorageDirectory().absolutePath + "/tesseract", tessLangCode)
    }


    //여기서부턴 퍼미션 관련 메소드
    private val PERMISSIONS_REQUEST_CODE = 1000
    private var PERMISSIONS = arrayOf("android.permission.CAMERA")

    private fun hasPermissions(permissions: Array<String>): Boolean {
        var result: Int

        //스트링 배열에 있는 퍼미션들의 허가 상태 여부 확인
        for (perms in permissions) {

            result = ContextCompat.checkSelfPermission(this, perms)

            if (result == PackageManager.PERMISSION_DENIED) {
                //허가 안된 퍼미션 발견
                return false
            }
        }

        //모든 퍼미션이 허가되었음
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> if (grantResults.isNotEmpty()) {
                val cameraPermissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED

                if (!cameraPermissionAccepted)
                    showDialogForPermission(getString(R.string.msg_need_permission))
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun showDialogForPermission(msg: String) {
        val builder = AlertDialog.Builder(this@OpenCvActivity)
        builder.setTitle(getString(R.string.common_notice))
        builder.setMessage(msg)
        builder.setCancelable(false)
        builder.setPositiveButton(getString(R.string.common_yes)) { _, _ ->
            requestPermissions(
                PERMISSIONS,
                PERMISSIONS_REQUEST_CODE
            )
        }
        builder.setNegativeButton(getString(R.string.common_no)) { _, _ -> finish() }
        builder.create().show()
    }
}
