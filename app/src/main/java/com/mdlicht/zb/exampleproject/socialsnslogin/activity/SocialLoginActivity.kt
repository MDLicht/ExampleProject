package com.mdlicht.zb.exampleproject.socialsnslogin.activity

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ActivitySocialLoginBinding
import com.mdlicht.zb.exampleproject.socialsnslogin.viewmodel.FacebookLoginViewModel
import com.mdlicht.zb.exampleproject.socialsnslogin.viewmodel.GoogleLogInViewModel


class SocialLoginActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivitySocialLoginBinding
    val facebookCallback: CallbackManager by lazy { CallbackManager.Factory.create() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_social_login)
        binding.googleVm = ViewModelProviders.of(this).get(GoogleLogInViewModel::class.java)
        binding.facebookVm = ViewModelProviders.of(this).get(FacebookLoginViewModel::class.java)

        binding.btnGoogleSignIn.setSize(SignInButton.SIZE_STANDARD)
        binding.btnGoogleSignIn.setOnClickListener(this)

        binding.btnFacebookSignIn.setReadPermissions("email")
        binding.btnFacebookSignIn.setOnClickListener(this)
        binding.btnFacebookSignIn.registerCallback(facebookCallback, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                binding.facebookVm!!.firebaseAuthWithFacebook(this@SocialLoginActivity, result?.accessToken!!)
            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {

            }
        })

//        startActivityWithAuthUI()
    }

    /**
     * Logout google and facebook
     */
    override fun onStart() {
        super.onStart()
        if(isLogIn()) {
            FirebaseAuth.getInstance().signOut()
            binding.googleVm!!.isLogin.set(false)
            binding.facebookVm!!.isLogin.set(false)
            Toast.makeText(this, "Logout Completed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnGoogleSignIn -> binding.googleVm!!.onLogInClick(v, this)
            R.id.btnFacebookSignIn -> binding.facebookVm!!.onLogInClick(v, this)
        }
    }

    /**
     * Social Login with AuthUI
     */
    fun startActivityWithAuthUI() {
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
                listOf(
                    AuthUI.IdpConfig.GoogleBuilder().build(),
                    AuthUI.IdpConfig.FacebookBuilder().build()
                )
            ).build(), 0
        )
    }

    private fun isLogIn() : Boolean{
        if(FirebaseAuth.getInstance().currentUser != null)
            return true
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GoogleLogInViewModel.LOGIN_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let {
                    Toast.makeText(this, "GoogleLogin", Toast.LENGTH_SHORT).show()
                    binding.googleVm!!.firebaseAuthWithGoogle(this, it)
                }
            } catch (e: ApiException) {
                Log.e("Social", "Google Login Failed")
            }
        } else  {
            facebookCallback.onActivityResult(requestCode, resultCode, data)
        }
    }
}
