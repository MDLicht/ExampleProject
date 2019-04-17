package com.mdlicht.zb.exampleproject.common.util

import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View


object TextUtil {
    interface OnTextClickListener {
        fun onTextClick(text: String)
    }

    /**
     * 특정 텍스트와 텍스트 사이에 있는 부분을 클릭 가능한 부분으로 만드는 기능 수행
     * @param targetString 클릭 가능하도록 할 텍스트가 포함된 전체 문자열
     * @param startText 클릭 가능하도록 할 텍스트의 시작 문자열
     * @param endText 클릭 가능하도록 할 텍스트의 끝 문자열
     * @param listener 클릭 시 이벤트를 전달받을 리스너
     * @return 클릭 가능한 영역이 지정된 SpannableStringBuilder
     */
    fun addClickablePart(
        targetString: String,
        startText: String,
        endText: String,
        listener: OnTextClickListener
    ): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(targetString)

        var idx1 = targetString.indexOf(startText)
        var idx2 = 0
        while (idx1 != -1) {
            idx2 = targetString.indexOf(endText, idx1) + 1

            val clickString = targetString.substring(idx1, idx2)
            ssb.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    listener.onTextClick(clickString)
                }
            }, idx1, idx2, 0)
            idx1 = targetString.indexOf(startText, idx2)
        }

        return ssb
    }

    /**
     * 특정 텍스트와 텍스트 사이에 있는 부분을 클릭 가능한 부분으로 만드는 기능 수행
     * @param targetString 클릭 가능하도록 할 텍스트가 포함된 전체 문자열
     * @param startText 클릭 가능하도록 할 텍스트의 시작 문자열
     * @param endText 클릭 가능하도록 할 텍스트의 끝 문자열
     * @param hasAccent 해당 텍스트 영역을 강조할 것인지에 대한 상태값
     * @param color hasAccent 값이 true 일 경우 변경할 텍스트 컬러
     * @param listener 클릭 시 이벤트를 전달받을 리스너
     * @return 클릭 가능한 영역이 지정된 SpannableStringBuilder
     */
    fun addClickablePart(
        targetString: String,
        startText: String,
        endText: String,
        hasAccent: Boolean,
        color: Int,
        listener: TextUtil.OnTextClickListener
    ): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(targetString)

        var idx1 = targetString.indexOf(startText)
        var idx2 = 0
        while (idx1 != -1) {
            idx2 = targetString.indexOf(endText, idx1) + 1

            val clickString = targetString.substring(idx1, idx2)
            ssb.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    listener.onTextClick(clickString)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    if (hasAccent)
                        ds.color = color
                }
            }, idx1, idx2, 0)
            idx1 = targetString.indexOf(startText, idx2)
        }

        return ssb
    }
}