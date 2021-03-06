package com.mdlicht.zb.exampleproject.rxbasic.viewmodel

import androidx.lifecycle.ViewModel
import android.content.Intent
import android.net.Uri
import android.view.View

class GitHubRepoViewModel : ViewModel() {
    /**
     * @param view : Clicked view
     * @param htmlUrl : User's repository url
     */
    fun onRepoClick(view: View, htmlUrl: String) {
        view.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(htmlUrl)))
    }
}