package com.mdlicht.zb.exampleproject.koinexample.di

import android.databinding.DataBindingUtil
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.mdlicht.zb.exampleproject.databinding.ActivityKoinExampleBinding
import com.mdlicht.zb.exampleproject.koinexample.activity.KoinExampleActivity
import com.mdlicht.zb.exampleproject.koinexample.repository.KoinRepository
import com.mdlicht.zb.exampleproject.koinexample.viewmodel.KoinViewModel
import com.mdlicht.zb.exampleproject.rxbasic.api.RxBasicService
import com.mdlicht.zb.exampleproject.rxbasic.viewmodel.GitHubProfileViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(getProperty("github_api_url") as String)
            .build()
            .create(RxBasicService::class.java)
    }

    single {
        KoinRepository(get())
    }

    factory {
        (activity: KoinExampleActivity, layout: Int) -> DataBindingUtil.setContentView(activity, layout) as ActivityKoinExampleBinding
    }

    viewModel {
        KoinViewModel(get())
    }
}