package com.mdlicht.zb.exampleproject.simpletranslator.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ActivitySimpleTranslatorBinding
import com.mdlicht.zb.exampleproject.simpletranslator.viewmodel.TranslatorViewModel

class SimpleTranslatorActivity : AppCompatActivity() {
    lateinit var binding: ActivitySimpleTranslatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_simple_translator)
        binding.vm = ViewModelProviders.of(this).get(TranslatorViewModel::class.java)
    }
}
