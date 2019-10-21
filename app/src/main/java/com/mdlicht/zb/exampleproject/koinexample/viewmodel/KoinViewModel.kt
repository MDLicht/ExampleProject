package com.mdlicht.zb.exampleproject.koinexample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mdlicht.zb.exampleproject.koinexample.repository.KoinRepository
import com.mdlicht.zb.exampleproject.rxbasic.model.GitHubData

class KoinViewModel(val repository: KoinRepository): ViewModel() {
    val githubInfo: MutableLiveData<List<GitHubData>> = repository.getGitHubInfo()
}