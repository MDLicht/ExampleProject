package com.mdlicht.zb.exampleproject.main.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.baserecyclerview.adapter.BaseRecyclerAdapter
import com.mdlicht.zb.exampleproject.baserecyclerview.viewholder.BaseViewHolder
import com.mdlicht.zb.exampleproject.main.model.Title
import com.mdlicht.zb.exampleproject.main.viewholder.MainViewHolder
import com.mdlicht.zb.exampleproject.main.viewmodel.ItemMainViewModel

class MainRecyclerAdapter<ITEM>(val context: Context) : BaseRecyclerAdapter<ITEM>() {
    init {
        val dataSet = context.resources.getStringArray(R.array.main_recycler_items).toList().map {
            Title(it)
        }
        setDataSet(dataSet)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ITEM> =
        MainViewHolder.newInstance(parent) as BaseViewHolder<ITEM>
}