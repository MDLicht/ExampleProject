package com.mdlicht.zb.exampleproject.mvppractice.model.repository

import com.mdlicht.zb.exampleproject.mvppractice.api.RetrofitUtil
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubProfile
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubProfileAndRepos
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubRepo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class GitHubRepositoryImpl: GitHubRepository {
    override fun searchRepository(userName: String, callback: GitHubCallback) {
        Single.zip(
            // Zip two Observable - Get Profile & Get Repos
            RetrofitUtil.create().getUserProfile(userName),
            RetrofitUtil.create().getUserRepos(userName),
            BiFunction<GitHubProfile, List<GitHubRepo>, GitHubProfileAndRepos> { t1, t2 ->
                GitHubProfileAndRepos(t1, t2)
            })
            .map {
                // Convert original observable into List<GitHubData> Observable
                mutableListOf(it.profile, *(it.sortReposByStar().repos.toTypedArray()))
            }
            // Set schedulers
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onLoadComplete(it)
            }, {
                // if error, set null and show toast message
                callback.onLoadFail(it)
            })
    }
}