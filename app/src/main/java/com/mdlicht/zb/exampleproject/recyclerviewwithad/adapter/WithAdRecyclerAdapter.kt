package com.mdlicht.zb.exampleproject.recyclerviewwithad.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ItemWithAdAdBinding
import com.mdlicht.zb.exampleproject.databinding.ItemWithAdContentsBinding
import java.lang.Exception

class WithAdRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val INTERVAL: Int = 6
    val dataSet = mutableListOf<String>(
        "Item1", "Item2", "Item3", "Item4", "Item5", "Item6", "Item7", "Item8", "Item9", "Item10",
        "Item11", "Item12", "Item13", "Item14", "Item15", "Item16", "Item17", "Item18", "Item19", "Item20"
    )

    enum class ViewType(val type: Int) {
        TYPE_CONTENTS(0),
        TYPE_AD(1)
    }

    override fun getItemViewType(position: Int): Int {
        if((position + 1) % INTERVAL != 0)
            return ViewType.TYPE_CONTENTS.type
        return ViewType.TYPE_AD.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ViewType.TYPE_CONTENTS.type -> {
                ContentsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_with_ad_contents, parent, false))
            }
            ViewType.TYPE_AD.type -> {
                AdViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_with_ad_ad, parent, false))
            }
            else -> {
                throw Exception()
            }
        }
    }

    override fun getItemCount(): Int {
        return if(dataSet.size > 0) dataSet.size + Math.round((dataSet.size / INTERVAL).toFloat()) else dataSet.size
    }

    fun getItemPosition(position: Int): Int {
        return position - Math.round((position / INTERVAL).toFloat())
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ContentsViewHolder) {
            holder.binding.text = dataSet[getItemPosition(position)]
        } else if(holder is AdViewHolder) {

        }
    }

    class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemWithAdAdBinding = DataBindingUtil.bind(itemView)!!
    }

    class ContentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemWithAdContentsBinding = DataBindingUtil.bind(itemView)!!
    }
}