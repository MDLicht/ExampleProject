package com.mdlicht.zb.exampleproject.rxbasic.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ActivityRxBasicBinding
import com.mdlicht.zb.exampleproject.rxbasic.adapter.RxBasicRecyclerAdapter
import com.mdlicht.zb.exampleproject.rxbasic.viewmodel.GitHubExampleViewModel

class RxBasicActivity : AppCompatActivity() {
    lateinit var binding: ActivityRxBasicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rx_basic)
        binding.vm = GitHubExampleViewModel()
        binding.rvRxBasic.layoutManager = LinearLayoutManager(this)
        binding.rvRxBasic.adapter = RxBasicRecyclerAdapter(this)
        binding.rvRxBasic.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
