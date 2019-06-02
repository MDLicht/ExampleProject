package com.mdlicht.zb.exampleproject.mvppractice.constract


interface MvpPracticeConstract {
    interface View {
        fun initView()
        fun getKeyword(): String
        fun showData()
        fun showEmpty()
    }

    interface Presenter {
        fun searchRepository(userName: String)
    }
}