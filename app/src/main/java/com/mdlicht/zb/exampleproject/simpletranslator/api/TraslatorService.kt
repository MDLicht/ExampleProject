package com.mdlicht.zb.exampleproject.simpletranslator.api

import com.mdlicht.zb.exampleproject.simpletranslator.model.Require
import com.mdlicht.zb.exampleproject.simpletranslator.model.Response
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface TraslatorService {
//    @Headers(*["X-Naver-Client-Id: " + BuildConfig.PapagoTranslatorClientId, "X-Naver-Client-Secret: " + BuildConfig.PapagoTranslatorClientSecret])
    @POST("translate")
    fun translate(@Body require: Require): Observable<Response>
}