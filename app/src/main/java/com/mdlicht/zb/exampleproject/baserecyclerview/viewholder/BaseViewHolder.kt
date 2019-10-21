package com.mdlicht.zb.exampleproject.baserecyclerview.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<ITEM>(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun onBindView(position: Int, item: ITEM)
}