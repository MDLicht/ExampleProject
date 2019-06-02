package com.mdlicht.zb.exampleproject.mvppractice.presenter

import com.mdlicht.zb.exampleproject.mvppractice.constract.AdapterContract
import com.mdlicht.zb.exampleproject.mvppractice.constract.MvpPracticeConstract
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubData
import com.mdlicht.zb.exampleproject.mvppractice.model.repository.GitHubCallback
import com.mdlicht.zb.exampleproject.mvppractice.model.repository.GitHubRepository

class MvpPracticePresenter(
    _view: MvpPracticeConstract.View,
    _adapterView: AdapterContract.View,
    _adapterModel: AdapterContract.Model,
    private val repository: GitHubRepository
) : BasePresenter(), MvpPracticeConstract.Presenter {
    private var view: MvpPracticeConstract.View = _view
    private var adapterView: AdapterContract.View = _adapterView
    private var adapterModel: AdapterContract.Model = _adapterModel

    init {
        view.initView()
    }

    override fun searchRepository(userName: String) {
        repository.searchRepository(userName, object : GitHubCallback {
            override fun onLoadComplete(item: List<GitHubData>) {
                // If success, set dataset
                adapterModel.setDataSet(item)

                adapterView.notifyGitHubDataSetChanged()

                if (item.isNullOrEmpty())
                    view.showEmpty()
                else
                    view.showData()
            }

            override fun onLoadFail(error: Throwable) {
                adapterModel.setDataSet(null)

                adapterView.notifyGitHubDataSetChanged()

                view.showEmpty()
            }
        })
    }

    override fun onClear() {

    }
}