package com.mdlicht.zb.exampleproject.main.viewholder

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.baserecyclerview.viewholder.BaseViewHolder
import com.mdlicht.zb.exampleproject.databinding.ItemMainBinding
import com.mdlicht.zb.exampleproject.main.model.Title
import com.mdlicht.zb.exampleproject.main.viewmodel.ItemMainViewModel

class MainViewHolder private constructor(itemView: View) : BaseViewHolder<Title>(itemView) {
    val binding: ItemMainBinding = DataBindingUtil.bind(itemView)!!

    companion object {
        fun newInstance(parent: ViewGroup): MainViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
            return MainViewHolder(view)
        }
    }

    override fun onBindView(position: Int, item: Title) {
        val pos = position + 1
        val prefix = if(pos < 10) "0$pos" else "$pos"
        binding.title = "$prefix. ${item.title}"
        binding.vm = ItemMainViewModel()
    }
}