package com.mdlicht.zb.exampleproject.mvppractice.constract

import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubData

interface AdapterContract {
    interface View {
        fun notifyGitHubDataSetChanged()
    }
    interface Model {
        fun setDataSet(dataSet: List<GitHubData>?)
    }
}