package com.mdlicht.zb.exampleproject.mvppractice.presenter

import com.mdlicht.zb.exampleproject.mvppractice.api.RetrofitUtil
import com.mdlicht.zb.exampleproject.mvppractice.constract.AdapterContract
import com.mdlicht.zb.exampleproject.mvppractice.constract.MvpPracticeConstract
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubProfile
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubProfileAndRepos
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubRepo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class MvpPracticePresenter(
    _view: MvpPracticeConstract.View,
    _adapterView: AdapterContract.View,
    _adapterModel: AdapterContract.Model
) : BasePresenter(), MvpPracticeConstract.Presenter {
    private var view: MvpPracticeConstract.View = _view
    private var adapterView: AdapterContract.View = _adapterView
    private var adapterModel: AdapterContract.Model = _adapterModel
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
                    adapterModel.setDataSet(it)
//                    view.updateData(it)
                    adapterView.notifyGitHubDataSetChanged()

                    if (it.isNullOrEmpty())
                        view.showEmpty()
                    else
                        view.showData()
                }, {
                    // if error, set null and show toast message
                    adapterModel.setDataSet(null)
//                    view.updateData(null)
                    adapterView.notifyGitHubDataSetChanged()

                    view.showEmpty()
                })
        )
    }

    override fun onClear() {
        compositeDisposable.clear()
    }
}