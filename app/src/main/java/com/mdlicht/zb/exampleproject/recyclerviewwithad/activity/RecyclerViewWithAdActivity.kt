package com.mdlicht.zb.exampleproject.recyclerviewwithad.activity

import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ActivityRecyclerViewWithAdBinding
import com.mdlicht.zb.exampleproject.recyclerviewwithad.adapter.WithAdRecyclerAdapter

class RecyclerViewWithAdActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecyclerViewWithAdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view_with_ad)
        binding.rvWithAd.layoutManager = LinearLayoutManager(this)
        binding.rvWithAd.addItemDecoration(DividerItemDecoration(this, (DividerItemDecoration.VERTICAL)))
        binding.rvWithAd.adapter = WithAdRecyclerAdapter()
    }
}
