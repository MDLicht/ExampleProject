package com.mdlicht.zb.exampleproject.exoplayer.adapter

import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.baserecyclerview.adapter.BaseRecyclerAdapter
import com.mdlicht.zb.exampleproject.baserecyclerview.viewholder.BaseViewHolder
import com.mdlicht.zb.exampleproject.exoplayer.viewholder.VideoViewHolder

class VideoRecyclerAdapter<ITEM>: BaseRecyclerAdapter<ITEM>() {
    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ITEM> {
        return VideoViewHolder.newInstance(parent) as BaseViewHolder<ITEM>
    }
}