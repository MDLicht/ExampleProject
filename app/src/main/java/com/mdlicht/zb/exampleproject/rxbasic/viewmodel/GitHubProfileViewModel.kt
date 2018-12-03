package com.mdlicht.zb.exampleproject.rxbasic.viewmodel

import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.databinding.ObservableField
import android.net.Uri
import android.view.View
import com.mdlicht.zb.exampleproject.rxbasic.model.GitHubProfile

class GitHubProfileViewModel : ViewModel() {
    val profile: ObservableField<GitHubProfile> = ObservableField()

    /**
     * @Param view : Clicked view
     * @Param htmlUrl : GitHub user's profile url
     */
    fun onLinkClick(view: View, htmlUrl: String) {
        view.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(htmlUrl)))
    }
}