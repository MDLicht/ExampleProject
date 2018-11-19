package com.mdlicht.zb.exampleproject.databinding.activity

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ActivityDatabindingExampleBinding
import com.mdlicht.zb.exampleproject.databinding.adapter.DatabindingExampleSpinnerAdapter
import com.mdlicht.zb.exampleproject.databinding.viewmodel.DatabindingExampleViewModel

class DatabindingExampleActivity : AppCompatActivity() {
    lateinit var binding: ActivityDatabindingExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding_example)
        binding.vm = DatabindingExampleViewModel()
        binding.spinner.adapter = DatabindingExampleSpinnerAdapter(this)
    }
}
