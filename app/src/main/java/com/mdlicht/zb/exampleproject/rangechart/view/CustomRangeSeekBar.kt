package com.mdlicht.zb.exampleproject.rangechart.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar

class CustomRangeSeekBar : CrystalRangeSeekbar {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var mOnCustomRangeSeekbarChangeListener: OnCustomSeekBarListener? = null

    public interface OnCustomSeekBarListener: OnRangeSeekbarChangeListener {
        fun onThumbChanged(minThumb: RectF, maxThumb: RectF)
    }

    fun setOnCustomRangeSeekbarChangeListener(listener: OnCustomSeekBarListener) {
        this.mOnCustomRangeSeekbarChangeListener = listener
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mOnCustomRangeSeekbarChangeListener?.onThumbChanged(leftThumbRect, rightThumbRect)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)

        mOnCustomRangeSeekbarChangeListener?.valueChanged(selectedMinValue, selectedMaxValue)

        return true
    }

    override fun apply() {
        super.apply()
        mOnCustomRangeSeekbarChangeListener?.valueChanged(selectedMinValue, selectedMaxValue)
    }

    public fun getLeftThumbRectF(): RectF {
        return leftThumbRect
    }

    public fun getRightThumbRectF(): RectF {
        return rightThumbRect
    }

    public fun getThumbWidthValue(): Float {
        return thumbWidth
    }
}