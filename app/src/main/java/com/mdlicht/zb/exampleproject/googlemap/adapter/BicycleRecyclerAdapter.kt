package com.mdlicht.zb.exampleproject.googlemap.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ItemBicycleListBinding
import com.mdlicht.zb.exampleproject.googlemap.model.Row

class BicycleRecyclerAdapter: RecyclerView.Adapter<BicycleRecyclerAdapter.BicycleViewHolder>() {
    private var dataSet: List<Row>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BicycleViewHolder {
        return BicycleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_bicycle_list, parent, false))
    }

    override fun getItemCount(): Int {
        return dataSet?.size ?: 0
    }

    override fun onBindViewHolder(holder: BicycleViewHolder, position: Int) {
        dataSet?.let {
            holder.binding.item = it[position]
        }
    }

    fun setDataSet(dataSet: List<Row>?) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    class BicycleViewHolder(itemVIew: View): RecyclerView.ViewHolder(itemVIew) {
        val binding = DataBindingUtil.bind<ItemBicycleListBinding>(itemVIew)!!
    }
}