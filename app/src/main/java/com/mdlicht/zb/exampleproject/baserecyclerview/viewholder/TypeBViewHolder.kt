package com.mdlicht.zb.exampleproject.baserecyclerview.viewholder

import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.baserecyclerview.model.Bmodel
import com.mdlicht.zb.exampleproject.baserecyclerview.viewmodel.ItemViewModel
import com.mdlicht.zb.exampleproject.databinding.ItemBaseRecyclerTypeBBinding

class TypeBViewHolder private constructor(itemView: View): BaseViewHolder<Bmodel>(itemView) {
    var binding: ItemBaseRecyclerTypeBBinding = DataBindingUtil.bind(itemView)!!

    companion object {
        fun newInstance(parent: ViewGroup): TypeBViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_base_recycler_type_b, parent, false)
            return TypeBViewHolder(view)
        }
    }

    override fun onBindView(position: Int, item: Bmodel) {
        binding.bmodel = item
        binding.vm = ItemViewModel()
    }
}