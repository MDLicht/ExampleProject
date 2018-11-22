package com.mdlicht.zb.exampleproject.simpletranslator.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.mdlicht.zb.exampleproject.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtil {
    fun create(): TraslatorService {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val request: Request = chain.request().newBuilder()
                    //                            .header("Content-Type", "application/json; charset=UTF-8")
                    //                            .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .header("X-Naver-Client-Id", BuildConfig.PapagoTranslatorClientId)
                    .header("X-Naver-Client-Secret", BuildConfig.PapagoTranslatorClientSecret)
                    .build()
                chain.proceed(request)
            }
        }


        return Retrofit.Builder()
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.PapagoTranslatorBaseUrl)
            .build()
            .create(TraslatorService::class.java)
    }
}