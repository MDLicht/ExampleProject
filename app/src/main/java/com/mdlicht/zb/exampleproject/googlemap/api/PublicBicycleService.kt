package com.mdlicht.zb.exampleproject.googlemap.api

import com.mdlicht.zb.exampleproject.BuildConfig
import com.mdlicht.zb.exampleproject.googlemap.model.Response
import io.reactivex.Observable
import retrofit2.http.GET

interface PublicBicycleService {
    @GET(BuildConfig.PublicBicycleKey+"/json/SebcBicycleRetalKor/1/1000")
    fun getAllStationList(): Observable<Response>
}