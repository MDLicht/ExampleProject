package com.mdlicht.zb.exampleproject.exoplayer.acitivity

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ActivityVideoListBinding
import com.mdlicht.zb.exampleproject.exoplayer.adapter.VideoRvAdapter
import com.mdlicht.zb.exampleproject.exoplayer.model.Video

class VideoListActivity : AppCompatActivity() {
    lateinit var binding: ActivityVideoListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_list)
        binding.apply {
            rvVideo.apply {
                layoutManager = LinearLayoutManager(this@VideoListActivity)
                adapter = VideoRvAdapter<Video>()
                addItemDecoration(DividerItemDecoration(this@VideoListActivity, DividerItemDecoration.VERTICAL))
            }
        }
    }
}
