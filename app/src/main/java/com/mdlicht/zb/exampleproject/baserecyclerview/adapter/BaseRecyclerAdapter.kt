package com.mdlicht.zb.exampleproject.baserecyclerview.adapter

import androidx.recyclerview.widget.RecyclerView
import com.mdlicht.zb.exampleproject.baserecyclerview.model.BaseModel
import com.mdlicht.zb.exampleproject.baserecyclerview.viewholder.BaseViewHolder

abstract class BaseRecyclerAdapter<ITEM>: RecyclerView.Adapter<BaseViewHolder<ITEM>>() {
    private val dataSet: MutableList<BaseModel> = mutableListOf()

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: BaseViewHolder<ITEM>, position: Int) {
        holder.onBindView(position, dataSet[position] as ITEM)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun getItemViewType(position: Int): Int = dataSet[position].getViewType()

    fun setDataSet(dataSet: List<BaseModel>) {
        this.dataSet.clear()
        this.dataSet.addAll(dataSet)
        notifyDataSetChanged()
    }
}