package com.mdlicht.zb.exampleproject.mvppractice.activity

import android.support.v7.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {
    abstract fun initPresenter()
}