package com.mdlicht.zb.exampleproject.baserecyclerview.viewholder

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.baserecyclerview.model.Amodel
import com.mdlicht.zb.exampleproject.baserecyclerview.viewmodel.ItemViewModel
import com.mdlicht.zb.exampleproject.databinding.ItemBaseRecyclerTypeABinding

class TypeAViewHolder private constructor(itemView: View): BaseViewHolder<Amodel>(itemView) {
    var binding: ItemBaseRecyclerTypeABinding = DataBindingUtil.bind(itemView)!!

    companion object {
        fun newInstance(parent: ViewGroup): TypeAViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_base_recycler_type_a, parent, false)
            return TypeAViewHolder(view)
        }
    }

    override fun onBindView(position: Int, item: Amodel) {
        binding.amodel = item
        binding.vm = ItemViewModel()
    }
}