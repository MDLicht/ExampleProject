package com.mdlicht.zb.exampleproject.koinexample.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.mdlicht.zb.exampleproject.koinexample.repository.KoinRepository
import com.mdlicht.zb.exampleproject.rxbasic.model.GitHubData

class KoinViewModel(val repository: KoinRepository): ViewModel() {
    val githubInfo: MutableLiveData<List<GitHubData>> = repository.getGitHubInfo()
}