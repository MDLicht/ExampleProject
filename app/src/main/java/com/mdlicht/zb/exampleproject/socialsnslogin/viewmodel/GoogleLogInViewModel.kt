package com.mdlicht.zb.exampleproject.socialsnslogin.viewmodel

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.mdlicht.zb.exampleproject.R


class GoogleLogInViewModel : ViewModel() {
    val isLogin: ObservableBoolean = ObservableBoolean(false)

    companion object {
        const val LOGIN_CODE = 0
        const val LOGIN_NAME = "Google"
    }

    fun onLogInClick(view: View, activity: Activity) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(view.context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(activity, gso)
        activity.startActivityForResult(client.signInIntent, 0)
    }

    fun firebaseAuthWithGoogle(activity: Activity, acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(activity
            ) { task ->
                if (task.isSuccessful) {
                    Log.e("GoogleAuth", "Success")
                    isLogin.set(true)
                } else {
                    Log.e("GoogleAuth", "Failed")
                }
            }
    }
}