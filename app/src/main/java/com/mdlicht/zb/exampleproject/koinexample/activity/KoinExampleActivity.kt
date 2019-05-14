package com.mdlicht.zb.exampleproject.koinexample.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ActivityKoinExampleBinding
import com.mdlicht.zb.exampleproject.koinexample.viewmodel.KoinViewModel
import com.mdlicht.zb.exampleproject.rxbasic.adapter.RxBasicRecyclerAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class KoinExampleActivity : AppCompatActivity() {
    lateinit var binding: ActivityKoinExampleBinding
    val vm: KoinViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_koin_example)
        binding.apply {
            vm = this@KoinExampleActivity.vm

            rvKoin.apply {
                layoutManager = LinearLayoutManager(this@KoinExampleActivity)
                adapter = RxBasicRecyclerAdapter(this@KoinExampleActivity)
            }

            lifecycleOwner = this@KoinExampleActivity
        }
    }
}
