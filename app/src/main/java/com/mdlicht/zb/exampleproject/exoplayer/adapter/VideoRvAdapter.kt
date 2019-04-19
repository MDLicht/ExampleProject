package com.mdlicht.zb.exampleproject.exoplayer.adapter

import android.content.Context
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.baserecyclerview.adapter.BaseRecyclerAdapter
import com.mdlicht.zb.exampleproject.baserecyclerview.viewholder.BaseViewHolder
import com.mdlicht.zb.exampleproject.exoplayer.model.Repository
import com.mdlicht.zb.exampleproject.exoplayer.viewholder.VideoViewHolder

class VideoRvAdapter<ITEM>(): BaseRecyclerAdapter<ITEM>() {
    init {
        val dataSet = Repository.getVideoList()
        setDataSet(dataSet)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ITEM> {
        return VideoViewHolder.newInstance(parent) as BaseViewHolder<ITEM>
    }
}