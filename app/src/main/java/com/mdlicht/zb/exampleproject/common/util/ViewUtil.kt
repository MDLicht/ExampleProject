package com.mdlicht.zb.exampleproject.common.util

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

object ViewUtil {
    fun dpToPx(context: Context, dp: Int): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()

    fun pxToDp(context: Context, px: Int): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px.toFloat(), context.resources.displayMetrics).toInt()

    fun dpToPx(dp: Int) = (dp * Resources.getSystem().displayMetrics.density).toInt()

    fun pxToDp(px: Int) = (px / Resources.getSystem().displayMetrics.density).toInt()
}