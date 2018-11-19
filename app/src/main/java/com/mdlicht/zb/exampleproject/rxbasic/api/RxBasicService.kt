package com.mdlicht.zb.exampleproject.rxbasic.api

import com.mdlicht.zb.exampleproject.rxbasic.model.GitHubProfile
import com.mdlicht.zb.exampleproject.rxbasic.model.GitHubRepo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RxBasicService {
    @GET("users/{username}")
    fun getUserProfile(@Path("username") username: String): Single<GitHubProfile>

    @GET("users/{username}/repos")
    fun getUserRepos(@Path("username") username: String): Single<List<GitHubRepo>>
}