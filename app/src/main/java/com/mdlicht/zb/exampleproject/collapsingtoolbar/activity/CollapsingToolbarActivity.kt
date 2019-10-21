package com.mdlicht.zb.exampleproject.collapsingtoolbar.activity

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
