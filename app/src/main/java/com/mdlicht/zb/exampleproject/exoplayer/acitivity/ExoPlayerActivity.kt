package com.mdlicht.zb.exampleproject.exoplayer.acitivity

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ActivityExoPlayerBinding
import com.mdlicht.zb.exampleproject.exoplayer.adapter.ExoListRvAdapter


/**
 * @Refence - https://black-jin0427.tistory.com/175
 *             https://medium.com/fungjai/playing-video-by-exoplayer-b97903be0b33
 */
class ExoPlayerActivity : AppCompatActivity() {
    lateinit var binding: ActivityExoPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exo_player)
        binding.apply {
            rvList.apply {
                layoutManager = LinearLayoutManager(this@ExoPlayerActivity)
                adapter = ExoListRvAdapter<String>(this@ExoPlayerActivity)
                addItemDecoration(DividerItemDecoration(this@ExoPlayerActivity, DividerItemDecoration.VERTICAL))
            }
        }
    }
}
