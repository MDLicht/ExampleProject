package com.mdlicht.zb.exampleproject.xmlparsing.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitXmlUtil {
    fun create(): XmlParsingService =
        Retrofit.Builder().baseUrl("https://www.w3schools.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(
                SimpleXmlConverterFactory.create()
            ).build().create(XmlParsingService::class.java)
}