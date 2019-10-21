package com.mdlicht.zb.exampleproject.mvppractice.activity

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {
    abstract fun initPresenter()
}