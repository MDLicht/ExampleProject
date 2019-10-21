package com.mdlicht.zb.exampleproject.exoplayer.viewholder

import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.baserecyclerview.viewholder.BaseViewHolder
import com.mdlicht.zb.exampleproject.databinding.ItemExoVideoBinding
import com.mdlicht.zb.exampleproject.exoplayer.model.Video

class VideoViewHolder private constructor(itemView: View): BaseViewHolder<Video>(itemView) {
    var binding: ItemExoVideoBinding = DataBindingUtil.bind(itemView)!!

    companion object {
        fun newInstance(parent: ViewGroup): VideoViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exo_video, parent, false)
            return VideoViewHolder(view)
        }
    }

    override fun onBindView(position: Int, item: Video) {
        itemView.tag = this
        binding.video = item
    }
}