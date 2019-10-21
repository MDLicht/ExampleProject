package com.mdlicht.zb.exampleproject.common

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.mdlicht.zb.exampleproject.R


open class FullScreenDialog: DialogFragment() {
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreenDialogTheme) // Full Screen Dialog
    }
}