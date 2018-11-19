package com.mdlicht.zb.exampleproject.main.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ItemMainBinding
import com.mdlicht.zb.exampleproject.main.viewmodel.ItemMainViewModel

class MainRecyclerAdapter(val context: Context) : RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {
    private val dataSet: List<String> = context.resources.getStringArray(R.array.main_recycler_items).toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item_main, parent, false))

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.title = dataSet[position]
        holder.binding.vm = ItemMainViewModel()
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemMainBinding = DataBindingUtil.bind(itemView)!!
    }
}