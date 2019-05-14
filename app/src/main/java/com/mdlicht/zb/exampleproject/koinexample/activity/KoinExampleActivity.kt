package com.mdlicht.zb.exampleproject.koinexample.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ActivityKoinExampleBinding
import com.mdlicht.zb.exampleproject.koinexample.viewmodel.KoinViewModel
import com.mdlicht.zb.exampleproject.rxbasic.adapter.RxBasicRecyclerAdapter
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class KoinExampleActivity : AppCompatActivity() {
    val binding: ActivityKoinExampleBinding by inject { parametersOf(this, R.layout.activity_koin_example) }
    val vm: KoinViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
