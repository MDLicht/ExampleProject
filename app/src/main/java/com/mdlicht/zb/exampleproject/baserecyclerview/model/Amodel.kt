package com.mdlicht.zb.exampleproject.baserecyclerview.model

import com.mdlicht.zb.exampleproject.baserecyclerview.etc.Constants

data class Amodel(val title: String): BaseModel {
    override fun getViewType(): Int = Constants.VIEW_TYPE_A
}