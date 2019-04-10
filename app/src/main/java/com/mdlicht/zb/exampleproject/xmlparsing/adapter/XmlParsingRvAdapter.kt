package com.mdlicht.zb.exampleproject.xmlparsing.adapter

import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.baserecyclerview.adapter.BaseRecyclerAdapter
import com.mdlicht.zb.exampleproject.baserecyclerview.viewholder.BaseViewHolder
import com.mdlicht.zb.exampleproject.xmlparsing.viewholder.XmlParsingViewHolder

class XmlParsingRvAdapter<ITEM>: BaseRecyclerAdapter<ITEM>() {
    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ITEM> {
        return XmlParsingViewHolder.newInstance(parent) as BaseViewHolder<ITEM>
    }
}