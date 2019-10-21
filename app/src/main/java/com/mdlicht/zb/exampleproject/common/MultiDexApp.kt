package com.mdlicht.zb.exampleproject.common

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.mdlicht.zb.exampleproject.koinexample.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MultiDexApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MultiDexApp)
            fileProperties()
            modules(listOf(appModule))
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}