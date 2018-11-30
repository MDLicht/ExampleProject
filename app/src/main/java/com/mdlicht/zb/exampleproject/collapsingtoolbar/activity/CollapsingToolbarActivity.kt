package com.mdlicht.zb.exampleproject.collapsingtoolbar.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.collapsingtoolbar.adapter.CollapsingToolbarRecycleAdapter
import com.mdlicht.zb.exampleproject.databinding.ActivityCollapsingToolbarBinding

class CollapsingToolbarActivity : AppCompatActivity() {
    lateinit var binding: ActivityCollapsingToolbarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collapsing_toolbar)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = CollapsingToolbarRecycleAdapter()
    }
}
