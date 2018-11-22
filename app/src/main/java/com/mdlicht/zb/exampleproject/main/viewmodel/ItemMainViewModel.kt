package com.mdlicht.zb.exampleproject.main.viewmodel

import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.view.View
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.activity.DatabindingExampleActivity
import com.mdlicht.zb.exampleproject.rxbasic.activity.RxBasicActivity
import com.mdlicht.zb.exampleproject.simpletranslator.activity.SimpleTranslatorActivity
import com.mdlicht.zb.exampleproject.socialsnslogin.activity.SocialLoginActivity

class ItemMainViewModel : ViewModel() {
    fun onItemClick(view: View, title: String) {
        val context = view.context
        when(title) {
            context.getString(R.string.item_title_social_login) -> {
                context.startActivity(Intent(context, SocialLoginActivity::class.java))
            }
            context.getString(R.string.item_title_rx_basic) -> {
                context.startActivity(Intent(context, RxBasicActivity::class.java))
            }
            context.getString(R.string.item_title_databinding_example) -> {
                context.startActivity(Intent(context, DatabindingExampleActivity::class.java))
            }
            context.getString(R.string.item_title_simple_translator) -> {
                context.startActivity(Intent(context, SimpleTranslatorActivity::class.java))
            }
        }
    }
}