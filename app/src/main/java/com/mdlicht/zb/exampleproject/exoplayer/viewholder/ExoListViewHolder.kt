package com.mdlicht.zb.exampleproject.exoplayer.viewholder

import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.baserecyclerview.viewholder.BaseViewHolder
import com.mdlicht.zb.exampleproject.databinding.ItemExoListBinding
import com.mdlicht.zb.exampleproject.exoplayer.acitivity.SingleVideoActivity
import com.mdlicht.zb.exampleproject.exoplayer.acitivity.VideoListActivity
import com.mdlicht.zb.exampleproject.main.model.Title

class ExoListViewHolder private constructor(itemView: View): BaseViewHolder<Title>(itemView) {
    var binding: ItemExoListBinding = DataBindingUtil.bind(itemView)!!

    companion object {
        fun newInstance(parent: ViewGroup): ExoListViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exo_list, parent, false)
            return ExoListViewHolder(view)
        }
    }

    override fun onBindView(position: Int, item: Title) {
        binding.tvTitle.text = item.title
        binding.tvTitle.setOnClickListener {
            val title = (it as TextView).text
            val context = binding.root.context
            when(title) {
                context.getString(R.string.sub_item_title_exo_single_video) -> {
                    context.startActivity(Intent(context, SingleVideoActivity::class.java))
                }
                context.getString(R.string.sub_item_title_exo_video_list) -> {
                    context.startActivity(Intent(context, VideoListActivity::class.java))
                }
            }
        }
    }
}