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
    private var isFirst: Boolean = true

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

        if(isFirst) {
            binding.rvVideo.playVideo()
            isFirst = false
        }
    }

    override fun onResume() {
        binding.rvVideo.onRestartPlayer()
        super.onResume()
    }

    override fun onPause() {
        binding.rvVideo.onPausePlayer()
        super.onPause()
    }

    override fun onDestroy() {
        binding.rvVideo.onRelease()
        super.onDestroy()
    }
}
