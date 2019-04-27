package com.mdlicht.zb.exampleproject.mvppractice.constract

import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubData


interface MvpPracticeConstract {
    interface View {
        fun initView()
        fun updateData(data: List<GitHubData>?)
        fun showData()
        fun showEmpty()
    }

    interface Presenter {
        fun searchRepository(userName: String)
    }
}