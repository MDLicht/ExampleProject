package com.mdlicht.zb.exampleproject.mvppractice.presenter

import com.mdlicht.zb.exampleproject.mvppractice.api.RetrofitUtil
import com.mdlicht.zb.exampleproject.mvppractice.constract.MvpPracticeConstract
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubProfile
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubProfileAndRepos
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubRepo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class MvpPracticePresenter(_view: MvpPracticeConstract.View): BasePresenter(), MvpPracticeConstract.Presenter {
    private var view: MvpPracticeConstract.View = _view
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        view.initView()
    }

    override fun searchRepository(userName: String) {
        compositeDisposable.add(
            Single.zip(
                // Zip two Observable - Get Profile & Get Repos
                RetrofitUtil.create().getUserProfile(userName),
                RetrofitUtil.create().getUserRepos(userName),
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
                    view.updateData(it)
                }, {
                    // if error, set null and show toast message
                    view.updateData(null)
                })
        )
    }

    override fun onClear() {
        compositeDisposable.clear()
    }
}