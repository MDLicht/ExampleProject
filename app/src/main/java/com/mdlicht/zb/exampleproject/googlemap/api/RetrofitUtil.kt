package com.mdlicht.zb.exampleproject.googlemap.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.mdlicht.zb.exampleproject.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtil {
    fun create(): PublicBicycleService {
        return Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).baseUrl(BuildConfig.PublicBicycleBaseUrl).build()
            .create(PublicBicycleService::class.java)
    }
}