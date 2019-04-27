package com.mdlicht.zb.exampleproject.mvppractice.api

import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubProfile
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubRepo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MvpPracticeService {
    @GET("users/{username}")
    fun getUserProfile(@Path("username") username: String): Single<GitHubProfile>

    @GET("users/{username}/repos")
    fun getUserRepos(@Path("username") username: String): Single<List<GitHubRepo>>
}