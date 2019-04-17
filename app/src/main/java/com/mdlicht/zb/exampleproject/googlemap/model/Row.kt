package com.mdlicht.zb.exampleproject.googlemap.model

import android.os.Parcel
import android.os.Parcelable
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
) : ClusterItem, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun getSnippet(): String {
        return NAME_KOR ?: ""
    }

    override fun getTitle(): String {
        return NAME_KOR ?: ""
    }

    override fun getPosition(): LatLng {
        return LatLng(LATITUDE?.toDouble()!!, LONGITUDE?.toDouble()!!)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ADD_KOR)
        parcel.writeString(ADD_KOR_ROAD)
        parcel.writeString(H_KOR_CITY)
        parcel.writeString(H_KOR_DONG)
        parcel.writeString(H_KOR_GU)
        parcel.writeString(LATITUDE)
        parcel.writeString(LONGITUDE)
        parcel.writeString(MAIN_KEY)
        parcel.writeString(MNG_NO)
        parcel.writeString(NAME_KOR)
        parcel.writeString(NOTE)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Row> {
        override fun createFromParcel(parcel: Parcel): Row {
            return Row(parcel)
        }

        override fun newArray(size: Int): Array<Row?> {
            return arrayOfNulls(size)
        }
    }
}