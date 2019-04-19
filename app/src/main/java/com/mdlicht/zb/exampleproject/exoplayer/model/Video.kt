package com.mdlicht.zb.exampleproject.exoplayer.model

import com.google.gson.annotations.SerializedName
import com.mdlicht.zb.exampleproject.baserecyclerview.model.BaseModel

data class Video(
    @SerializedName("description") val description: String?,
    @SerializedName("sources") val sources: List<String?>?,
    @SerializedName("subtitle") val subtitle: String?,
    @SerializedName("thumb") val thumb: String?,
    @SerializedName("title") val title: String?
): BaseModel {
    override fun getViewType(): Int = -1
}