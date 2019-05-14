package com.mdlicht.zb.exampleproject.koinexample.repository

import android.arch.lifecycle.MutableLiveData
import com.mdlicht.zb.exampleproject.rxbasic.api.RxBasicService
import com.mdlicht.zb.exampleproject.rxbasic.model.GitHubData
import com.mdlicht.zb.exampleproject.rxbasic.model.GitHubProfile
import com.mdlicht.zb.exampleproject.rxbasic.model.GitHubProfileAndRepos
import com.mdlicht.zb.exampleproject.rxbasic.model.GitHubRepo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class KoinRepository(private val service: RxBasicService) {
    fun getGitHubInfo(): MutableLiveData<List<GitHubData>> {
        val github: MutableLiveData<List<GitHubData>> = MutableLiveData()
        val userName = "mdlicht"
        Single.zip(
            // Zip two Observable - Get Profile & Get Repos
            service.getUserProfile(userName),
            service.getUserRepos(userName),
            BiFunction<GitHubProfile, List<GitHubRepo>, GitHubProfileAndRepos> { t1, t2 ->
                GitHubProfileAndRepos(t1, t2)
            })
            .flatMap {
                // Convert original observable into List<GitHubData> Observable
                Single.just(mutableListOf(it.profile, *(it.sortReposByStar().repos.toTypedArray())))
            }
            // Set schedulers
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // If success, set dataset
                github.value = it
            }, {
                // if error, set null and show toast message
                github.value = null
            })
        return github
    }
}