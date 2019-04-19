package com.mdlicht.zb.exampleproject.exoplayer.model

import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("videos") val videos: List<Video>
)