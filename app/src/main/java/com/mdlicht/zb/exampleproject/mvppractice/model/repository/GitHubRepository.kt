package com.mdlicht.zb.exampleproject.mvppractice.model.repository

import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubData

interface GitHubRepository {
    fun searchRepository(userName: String, callback: GitHubCallback)
}

interface GitHubCallback {
    fun onLoadComplete(item: List<GitHubData>)
    fun onLoadFail(error: Throwable)
}