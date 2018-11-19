package com.mdlicht.zb.exampleproject.rxbasic.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.rxbasic.api.RetrofitUtil
import com.mdlicht.zb.exampleproject.rxbasic.model.GitHubData
import com.mdlicht.zb.exampleproject.rxbasic.model.GitHubProfile
import com.mdlicht.zb.exampleproject.rxbasic.model.GitHubProfileAndRepos
import com.mdlicht.zb.exampleproject.rxbasic.model.GitHubRepo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class GitHubExampleViewModel : ViewModel(), TextView.OnEditorActionListener {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val dataSet: ObservableField<List<GitHubData>?> = ObservableField()
    val userNameInput: ObservableField<String> = ObservableField()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    /**
     * Call two API - Get Profile & Repos
     * And convert GitHubProfileAndRepos into List<GitHubData>
     * Finally Set data into RecyclerAdapter
     */
    fun onSearchClick(view: View, userName: String) {
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
                    dataSet.set(it)
                }, {
                    // if error, set null and show toast message
                    dataSet.set(null)
                    Toast.makeText(view.context, view.context.getString(R.string.rxbasic_msg_search_failed), Toast.LENGTH_SHORT).show()
                })
        )
    }

    /**
     * Listener on IME Action
     * This method catches only IME_ACTION_SEARCH
     */
    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if(actionId == EditorInfo.IME_ACTION_SEARCH) {
            onSearchClick(v!!, userNameInput.get()!!)
            return true
        }
        return false
    }
}