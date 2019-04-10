package com.mdlicht.zb.exampleproject.baserecyclerview.adapter

import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.baserecyclerview.etc.Constants
import com.mdlicht.zb.exampleproject.baserecyclerview.etc.SampleUtil
import com.mdlicht.zb.exampleproject.baserecyclerview.viewholder.BaseViewHolder
import com.mdlicht.zb.exampleproject.baserecyclerview.viewholder.TypeAViewHolder
import com.mdlicht.zb.exampleproject.baserecyclerview.viewholder.TypeBViewHolder

class RecyclerViewAdapter<ITEM>: BaseRecyclerAdapter<ITEM>() {
    init {
        setDataSet(SampleUtil.getSampleDataSet())
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ITEM> {
        return when (viewType) {
            Constants.VIEW_TYPE_A -> TypeAViewHolder.newInstance(parent) as BaseViewHolder<ITEM>
            Constants.VIEW_TYPE_B -> TypeBViewHolder.newInstance(parent) as BaseViewHolder<ITEM>
            else -> throw Exception()
        }
    }
}