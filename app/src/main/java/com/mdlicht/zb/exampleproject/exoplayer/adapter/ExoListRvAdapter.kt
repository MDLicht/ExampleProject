package com.mdlicht.zb.exampleproject.exoplayer.adapter

import android.content.Context
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.baserecyclerview.adapter.BaseRecyclerAdapter
import com.mdlicht.zb.exampleproject.baserecyclerview.viewholder.BaseViewHolder
import com.mdlicht.zb.exampleproject.exoplayer.viewholder.ExoListViewHolder
import com.mdlicht.zb.exampleproject.main.model.Title

class ExoListRvAdapter<ITEM>(context: Context): BaseRecyclerAdapter<ITEM>() {
    init {
        val dataSet = context.resources.getStringArray(R.array.exo_recycler_items).toList().map {
            Title(it)
        }
        setDataSet(dataSet)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ITEM> {
        return ExoListViewHolder.newInstance(parent) as BaseViewHolder<ITEM>
    }
}