package com.mdlicht.zb.exampleproject.googlemap.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class Row(
    val ADD_KOR: String?,
    val ADD_KOR_ROAD: String?,
    val H_KOR_CITY: String?,
    val H_KOR_DONG: String?,
    val H_KOR_GU: String?,
    val LATITUDE: String?,
    val LONGITUDE: String?,
    val MAIN_KEY: String?,
    val MNG_NO: String?,
    val NAME_KOR: String?,
    val NOTE: String?
) : ClusterItem {
    override fun getSnippet(): String {
        return NAME_KOR ?: ""
    }

    override fun getTitle(): String {
        return NAME_KOR ?: ""
    }

    override fun getPosition(): LatLng {
        return LatLng(LATITUDE?.toDouble()!!, LONGITUDE?.toDouble()!!)
    }
}