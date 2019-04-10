package com.mdlicht.zb.exampleproject.baserecyclerview.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.baserecyclerview.adapter.RecyclerViewAdapter
import com.mdlicht.zb.exampleproject.baserecyclerview.model.BaseModel
import com.mdlicht.zb.exampleproject.databinding.ActivityBaseRecyclerViewBinding

class BaseRecyclerViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityBaseRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_base_recycler_view)
        binding.apply {
            recyclerview.apply {
                layoutManager = LinearLayoutManager(this@BaseRecyclerViewActivity)
                adapter = RecyclerViewAdapter<BaseModel>()
            }
        }
    }
}
