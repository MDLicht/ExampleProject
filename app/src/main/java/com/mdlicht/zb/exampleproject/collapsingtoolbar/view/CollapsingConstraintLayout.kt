package com.mdlicht.zb.exampleproject.collapsingtoolbar.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.AppBarLayout
import android.util.AttributeSet
import com.mdlicht.zb.exampleproject.R

/**
 * Reference (DroidCon SF 2017) : https://www.youtube.com/watch?v=8lAXJ5NFXTM
 */
class CollapsingConstraintLayout: ConstraintLayout, AppBarLayout.OnOffsetChangedListener {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val openConstraintSet: ConstraintSet by lazy { ConstraintSet().apply { load(context, R.layout.header_collapsing_toolbar) } }
    val closeConstraintSet: ConstraintSet by lazy { ConstraintSet().apply { load(context, R.layout.header_collapsing_toolbar2) } }

    private var transitionThreshhold = 0.35f
    private var lastPosition = 0
    private var isToolbarOpen: Boolean = true

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if(parent is AppBarLayout) {
            val appBarLayout = parent as AppBarLayout
            appBarLayout.addOnOffsetChangedListener(this)
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if(lastPosition == verticalOffset)
            return

        lastPosition = verticalOffset
        val progress = Math.abs(verticalOffset / appBarLayout?.height?.toFloat()!!)

        val params = layoutParams as AppBarLayout.LayoutParams
        params.topMargin = -verticalOffset
        layoutParams = params

        if(isToolbarOpen && progress > transitionThreshhold) {
            closeConstraintSet.applyTo(this)
            isToolbarOpen = false
        } else if(!isToolbarOpen && progress < transitionThreshhold) {
            openConstraintSet.applyTo(this)
            isToolbarOpen = true
        }
    }
}