package com.mdlicht.zb.exampleproject.common.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File


object BitmapUtil {
    /**
     * 디바이스에 따라 촬영된 이미지가 회전되어 있어 정방향으로 설정하기 위한 메소드
     * @param imagePath
     * @param rotatedBitmap
     * @return
     */
    fun getUnRotatedImage(imagePath: String, rotatedBitmap: Bitmap): Bitmap? {
        var rotate = 0
        try {
            val imageFile = File(imagePath)
            val exif = ExifInterface(imageFile.absolutePath)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270
                ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180
                ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        val matrix = Matrix()
        matrix.postRotate(rotate.toFloat())
        return Bitmap.createBitmap(
            rotatedBitmap, 0, 0, rotatedBitmap.width, rotatedBitmap.height, matrix,
            true
        )
    }

    /**
     * 주어진 너비 및 높이에 맞춰 샘플링 크기를 계산하는 메소드
     * @param options 샘플링을 하려는 비트맵의 정보를 담고 있는 Option
     * @param reqWidth 샘플링을 하려는 너비
     * @param reqHeight 샘플링을 하려는 높이
     * @return 계산된 샘플링 크기
     */
    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    /**
     * 주어진 이미지 경로에 해당하는 이미지를 Base64 타입으로 변환
     * @param imgPath Base64로 변환하려는 이미지 경로
     * @param reqWidth 샘플링을 하려는 너비
     * @param reqHeight 샘플링을 하려는 높이
     * @param format 생성된 이미지 포맷
     * @param quality 생성된 이미지 품질
     * @return Base64로 변환된 이미지
     */
    fun getBase64StringImg(
        imgPath: String,
        reqWidth: Int,
        reqHeight: Int,
        format: Bitmap.CompressFormat,
        quality: Int
    ): String {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imgPath, options)

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

        options.inJustDecodeBounds = false

        val file = File(imgPath)
        val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)

        val stream = ByteArrayOutputStream()
        bitmap.compress(format, quality, stream)
        val byteArr = stream.toByteArray()
        return Base64.encodeToString(byteArr, Base64.DEFAULT)
    }

    /**
     * 이미지 경로에 해당하는 이미지의 크기를 계산하는 메소드
     * @param imgPath 이미지 경로
     * @param reqWidth 샘플링을 하려는 너비
     * @param reqHeight 샘플링을 하려는 높이
     * @param format 생성된 이미지 포맷
     * @param quality 생성된 이미지 품질
     * @return 생성된 이미지 용량
     */
    fun getImageSize(
        imgPath: String,
        reqWidth: Int,
        reqHeight: Int,
        format: Bitmap.CompressFormat,
        quality: Int
    ): Long {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imgPath, options)

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

        options.inJustDecodeBounds = false

        val file = File(imgPath)
        val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
        val stream = ByteArrayOutputStream()
        bitmap.compress(format, quality, stream)
        val byteArr = stream.toByteArray()

        return byteArr.size.toLong()
    }
}