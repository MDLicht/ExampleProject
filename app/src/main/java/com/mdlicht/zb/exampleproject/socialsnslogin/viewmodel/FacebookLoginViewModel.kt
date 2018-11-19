package com.mdlicht.zb.exampleproject.socialsnslogin.viewmodel

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.util.Log
import android.view.View
import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth

class FacebookLoginViewModel : ViewModel() {
    val isLogin: ObservableBoolean = ObservableBoolean(false)

    companion object {
        const val LOGIN_TYPE = 1
        const val LOGIN_NAME = "Facebook"
    }

    fun onLogInClick(view: View, activity: Activity) {

    }

    fun firebaseAuthWithFacebook(activity: Activity, token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(activity) {
                if(it.isSuccessful) {
                    Log.e("FacebookAuth", "Success")
                    isLogin.set(true)
                } else {
                    Log.e("FacebookAuth", "Failed")
                }
            }
    }
}