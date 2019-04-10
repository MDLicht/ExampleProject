package com.mdlicht.zb.exampleproject.main.model

import com.mdlicht.zb.exampleproject.baserecyclerview.model.BaseModel

data class Title(val title: String): BaseModel {
    override fun getViewType(): Int = -1
}