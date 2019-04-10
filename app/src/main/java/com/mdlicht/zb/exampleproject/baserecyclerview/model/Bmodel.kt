package com.mdlicht.zb.exampleproject.baserecyclerview.model

import com.mdlicht.zb.exampleproject.baserecyclerview.etc.Constants

data class Bmodel(val contents: String): BaseModel {
    override fun getViewType(): Int = Constants.VIEW_TYPE_B
}